package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ToDoListController {

    @FXML
    private Label lbl_currentDate;

    @FXML
    private DatePicker taskDatePicker;

    @FXML
    private ListView<?> todoListView;

    @FXML
    private TextArea txt_AddTask;

    @FXML
    void btn_AddOnAction(ActionEvent event) {

    }

    @FXML
    void btn_RemoveOnAction(ActionEvent event) {

    }

    @FXML
    void btn_UpdateOnAction(ActionEvent event) {

    }

}
