package com.iss.service;

import com.iss.domain.Activation;
import com.iss.domain.Role;
import com.iss.domain.User;
import com.iss.domain.UserRole;
import com.iss.persistance.RepositoryFactory;
import com.iss.persistance.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserService implements ICrudService<User,Integer> {

    private final RepositoryFactory factory;
    private final ServiceFactory parent;

    public UserService(RepositoryFactory factory, ServiceFactory parent) {
        this.factory = factory;
        this.parent = parent;
    }

    @Override
    public void add(Object... objects) {
        User user = new User();

        user.setNume((String) objects[0]);

        user.setPrenume((String) objects[1]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
        LocalDate localDate = LocalDate.parse((String)objects[2], formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        user.setBirthday(date);

        user.setDomiciliu((String)objects[3]);

        user.setJudet((String)objects[4]);

        user.setLocalitate((String)objects[5]);

        user.setResedinta((String)objects[6]);

        user.setJudet2((String)objects[7]);

        user.setLocalitate2((String)objects[8]);

        user.setEmail((String)objects[9]);

        user.setTelefon((String)objects[10]);

        user.setPassword((String)objects[11]);

        user.setRoles(new HashSet<>());

        user.setDonari(new HashSet<>());

        Activation activation = new Activation();
        activation.setGeneratedId(UUID.randomUUID().toString());
        activation.setActivated(objects.length == 13);
        user.setActivation(activation);
        activation.setUser(user);

        factory.get(UserRepository.class).put(user);
        if (objects.length == 12)
            parent.getService(MailService.class).sendActivationMail(user.getEmail(), user.getId(), activation.getGeneratedId());

    }

    @Override
    public void remove(Integer key) {
        factory.get(UserRepository.class).remove(key);
    }

    @Override
    public void edit(User self) {
        for (UserRole role : self.getRoles()){
            role.setUser(self);
        }
        self.setActivation(factory.get(UserRepository.class).getByEmail(self.getEmail()).getActivation());
        factory.get(UserRepository.class).put(self);
    }

    @Override
    public Iterable<User> getAll() {
        return factory.get(UserRepository.class).getAll();
    }

    @Override
    public Iterable<User> getAll(int count, int offset) {
        return factory.get(UserRepository.class).getAll(count, offset);
    }

    @Override
    public int count() {
        return factory.get(UserRepository.class).count();
    }

    public boolean login(String user, String pass){
        return factory.get(UserRepository.class).login(user, pass);
    }

    public void activation(int id, String uid) {
        factory.get(UserRepository.class).activation(id, uid);
    }

    public Iterable<Role> getRoles(String email) {
        User user = factory.get(UserRepository.class).getByEmail(email);
        List<Role> roles = new ArrayList<>();
        for (UserRole userRole : user.getRoles()) {
            roles.add(userRole.getRole());
        }
        return roles;
    }

    public User getByEmail(String email){
        return factory.get(UserRepository.class).getByEmail(email);
    }

    public boolean isActivated(String email) {
        User user = getByEmail(email);
        return user.getActivation().isActivated();
    }
}
