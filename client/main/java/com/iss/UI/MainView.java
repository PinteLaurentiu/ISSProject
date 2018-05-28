package com.iss.UI;

import com.iss.service.ProxyFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;


import java.io.IOException;

public class MainView {

    public JFXTabPane mainMenu;
    public Tab donorView;
    public Tab doctorView;
    public Tab labView;
    public Tab adminView;
    public ImageView donorImage;
    public ImageView doctorImage;
    public ImageView labImage;
    public ImageView adminImage;
    private Stage stage;
    private ProxyFactory factory;

    //From DonourView

    public AnchorPane donorPane;
    public JFXTabPane donorMenu;
    public Tab analysisView;
    public Tab donorNowView;
    public Label textRemain;
    public Label daysUntilDonating;
    public Label textDays;
    public Label textDonating;
    public Button logOutButton;
    public JFXTreeTableView<Analyses> analysisTable;
    public Label statusLabel;
    public Label statusText;
    public Label reasonLabel;
    public Label analysDetailLabel;
    public AnchorPane detailsPane;
    public JFXButton continueButton;


    @FXML
    public void initialize(){
        hideDetail();
        donorMenu.getTabs().remove(donorNowView);
        initDonor();
        menuListener();
        initTable();

    }
    public void initTable(){
        JFXTreeTableColumn<Analyses,String> date = new JFXTreeTableColumn<>("Data");
        date.setPrefWidth(100);
        date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Analyses, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Analyses, String> param) {
                return param.getValue().getValue().date;
            }
        });
        JFXTreeTableColumn <Analyses, String > centre = new JFXTreeTableColumn<>("Centru");
        centre.setPrefWidth(100);
        centre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Analyses, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Analyses, String> param) {
                return param.getValue().getValue().centre;
            }
        });
        JFXTreeTableColumn<Analyses,String> nuj = new JFXTreeTableColumn<>("Nuj");
        nuj.setPrefWidth(95);
        nuj.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Analyses, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Analyses, String> param) {
                return param.getValue().getValue().nuj;
            }
        });


        //Populate
        ObservableList<Analyses> analyses = FXCollections.observableArrayList();
        analyses.add(new Analyses("23-04-2018","Suceava","nush"));
        analyses.add(new Analyses("23-04-2017","Bucuresti","nush"));
        analyses.add(new Analyses("23-04-2016","Nuj","nush"));

        final TreeItem<Analyses> root = new RecursiveTreeItem<Analyses>(analyses,RecursiveTreeObject::getChildren);
        analysisTable.getColumns().setAll(date,centre,nuj);
        analysisTable.setRoot(root);
        analysisTable.setShowRoot(false);

        analysisTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showDetail();
            }
        });


    }


    class Analyses extends RecursiveTreeObject<Analyses>{ //MUST EXTEND
        SimpleStringProperty date;
        SimpleStringProperty centre;
        SimpleStringProperty nuj;

        public Analyses(String date, String centre, String nuj){
            this.date = new SimpleStringProperty(date);
            this.centre = new SimpleStringProperty(centre);
            this.nuj = new SimpleStringProperty(nuj);

        }
    }

    private void setTransition(FadeTransition trans,Node node){
        trans.setNode(node);
        trans.setFromValue(0.0);
        trans.setToValue(1.0);
        trans.setCycleCount(1);
        trans.setAutoReverse(false);


    }
    private void showDetail(){

        setTransition(fadeIn,detailsPane);
        detailsPane.setVisible(true);
        fadeIn.playFromStart();

        setTransition(fadeInLabel,statusText);
        statusText.setVisible(true);
        fadeInLabel.playFromStart();


        setTransition(fadeInLabel,statusLabel);
        statusLabel.setVisible(true);
        fadeInLabel.playFromStart();

        setTransition(fadeInLabel,reasonLabel);
        reasonLabel.setVisible(true);
        fadeInLabel.playFromStart();

        setTransition(fadeInLabel,analysDetailLabel);
        analysDetailLabel.setVisible(true);
        fadeInLabel.playFromStart();


    }
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(2000)
    );

    private FadeTransition fadeInLabel = new FadeTransition(Duration.millis(1500));


    private void hideDetail(){
        detailsPane.setVisible(false);
        statusLabel.setVisible(false);
        statusText.setVisible(false);
        reasonLabel.setVisible(false);
        analysDetailLabel.setVisible(false);
    }
    private void menuListener(){
        Image donorImageRed = new Image("icons/donor.png");
        Image donorImageGrey = new Image("icons/donor-grey.png");

        Image doctorImageRed = new Image("icons/doctor.png");
        Image doctorImageGrey = new Image("icons/doctor-grey.png");

        Image labImageRed = new Image("icons/lab.png");
        Image labImageGrey = new Image("icons/lab-grey.png");

        Image administratorRed = new Image("icons/administrator2.png");
        Image administratorGrey  = new Image("icons/administrator2-grey.png");

        mainMenu.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        if(t1.getText().equals("Doctor")){
                            doctorImage.setImage(doctorImageRed);
                            donorImage.setImage(donorImageGrey);
                            labImage.setImage(labImageGrey);
                            adminImage.setImage(administratorGrey);

                        }
                        else if(t1.getText().equals("Donator")){
                            donorImage.setImage(donorImageRed);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageGrey);
                            adminImage.setImage(administratorGrey);
                        }
                        else if(t1.getText().equals("Laborator")){
                            donorImage.setImage(donorImageGrey);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageRed);
                            adminImage.setImage(administratorGrey);
                        }
                        else if(t1.getText().equals("Admin")){
                            donorImage.setImage(donorImageGrey);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageGrey);
                            adminImage.setImage(administratorRed);
                        }
                    }
                }
        );
    }
    public boolean checkDonating(){
        //TODO: check if donor can donate ( returns true if last time he donate was at least 8 weaks ago ,false otherwise)
        return true;
    }
    public Integer daysUntilUserCanDonate(){
        //TODO:number of days remains until user can donate again
        return 20;
    }

    public void initDonor(){
        //if user can donate
        if(checkDonating()){
            textDonating.setVisible(true);
            textRemain.setVisible(false);
            textDays.setVisible(false);
            daysUntilDonating.setVisible(false);
            donorMenu.getTabs().add(donorNowView);
        }
        else{
                textDonating.setVisible(false);
                textRemain.setVisible(true);
                textDays.setVisible(true);
                daysUntilDonating.setVisible(true);
                daysUntilDonating.setText(daysUntilUserCanDonate().toString());
                donorMenu.getTabs().remove(donorNowView);
            }
        }


    public static void show(Stage stage, ProxyFactory factory ) throws IOException {

        FXMLLoader loaderMainView = new FXMLLoader(MainView.class.getResource("/mainView.fxml"));
        AnchorPane rootMainView = loaderMainView.load();
        Scene sceneMainView = new Scene(rootMainView, rootMainView.getPrefWidth() , rootMainView.getPrefHeight());

        MainView mainView = loaderMainView.getController();
        mainView.init(stage, factory);
        Animations.animate(sceneMainView, stage);
        stage.show();
    }


    public void init(Stage stage, ProxyFactory factory){
        this.stage = stage;
        this.factory = factory;

    }

    public void handleLogOut(){
        //TODO : logout
        System.out.println("LogOut");
    }

    public void handleContinue() throws IOException{
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/donorQuestionnaire.fxml"));
        ScrollPane root = loader.load();
        donorNowView.setContent(root);
    }

}
