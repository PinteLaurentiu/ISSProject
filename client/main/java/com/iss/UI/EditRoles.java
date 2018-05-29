package com.iss.UI;

import com.iss.enums.Role;
import com.iss.domain.User;
import com.iss.domain.UserRole;
import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;

public class EditRoles {
    public ListView<Role> leftList;
    public ListView<Role> rightList;
    public Button left;
    public Button right;
    public Button submit;
    private Stage stage;
    private User user;
    private ProxyFactory factory;

    public static void show(Stage stage, ProxyFactory factory, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/editRoles.fxml"));
        Pane load = loader.load();
        Scene scene = new Scene(load, load.getPrefWidth() , load.getPrefHeight());
        EditRoles editRoles = loader.getController();
        editRoles.init(stage, factory, user);
        Animations.animate(scene, stage);
        stage.showAndWait();
    }

    private void init(Stage stage, ProxyFactory factory, User user) {
        this.stage = stage;
        this.factory = factory;
        this.user = user;
        updateLists();
        leftList.getSelectionModel().selectedItemProperty().addListener((x,y,z)->selectionChanged());
        rightList.getSelectionModel().selectedItemProperty().addListener((x,y,z)->selectionChanged());
        selectionChanged();
    }

    private void selectionChanged() {
        right.setDisable(leftList.getSelectionModel().getSelectedItems().size() == 0);
        left.setDisable(rightList.getSelectionModel().getSelectedItems().size() == 0);
    }

    private void updateLists() {
        for (UserRole role : user.getRoles())
            rightList.getItems().add(role.getRole());
        for (Role role : Role.values())
            if (!rightList.getItems().contains(role))
                leftList.getItems().add(role);
    }

    public void leftPressed(@SuppressWarnings("unused") ActionEvent event) {
        Role role = rightList.getSelectionModel().getSelectedItem();
        rightList.getItems().remove(role);
        leftList.getItems().add(role);
        selectionChanged();
    }

    public void rightPressed(@SuppressWarnings("unused") ActionEvent event) {
        Role role = leftList.getSelectionModel().getSelectedItem();
        leftList.getItems().remove(role);
        rightList.getItems().add(role);
        selectionChanged();
    }

    public void submit(@SuppressWarnings("unused") ActionEvent event) {
        user.setRoles(new HashSet<>());
        for (Role role : rightList.getItems()) {
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            user.getRoles().add(userRole);
        }
        factory.get(UserProxy.class).edit(user);
        stage.close();
    }
}
