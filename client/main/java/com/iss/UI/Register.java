package com.iss.UI;

import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Register {

    public TextField numeText;
    public TextField emailText;
    public TextField usernameText;
    public Spinner varstaValue;
    public PasswordField passwordText1;
    public PasswordField passworText2;
    public TextField domiciliuText;
    public TextField prenumeText;
    public DatePicker birthdayText;
    public TextField judet1Text;
    public TextField localitate1Text;
    public TextField judet2Text;
    public TextField resedintaText;
    public TextField localitate2Text;
    public TextField telefonText;

    private Stage stage;
    private ProxyFactory factory;

    public void init(Stage stage, ProxyFactory factory){
        this.stage = stage;
        this.factory = factory;

    }

    public void register(ActionEvent actionEvent) throws IOException {

        if (!numeText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Numele nu este valid");
            return;
        }

        if (!prenumeText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Prenumele nu este valid");
            return;
        }

        if (birthdayText.getValue() == null){
            new AlertBox("Inregistrare esuata","Ziua de nastere este necompletata");
            return;
        }

        LocalDate date = LocalDate.now();
        long years = java.time.temporal.ChronoUnit.YEARS.between(birthdayText.getValue(), date );
        if (years < 18) {
            new AlertBox("Inregistrare esuata","Trebuie sa ai minim 18 ani pentru a dona!");
            return;
        }

        if (!domiciliuText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Adresa de domiciliu nu este valida");
            return;
        }

        if (!localitate1Text.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Adresa de domiciliu nu este valida");
            return;
        }

        if (!judet1Text.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Adresa de domiciliu nu este valida");
            return;
        }

        if (resedintaText.getText().trim().isEmpty()
                && localitate2Text.getText().trim().isEmpty()
                && judet2Text.getText().trim().isEmpty()) {}
        else if ((!resedintaText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")) ||
                (!localitate2Text.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")) ||
                (!judet2Text.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*"))) {
            new AlertBox("Inregistrare esuata", "Adresa de resedinta nu este valida");
            return;
        }

        if (!emailText.getText().trim().matches(".+@.+")) {
            new AlertBox("Inregistrare esuata", "Email invalid!");
            return;
        }

        if (!telefonText.getText().trim().matches("[0-9]{10}")){
            new AlertBox("Inregistrare esuata", "Numar de telefon invalid!");
            return;
        }

        if (!passwordText1.getText().matches("[a-zA-Z0-9_]+") || passwordText1.getText().length() < 6){
            new AlertBox("Inregistrare esuata", "Parola trebuie sa aiba cel putin 6 caractere alfa-numerice!");
            return;
        }

        if(!passwordText1.getText().equals(passworText2.getText())){
            new AlertBox("Inregistrare esuata", "Parola trebuie sa fie la fel!");
            return;
        }

        try {
            factory.get(UserProxy.class).add(numeText.getText(), prenumeText.getText(), birthdayText.getValue(),
                    domiciliuText.getText(), judet1Text.getText(), localitate1Text.getText(), resedintaText.getText(),
                    judet2Text.getText(), localitate2Text.getText(), emailText.getText(), telefonText.getText(),
                    passwordText1.getText());
            new AlertBox("Success", "Un email cu un link de activare a contului a fost \ntrimis pe adresa de email specificata!");
        } catch (Exception ex) {
            new AlertBox("Eroare la server","Something went wrong!");
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
