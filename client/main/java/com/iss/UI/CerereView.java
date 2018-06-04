package com.iss.UI;

import com.iss.enums.TipComponenteSange;
import com.iss.enums.GradDeUrgenta;
import com.iss.enums.GrupaSange;
import com.iss.service.CerereProxy;
import com.iss.service.ProxyFactory;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CerereView {
    public TextField numeText;
    public TextField prenumeText;
    public TextField cantitateaText;
    public TextField varstaText;
    public JFXComboBox<GrupaSange> grupaCombo;
    public JFXComboBox<GradDeUrgenta> gradCombo;
    public JFXComboBox<TipComponenteSange> componentaCombo;
    public JFXTextField locatiaText;

    private Stage stage;
    private ProxyFactory factory;

    private void init(Stage stage, ProxyFactory factory){
        this.stage = stage;
        this.factory = factory;

        componentaCombo.setItems(FXCollections.observableArrayList(TipComponenteSange.values()));
        gradCombo.setItems(FXCollections.observableArrayList(GradDeUrgenta.values()));
        grupaCombo.setItems(FXCollections.observableArrayList(GrupaSange.values()));
    }

    public void register(ActionEvent actionEvent) {
        if (!numeText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Numele nu este valid");
            return;
        }

        if (!prenumeText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Prenumele nu este valid");
            return;
        }

        if (!locatiaText.getText().trim().matches(".*[a-zA-Z][a-zA-Z][a-zA-Z].*")){
            new AlertBox("Inregistrare esuata", "Prenumele nu este valid");
            return;
        }

        if(Integer.parseInt(cantitateaText.getText())<0){
            new AlertBox("Inregistrare esuata", "Cantitatea de sange trebuie sa fie pozitiva");
        }

        try {
            factory.get(CerereProxy.class).add(
                    numeText.getText(),
                    prenumeText.getText(),
                    componentaCombo.getValue(),
                    grupaCombo.getValue(),
                    gradCombo.getValue(),
                    cantitateaText.getText(),
                    locatiaText.getText());

            new AlertBox("Success", "Cererea a fost inregistrata");
        } catch (Exception ex) {
            new AlertBox("Eroare la server","Something went wrong!");
        }
    }

    public void cancelCerere(ActionEvent actionEvent) throws IOException {
        MainView.show(stage, factory,4);
    }

    public static void show(Stage stage, ProxyFactory factory) throws IOException {
        FXMLLoader loaderCerere = new FXMLLoader(CerereView.class.getResource("/cerereView.fxml"));
        Pane rootCerere = loaderCerere.load();
        Scene sceneCerere = new Scene(rootCerere, rootCerere.getPrefWidth(), rootCerere.getPrefHeight());
        CerereView cerere = loaderCerere.getController();
        cerere.init(stage, factory);
        Animations.animate(sceneCerere, stage);
        stage.show();
    }
}
