package com.iss.view;

import com.iss.sample.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Register {

    public TextField numeText;
    public TextField emailText;
    public TextField usernameText;
    public Spinner varstaValue;
    public PasswordField passwordText1;
    public PasswordField passworText2;

    private Model model;

    public void init(Model model){
        this.model = model;

    }

    public void register(ActionEvent actionEvent) throws IOException {
        if(passwordText1.getText().equals(passworText2.getText())){
            new AlertBox("Register", "User added successfully");
            model.showLogin();
        }
        else{
            new AlertBox("Password", "Password must match");
        }
    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        model.showLogin();
    }
}
