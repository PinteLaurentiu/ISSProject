package com.iss.UI;

import com.iss.domain.Cerere;
import com.iss.domain.*;
import com.iss.service.ConsultProxy;
import com.iss.service.DonareProxy;
import com.iss.service.CerereProxy;
import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    //FROM ADMINISTRATOR VIEW
    public TableView<User> usersTable;
    public Pagination paginationUsers;
    public JFXButton modifyUsers;
    public JFXButton deleteUsers;
    public JFXButton addUsers;

    //FROM DOCTOR CENTRE VIEW
    public JFXTabPane doctorCentrePane;
    public Tab consulationTab;
    public Tab seeConsulationTab;
    public JFXTextField greutTextField;
    public JFXTextField inaltTextField;
    public JFXTextField tenTextField;
    public JFXTextField pulsTextField;
    public TextArea boliTextArea;
    public JFXButton addConsulationButton;
    public JFXCheckBox yesCheck;
    public JFXCheckBox noCheck;
    public JFXButton finalButton;

    //FROM LAB VIEW
    public JFXComboBox<String> locatiiCombo;
    public JFXButton transferButton;
    public TextArea imunoText;
    public TextArea boliText;
    public JFXComboBox<String> grupeCombo;
    public JFXButton completeButton;
    public TableView<Donare> donariForConsultTable;
    public ImageView addConsult;

    //FROM SPITAL VIEW
    public Tab cerereView;
    public TableView<Cerere> cerereTable;
    public Pagination paginationCerere;
    public JFXButton deleteCerere;
    public JFXButton addCerere;
    public TableView<Donare> consultatiiTable;

    private Stage stage;
    private ProxyFactory factory;

    private static final int RECORDS_ON_PAGE = 8;

    public void initTable(){
        JFXTreeTableColumn<Analyses,String> date = new JFXTreeTableColumn<>("Data");
        date.setPrefWidth(150);
        date.setCellValueFactory(param -> param.getValue().getValue().data);
        JFXTreeTableColumn <Analyses, String > centre = new JFXTreeTableColumn<>("Ora");
        centre.setPrefWidth(150);
        centre.setCellValueFactory(param -> param.getValue().getValue().ora);
//        JFXTreeTableColumn<Analyses,String> nuj = new JFXTreeTableColumn<>("Status");
//        nuj.setPrefWidth(95);
//        nuj.setCellValueFactory(param -> param.getValue().getValue().status);

        //Populate
        updateAnalisys();
        analysisTable.getColumns().setAll(date,centre);
        analysisTable.setShowRoot(false);

        analysisTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showDetail();
            }
        });
    }


    class Analyses extends RecursiveTreeObject<Analyses>{ //MUST EXTEND
        SimpleStringProperty data;
        SimpleStringProperty ora;
        SimpleStringProperty status;


        public Analyses(String data, String ora, String status){
            this.data = new SimpleStringProperty(data);
            this.ora = new SimpleStringProperty(ora);
            this.status = new SimpleStringProperty(status);
        }
        public String getStatus() {
            return status.getValue();
        }
    }

    private void setTransition(FadeTransition trans,Node node){
        trans.setNode(node);
        trans.setFromValue(0.0);
        trans.setToValue(1.0);
        trans.setCycleCount(1);
        trans.setAutoReverse(false);
    }
    private void showDetail() {

        Analyses analyses;
        if (analysisTable.getSelectionModel().getSelectedItems().size() == 0) {
            statusText.setText("");
        } else {
            analyses = analysisTable.getSelectionModel().getSelectedItem().getValue();
            statusText.setText(analyses.getStatus());
        }

        setTransition(fadeIn, detailsPane);
        detailsPane.setVisible(true);
        fadeIn.playFromStart();

        setTransition(fadeInLabel, statusText);
        statusText.setVisible(true);
        fadeInLabel.playFromStart();

        setTransition(fadeInLabel, statusLabel);
        statusLabel.setVisible(true);
        fadeInLabel.playFromStart();

        setTransition(fadeInLabel, reasonLabel);
        reasonLabel.setVisible(true);
        fadeInLabel.playFromStart();

        setTransition(fadeInLabel, analysDetailLabel);
        analysDetailLabel.setVisible(true);
        fadeInLabel.playFromStart();
    }


    private void updateAnalisys() {
        ObservableList<Analyses> analyses = FXCollections.observableArrayList();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-LL-yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");

        Iterable<Donare> donares = factory.get(DonareProxy.class).getAllByUser();

        for (Donare donare : donares) {
            Analyses analiza = new Analyses(formatter.format(donare.getDate()), formatter2.format(donare.getDate()), donare.getStatus().toString());
            analyses.add(analiza);
        }

        final TreeItem<Analyses> root = new RecursiveTreeItem<Analyses>(analyses, RecursiveTreeObject::getChildren);
        analysisTable.setRoot(root);
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

        //ADMIN PART
        @SuppressWarnings("WeakerAccess")
        public static void show(Stage stage, ProxyFactory factory) throws IOException {
            show(stage, factory, 0);
        }

    @SuppressWarnings("WeakerAccess")
    public static void show(Stage stage, ProxyFactory factory, int tabIndex) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/mainView.fxml"));
        AnchorPane load = loader.load();
        Scene scene = new Scene(load, load.getPrefWidth() , load.getPrefHeight());
        MainView main = loader.getController();
        main.init(stage, factory, tabIndex);
        Animations.animate(scene, stage);
        stage.show();
    }

    public void init(Stage stage, ProxyFactory factory,int tabIndex){

        this.stage = stage;
        this.factory = factory;
        initGrupeCombo();
        checkApt();
        usersTable.setDisable(false);
        hideDetail();
        donorMenu.getTabs().remove(donorNowView);
        initDonor();
        menuListener();
        initTable();
        List<Role> roles = (List<Role>) factory.get(UserProxy.class).getRoles();
        if(tabIndex==5) {
            mainMenu.getSelectionModel().select(adminView);
        }
        else if(tabIndex==1){
            mainMenu.getSelectionModel().select(donorView);
        }
        else if(tabIndex==2){
            mainMenu.getSelectionModel().select(doctorView);
        }
        else if(tabIndex==3){
            mainMenu.getSelectionModel().select(labView);
        }
        else if(tabIndex==4){
            mainMenu.getSelectionModel().select(cerereView);
        }
        if (!roles.contains(Role.DoctorDonare))
            mainMenu.getTabs().remove(labView);
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-LL-yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
            //noinspection unchecked
            donariForConsultTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getNume()));
            //noinspection unchecked
            donariForConsultTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getPrenume()));
            //noinspection unchecked
            donariForConsultTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter2.format(x.getValue().getDate())));
            //noinspection unchecked
            donariForConsultTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter.format(x.getValue().getDate())));
            updateDonariForConsult();

            //noinspection unchecked
            consultatiiTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getNume()));
            //noinspection unchecked
            consultatiiTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getPrenume()));
            //noinspection unchecked
            consultatiiTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter2.format(x.getValue().getDate())));
            //noinspection unchecked
            consultatiiTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter.format(x.getValue().getDate())));
            updateConsultatii();

        }

        if (!roles.contains(Role.DoctorLab))
            mainMenu.getTabs().remove(labView);

        if (!roles.contains(Role.DoctorSpital))
            mainMenu.getTabs().remove(doctorView);

        if (!roles.contains(Role.UsersEditor))
            mainMenu.getTabs().remove(donorView);
        else{
            paginationUsers.currentPageIndexProperty().addListener((x,y,z)->updateUsers());
            updateUsers();
            //noinspection unchecked
            usersTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getNume()));
            //noinspection unchecked
            usersTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getPrenume()));
            //noinspection unchecked
            usersTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getTelefon()));
            //noinspection unchecked
            usersTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getEmail()));
            //noinspection unchecked
            usersTable.getColumns().get(4).setCellValueFactory(x->new ReadOnlyObjectWrapper(rolesAsString(x.getValue())));
            usersTable.getSelectionModel().selectedItemProperty().addListener((x,y,z)->userSelectionChanged());
            userSelectionChanged();
        }
        mainMenu.getSelectionModel().selectedItemProperty().addListener(this::changedTab);
    }

    private void updateUsers() {
        usersTable.getItems().clear();
        for (User user : factory.get(UserProxy.class).getAll(RECORDS_ON_PAGE,paginationUsers.getCurrentPageIndex()))
            usersTable.getItems().add(user);
        paginationUsers.setPageCount((int)Math.ceil(factory.get(UserProxy.class).count() / (float)RECORDS_ON_PAGE));
    }

    private void updateCerere() {
        cerereTable.getItems().clear();
        for (Cerere cerere : factory.get(CerereProxy.class).getAll(RECORDS_ON_PAGE,paginationUsers.getCurrentPageIndex()))
            cerereTable.getItems().add(cerere);
        paginationUsers.setPageCount((int)Math.ceil(factory.get(CerereProxy.class).count() / (float)RECORDS_ON_PAGE));
    }

    private static String rolesAsString(User value) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (UserRole role : value.getRoles()) {
            if (index != 0)
                builder.append(", ");
            builder.append(role.getRole().toString());
            index++;
        }
        return builder.toString();
    }

    private void userSelectionChanged() {
        modifyUsers.setDisable(usersTable.getSelectionModel().getSelectedItems().size() == 0);
        deleteUsers.setDisable(usersTable.getSelectionModel().getSelectedItems().size() == 0);
    }


    @SuppressWarnings("unused")
    private void changedTab(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        //if (newValue.equals(usersTab)){
        //}
    }

    @SuppressWarnings("unused")
    public void modifyPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(this.stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        EditRoles.show(stage, factory, usersTable.getSelectionModel().getSelectedItem());
        updateUsers();
    }

    @SuppressWarnings("unused")
    public void deletePressed(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        factory.get(UserProxy.class).remove(user.getId());
        updateUsers();
    }

    @SuppressWarnings("unused")
    public void addPressed(ActionEvent actionEvent) throws IOException {
        Register.show(stage, factory, true);
    }

    public void deleteCererePressed(ActionEvent actionEvent) {
    }


    public void addCererePressed(ActionEvent actionEvent) throws IOException {
        CerereView.show(stage, factory);
    }

    //DOCTOR CENTRE PART
    public void checkApt() {

        yesCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (noCheck.isSelected() && oldValue == false) {
                    yesCheck.setSelected(newValue);
                    noCheck.setSelected(false);

                }

            }
        });
        noCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (yesCheck.isSelected() && oldValue == false) {
                    noCheck.setSelected(newValue);
                    yesCheck.setSelected(false);
                }
            }
        });
    }

    public void handleFinal(){
        //TODO - CE SE INTAMPLA DUPA CE TERMINA DE DONAT SANGE
    }


    public void handleAddConsult(){

        if(greutTextField.getText().isEmpty() || inaltTextField.getText().isEmpty() || tenTextField.getText().isEmpty()
                || pulsTextField.getText().isEmpty() || !(yesCheck.isSelected() || noCheck.isSelected() ||
                donariForConsultTable.getSelectionModel().getSelectedItems().size() != 1)) {
            new Alert(Alert.AlertType.WARNING, "Date invalide!", ButtonType.OK).showAndWait();
        }
        else {
            Float greutate = Float.parseFloat(greutTextField.getText());
            Float inaltime = Float.parseFloat(inaltTextField.getText());
            Float tensiune = Float.parseFloat(tenTextField.getText());
            Integer puls = Integer.parseInt(pulsTextField.getText());
            String boli = boliTextArea.getText();
            Integer id = donariForConsultTable.getSelectionModel().getSelectedItem().getId();
            factory.get(ConsultProxy.class).add(id, greutate, tensiune, puls,  boli, inaltime, yesCheck.isSelected());
        }
        updateDonariForConsult();
        updateAnalisys();
    }

    //LAB PART
    public void initGrupeCombo() {
        //TODO COMBO BOX PENTRU LOCATII
        grupeCombo.setItems(FXCollections.observableArrayList("0I", "0I-", "AII", "AII-", "BIII", "BIII-", "ABIV", "ABIV-"));
    }

    private void updateDonariForConsult() {
        donariForConsultTable.getItems().clear();
        for (Donare donare : factory.get(DonareProxy.class).getAll()) {
            if (donare.getConsult() == null)
                donariForConsultTable.getItems().add(donare);
        }
    }
    private void updateConsultatii() {
        consultatiiTable.getItems().clear();
        for (Donare donare : factory.get(DonareProxy.class).getAll()) {
            if (donare.getConsult() != null)
                consultatiiTable.getItems().add(donare);
        }
    }

    public void handleTransfer(){
        //TODO CE SE INTAMPLA DUPA CE TRANSFERA
    }

    public void handleComplete(){
        //TODO CE SE INTAMPLA DUPA CE COMPLETEA*A
    }
    public void handleLogOut(){
        //TODO : logout
        System.out.println("LogOut");
    }

    public void handleContinue() throws IOException{
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/donorQuestionnaire.fxml"));
        Node parent = donorNowView.getContent();
        ScrollPane root = loader.load();
        DonorQuestionnaire controller = loader.getController();
        controller.init(stage, factory);
        controller.setGoBack(()->donorNowView.setContent(parent));
        donorNowView.setContent(root);
    }
}
