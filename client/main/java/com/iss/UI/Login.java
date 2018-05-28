package com.iss.UI;

import com.iss.exceptions.BadAuthenticationException;
import com.iss.exceptions.NotActivatedUserException;
import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

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

    private void init(Stage stage, ProxyFactory factory){
        this.stage = stage;
        this.factory = factory;
        usernameText.setText("");
        passwordText.setText("");
        passwordHiddenText.setText("");
        showPassword(null);
    }

    public void login(@SuppressWarnings("unused") ActionEvent actionEvent) {
        try {
            String password = showPasswordCheckBox.isSelected() ? passwordText.getText() : passwordHiddenText.getText();
            factory.get(UserProxy.class).login(usernameText.getText(), password);

//            MainController.show(stage, factory); //TODO : fut Iliuta!
        FormularInscriereDonare.show(stage, factory);

        } catch (NotActivatedUserException ex) {
            Optional<ButtonType> answer = new Alert(Alert.AlertType.NONE, "Userul nu este activ! Doriti sa " +
                    "retrimitem \nlinkul de activare pe emailul specificat?", ButtonType.YES, ButtonType.NO).
                    showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.YES) {
                factory.get(UserProxy.class).resendEmail(usernameText.getText());
                new Alert(Alert.AlertType.INFORMATION, "Un email cu un link de activare a contului \na fost trimis pe adresa de email specificata!", ButtonType.OK).showAndWait();
            }
        } catch (BadAuthenticationException e) {
            new AlertBox("Eroare la logare","Email sau password incorecte!");
        } catch (Exception e) {
            new AlertBox("Eroare la server","Something went wrong!");
        }
    }

    public void register(@SuppressWarnings("unused") ActionEvent actionEvent) throws IOException {
        Register.show(stage, factory, false);
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
