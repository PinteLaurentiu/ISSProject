package com.iss.UI;

import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Login {
    public TextField usernameText;
    public TextField passwordText;
    public CheckBox showPasswordCheckBox;
    public PasswordField passwordHiddenText;
    public CheckBox rememberMeCheckBox;

    private Stage stage;
    private ProxyFactory factory;

    public static void show(Stage stage, ProxyFactory factory ) throws IOException {
        FXMLLoader loaderLogin = new FXMLLoader(Login.class.getResource("/login.fxml"));
        Pane rootLogin = loaderLogin.load();
        Scene sceneLogin = new Scene(rootLogin, rootLogin.getPrefWidth() , rootLogin.getPrefHeight());
        Login login = loaderLogin.getController();
        login.init(stage, factory);
        Animations.animate(sceneLogin, stage);
        stage.show();
    }

    public void init(Stage stage, ProxyFactory factory){
        this.stage = stage;
        this.factory = factory;
        usernameText.setText("");
        passwordText.setText("");
        passwordHiddenText.setText("");

    }

    public void login(@SuppressWarnings("unused") ActionEvent actionEvent) {
        try{
            factory.get(UserProxy.class).login(usernameText.getText(), passwordText.getText());
        } catch (LoginException e) {
            new AlertBox("Eroare la logare","Email sau password gresite");
        } catch (Exception e) {
            new AlertBox("Eroare la server","Something went wrong!");
        }
    }

    public void register(@SuppressWarnings("unused") ActionEvent actionEvent) throws IOException {
        Register.show(stage, factory);
    }

    public void showPassword(@SuppressWarnings("unused") ActionEvent actionEvent) {
        if(showPasswordCheckBox.isSelected()){
            passwordText.setText(passwordHiddenText.getText());
            passwordText.setVisible(true);
            passwordHiddenText.setText("");
            passwordHiddenText.setVisible(false);
        }
        else {
            passwordHiddenText.setText(passwordText.getText());
            passwordHiddenText.setVisible(true);
            passwordText.setText("");
            passwordText.setVisible(false);
        }
    }
}
