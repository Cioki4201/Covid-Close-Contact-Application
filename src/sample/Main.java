package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    ArrayList<Contact> activeContactList = new ArrayList<>();
    Contact newContact = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Parent[] root = {FXMLLoader.load(getClass().getResource("sample.fxml"))};
        primaryStage.setTitle("Covid GUI");

/* ===================================================================================================================================
========================================================  Contacts Tab Start   =======================================================
 ===================================================================================================================================== */

        // --------------------- List of People Row ---------------------
        HBox textBox = new HBox();
        TextArea displayPeople = new TextArea();

        //TextField Formatting
        displayPeople.setMaxSize(600,90);
        displayPeople.setMinSize(600,90);
        displayPeople.setPromptText("No people found");
        displayPeople.setEditable(false);

        textBox.getChildren().addAll(displayPeople);
        textBox.setPadding(new Insets(10, 10, 10, 10));



        // --------------------- Top Text Row ---------------------
        Label topTextLabel = new Label("---------------- Contacts ----------------");
        topTextLabel.setPadding(new Insets(10, 10, 10, 72));



        // --------------------- First Name Input Row ---------------------
        HBox fName = new HBox();
        Label fNameLabel = new Label("Enter First Name");
        TextField fNameInput = new TextField ();
        fName.getChildren().addAll(fNameLabel, fNameInput);
        fName.setSpacing(10);
        fName.setPadding(new Insets(10, 10, 10, 10));



        // --------------------- Last Name Input Row ---------------------
        HBox lName = new HBox();
        Label lNameLabel = new Label("Enter Last Name");
        TextField lNameInput = new TextField ();
        lName.getChildren().addAll(lNameLabel, lNameInput);
        lName.setSpacing(10);
        lName.setPadding(new Insets(10, 10, 10, 10));



        // --------------------- ID Input Row ---------------------
        HBox id = new HBox();
        Label idLabel = new Label("Enter Unique ID");
        Label removeMessage = new Label("(Enter ID to Remove person)");
        TextField idLabelInput = new TextField();
        id.getChildren().addAll(idLabel, idLabelInput, removeMessage);
        id.setSpacing(10);
        id.setPadding(new Insets(10, 10, 10, 10));



        // --------------------- Phone Number Input Row ---------------------
        HBox phoneNumber = new HBox();
        Label phoneNumberLabel = new Label("Enter Phone Number");
        TextField phoneNumberInput = new TextField ();
        phoneNumber.getChildren().addAll(phoneNumberLabel, phoneNumberInput);
        phoneNumber.setSpacing(10);
        phoneNumber.setPadding(new Insets(10, 10, 10, 10));



        // -------------------------------- Add/Remove/List Buttons Row --------------------------------
        HBox addRemoveList = new HBox();
        Label errorField = new Label("");
        errorField.setPadding(new Insets(10, 10, 10, 10));



        //------------------- Add Button -------------------
        Button add = new Button("Add");
        add.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            //Add Button Functionality
            public void handle(ActionEvent actionEvent) {

                //If any field is missing display error message
                if (fNameInput.getLength() == 0 || lNameInput.getLength() == 0 || idLabelInput.getLength() == 0 || phoneNumberInput.getLength() == 0 ){
                    errorField.setText("All fields must be filled in!");
                }

                //if all fields are filled in -->
                else{
                    errorField.setText("");
                    newContact = new Contact(fNameInput.getText(), lNameInput.getText(), idLabelInput.getText(), phoneNumberInput.getText());
                    fNameInput.clear(); lNameInput.clear(); idLabelInput.clear(); phoneNumberInput.clear();   //Clearing all User inputs
                    activeContactList.add(newContact);
                }
            }
        });



        //------------------- Remove Button -------------------
        Button remove = new Button("Remove");

        //Remove Button Functionality
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i = 0; i < activeContactList.size(); i++){
                    Contact activeContact = activeContactList.get(i);
                    if (activeContact.getId().equals(idLabelInput.getText())){ //If an ID match is found, contact removed from ArrayList
                        activeContactList.remove(i);
                        errorField.setText("Contact successfully removed!");
                        break;
                    }

                    if ((i == activeContactList.size()-1) && (!activeContact.getId().equals(idLabelInput.getText()))){ //End of ArrayList and no match
                        errorField.setText("No match was found!");
                        break;
                    }
                }
                if (activeContactList.size() <= 0 || activeContactList == null){ errorField.setText("No People found in list!"); }
                idLabelInput.clear();
            }
        });



        //------------------- List Button -------------------
        Button list = new Button("List");
        addRemoveList.getChildren().addAll(add, remove, list);
        addRemoveList.setSpacing(10);
        addRemoveList.setPadding(new Insets(10, 10, 10, 10));

        //List Button Functionality
        list.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorField.setText("");
                String listText = "";
                for (int i = 0; i < activeContactList.size(); i++){
                    listText = listText + activeContactList.get(i).toString() + "\n";
                }
                displayPeople.setText(listText);
            }
        });



        //-------------------------------- Load/Save/Exit Buttons Row --------------------------------
        HBox loadSaveExit = new HBox();

        //------------------- Load Button -------------------
        Button load = new Button("Load");

        //------------------- Save Button -------------------
        Button save = new Button("Save");

        //Save Button Functionality

        //------------------- Exit Button -------------------
        Button exit = new Button("Exit");

        //Exit Button Functionality
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        HBox exitButtonContainer = new HBox();
        exitButtonContainer.setPadding(new Insets(0, 0, 0, 430));
        exitButtonContainer.getChildren().add(exit);

        loadSaveExit.getChildren().addAll(load, save, exitButtonContainer);
        loadSaveExit.setSpacing(10);
        loadSaveExit.setPadding(new Insets(10, 10, 10, 10));

        VBox tab1Content = new VBox();
        tab1Content.getChildren().addAll(topTextLabel,fName, lName, id, phoneNumber, addRemoveList, errorField, textBox, loadSaveExit);

/* ===================================================================================================================================
=========================================================  Contacts Tab End   ========================================================
 ===================================================================================================================================== */


//                                                          Next Tab...


/* ===================================================================================================================================
=====================================================  Close Contact Tab Start   =====================================================
 ===================================================================================================================================== */

        // --------------------- Variables ---------------------

        ArrayList<CloseContact> closeContactArrayList = new ArrayList<>();

        Label tab2TopTextLabel = new Label("---------------- Close Contacts ----------------");
        tab2TopTextLabel.setPadding(new Insets(10, 10, 10, 72));


        // --------------------- List of Close Contact Row ---------------------
        HBox closeContactList = new HBox();
        TextArea displayCloseContact = new TextArea();

        //TextField Formatting
        displayCloseContact.setMaxSize(600,90);
        displayCloseContact.setMinSize(600,90);
        displayCloseContact.setPromptText("No people found");
        displayCloseContact.setEditable(false);

        closeContactList.getChildren().addAll(displayCloseContact);
        closeContactList.setPadding(new Insets(10, 10, 10, 10));


        // --------------------- Select Contacts Row ---------------------
        HBox selectContacts = new HBox();

        Label contact1Label = new Label("Select Contact 1:");
        ComboBox<Contact> contact1 = new ComboBox<Contact>();
        contact1.setMaxWidth(130);
        contact1.setMinWidth(130);

        Label contact2Label = new Label("Select Contact 1:");
        ComboBox<Contact> contact2 = new ComboBox<Contact>();
        contact2.setMaxWidth(130);
        contact2.setMinWidth(130);



        // --------------------- Select Date/Time Row ---------------------
        HBox selectDateRow = new HBox();

        Label dateLabel = new Label("Select Date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);

        Label timeLabel = new Label("  Select Time:");

        HBox timeSelection = new HBox();
        String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        ComboBox<String> hoursDropDown = new ComboBox<String>(FXCollections.observableArrayList(hours));

        Label colon = new Label(":");

        String[] minutes = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        ComboBox<String> minutesDropDown = new ComboBox<String>(FXCollections.observableArrayList(minutes));
        timeSelection.getChildren().addAll(hoursDropDown, colon, minutesDropDown);
        timeSelection.setSpacing(4);

        selectDateRow.getChildren().addAll(dateLabel, datePicker, timeLabel, timeSelection);
        selectDateRow.setPadding(new Insets(10, 10, 10, 10));
        selectDateRow.setSpacing(10);


        // --------------------- "Add" Row ---------------------
        HBox addCloseContactRow = new HBox();

        Label closeContactErrorMessage = new Label("");
        Button addCloseContactButton = new Button("Add");

        //Add Close Contact button functionality
        addCloseContactButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(contact1.getValue() != null && contact2.getValue() != null && datePicker.getValue() != null && hoursDropDown.getValue() != null && minutesDropDown.getValue() != null){
                    closeContactErrorMessage.setText(""); //Clear error message
                    String hoursAndMinsString = hoursDropDown.getValue() + ":" + minutesDropDown.getValue();
                    CloseContact newCloseContact = new CloseContact(contact1.getValue(), contact2.getValue(), datePicker.getValue().toString(), hoursAndMinsString); //Create new CloseContact

                    closeContactArrayList.add(newCloseContact);

                    String listText = "";
                    for (int i = 0; i < closeContactArrayList.size(); i++){
                        listText = listText + closeContactArrayList.get(i).toString() + "\n";
                    }
                    displayCloseContact.setText(listText);

                    //Clearing selected fields
                    contact1.getSelectionModel().clearSelection();
                    contact2.getSelectionModel().clearSelection();
                    datePicker.getEditor().clear();
                    hoursDropDown.getSelectionModel().clearSelection();
                    minutesDropDown.getSelectionModel().clearSelection();

                }

                else{
                    closeContactErrorMessage.setText("All fields must be filled in!");
                }
            }
        });

        addCloseContactRow.getChildren().addAll(addCloseContactButton, closeContactErrorMessage);
        addCloseContactRow.setSpacing(10);
        addCloseContactRow.setPadding(new Insets(10, 10, 10, 10));

        selectContacts.getChildren().addAll(contact1Label, contact1, contact2Label, contact2);
        selectContacts.setSpacing(10);
        selectContacts.setPadding(new Insets(10, 10, 10, 10));

        VBox tab2Content = new VBox();
        tab2Content.getChildren().addAll(tab2TopTextLabel, selectContacts, selectDateRow, addCloseContactRow, closeContactList);

/* ===================================================================================================================================
======================================================  Close Contact Tab End   ======================================================
 ===================================================================================================================================== */


//                                                          Next Tab...


/* ===================================================================================================================================
=====================================================  Covid Positive Tab Start  =====================================================
 ===================================================================================================================================== */

        // --------------------- Variables ---------------------

        Label tab3TopTextLabel = new Label("---------------- Positive Cases ----------------");
        tab3TopTextLabel.setPadding(new Insets(10, 10, 10, 72));

        //--------------------- Select Covid Positive Contact -------------------
        HBox selectCovidPositiveContact = new HBox();

        Label CPLabel = new Label("Select Covid Positive Contact:");
        ComboBox<Contact> CPContact = new ComboBox<Contact>();
        CPContact.setMaxWidth(130);
        CPContact.setMinWidth(130);

        Button showCloseContact = new Button("Show Close Contacts");

        selectCovidPositiveContact.getChildren().addAll(CPLabel, CPContact, showCloseContact);
        selectCovidPositiveContact.setSpacing(10);
        selectCovidPositiveContact.setPadding(new Insets(10,10,10,10));

        // --------------------- List of Close Contact Row ---------------------
        HBox covidCloseContactList = new HBox();
        TextArea displayCovidCloseContact = new TextArea();

        //TextArea Formatting
        displayCovidCloseContact.setMaxSize(600,90);
        displayCovidCloseContact.setMinSize(600,90);
        displayCovidCloseContact.setPromptText("No people found");
        displayCovidCloseContact.setEditable(false);

        covidCloseContactList.getChildren().addAll(displayCovidCloseContact);
        covidCloseContactList.setPadding(new Insets(10, 10, 10, 10));


        VBox tab3Content = new VBox();
        tab3Content.getChildren().addAll(tab3TopTextLabel, selectCovidPositiveContact, covidCloseContactList);


        //showCloseContacts button functionality

        showCloseContact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String covidListText = "";

                String covidPatientID = CPContact.getValue().getId();
                for (int i = 0; i < closeContactArrayList.size(); i++){
                    if ( (closeContactArrayList.get(i).getContact1().getId().equals(covidPatientID)) || (closeContactArrayList.get(i).getContact2().getId().equals(covidPatientID))){
                        covidListText = covidListText + closeContactArrayList.get(i).toString() + "\n";
                    }
                }
                displayCovidCloseContact.setText(covidListText);
            }
        });

/* ===================================================================================================================================
======================================================  Covid Positive Tab End  ======================================================
 ===================================================================================================================================== */

        //Load Button Functionality
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FileChooser filechooser = new FileChooser();
                    filechooser.setTitle("Select File to Load");
                    filechooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    File selectedFile = filechooser.showOpenDialog(primaryStage);
                    Scanner scanner = new Scanner(selectedFile);
                    activeContactList = new ArrayList<>();
                    newContact = null;
                    Contact c1 = null;
                    Contact c2 = null;

                    while(scanner.hasNextLine()){

                        String loadedContact = scanner.nextLine();
                        String str[] = loadedContact.split(",");

                        if (str.length < 5) {
                            newContact = new Contact(str[0], str[1], str[2], str[3]);
                            activeContactList.add(newContact);
                        }

                        else{
                            for (int i = 0; i<activeContactList.size(); i++){
                                if (activeContactList.get(i).getId().equals(str[1])){
                                    c1 = activeContactList.get(i);
                                }
                                else if (activeContactList.get(i).getId().equals(str[2])){
                                    c2 = activeContactList.get(i);
                                }
                            }
                            CloseContact cc = new CloseContact(c1, c2, str[3], str[4]);
                            closeContactArrayList.add(cc);

                            String listText = "";
                            for (int i = 0; i < closeContactArrayList.size(); i++){
                                listText = listText + closeContactArrayList.get(i).toString() + "\n";
                            }
                            displayCloseContact.setText(listText);
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //Save Button Functionality
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Save) {
                Stage stage = new Stage();
                stage.setTitle("Save File");

                HBox content = new HBox();
                Label fileName = new Label("File Name: ");
                Button save = new Button("Save File");
                TextField fileNameInput = new TextField();
                content.getChildren().addAll(fileName, fileNameInput, save);
                content.setPadding(new Insets(30, 30, 30, 30));
                content.setSpacing(10);

                save.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        FileWriter contactsFile = null;
                        try {
                            File filePathName = new File(System.getProperty("user.dir"), (fileNameInput.getText() + ".txt"));
                            contactsFile = new FileWriter(filePathName);

                            for (int i = 0; i < activeContactList.size(); i++){
                                Contact activeContact = activeContactList.get(i);
                                contactsFile.write(activeContact.getfName() + "," + activeContact.getlName() + "," + activeContact.getId() + "," + activeContact.getPhoneNo() + "\n");
                            }

                            for (int i = 0; i < closeContactArrayList.size(); i++){
                                CloseContact id = closeContactArrayList.get(i);
                                contactsFile.write( "cc," + id.getContact1().getId() + "," + id.getContact2().getId() + "," + id.getDate() + "," + id.getTime() + "\n");
                            }

                            contactsFile.flush();
                            contactsFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.close();
                    }
                });

                stage.setScene(new Scene(content, 360, 100));
                stage.show();
            }
        });




        // --------------------- Configuring Tabs ---------------------
        TabPane tabPane = new TabPane();

        Tab contactsTab = new Tab("Contacts", tab1Content);
        Tab closeContactTab = new Tab("Close Contact", tab2Content);
        Tab positiveCases = new Tab("Positive Cases", tab3Content);

        closeContactTab.setOnSelectionChanged(new EventHandler<Event>() { //When switching to Close Contact tab, update drop down contact selection menu
            @Override
            public void handle(Event t) {
                if (closeContactTab.isSelected()) {
                    contact1.setItems(FXCollections.observableArrayList(activeContactList));
                    contact2.setItems(FXCollections.observableArrayList(activeContactList));
                }
            }
        });

        positiveCases.setOnSelectionChanged(new EventHandler<Event>() { //When switching to Positive Cases tab, update drop down contact selection menu
            @Override
            public void handle(Event t) {
                if (positiveCases.isSelected()) {
                    CPContact.setItems(FXCollections.observableArrayList(activeContactList));
                }
            }
        });

        tabPane.getTabs().addAll(contactsTab, closeContactTab, positiveCases);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //Disables close tab option

        primaryStage.setScene(new Scene(tabPane, 630, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
