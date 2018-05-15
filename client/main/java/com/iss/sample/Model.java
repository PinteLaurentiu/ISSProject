package com.iss.sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.iss.view.Login;
import com.iss.view.Register;

import java.io.IOException;

public class Model {
    private Scene sceneLogin, sceneRegister;
    private Stage stage;
    private Parent rootLogin;
    private Parent rootRegister;

    Model(Stage stage) throws IOException {
        this.stage = stage;

        createLogin();
        createRegister();

        stage.setTitle("Blood Donation");
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
    }

    private void createLogin() throws IOException {
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        rootLogin = loaderLogin.load();
        sceneLogin = new Scene(rootLogin, 790, 500);
        Login login = loaderLogin.getController();
        login.init(this);
    }

    private void createRegister() throws IOException {
        FXMLLoader loaderRegister = new FXMLLoader(getClass().getResource("/register.fxml"));
        rootRegister = loaderRegister.load();
        sceneRegister = new Scene(rootRegister, 790, 500);
        Register register = loaderRegister.getController();
        register.init(this);
    }

    private void animation(Parent root, Scene scene){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(300));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished((ActionEvent event)-> stage.setScene(scene));
        fadeTransition.play();
    }

    public void showLogin() throws IOException {
        createLogin();
        animation(rootRegister, sceneLogin);
    }

    public void showRegister() throws IOException {
        createRegister();
        animation(rootLogin, sceneRegister);
    }
}
