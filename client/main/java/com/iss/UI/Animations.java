package com.iss.UI;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animations {
    public static void animate(Scene scene, Stage stage){
        if (stage.getScene() != null) {
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(300));
            fadeTransition.setNode(stage.getScene().getRoot());
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);

            fadeTransition.setOnFinished((ActionEvent event) -> stage.setScene(scene));
            fadeTransition.play();
        } else {
            stage.setScene(scene);
        }
    }
}
