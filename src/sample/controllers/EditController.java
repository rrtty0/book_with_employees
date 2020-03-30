package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.objects.Employee;
import sample.sources.Main;
import sample.utils.LocaleManager;
import java.net.URL;
import java.util.ResourceBundle;


public class EditController implements Initializable {

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPassport;

    @FXML
    private TextField txtSalary;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;


    private Employee employee;

    private ResourceBundle resourceBundle;
    private Parent modalFxml;
    private Stage modalStage;
    private Window editWindow;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private static final String FXML_MODAL = "../fxml/modal.fxml";

    public static boolean closeAfterErrorMessage;




    public void setEmployee(Employee employee)
    {
        this.employee = employee;

        txtFullName.setText(employee.getFullName());
        txtPhone.setText(employee.getPhone());
        txtPassport.setText(employee.getPassport());
        txtSalary.setText(Double.toString(employee.getSalary()));
    }

    public void actionOk(ActionEvent actionEvent) {
        if(editWindow == null)
            editWindow = ((Node)actionEvent.getSource()).getScene().getWindow();

        if(employee.fullNameIsCorrect(txtFullName.getText()) && employee.phoneIsCorrect(txtPhone.getText()) &&
           employee.passportIsCorrect(txtPassport.getText()) && employee.salaryIsCorrect(txtSalary.getText())) {
              employee.setFullName(txtFullName.getText());
              employee.setPhone(txtPhone.getText());
              employee.setPassport(txtPassport.getText());
              employee.setSalary(Double.parseDouble(txtSalary.getText()));
              closeWithSave(actionEvent);
        }
        else
        {
            showModalStage();
            if(closeAfterErrorMessage)
                actionClose(actionEvent);
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        MainFxmlController.saveChanges = false;
        closeStage(actionEvent);
    }

    private void closeWithSave(ActionEvent actionEvent){
        MainFxmlController.saveChanges = true;
        closeStage(actionEvent);
    }

    private void closeStage(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public Employee getEmployee()
    {
        return employee;
    }

    private void showModalStage()
    {
        if(modalStage == null)
        {
            modalStage = new Stage();
            modalStage.setTitle(resourceBundle.getString("key.errorTitle"));
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image("sample/img/error_icon.png"));
            modalStage.setScene(new Scene(modalFxml));
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(editWindow);
        }

        modalStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        try {
            fxmlLoader.setLocation(getClass().getResource(FXML_MODAL));
            fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
            modalFxml = fxmlLoader.load();
        }
        catch (Exception e)
        {

        }
    }
}
