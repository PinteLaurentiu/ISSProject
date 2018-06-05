package com.iss.UI;

import com.iss.domain.*;
import com.iss.enums.Role;
import com.iss.service.CerereProxy;
import com.iss.service.DonareProxy;
import com.iss.service.ProxyFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditProbaCompabilitateMajora {
    public TableView<Donare> leftList;
    public Button left;
    public Button right;
    public TableView<Donare> rightList;
    private Stage stage;
    private ProxyFactory factory;
    private Cerere cerere;

    public static void show(Stage stage, ProxyFactory factory, Cerere cerere) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/editProbaCompabilitateMajora.fxml"));
        Pane load = loader.load();
        Scene scene = new Scene(load, load.getPrefWidth() , load.getPrefHeight());
        EditProbaCompabilitateMajora editRoles = loader.getController();
        editRoles.init(stage, factory, cerere);
        Animations.animate(scene, stage);
        stage.showAndWait();
    }

    private void init(Stage stage, ProxyFactory factory, Cerere cerere) {
        this.stage = stage;
        this.factory = factory;
        this.cerere = cerere;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-LL-yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        //noinspection unchecked
        leftList.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getNume()));
        //noinspection unchecked
        leftList.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getPrenume()));
        //noinspection unchecked
        leftList.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter2.format(x.getValue().getDate())));
        //noinspection unchecked
        leftList.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter.format(x.getValue().getDate())));
        //noinspection unchecked
        rightList.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getNume()));
        //noinspection unchecked
        rightList.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getPrenume()));
        //noinspection unchecked
        rightList.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter2.format(x.getValue().getDate())));
        //noinspection unchecked
        rightList.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter.format(x.getValue().getDate())));

        updateLists();
        leftList.getSelectionModel().selectedItemProperty().addListener((x,y,z)->selectionChanged());
        rightList.getSelectionModel().selectedItemProperty().addListener((x,y,z)->selectionChanged());
        selectionChanged();
    }

    private void selectionChanged() {
        right.setDisable(leftList.getSelectionModel().getSelectedItems().size() == 0);
        left.setDisable(rightList.getSelectionModel().getSelectedItems().size() == 0);
    }

    private void updateLists() {
        for (Donare donare : factory.get(DonareProxy.class).getAll())
            for (CompabilitateMajora proba : cerere.getProbe())
                for (ComponentaSange componentaSange : donare.getComponenteSange())
                    if (proba.getComponentaSange().getIdComponenta() == componentaSange.getIdComponenta())
                        proba.getComponentaSange().setDonare(donare);
        for (CompabilitateMajora proba : cerere.getProbe())
            rightList.getItems().add(proba.getComponentaSange().getDonare());
    }

    public void leftPressed(ActionEvent actionEvent) {
        Donare donare = rightList.getSelectionModel().getSelectedItem();
        rightList.getItems().remove(donare);
        leftList.getItems().add(donare);
        selectionChanged();
    }

    public void rightPressed(@SuppressWarnings("unused") ActionEvent event) {
        Donare donare = leftList.getSelectionModel().getSelectedItem();
        leftList.getItems().remove(donare);
        rightList.getItems().add(donare);
        selectionChanged();
    }

    public void submit(ActionEvent actionEvent) {
        List<Integer> ids = new ArrayList<>();
        for (CompabilitateMajora proba : cerere.getProbe())
            for (Donare donare : rightList.getItems())
                if (proba.getComponentaSange().getDonare().getId() == donare.getId())
                    ids.add(proba.getComponentaSange().getIdComponenta());
        factory.get(CerereProxy.class).reemit(cerere.getIdCerere(),ids);
    }
}
