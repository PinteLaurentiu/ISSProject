package com.iss.UI;

import com.iss.domain.DonareStatus;
import com.iss.domain.Spital;
import com.iss.service.ConsultProxy;
import com.iss.service.DonareProxy;
import com.iss.service.ProxyFactory;
import com.iss.service.SpitalProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class FormularInscriereDonare {
    public ComboBox<Spital> centruCombo;
    public ComboBox<String> oraCombo;
    public TextField pentruField;
    public Button adaugaBtn;
    public DatePicker datePicker;

    private Stage stage;
    private ProxyFactory factory;

    @SuppressWarnings("WeakerAccess")
    public static void show(Stage stage, ProxyFactory factory) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/formularInscriereDonare.fxml"));
        stage.setTitle("Formular donare : ");
        Pane load = loader.load();
        Scene scene = new Scene(load, load.getPrefWidth() , load.getPrefHeight());
        FormularInscriereDonare main = loader.getController();
        main.init(stage, factory);
        Animations.animate(scene, stage);
        stage.show();
    }

    private void init(Stage stage, ProxyFactory factory) {
        this.stage = stage;
        this.factory = factory;

        initializeCombos();

    }

    private void initializeCombos() {
        for (Spital spital : factory.get(SpitalProxy.class).getAll()){
            this.centruCombo.getItems().add(spital);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 30);
        datePicker.setValue(LocalDate.now());

        while(cal.get(Calendar.HOUR_OF_DAY) != 13 || cal.get(Calendar.MINUTE) != 30){
            oraCombo.getItems().add(cal.get(Calendar.HOUR_OF_DAY) +":"+ String.format("%02d", cal.get(Calendar.MINUTE)));
            cal.add(Calendar.MINUTE, 30);
        }

    }

    public void adaugaPressed(ActionEvent event) throws IOException {
        if (datePicker.getValue().compareTo(LocalDate.now()) <= 0){
            new Alert(Alert.AlertType.WARNING, "Data trebuie sa fie valida", ButtonType.CLOSE).showAndWait();
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.YEAR, datePicker.getValue().getYear());
        cal.set(Calendar.MONTH, datePicker.getValue().getMonthValue());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getValue().getDayOfMonth());
        String[] strings = oraCombo.getSelectionModel().getSelectedItem().split(":");
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strings[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(strings[1]));
        cal.set(Calendar.SECOND, 0);
        factory.get(DonareProxy.class).add(centruCombo.getSelectionModel().getSelectedItem(),
                cal.getTime(), pentruField.getText(), DonareStatus.PROGRAMAT);
        MainController.show(stage, factory);
    }
}
