package com.iss.service;

import com.iss.domain.*;
import com.iss.enums.CerereStatus;
import com.iss.enums.TipComponenteSange;
import com.iss.enums.GradDeUrgenta;
import com.iss.enums.GrupaSange;
import com.iss.persistance.CerereRepository;
import com.iss.persistance.ComponentaSangeRepository;
import com.iss.persistance.RepositoryFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class CerereService implements ICrudService<Cerere,Integer> {
    private final RepositoryFactory factory;
    private final ServiceFactory parent;

    public CerereService(RepositoryFactory factory, ServiceFactory parent) {
        this.factory = factory;
        this.parent = parent;
    }

    @Override
    public void add(Object... objects) {
        Cerere cerere = new Cerere();
        cerere.setNumePacient(objects[0].toString());
        cerere.setPrenumePacient(objects[1].toString());
        cerere.setTipComponenteSange((TipComponenteSange) objects[2]);
        cerere.setGrupaSange((GrupaSange) objects[3]);
        cerere.setGradDeUrgenta((GradDeUrgenta) objects[4]);
        cerere.setCantitatea((Integer) objects[5]);
        cerere.setCantitateDonata(0);
        cerere.setLocatie(objects[6].toString());
        cerere.setCerereStatus(CerereStatus.Emisa);
        List<ComponentaSange> componentaSangeList = filterComponentaSange((List<ComponentaSange>)
                factory.get(ComponentaSangeRepository.class).getAll());
        for (ComponentaSange componentaSange : componentaSangeList) {
            if (componentaSange.getTipComponentaSange().equals(cerere.getTipComponenteSange()))
                updateSolve(cerere, componentaSange.getDonare());
        }
        factory.get(CerereRepository.class).put(cerere);
        trySolve();
    }

    @Override
    public void remove(Integer key) {
        factory.get(CerereRepository.class).remove(key);
    }

    @Override
    public void edit(Cerere self) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Cerere> getAll() {
        return factory.get(CerereRepository.class).getAll();
    }

    @Override
    public Iterable<Cerere> getAll(int count, int offset) {
        return factory.get(CerereRepository.class).getAll(count, offset);
    }

    @Override
    public int count() {
        return factory.get(CerereRepository.class).count();
    }

    public void updateSolve(Cerere cerere, Donare donare) {
        if ((cerere.getNumePacient() + " " + cerere.getPrenumePacient()).equals(donare.getPentru())) {
            cerere.setCantitateDonata(cerere.getCantitateDonata()+1);
        }
    }

    public void trySolve() {
        List<Cerere> cerereList = filterCereri((List<Cerere>) factory.get(CerereRepository.class).getAll());
        cerereList.sort(Comparator.comparingInt(x -> x.getGradDeUrgenta().ordinal() -
                GradDeUrgenta.values().length * (x.getCantitateDonata() == x.getCantitatea() ? 1:0)));

        List<ComponentaSange> componentaSangeList = filterComponentaSange((List<ComponentaSange>)
                    factory.get(ComponentaSangeRepository.class).getAll());

        List<ComponentaSange> used = new ArrayList<>();

        for (Cerere cerere : cerereList) {

            List<ComponentaSange> componentaSanges = new ArrayList<>();
            for (ComponentaSange componentaSange : componentaSangeList) {
                boolean usedThis = false;
                for (CompabilitateMajora compabilitateMajora : componentaSange.getProbe())
                    if (compabilitateMajora.getCerere().getIdCerere() == cerere.getIdCerere())
                        usedThis = true;
                if (cerere.getGrupaSange().equals(componentaSange.getDonare().getAnaliza().getGrupaSange()) &&
                        cerere.getTipComponenteSange().equals(componentaSange.getTipComponentaSange()) &&
                        !used.contains(componentaSange) && !usedThis) {
                    componentaSanges.add(componentaSange);
                    if (componentaSanges.size() == cerere.getCantitatea())
                        break;
                }
            }
            if (componentaSanges.size() == cerere.getCantitatea()){
                solve(componentaSanges, cerere);
                used.addAll(componentaSanges);
            }
        }
    }

    private void solve(List<ComponentaSange> componentaSanges, Cerere cerere) {
        for (ComponentaSange componentaSange : componentaSanges){
            CompabilitateMajora compabilitateMajora = new CompabilitateMajora();
            compabilitateMajora.setCerere(cerere);
            compabilitateMajora.setComponentaSange(componentaSange);
            if (componentaSange.getProbe() == null)
                componentaSange.setProbe(new HashSet<>());
            if (cerere.getProbe() == null)
                cerere.setProbe(new HashSet<>());
            componentaSange.getProbe().add(compabilitateMajora);
            cerere.getProbe().add(compabilitateMajora);
            compabilitateMajora.setAcceptat(true);
        }
        cerere.setCerereStatus(CerereStatus.Transfer);
        factory.get(CerereRepository.class).put(cerere);
    }

    public List<Cerere> filterCereri(List<Cerere> cereres){
        List<Cerere> cerere2 = new ArrayList<>();
        for (Cerere cerere : cereres){
            if (cerere.getCerereStatus().equals(CerereStatus.Emisa) || cerere.getCerereStatus().equals(CerereStatus.Reemisa)){
                cerere2.add(cerere);
            }
        }
        return cerere2;
    }

    private List<ComponentaSange> filterComponentaSange(List<ComponentaSange> componentaSanges){
        List<ComponentaSange> componentaSanges2 = new ArrayList<>();
        for (ComponentaSange componentaSange : componentaSanges){
            boolean used = false;
            for (CompabilitateMajora compabilitateMajora : componentaSange.getProbe())
                if (compabilitateMajora.isAcceptat())
                    used = true;
            if (componentaSange.getDataExpirare().compareTo(new Date()) >= 0 && !used)
                componentaSanges2.add(componentaSange);
        }
        return componentaSanges2;
    }

    public Cerere getById(Integer idCerere) {
        return factory.get(CerereRepository.class).getById(idCerere);
    }

    public void reemit(Cerere cerere, List<Integer> ids) {
        boolean doReemit = false;
        for (CompabilitateMajora compabilitateMajora : cerere.getProbe()) {
            compabilitateMajora.setAcceptat(false);
            if (ids.contains(compabilitateMajora.getComponentaSange().getIdComponenta()))
                compabilitateMajora.setAcceptat(true);
            if (!compabilitateMajora.isAcceptat())
                doReemit = true;
        }
        if (doReemit) {
            cerere.setCerereStatus(CerereStatus.Reemisa);
        }
        else {
            cerere.setCerereStatus(CerereStatus.Rezolvata);
        }
        factory.get(CerereRepository.class).put(cerere);
        trySolve();
    }
}
