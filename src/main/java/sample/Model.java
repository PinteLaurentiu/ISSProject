package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Login;
import view.Register;

import java.io.IOException;

public class Model {
    private Scene sceneLogin, sceneRegister;
    private Stage stage;

    Model(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root1 = loaderLogin.load();
        sceneLogin = new Scene(root1, 790, 500);
        Login login = loaderLogin.getController();
        login.init(this);

        FXMLLoader loaderRegister = new FXMLLoader(getClass().getResource("/register.fxml"));
        Parent root2 = loaderRegister.load();
        sceneRegister = new Scene(root2, 790, 500);
        Register register = loaderRegister.getController();
        register.init(this);

        stage.setTitle("Blood Donation");
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
    }

    public void showLogin() {
        stage.setScene(sceneLogin);
    }

    public void showRegister(){
        stage.setScene(sceneRegister);
    }
}
