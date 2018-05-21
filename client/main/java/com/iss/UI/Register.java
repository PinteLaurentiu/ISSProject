package com.iss.UI;

import com.iss.service.ProxyFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Register {

    public TextField numeText;
    public TextField emailText;
    public TextField usernameText;
    public Spinner varstaValue;
    public PasswordField passwordText1;
    public PasswordField passworText2;

    private Stage stage;
    private ProxyFactory factory;

    public void init(Stage stage, ProxyFactory factory){
        this.stage = stage;
        this.factory = factory;

    }

    public void register(ActionEvent actionEvent) throws IOException {
        if(passwordText1.getText().equals(passworText2.getText())){
            new AlertBox("Register", "User added successfully");
            Login.show(stage, factory);
        }
        else{
            new AlertBox("Password", "Password must match");
        }
    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        Login.show(stage, factory);
    }

    public static void show(Stage stage, ProxyFactory factory ) throws IOException {
        FXMLLoader loaderRegister = new FXMLLoader(Register.class.getResource("/register.fxml"));
        Pane rootRegister = loaderRegister.load();
        Scene sceneRegister = new Scene(rootRegister, rootRegister.getPrefWidth(), rootRegister.getPrefHeight());
        Register register = loaderRegister.getController();
        register.init(stage, factory);
//        stage.setScene(sceneRegister);
        Animations.animate(sceneRegister, stage);
        stage.show();
    }
}
