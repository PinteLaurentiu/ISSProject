package com.iss.sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model(primaryStage);
        model.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
