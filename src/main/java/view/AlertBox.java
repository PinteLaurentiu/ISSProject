package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class AlertBox {

    AlertBox(String title, String message) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label la = new Label(message);
        Button close = new Button("Close window");
        close.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(la, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,450,100);
        window.setScene(scene);

        window.showAndWait();
    }
}
