package com.iss.UI;

import com.iss.domain.DonareStatus;
import com.iss.domain.Spital;
import com.iss.service.DonareProxy;
import com.iss.service.ProxyFactory;
import com.iss.service.SpitalProxy;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DetailDonation {
    public AnchorPane pane;
    public Label titleLabel;
    public AnchorPane detailPane;
    public AnchorPane centrePane;
    public AnchorPane stepOnePane;
    public AnchorPane stepTwoPane;
    public AnchorPane stepThreePane;
    public Label stepOneLabel;
    public Label stepTwoLabel;
    public Label stepThreeLabel;
    public Label completeLabel;
    public AnchorPane namePane;
    public AnchorPane oraPane;
    public JFXComboBox<Spital> centreComboBox;
    public JFXComboBox<String> oraComboBox;
    public JFXTextField nameTextField;
    public JFXButton donateButton;
    public JFXDatePicker datePicker;
    public AnchorPane stepFourPane;
    public Label stepFourLabel;
    public AnchorPane datePane;

    private ProxyFactory factory;
    private Stage stage;
    private Runnable goBack;

    public void init(Stage stage, ProxyFactory factory){

        this.stage = stage;
        this.factory = factory;

        for (Spital spital : factory.get(SpitalProxy.class).getAll()){
            this.centreComboBox.getItems().add(spital);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 30);
        datePicker.setValue(LocalDate.now());

        while(cal.get(Calendar.HOUR_OF_DAY) != 13 || cal.get(Calendar.MINUTE) != 30){
            oraComboBox.getItems().add(cal.get(Calendar.HOUR_OF_DAY) +":"+ String.format("%02d", cal.get(Calendar.MINUTE)));
            cal.add(Calendar.MINUTE, 30);
        }
    }

    public void hideOrShow(boolean bool){
        detailPane.setVisible(bool);
        centrePane.setVisible(bool);
        stepOnePane.setVisible(bool);
        stepTwoPane.setVisible(bool);
        stepThreePane.setVisible(bool);
        namePane.setVisible(bool);
        oraPane.setVisible(bool);
        centrePane.setVisible(bool);
        datePane.setVisible(bool);
        stepFourLabel.setVisible(bool);
        stepOneLabel.setVisible(bool);
        stepTwoLabel.setVisible(bool);
        stepThreeLabel.setVisible(bool);
        stepFourLabel.setVisible(bool);
        centreComboBox.setVisible(bool);
        oraComboBox.setVisible(bool);
        nameTextField.setVisible(bool);
        datePane.setVisible(bool);
        datePicker.setVisible(bool);
        donateButton.setVisible(bool);
    }

    public void handleDonate(){
        if (datePicker.getValue().compareTo(LocalDate.now()) <= 0){
            new Alert(Alert.AlertType.WARNING, "Data trebuie sa fie valida", ButtonType.CLOSE).showAndWait();
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.YEAR, datePicker.getValue().getYear());
        cal.set(Calendar.MONTH, datePicker.getValue().getMonthValue());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getValue().getDayOfMonth());
        String[] strings = oraComboBox.getSelectionModel().getSelectedItem().split(":");
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strings[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(strings[1]));
        cal.set(Calendar.SECOND, 0);
        factory.get(DonareProxy.class).add(centreComboBox.getSelectionModel().getSelectedItem(),
                cal.getTime(), nameTextField.getText(), DonareStatus.PROGRAMAT);
        goBack.run();

    }

    public void setGoBack(Runnable goBack) {
        this.goBack = goBack;
    }
}
