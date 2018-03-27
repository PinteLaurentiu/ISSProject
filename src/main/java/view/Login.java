package view;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Model;

import java.io.IOException;

public class Login {
    public TextField usernameText;
    public TextField passwordText;
    public CheckBox showPasswordCheckBox;
    public PasswordField passwordHiddenText;
    public CheckBox rememberMeCheckBox;

    private Model model;

    public void init(Model model){
        this.model = model;

        usernameText.setText("");
        passwordText.setText("");
        passwordHiddenText.setText("");

    }

    public void login(ActionEvent actionEvent) {

    }

    public void register(ActionEvent actionEvent) throws IOException {
        model.showRegister();
    }

    public void showPassword(ActionEvent actionEvent) {
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
