package com.iss.service;

import com.iss.domain.Cerere;
import com.iss.domain.CompabilitateMajora;
import com.iss.domain.ComponentaSange;
import com.iss.domain.PungaSange;
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
        cerere.setLocatie(objects[6].toString());
        cerere.setCerereStatus(CerereStatus.Emisa);
        factory.get(CerereRepository.class).put(cerere);
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
                if (cerere.getGrupaSange().equals(componentaSange.getDonare().getAnaliza().getGrupaSange()) &&
                        cerere.getTipComponenteSange().equals(componentaSange.getTipComponentaSange()) &&
                        !used.contains(componentaSange)) {
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
        }
        cerere.setCerereStatus(CerereStatus.Transfer);
        factory.get(CerereRepository.class).put(cerere);
    }

    private List<Cerere> filterCereri(List<Cerere> cereres){
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
//            for (CompabilitateMajora compabilitateMajora : )
            // TODO : be right back!
           if (componentaSange.getDataExpirare().compareTo(new Date()) >= 0)
               componentaSanges2.add(componentaSange);
        }
        return componentaSanges2;
    }

}
