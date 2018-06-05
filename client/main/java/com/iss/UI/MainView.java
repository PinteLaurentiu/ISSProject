package com.iss.UI;

import com.iss.domain.Cerere;
import com.iss.domain.*;
import com.iss.enums.*;
import com.iss.service.ConsultProxy;
import com.iss.service.DonareProxy;
import com.iss.service.CerereProxy;
import com.iss.service.ProxyFactory;
import com.iss.service.UserProxy;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.beans.Observable;
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
    public ImageView spitalImage;

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
    public JFXTextField cautaField;
    public JFXTextArea detaliiDonareArea;
    private ObservableList<Donare> donariForConsultList = FXCollections.observableArrayList();
    private ObservableList<Donare> consultatiiList = FXCollections.observableArrayList();

    //FROM LAB VIEW
    public JFXButton transferButton;
    public TextArea imunoText;
    public TextArea boliText;
    public JFXComboBox<GrupaSange> grupeCombo;
    public JFXButton completeButton;
    public TableView<Donare> donariForConsultTable;
    public ImageView addConsult;

    //FROM SPITAL VIEW
    public Tab cerereView;
    public TableView<Cerere> cerereTable;
    public Pagination paginationCerere;
    public JFXButton deleteCerere;
    public JFXButton addCerere;
    public JFXButton probaCompatibilitateBtn;
    public TableView<Donare> consultatiiTable;
    public JFXTextField cautaCerere;
    private ObservableList<Cerere> cerereList = FXCollections.observableArrayList();

    public Label usernameText;
    public TableView<PungaSange> pungiSangeLabTable;
    public JFXTextField locationToField;

    private Stage stage;
    private ProxyFactory factory;

    private static User currentUser;

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
        updateTables();
        analysisTable.getColumns().setAll(date,centre);
        analysisTable.setShowRoot(false);

        analysisTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showDetail();
            }
        });
    }

    private void updateTables(){
        List<Role> roles = (List<Role>) factory.get(UserProxy.class).getRoles();
        updateAnalisys();
        if (roles.contains(Role.DoctorDonare)) {
            updateConsultatii();
            updateDonariForConsult();
        }
        if (roles.contains(Role.UsersEditor))
            updateUsers();
        if (roles.contains(Role.DoctorLab))
            updatePungiSange();
        if (roles.contains(Role.DoctorSpital))
            updateCerere();
    }

    private void updatePungiSange() {
        pungiSangeLabTable.getItems().clear();
        for (Donare donare : factory.get(DonareProxy.class).getAll()) {
            if (donare.getConsult() != null && donare.getPungiSange().size() != 0 &&
                    donare.getStatus() != DonareStatus.Respins && donare.getComponenteSange().size() == 0) {
                for (PungaSange pungaSange : donare.getPungiSange()){
                    pungaSange.setDonare(donare);
                    pungiSangeLabTable.getItems().add(pungaSange);
                }
            }
        }
    }


    class Analyses extends RecursiveTreeObject<Analyses>{ //MUST EXTEND
        SimpleStringProperty data;
        SimpleStringProperty ora;
        SimpleStringProperty status;
        Donare donare;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-LL-yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");


        public Analyses(Donare donare){
            this.donare = donare;
            this.data = new SimpleStringProperty(formatter.format(donare.getDate()));
            this.ora = new SimpleStringProperty(formatter2.format(donare.getDate()));
            this.status = new SimpleStringProperty(donare.getStatus().toString());
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
        statusText.setText("");
        detaliiDonareArea.setVisible(false);
        detaliiDonareArea.setText("");
        Analyses analyses;
        if (analysisTable.getSelectionModel().getSelectedItems().size() != 0) {
            analyses = analysisTable.getSelectionModel().getSelectedItem().getValue();
            DonareStatus status = analyses.donare.getStatus();
//            statusText.setText(analyses.getStatus());

            switch (status){
                case Programat:
                    statusText.setText("Donarea este programată");
                    break;
                case Consultat:
                    statusText.setText("Consultația a fost efectuată");
                    showDetailsConsult(analyses, false);
                    break;
                case Respins:
                    if (analyses.donare.getConsult() == null){
                        statusText.setText("Nu v-ati prezentat la donare! ");

                    } else if (analyses.donare.getPungiSange().isEmpty()) {
                        statusText.setText("Donarea a fost respinsă");
                        showDetailsConsult(analyses, true);
                    } else {
                        statusText.setText("Punga de sânge a fost respinsă");
                        showDetailsConsult(analyses, true);
                        showDetailsAnalyses(analyses, true);
                    }
                    break;
                case Efectuat:
                    statusText.setText("Donarea a fost efectuată");
                    detaliiDonareArea.setVisible(true);
                    detaliiDonareArea.setText("Analize in curs de procesare\n");
                    showDetailsConsult(analyses, false);
                    break;
                case Analizat:
                    statusText.setText("Donarea a fost efectuată");
                    showDetailsConsult(analyses, false);
                    showDetailsAnalyses(analyses, false);
                    break;
                default:
                    break;
            }
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

        setTransition(fadeInLabel, detaliiDonareArea);
        fadeInLabel.playFromStart();

        setTransition(fadeInLabel, analysDetailLabel);
        analysDetailLabel.setVisible(true);
        fadeInLabel.playFromStart();
    }

    private void showDetailsAnalyses(Analyses analyses, boolean showBoli) {
        detaliiDonareArea.setVisible(true);
        String text = "S-a descoperit la analize:\nImuno-Hematologie: ";
        text += analyses.donare.getAnaliza().getImunoHematologice();
        if (showBoli) {
            text += "\nBoli transmisibile: ";
            text += analyses.donare.getAnaliza().getBoliTransmisibile();
        }
        text += "\n";
        detaliiDonareArea.setText(detaliiDonareArea.getText() + text);
    }

    private void showDetailsConsult(Analyses analyses, boolean showBoli) {
        detaliiDonareArea.setVisible(true);
        String text = "S-a descoperit la consult:\nGreutate: ";
        text += analyses.donare.getConsult().getGreutate();
        text += "\nÎnălțime: ";
        text += analyses.donare.getConsult().getInaltime();
        text += "\nTensiune: ";
        text += analyses.donare.getConsult().getTensiune();
        text += "\nPuls: ";
        text += analyses.donare.getConsult().getPuls();
        if (showBoli) {
            text += "\nBoli: ";
            text += analyses.donare.getConsult().getBoliDepistate();
        }
        text +="\n";
        detaliiDonareArea.setText(detaliiDonareArea.getText() + text);
    }


    private void updateAnalisys() {
        ObservableList<Analyses> analyses = FXCollections.observableArrayList();
        Iterable<Donare> donares = factory.get(DonareProxy.class).getAllByUser();

        for (Donare donare : donares) {
            Analyses analiza = new Analyses(donare);
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

        Image spitalRed = new Image("icons/pill.png");
        Image spitalGrey  = new Image("icons/pill-grey.png");

        mainMenu.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        if(t1.getText().equals("Doctor")){
                            doctorImage.setImage(doctorImageRed);
                            donorImage.setImage(donorImageGrey);
                            spitalImage.setImage(spitalGrey);
                            adminImage.setImage(administratorGrey);
                        }
                        else if(t1.getText().equals("Donator")){
                            donorImage.setImage(donorImageRed);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageGrey);
                            spitalImage.setImage(spitalGrey);
                            adminImage.setImage(administratorGrey);
                        }
                        else if(t1.getText().equals("Laborator")){
                            donorImage.setImage(donorImageGrey);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageRed);
                            spitalImage.setImage(spitalGrey);
                            adminImage.setImage(administratorGrey);
                        }
                        else if(t1.getText().equals("Spital")){
                            donorImage.setImage(donorImageGrey);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageGrey);
                            spitalImage.setImage(spitalRed);
                            adminImage.setImage(administratorGrey);
                        }
                        else if(t1.getText().equals("Admin")){
                            donorImage.setImage(donorImageGrey);
                            doctorImage.setImage(doctorImageGrey);
                            labImage.setImage(labImageGrey);
                            spitalImage.setImage(spitalGrey);
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
        public static void show(Stage stage, ProxyFactory factory, User user) throws IOException {
            currentUser = user;
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
            mainMenu.getTabs().remove(doctorView);
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
            donariForConsultTable.setItems(donariForConsultList.filtered(x->true));
            donariForConsultTable.getSelectionModel().selectedItemProperty().addListener(this::donariSelectionChanged);

            //noinspection unchecked
            consultatiiTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getNume()));
            //noinspection unchecked
            consultatiiTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getUser().getPrenume()));
            //noinspection unchecked
            consultatiiTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter2.format(x.getValue().getDate())));
            //noinspection unchecked
            consultatiiTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(formatter.format(x.getValue().getDate())));
            updateTables();
            consultatiiTable.setItems(consultatiiList.filtered(x->true));
            cautaField.textProperty().addListener(this::cautaFieldChanged);
        }

        if (!roles.contains(Role.DoctorLab)) {
            mainMenu.getTabs().remove(labView);
        }
        else{
            //noinspection unchecked
            pungiSangeLabTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getDonare().getUser().getNume()));
            //noinspection unchecked
            pungiSangeLabTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getDonare().getUser().getPrenume()));
            //noinspection unchecked
            pungiSangeLabTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getLocatie()));
            //noinspection unchecked
            pungiSangeLabTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getTip()));
            pungiSangeLabTable.getSelectionModel().selectedItemProperty().addListener(this::pungiSangeSelectionChanged);
            pungiSangeSelectionChanged(null, null, null);
            updateTables();
        }

        if (!roles.contains(Role.DoctorSpital))
            mainMenu.getTabs().remove(cerereView);
        else{
            //noinspection unchecked
            cerereTable.getColumns().get(0).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getNumePacient()));
            //noinspection unchecked
            cerereTable.getColumns().get(1).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getPrenumePacient()));
            //noinspection unchecked
            cerereTable.getColumns().get(2).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getTipComponenteSange()));
            //noinspection unchecked
            cerereTable.getColumns().get(3).setCellValueFactory(x->new ReadOnlyObjectWrapper(createCantitateValue(x.getValue())));
            //noinspection unchecked
            cerereTable.getColumns().get(4).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getGradDeUrgenta()));
            //noinspection unchecked
            cerereTable.getColumns().get(5).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getGrupaSange()));
            //noinspection unchecked
            cerereTable.getColumns().get(6).setCellValueFactory(x->new ReadOnlyObjectWrapper(x.getValue().getCerereStatus()));
            cerereTable.getSelectionModel().selectedItemProperty().addListener((x,y,z)->cerereSelectionChanged());
            cerereSelectionChanged();
            updateTables();
            cerereTable.setItems(cerereList.filtered(x->true));
            cautaCerere.textProperty().addListener(this::cautaCerereChanged);
        }

        if (!roles.contains(Role.UsersEditor))
            mainMenu.getTabs().remove(adminView);
        else{
            paginationUsers.currentPageIndexProperty().addListener((x,y,z)->updateTables());
            updateTables();
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

        this.usernameText.setText(currentUser.getNume()+" "+currentUser.getPrenume());
    }


    private String createCantitateValue(Cerere value) {
        String cantitate = "";
        if (value.getCerereStatus().equals(CerereStatus.Emisa) || value.getCerereStatus().equals(CerereStatus.Reemisa))
            cantitate = value.getCantitateDonata() + "/";
        cantitate += value.getCantitatea();
        return cantitate;
    }

    private void pungiSangeSelectionChanged(ObservableValue<? extends PungaSange> observable, PungaSange oldValue, PungaSange newValue) {
        boolean selected = pungiSangeLabTable.getSelectionModel().getSelectedItems().size() != 0;
        transferButton.setDisable(!selected);
        completeButton.setDisable(!selected);
        imunoText.setText("");
        grupeCombo.setValue(null);
        boliText.setText("");
        imunoText.setDisable(false);
        grupeCombo.setDisable(false);
        boliText.setDisable(false);
        grupeCombo.setEditable(false);
        if (selected){
            PungaSange pungaSangeSelected = pungiSangeLabTable.getSelectionModel().getSelectedItem();
            if (pungaSangeSelected.getTip().equals(TipPungaSange.Utilizabil)){
                Analiza analiza = pungaSangeSelected.getDonare().getAnaliza();
                completeButton.setText("Imparte pe componente");
                imunoText.setDisable(true);
                grupeCombo.setDisable(true);
                boliText.setDisable(true);
                if (analiza != null) {
                    completeButton.setDisable(false);
                    imunoText.setText(analiza.getImunoHematologice());
                    grupeCombo.setEditable(true);
                    grupeCombo.getEditor().setText(analiza.getGrupaSange().toString());
                    boliText.setText(analiza.getBoliTransmisibile());
                }
                else {
                    completeButton.setDisable(true);
                }
            }
            else {
                completeButton.setDisable(false);
                completeButton.setText("Adaugă analize");
                Analiza analiza = pungaSangeSelected.getDonare().getAnaliza();
                if (analiza != null) {
                    completeButton.setDisable(true);
                    imunoText.setText(analiza.getImunoHematologice());
                    grupeCombo.setEditable(true);
                    grupeCombo.getEditor().setText(analiza.getGrupaSange().toString());
                    boliText.setText(analiza.getBoliTransmisibile());
                    imunoText.setDisable(true);
                    grupeCombo.setDisable(true);
                    boliText.setDisable(true);
                }
            }
        }
    }

    private void donariSelectionChanged(ObservableValue<? extends Donare> observable, Donare oldValue, Donare newValue) {
        addConsulationButton.setDisable(donariForConsultTable.getSelectionModel().getSelectedItems().size() == 0);
        greutTextField.setText("");
        inaltTextField.setText("");
        pulsTextField.setText("");
        tenTextField.setText("");
        boliTextArea.setText("");
        noCheck.setSelected(false);
        yesCheck.setSelected(false);
    }

    public void cautaFieldChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        consultatiiTable.setItems(consultatiiList.filtered(x->x.getUser().getNume().contains(newValue) ||
                                                            x.getUser().getPrenume().contains(newValue)));
        donariForConsultTable.setItems(donariForConsultList.filtered(x->x.getUser().getNume().contains(newValue) ||
                                                            x.getUser().getPrenume().contains(newValue)));

    }


    private void cautaCerereChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        cerereTable.setItems(cerereList.filtered(x->x.getNumePacient().contains(newValue) ||
                                                    x.getPrenumePacient().contains(newValue)));
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

    private void cerereSelectionChanged() {
        boolean selected = cerereTable.getSelectionModel().getSelectedItems().size() == 0;
        Cerere cerereSelected = cerereTable.getSelectionModel().getSelectedItem();
        deleteCerere.setDisable(selected || !cerereSelected.getCerereStatus().equals(CerereStatus.Emisa));
        probaCompatibilitateBtn.setDisable(selected || !cerereSelected.getCerereStatus().equals(CerereStatus.Transfer));
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
        updateTables();
    }

    @SuppressWarnings("unused")
    public void deletePressed(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        factory.get(UserProxy.class).remove(user.getId());
        updateTables();
    }

    @SuppressWarnings("unused")
    public void addPressed(ActionEvent actionEvent) throws IOException {
        Register.show(stage, factory, true);
    }


    public void probaCompatibilitatePressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(this.stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        EditProbaCompabilitateMajora.show(stage, factory, cerereTable.getSelectionModel().getSelectedItem());
        updateTables();
    }

    public void deleteCererePressed(ActionEvent actionEvent) throws IOException {
        factory.get(CerereProxy.class).remove(cerereTable.getSelectionModel().getSelectedItem().getIdCerere());
        updateTables();
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
        if (consultatiiTable.getSelectionModel().getSelectedItems().size() == 0){
            new Alert(Alert.AlertType.WARNING, "Selectati o donare!", ButtonType.OK).showAndWait();
            return;
        }
        Donare donare = consultatiiTable.getSelectionModel().getSelectedItem();
        factory.get(DonareProxy.class).finalizare(donare);
        updateTables();
        new Alert(Alert.AlertType.INFORMATION, "Pungile de sange au fost inregistrate!", ButtonType.OK).showAndWait();
    }
    public boolean checkIfFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean checkIfInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void handleAddConsult(){

        if (!checkIfFloat(greutTextField.getText()))
            new Alert(Alert.AlertType.WARNING, "Greutate invalidă!", ButtonType.OK).showAndWait();
        else if (!checkIfInt(inaltTextField.getText()))
            new Alert(Alert.AlertType.WARNING, "Înălțime invalidă!", ButtonType.OK).showAndWait();
        else if (!checkIfInt(tenTextField.getText()))
            new Alert(Alert.AlertType.WARNING, "Tensiune invalidă!", ButtonType.OK).showAndWait();
        else if (!checkIfInt(pulsTextField.getText()))
            new Alert(Alert.AlertType.WARNING, "Puls invalid!", ButtonType.OK).showAndWait();
        else if (!yesCheck.isSelected() && !noCheck.isSelected())
            new Alert(Alert.AlertType.WARNING, "Bifați DA sau NU!", ButtonType.OK).showAndWait();
        else {
            Float greutate = Float.parseFloat(greutTextField.getText());
            Float inaltime = Float.parseFloat(inaltTextField.getText());
            Float tensiune = Float.parseFloat(tenTextField.getText());
            Integer puls = Integer.parseInt(pulsTextField.getText());
            String boli = boliTextArea.getText();
            Integer id = donariForConsultTable.getSelectionModel().getSelectedItem().getId();
            factory.get(ConsultProxy.class).add(id, greutate, tensiune, puls,  boli, inaltime, yesCheck.isSelected());
            updateTables();
        }
    }

    //LAB PART
    public void initGrupeCombo() {
        grupeCombo.setItems(FXCollections.observableArrayList(GrupaSange.values()));
    }

    private void updateDonariForConsult() {
//        donariForConsultTable.getItems().clear();
        donariForConsultList.clear();
        for (Donare donare : factory.get(DonareProxy.class).getAll()) {
            if (donare.getConsult() == null)
//                donariForConsultTable.getItems().add(donare);
                donariForConsultList.add(donare);
        }
    }
    private void updateConsultatii() {
//        consultatiiTable.getItems().clear();
        consultatiiList.clear();
        for (Donare donare : factory.get(DonareProxy.class).getAll()) {
            if (donare.getConsult() != null && donare.getPungiSange().size() == 0 && donare.getStatus() != DonareStatus.Respins)
//                consultatiiTable.getItems().add(donare);
                consultatiiList.add(donare);
        }
    }

    private void updateUsers() {
        usersTable.getItems().clear();
        for (User user : factory.get(UserProxy.class).getAll(RECORDS_ON_PAGE,paginationUsers.getCurrentPageIndex()))
            usersTable.getItems().add(user);
        paginationUsers.setPageCount((int)Math.ceil(factory.get(UserProxy.class).count() / (float)RECORDS_ON_PAGE));
    }

    private void updateCerere() {
//        cerereTable.getItems().clear();
        cerereList.clear();
        for (Cerere cerere : factory.get(CerereProxy.class).getAll())
//            cerereTable.getItems().add(cerere);
            cerereList.add(cerere);
    }

    public void handleTransfer(){
        if (locationToField.getText().equals("") || pungiSangeLabTable.getSelectionModel().getSelectedItems().size() == 0) {
            new Alert(Alert.AlertType.WARNING, "Introduceți locația!", ButtonType.OK).showAndWait();
            return;
        }
        factory.get(DonareProxy.class).addTransfer(pungiSangeLabTable.getSelectionModel().getSelectedItem(), locationToField.getText());
        updateTables();
    }

    public void handleComplete(){
        PungaSange pungaSange = pungiSangeLabTable.getSelectionModel().getSelectedItem();
        if (pungaSange.getTip() == TipPungaSange.Proba) {
            if (imunoText.getText().equals(""))
                new Alert(Alert.AlertType.WARNING, "Completati analizele imuno-hematologice!", ButtonType.OK).showAndWait();
            else if (grupeCombo.getSelectionModel().getSelectedItem() == null)
                new Alert(Alert.AlertType.WARNING, "Selectați o grupă de sânge!", ButtonType.OK).showAndWait();
            else {
                Donare donare = pungaSange.getDonare();
                GrupaSange grupaSange = grupeCombo.getValue();
                String imonoH = imunoText.getText();
                String boli = boliText.getText();
                factory.get(DonareProxy.class).addAnaliza(donare, boli, imonoH, grupaSange);
            }
        }
        else {
            Donare donare = pungaSange.getDonare();
            factory.get(DonareProxy.class).addComponente(donare);
        }
        updateTables();
    }
    public void handleLogOut() throws IOException {
        Login.show(stage, factory);
    }

    public void handleContinue() throws IOException{
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/donorQuestionnaire.fxml"));
        Node parent = donorNowView.getContent();
        ScrollPane root = loader.load();
        DonorQuestionnaire controller = loader.getController();
        controller.init(stage, factory);
        controller.setGoBack(()-> {
            donorNowView.setContent(parent);
            updateTables();
        });
        donorNowView.setContent(root);
    }
}
