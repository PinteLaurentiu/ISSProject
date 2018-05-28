package com.iss.UI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    public JFXComboBox<String> centreComboBox;
    public JFXComboBox<String> oraComboBox;
    public JFXTextField nameTextField;
    public JFXButton donateButton;


    @FXML
    public void initialize(){
        //TODO INIT COMBOBOX PENTRU CENTRE;

        oraComboBox.setItems(FXCollections.observableArrayList("7:00","7:30","8:00","8:30","9:00","9:30","10:00",
                "10:30","11:00","11:30","12:00","12:30"));
    }
    public void setTitleLabel(String titleLabel) {
        this.titleLabel.setText(titleLabel);
    }

    public void hideOrShow(boolean bool){
        detailPane.setVisible(bool);
        centrePane.setVisible(bool);
        stepOnePane.setVisible(bool);
        stepTwoPane.setVisible(bool);
        stepThreePane.setVisible(bool);
        stepOneLabel.setVisible(bool);
        stepTwoLabel.setVisible(bool);
        stepThreeLabel.setVisible(bool);
        centreComboBox.setVisible(bool);
        oraComboBox.setVisible(bool);
        nameTextField.setVisible(bool);
        donateButton.setVisible(bool);
    }

    public void handleDonate(){
        //TODO CE SE INTAMPLA DUPA CE SE INSCRIE LA DONARE
    }

}
