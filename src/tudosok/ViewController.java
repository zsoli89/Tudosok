package tudosok;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ViewController implements ChangeListener, Initializable {

    @FXML
    TableView table;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputEmail;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;
    @FXML
    AnchorPane anchor;
    @FXML
    SplitPane mainSplit;
    @FXML
    TextField inputAge;
    @FXML
    TextField inputTypeDegree;
    @FXML
    private Pane scientistPane;
    @FXML
    private Pane inventorPane;
    @FXML
    private TextField inputLastnameInv;
    @FXML
    private TextField inputFirstNameInv;
    @FXML
    private TextField inputEmailInv;
    @FXML
    private TextField inputAgeInv;
    @FXML
    private TextField inputInvention;
    @FXML
    private Button addNewContactButton2;
    @FXML
    private ComboBox box;

    DB db = new DB();

    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";

    //javaoktato.com
//    private final ObservableList<Person> data
//            = FXCollections.observableArrayList(
//                    new Person("Super", "Man", "glasses@secret.com","35","phd"));
//                    
    private final ObservableList<Person> data = FXCollections.observableArrayList();

    ObservableList<String> comboList = FXCollections.observableArrayList("Tudós", "Feltaláló");

    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        int ages = Integer.parseInt(inputAge.getText());
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
                if (ages >= 28) {
//            data.add(new Person(inputLastname.getText(), inputFirstName.getText(), email));
                    Person newPerson = new Person(inputLastname.getText(), inputFirstName.getText(), email,
                            inputAge.getText());
                    data.add(newPerson);
                    db.addContact(newPerson);
                    inputLastname.clear();
                    inputFirstName.clear();
                    inputEmail.clear();
                    inputAge.clear();
//                    inputTypeDegree.clear();
                } else {
                    alert("Legalább 28 évesnek kell lenned.");
                }
        } else {
            alert("Adj meg egy valódi email címet.");
        }
    }
    
    @FXML
    private void addContact2(ActionEvent event) {
        String email = inputEmailInv.getText();
        int ages = Integer.parseInt(inputAgeInv.getText());
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
                if (ages >= 28) {
//            data.add(new Person(inputLastname.getText(), inputFirstName.getText(), email));
                    Person newPerson = new Person(inputLastnameInv.getText(), inputFirstNameInv.getText(), email,
                            inputAgeInv.getText());
                    data.add(newPerson);
                    db.addContact(newPerson);
                    inputLastnameInv.clear();
                    inputFirstNameInv.clear();
                    inputEmailInv.clear();
                    inputAgeInv.clear();
                } else {
                    alert("Legalább 28 évesnek kell lenned.");
                }
        } else {
            alert("Adj meg egy valódi email címet.");
        }
    }
    

    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, data);
        } else {
            alert("Adj meg egy fájlnevet");
        }
    }

    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(120);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                /*((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setLastName(t.getNewValue());*/
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setLastName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(120);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setFirstName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setEmail(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn ageCol = new TableColumn("Életkor");
        ageCol.setMinWidth(40);
        ageCol.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));
        ageCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ageCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setAge(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

//        TableColumn typeDegreeCol = new TableColumn("Fokozat");
//        typeDegreeCol.setMinWidth(40);
//        typeDegreeCol.setCellValueFactory(new PropertyValueFactory<Person, String>("typedegree"));
//        typeDegreeCol.setCellFactory(TextFieldTableCell.forTableColumn());
//
//        typeDegreeCol.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Person, String> t) {
//                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
//                actualPerson.setTypeDegree(t.getNewValue());
//                db.updateContact(actualPerson);
//            }
//        }
//        );
        TableColumn removeCol = new TableColumn("Törlés");
        removeCol.setMinWidth(75);

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory
                = new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            @Override
            public TableCell call(final TableColumn<Person, String> param) {
                final TableCell<Person, String> cell = new TableCell<Person, String>() {
                    final Button btn = new Button("Törlés");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event)
                                    -> {
                                Person person = getTableView().getItems().get(getIndex());
                                //System.out.println(person.toString());
                                data.remove(person);
                                db.removeContact(person);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        removeCol.setCellFactory(cellFactory);

        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol, ageCol, removeCol);

        data.addAll(db.getAllContacts());

        table.setItems(data);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        nodeItemA.setExpanded(true);

        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        /*
        nodeItemA.getChildren().add(nodeItemA1);
        nodeItemA.getChildren().add(nodeItemA2);*/
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }
            }
        });
    }

    //helyben kezelhető hibaüzenet
    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);

        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });

        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);   //muszáj pozicionálni
        anchor.setLeftAnchor(vbox, 300.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
        //alert("Teszt");
        box.setItems(comboList);
    }

    @Override
    public void changed(ObservableValue ov, Object t, Object t1) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void switchPerson(ActionEvent event) {
        int selection = box.getSelectionModel().getSelectedIndex();
        switch (selection) {
            case 0:
                scientistPane.setVisible(true);
                inventorPane.setVisible(false);
                break;
            case 1:
                scientistPane.setVisible(false);
                inventorPane.setVisible(true);
                break;
        }
    }

    public boolean incorrectInteger(String text) {
        if(text == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;         
    }

    

}
