package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import sample.sources.Main;
import sample.objects.Employee;
import sample.objects.Person;
import sample.utils.CollectionListOfEmployees;
import sample.utils.Lang;
import sample.utils.LocaleManager;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainFxmlController extends Observable implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView tableListOfEmployees;

    @FXML
    private TableColumn<Employee, String> columnFullName;

    @FXML
    private TableColumn<Employee, String> columnPhone;

    @FXML
    private TableColumn<Employee, String> columnPassport;

    @FXML
    private TableColumn<Employee, Double> columnSalary;

    @FXML
    private Label lblCount;

    @FXML
    private ComboBox<Lang> comboLocale;



    private static final String FXML_EDIT = "../fxml/edit.fxml";

    private CollectionListOfEmployees listOfEmployees = new CollectionListOfEmployees();
    private ResourceBundle resourceBundle;
    private EditController editController;
    private Window mainWindow;
    private Parent editFxml;
    private Stage editStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private ObservableList<Employee> backUpListOfEmployees = FXCollections.observableArrayList();

    public static boolean saveChanges;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resourceBundle = resources;

        columnFullName.setCellValueFactory(new PropertyValueFactory<Employee, String>("fullName"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
        columnPassport.setCellValueFactory(new PropertyValueFactory<Employee, String>("passport"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));

        initListeners();
        fillData();
        fillLangComboBox();
        initLoadEditFXML();
    }

    private void fillLangComboBox()
    {
        Lang langRU = new Lang(0, resourceBundle.getString("key.ru"), LocaleManager.RU_LOCALE);
        Lang langEN = new Lang(1, resourceBundle.getString("key.en"), LocaleManager.EN_LOCALE);

        comboLocale.getItems().add(langRU);
        comboLocale.getItems().add(langEN);

        if(LocaleManager.getCurrentLang() == null)
        {
            comboLocale.getSelectionModel().select(0);
            LocaleManager.setCurrentLang(langRU);
        }
        else
            comboLocale.getSelectionModel().select(LocaleManager.getCurrentLang().getIndex());
    }

    private void fillData() {
        listOfEmployees.readFromFile();
        backUpListOfEmployees.addAll(listOfEmployees.getEmployeeList());
        tableListOfEmployees.setItems(listOfEmployees.getEmployeeList());
        if(listOfEmployees.getEmployeeList().isEmpty())
            updateCountlabel();
    }

    private void initListeners()
    {
        listOfEmployees.getEmployeeList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountlabel();
            }
        });

        tableListOfEmployees.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2)
                {
                    if(mainWindow == null)
                        mainWindow = ((Node)event.getSource()).getScene().getWindow();

                    editController.setEmployee((Employee) tableListOfEmployees.getSelectionModel().getSelectedItem());
                    showEditStage();
                    normalizeListOfEmployees();
                    listOfEmployees.writeToFile();
                }
            }
        });

        comboLocale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lang selectedLang = (Lang) comboLocale.getSelectionModel().getSelectedItem();
                LocaleManager.setCurrentLang(selectedLang);
                setChanged();
                notifyObservers(selectedLang);
            }
        });
    }

    private void normalizeListOfEmployees(){
        if(!listOfEmployees.getEmployeeList().equals(backUpListOfEmployees))
        {
            txtSearch.setText("");
            listOfEmployees.getEmployeeList().clear();
            listOfEmployees.getEmployeeList().addAll(backUpListOfEmployees);
        }
    }

    private void updateCountlabel()
    {
        lblCount.setText(resourceBundle.getString("key.count") + ": " + listOfEmployees.getEmployeeList().size());
    }

    private void showEditStage()
    {
        if(editStage == null)
        {
            editStage = new Stage();
            editStage.setTitle(resourceBundle.getString("key.edit"));
            editStage.setResizable(false);
            editStage.getIcons().add(new Image("sample/img/icon.png"));
            editStage.setScene(new Scene(editFxml));
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(mainWindow);
            editStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    saveChanges = false;
                }
            });
        }

        editStage.showAndWait();
    }

    private void initLoadEditFXML()
    {
        try {
            fxmlLoader.setLocation(getClass().getResource(FXML_EDIT));
            fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
            editFxml = fxmlLoader.load();
            editController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button))
            return;

        Button clickedButton = (Button) source;

        if(mainWindow == null)
            mainWindow = ((Node)actionEvent.getSource()).getScene().getWindow();


        switch (clickedButton.getId())
        {
            case "btnAdd":
                editController.setEmployee(new Employee());
                showEditStage();
                if(saveChanges){
                    normalizeListOfEmployees();
                    listOfEmployees.add(editController.getEmployee());
                    backUpListOfEmployees.clear();
                    backUpListOfEmployees.addAll(listOfEmployees.getEmployeeList());
                    listOfEmployees.writeToFile();
                }
                break;

            case "btnEdit":
                if(tableListOfEmployees.getSelectionModel().getSelectedItem() != null)
                {
                    editController.setEmployee((Employee)tableListOfEmployees.getSelectionModel().getSelectedItem());
                    showEditStage();
                    normalizeListOfEmployees();
                    listOfEmployees.writeToFile();
                }

                break;

            case "btnDelete":
                txtSearch.setText("");
                backUpListOfEmployees.remove((Employee) tableListOfEmployees.getSelectionModel().getSelectedItem());
                listOfEmployees.getEmployeeList().clear();
                listOfEmployees.getEmployeeList().addAll(backUpListOfEmployees);
                listOfEmployees.writeToFile();
                break;
        }
    }

    public void actionSearch(ActionEvent actionEvent) {
        listOfEmployees.getEmployeeList().clear();

        for(Employee employee : backUpListOfEmployees)
            if(employee.getFullName().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    employee.getPhone().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    employee.getPassport().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    Double.toString(employee.getSalary()).toLowerCase().contains(txtSearch.getText().toLowerCase()))
                listOfEmployees.add(employee);
    }
}
