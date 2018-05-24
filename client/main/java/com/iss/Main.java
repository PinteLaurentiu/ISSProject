package com.iss;

import com.iss.UI.EditRoles;
import com.iss.UI.Login;
import com.iss.UI.MainController;
import com.iss.UI.MainView;
import com.iss.service.ProxyFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Blood Donation");
        primaryStage.setOnCloseRequest(x -> Platform.exit());
        Login.show(primaryStage,new ProxyFactory("http://localhost:8080"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
