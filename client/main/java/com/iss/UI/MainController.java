package com.iss.UI;

import com.iss.domain.Role;
import com.iss.domain.User;
import com.iss.domain.UserRole;
import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainController {
    public Button addUser;
    public Button deleteUser;
    public Button modifyUser;
    public TableView<User> usersTable;

    public Tab donorTab;
    public Tab doctorCenterTab;
    public Tab doctorLabTab;
    public Tab hospitalDoctorTab;
    public Tab usersTab;
    public TabPane tabPane;
    public Pagination paginationUsers;

    private Stage stage;
    private ProxyFactory factory;

    private static final int RECORDS_ON_PAGE = 8;

    public static void show(Stage stage, ProxyFactory factory ) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/mainAdministrator.fxml"));
        Pane load = loader.load();
        Scene scene = new Scene(load, load.getPrefWidth() , load.getPrefHeight());
        MainController main = loader.getController();
        main.init(stage, factory);
        Animations.animate(scene, stage);
        stage.show();
    }

    private void init(Stage stage, ProxyFactory factory) {
        this.stage = stage;
        this.factory = factory;
        List<Role> roles = (List<Role>) factory.get(UserProxy.class).getRoles();
        if (!roles.contains(Role.DoctorDonare))
            tabPane.getTabs().remove(doctorCenterTab);

        if (!roles.contains(Role.DoctorLab))
            tabPane.getTabs().remove(doctorLabTab);

        if (!roles.contains(Role.DoctorSpital))
            tabPane.getTabs().remove(hospitalDoctorTab);

        if (!roles.contains(Role.UsersEditor))
            tabPane.getTabs().remove(usersTab);
        else{
            paginationUsers.currentPageIndexProperty().addListener((x,y,z)->updateUsers());
            updateUsers();
            //noinspection unchecked
            usersTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getNume()));
            //noinspection unchecked
            usersTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getPrenume()));
            //noinspection unchecked
            usersTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getTelefon()));
            //noinspection unchecked
            usersTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getEmail()));
            //noinspection unchecked
            usersTable.getColumns().get(4).setCellValueFactory(x->new ReadOnlyObjectWrapper(rolesAsString(x.getValue())));


        }

        tabPane.getSelectionModel().selectedItemProperty().addListener(this::changedTab);
    }

    private void updateUsers() {
        usersTable.getItems().clear();
        for (User user : factory.get(UserProxy.class).getAll(RECORDS_ON_PAGE,paginationUsers.getCurrentPageIndex()))
            usersTable.getItems().add(user);
        paginationUsers.setPageCount((int)Math.ceil(factory.get(UserProxy.class).count() / (float)RECORDS_ON_PAGE));
    }

    private static String rolesAsString(User value) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (UserRole role : value.getRoles()) {
            if (index != 0)
                builder.append(", ");
            builder.append(role.getRole().toString());
            index++;
        }
        return builder.toString();
    }

    public void changedTab(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        if (newValue.equals(usersTab)){
        }

    }

    public void modifyPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(this.stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        EditRoles.show(stage, factory, usersTable.getSelectionModel().getSelectedItem());
        updateUsers();
    }

    public void deletePressed(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        factory.get(UserProxy.class).remove(user.getId());
        updateUsers();
    }
}