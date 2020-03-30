package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class ModalController {

    @FXML
    private Label lblError;

    @FXML
    private Button btnOk;

    public void actionOk(ActionEvent actionEvent){
        closeStage(actionEvent);
        EditController.closeAfterErrorMessage = false;
    }

    public void actionCancel(ActionEvent actionEvent){
        closeStage(actionEvent);
        EditController.closeAfterErrorMessage = true;
    }

    private void closeStage(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
