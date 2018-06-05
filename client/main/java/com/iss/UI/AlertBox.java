package com.iss.UI;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


class AlertBox {

    AlertBox( String title, String message) {



       Stage window = new Stage();
        window.initModality(Modality.NONE);
        window.setTitle(title);

        Label la = new Label(message);
        la.setWrapText(true);
        la.getStylesheets().add("/css/buttonAlert.css");
        Button close = new Button("Am inteles");
        //String uri = Paths.get("css/buttonAlert.css").toUri().toString();
        close.getStylesheets().add("/css/buttonAlert.css");
        close.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(la, close);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #E9E5E7");

        Scene scene = new Scene(layout,450,100);
        window.setScene(scene);

        window.showAndWait();
    }
}
