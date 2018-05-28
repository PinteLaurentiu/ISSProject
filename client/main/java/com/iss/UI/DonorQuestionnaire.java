package com.iss.UI;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;


public class DonorQuestionnaire {
    public ScrollPane thisScroll;
    public AnchorPane thisPane;
    public JFXCheckBox one;
    public JFXCheckBox oneOne;
    public JFXCheckBox oneTwo;
    public JFXCheckBox oneThree;

    public JFXCheckBox two;
    public JFXCheckBox threeOne;
    public JFXCheckBox threeTwo;
    public JFXCheckBox threeThree;
    public JFXCheckBox threeFour;
    public JFXCheckBox threeFive;
    public JFXCheckBox threeSix;
    public JFXCheckBox threeSeven;
    public JFXCheckBox threeEight;
    public JFXCheckBox threeNine;
    public JFXCheckBox threeTen;
    public JFXCheckBox fourOne;
    public JFXCheckBox fourTwo;
    public JFXCheckBox fourThree;
    public JFXDatePicker fourFour;
    public JFXDatePicker fourFive;
    public JFXCheckBox five;
    public JFXTextField fiveOne;
    public JFXCheckBox six;
    public JFXCheckBox seven;
    public JFXCheckBox eightOne;
    public JFXCheckBox eightTwo;
    public JFXCheckBox eightThree;
    public JFXCheckBox eightFour;
    public JFXCheckBox eightFive;
    public JFXCheckBox eightSix;
    public JFXCheckBox eightSeven;
    public JFXCheckBox eightEight;
    public JFXCheckBox nine;
    public JFXCheckBox ten;

    public JFXCheckBox oneN;
    public JFXCheckBox oneOneN;
    public JFXCheckBox oneTwoN;
    public JFXCheckBox oneThreeN;
    public JFXCheckBox twoN;
    public JFXCheckBox threeOneN;
    public JFXCheckBox threeTwoN;
    public JFXCheckBox threeThreeN;
    public JFXCheckBox threeFourN;
    public JFXCheckBox threeFiveN;
    public JFXCheckBox threeSixN;
    public JFXCheckBox threeSevenN;
    public JFXCheckBox threeEightN;
    public JFXCheckBox threeNineN;
    public JFXCheckBox threeTenN;
    public JFXCheckBox fourOneN;
    public JFXCheckBox fourTwoN;
    public JFXCheckBox fourThreeN;
    public JFXCheckBox fiveN;
    public JFXCheckBox sixN;
    public JFXCheckBox sevenN;
    public JFXCheckBox eightOneN;
    public JFXCheckBox eightTwoN;
    public JFXCheckBox eightThreeN;
    public JFXCheckBox eightFourN;
    public JFXCheckBox eightFiveN;
    public JFXCheckBox eightSixN;
    public JFXCheckBox eightSevenN;
    public JFXCheckBox eightEightN;
    public JFXCheckBox nineN;
    public JFXCheckBox tenN;
    public Button continueButton;

    public StackPane thisStack;


    @FXML
    public void initialize(){
        addNodes();
        handleChange();
    }

    public void handleChange(){

        one.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneN.isSelected() && oldValue==false){
                    one.setSelected(newValue);
                    oneN.setSelected(false);

                }

            }
        });
        oneN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(one.isSelected() && oldValue==false){
                    oneN.setSelected(newValue);
                    one.setSelected(false);
                }
            }
        });

        oneOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneOneN.isSelected() && oldValue==false){
                    oneOne.setSelected(newValue);
                    oneOneN.setSelected(false);

                }

            }
        });
        oneOneN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneOne.isSelected() && oldValue==false){
                    oneOneN.setSelected(newValue);
                    oneOne.setSelected(false);
                }
            }
        });


        oneTwo.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneTwoN.isSelected() && oldValue==false){
                    oneTwo.setSelected(newValue);
                    oneTwoN.setSelected(false);

                }

            }
        });
        oneTwoN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneTwo.isSelected() && oldValue==false){
                    oneTwoN.setSelected(newValue);
                    oneTwo.setSelected(false);
                }
            }
        });


        oneTwo.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneTwoN.isSelected() && oldValue==false){
                    oneTwo.setSelected(newValue);
                    oneTwoN.setSelected(false);

                }

            }
        });
        oneTwoN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneTwo.isSelected() && oldValue==false){
                    oneTwoN.setSelected(newValue);
                    oneTwo.setSelected(false);
                }
            }
        });
        oneThree.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneThreeN.isSelected() && oldValue==false){
                    oneThree.setSelected(newValue);
                    oneThreeN.setSelected(false);

                }

            }
        });
        oneThreeN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oneThree.isSelected() && oldValue==false){
                    oneThreeN.setSelected(newValue);
                    oneThree.setSelected(false);
                }
            }
        });


        two.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(twoN.isSelected() && oldValue==false){
                    two.setSelected(newValue);
                    twoN.setSelected(false);

                }

            }
        });
        twoN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(two.isSelected() && oldValue==false){
                    twoN.setSelected(newValue);
                    two.setSelected(false);
                }
            }
        });

        threeOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeOneN.isSelected() && oldValue==false){
                    threeOne.setSelected(newValue);
                    threeOneN.setSelected(false);

                }

            }
        });
        threeOneN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeOne.isSelected() && oldValue==false){
                    threeOneN.setSelected(newValue);
                    threeOne.setSelected(false);
                }
            }
        });


        threeTwo.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeTwoN.isSelected() && oldValue==false){
                    threeTwo.setSelected(newValue);
                    threeTwoN.setSelected(false);

                }

            }
        });
        threeTwoN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeTwo.isSelected() && oldValue==false){
                    threeTwoN.setSelected(newValue);
                    threeTwo.setSelected(false);
                }
            }
        });


        threeThree.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeThreeN.isSelected() && oldValue==false){
                    threeThree.setSelected(newValue);
                    threeThreeN.setSelected(false);

                }

            }
        });
        threeThreeN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeThree.isSelected() && oldValue==false){
                    threeThreeN.setSelected(newValue);
                    threeThree.setSelected(false);
                }
            }
        });

        threeFour.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeFourN.isSelected() && oldValue==false){
                    threeFour.setSelected(newValue);
                    threeFourN.setSelected(false);

                }

            }
        });
        threeFourN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeFour.isSelected() && oldValue==false){
                    threeFourN.setSelected(newValue);
                    threeFour.setSelected(false);
                }
            }
        });


        threeFive.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeFiveN.isSelected() && oldValue==false){
                    threeFive.setSelected(newValue);
                    threeFiveN.setSelected(false);

                }

            }
        });
        threeFiveN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeFive.isSelected() && oldValue==false){
                    threeFiveN.setSelected(newValue);
                    threeFive.setSelected(false);
                }
            }
        });


        threeSix.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeSixN.isSelected() && oldValue==false){
                    threeSix.setSelected(newValue);
                    threeSixN.setSelected(false);

                }

            }
        });
        threeSixN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeSix.isSelected() && oldValue==false){
                    threeSixN.setSelected(newValue);
                    threeSix.setSelected(false);
                }
            }
        });


        threeSeven.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeSevenN.isSelected() && oldValue==false){
                    threeSeven.setSelected(newValue);
                    threeSevenN.setSelected(false);

                }

            }
        });
        threeSevenN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeSeven.isSelected() && oldValue==false){
                    threeSevenN.setSelected(newValue);
                    threeSeven.setSelected(false);
                }
            }
        });

        threeEight.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeEightN.isSelected() && oldValue==false){
                    threeEight.setSelected(newValue);
                    threeEightN.setSelected(false);

                }

            }
        });
        threeEightN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeEight.isSelected() && oldValue==false){
                    threeEightN.setSelected(newValue);
                    threeEight.setSelected(false);
                }
            }
        });

        threeNine.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeNineN.isSelected() && oldValue==false){
                    threeNine.setSelected(newValue);
                    threeNineN.setSelected(false);

                }

            }
        });
        threeNineN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeNine.isSelected() && oldValue==false){
                    threeNineN.setSelected(newValue);
                    threeNine.setSelected(false);
                }
            }
        });

        threeTen.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeTenN.isSelected() && oldValue==false){
                    threeTen.setSelected(newValue);
                    threeTenN.setSelected(false);

                }

            }
        });
        threeTenN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(threeTen.isSelected() && oldValue==false){
                    threeTenN.setSelected(newValue);
                    threeTen.setSelected(false);
                }
            }
        });


        fourOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourOneN.isSelected() && oldValue==false){
                    fourOne.setSelected(newValue);
                    fourOneN.setSelected(false);

                }

            }
        });
        fourOneN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourOne.isSelected() && oldValue==false){
                    fourOneN.setSelected(newValue);
                    fourOne.setSelected(false);
                }
            }
        });
        fourOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourOneN.isSelected() && oldValue==false){
                    fourOne.setSelected(newValue);
                    fourOneN.setSelected(false);

                }

            }
        });
        fourOneN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourOne.isSelected() && oldValue==false){
                    fourOneN.setSelected(newValue);
                    fourOne.setSelected(false);
                }
            }
        });
        fourTwo.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourTwoN.isSelected() && oldValue==false){
                    fourTwo.setSelected(newValue);
                    fourTwoN.setSelected(false);

                }

            }
        });
        fourTwoN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourTwo.isSelected() && oldValue==false){
                    fourTwoN.setSelected(newValue);
                    fourTwo.setSelected(false);
                }
            }
        });
        fourThree.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourThreeN.isSelected() && oldValue==false){
                    fourThree.setSelected(newValue);
                    fourThreeN.setSelected(false);

                }

            }
        });
        fourThreeN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fourThree.isSelected() && oldValue==false){
                    fourThreeN.setSelected(newValue);
                    fourThree.setSelected(false);
                }
            }
        });
        five.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(fiveN.isSelected() && oldValue==false){
                    five.setSelected(newValue);
                    fiveN.setSelected(false);

                }

            }
        });
        fiveN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(five.isSelected() && oldValue==false){
                    fiveN.setSelected(newValue);
                    five.setSelected(false);
                }
            }
        });

        six.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(sixN.isSelected() && oldValue==false){
                    six.setSelected(newValue);
                    sixN.setSelected(false);

                }

            }
        });
        sixN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(six.isSelected() && oldValue==false){
                    sixN.setSelected(newValue);
                    six.setSelected(false);
                }
            }
        });


        seven.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(sevenN.isSelected() && oldValue==false){
                    seven.setSelected(newValue);
                    sevenN.setSelected(false);

                }

            }
        });
        sevenN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(seven.isSelected() && oldValue==false){
                    sevenN.setSelected(newValue);
                    seven.setSelected(false);
                }
            }
        });


        eightOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightOneN.isSelected() && oldValue==false){
                    eightOne.setSelected(newValue);
                    eightOneN.setSelected(false);

                }

            }
        });
        eightOneN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightOne.isSelected() && oldValue==false){
                    eightOneN.setSelected(newValue);
                    eightOne.setSelected(false);
                }
            }
        });


        eightTwo.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightTwoN.isSelected() && oldValue==false){
                    eightTwo.setSelected(newValue);
                    eightTwoN.setSelected(false);

                }

            }
        });
        eightTwoN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightTwo.isSelected() && oldValue==false){
                    eightTwoN.setSelected(newValue);
                    eightTwo.setSelected(false);
                }
            }
        });


        eightThree.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightThreeN.isSelected() && oldValue==false){
                    eightThree.setSelected(newValue);
                    eightThreeN.setSelected(false);

                }

            }
        });
        eightThreeN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightThree.isSelected() && oldValue==false){
                    eightThreeN.setSelected(newValue);
                    eightThree.setSelected(false);
                }
            }
        });

        eightFour.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightFourN.isSelected() && oldValue==false){
                    eightFour.setSelected(newValue);
                    eightFourN.setSelected(false);

                }

            }
        });
        eightFourN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightFour.isSelected() && oldValue==false){
                    eightFourN.setSelected(newValue);
                    eightFour.setSelected(false);
                }
            }
        });
        eightFive.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightFiveN.isSelected() && oldValue==false){
                    eightFive.setSelected(newValue);
                    eightFiveN.setSelected(false);

                }

            }
        });
        eightFiveN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightFive.isSelected() && oldValue==false){
                    eightFiveN.setSelected(newValue);
                    eightFive.setSelected(false);
                }
            }
        });
        eightSix.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightSixN.isSelected() && oldValue==false){
                    eightSix.setSelected(newValue);
                    eightSixN.setSelected(false);

                }

            }
        });
        eightSixN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightSix.isSelected() && oldValue==false){
                    eightSixN.setSelected(newValue);
                    eightSix.setSelected(false);
                }
            }
        });

        eightSeven.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightSevenN.isSelected() && oldValue==false){
                    eightSeven.setSelected(newValue);
                    eightSevenN.setSelected(false);

                }

            }
        });
        eightSevenN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightSeven.isSelected() && oldValue==false){
                    eightSevenN.setSelected(newValue);
                    eightSeven.setSelected(false);
                }
            }
        });
        eightEight.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightEightN.isSelected() && oldValue==false){
                    eightEight.setSelected(newValue);
                    eightEightN.setSelected(false);

                }

            }
        });
        eightEightN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(eightEight.isSelected() && oldValue==false){
                    eightEightN.setSelected(newValue);
                    eightEight.setSelected(false);
                }
            }
        });
        nine.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(nineN.isSelected() && oldValue==false){
                    nine.setSelected(newValue);
                    nineN.setSelected(false);

                }

            }
        });
        nineN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(nine.isSelected() && oldValue==false){
                    nineN.setSelected(newValue);
                    nine.setSelected(false);
                }
            }
        });

        ten.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(tenN.isSelected() && oldValue==false){
                    ten.setSelected(newValue);
                    tenN.setSelected(false);

                }

            }
        });
        tenN.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(ten.isSelected() && oldValue==false){
                    tenN.setSelected(newValue);
                    ten.setSelected(false);
                }
            }
        });


    }
    BoxBlur blur = new BoxBlur(3,3,3);
    ArrayList<Node> nodes = new ArrayList<>();
    public void addNodes(){
        for(Node n:thisPane.getChildren()){
            nodes.add(n);
        }
    }
    public void setBlur(){
        for(Node n:nodes){
            if(n.getId()!=null  && !n.getId().equals("thisStack")) {
                n.setEffect(blur);
            }
            else if(n.getId()==null) {
                n.setEffect(blur);
            }
        }
    }

    public void setUnblur(){
        for(Node n:nodes){
            n.setEffect(null);
        }
    }
    public void validate() throws  IOException {
        if(!(one.isSelected() || oneN.isSelected())) {
                this.thisScroll.setVvalue(0.0);
                setBlur();
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading(new Text("Eroare"));
                layout.setBody(new Text("Nu ati completat toate campurile!"));
                JFXDialog dialog = new JFXDialog(thisStack,layout, JFXDialog.DialogTransition.TOP);
                JFXButton button = new JFXButton("Ok");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setUnblur();
                        dialog.close();
                    }
                });
                layout.setActions(button);
                dialog.show();

            }
        else{

            checkIfOkForDonate();
        }
    }
    public void loadDetailDonor(boolean goodOrBad) throws IOException{
        FXMLLoader loader = new FXMLLoader(DonorQuestionnaire.class.getResource("/detailDonation.fxml"));
        AnchorPane root = loader.load();
        DetailDonation detailDonation = loader.getController();
        thisScroll.setContent(root);
        thisScroll.setVvalue(0.0);
        if(!goodOrBad) {
                detailDonation.titleLabel.setText("Ne pare rau , nu sunteti eligibil pentru donare.");
                detailDonation.hideOrShow(false);
        }
        else{
                detailDonation.titleLabel.setText("Felicitari! Sunteti eligibil pentru donare");
                detailDonation.hideOrShow(true);

        }

    }

    public void checkIfOkForDonate() throws  IOException{
       if(oneOne.isSelected() || oneTwo.isSelected() || oneThree.isSelected() ||
               threeOne.isSelected() || threeTwo.isSelected() || threeFour.isSelected() ||
               threeFive.isSelected() || threeSeven.isSelected() || fourOne.isSelected() ||
               fourTwo.isSelected() || fourThree.isSelected() || seven.isSelected() ||
               eightOne.isSelected() || eightTwo.isSelected() || eightThree.isSelected() || eightFour.isSelected()
               || eightFive.isSelected() || eightSix.isSelected() || eightEight.isSelected()){
                loadDetailDonor(false);
       }
       else{
                loadDetailDonor(true);
       }
    }

    public void handleContinue() throws IOException{
        validate();
    }


}
