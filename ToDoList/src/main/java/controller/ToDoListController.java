package controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import model.ToDoItem;

import java.time.LocalDate;
import java.util.Date;

public class ToDoListController {

    @FXML
    private Label lblCurrentDate;

    @FXML
    private DatePicker taskDatePicker;

    @FXML
    private ListView<ToDoItem> todoListView;

    @FXML
    private TextArea txt_AddTask;

    @FXML
    private TableView<ToDoItem> todoTableView;

    @FXML
    private TableColumn<ToDoItem, String> colTask;

    @FXML
    private TableColumn<ToDoItem, Date> colDate;

    @FXML
    void btnAddOnAction(ActionEvent evt) {
        String taskText = txt_AddTask.getText();
        LocalDate date = taskDatePicker.getValue();

        if (taskText == null || taskText.isEmpty() || date == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter a task and a date");
            alert.showAndWait();

            return;
        }

        ToDoItem newItem = new ToDoItem();
        newItem.setTask(taskText);
        newItem.setDate(java.sql.Date.valueOf(date));
        newItem.setDone(false);

        todoListView.getItems().add(newItem);
        txt_AddTask.clear();
        taskDatePicker.setValue(null);

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            todoListView.getItems().remove(selectedItem);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txt_AddTask.setText(selectedItem.getTask());
            taskDatePicker.setValue(((java.sql.Date) selectedItem.getDate()).toLocalDate());
            todoListView.getItems().remove(selectedItem);
        }
    }

    @FXML
    public void initialize() {

        if (taskDatePicker.getValue() != null){
            lblCurrentDate.setText(taskDatePicker.getValue().toString());
        }else{
            lblCurrentDate.setText(LocalDate.now().toString());
        }

        todoListView.setCellFactory(listView -> new ListCell<ToDoItem>(){
            private final CheckBox checkBox = new CheckBox();
            private final Label label = new Label();
            private final HBox hBox = new HBox(10, checkBox, label);

            private ToDoItem currentItem;

            {
                checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    if (currentItem != null) {
                        currentItem.setDone(isNowSelected);
                    }
                });

                hBox.setOnMouseClicked(event -> {
                    if (!isEmpty()) {
                        getListView().getSelectionModel().select(getItem());
                    }
                });
            }

            @Override
            protected void updateItem(ToDoItem item, boolean empty) {
                super.updateItem(item, empty);
                if (currentItem != null) {
                    currentItem.doneProperty().removeListener(doneChangeListener);
                }

                if (empty || item == null) {
                    currentItem = null;
                    setGraphic(null);
                } else {
                    currentItem = item;
                    label.setText(item.getTask() + " - " + item.getDate());
                    checkBox.setSelected(item.isDone());

                    currentItem.doneProperty().addListener(doneChangeListener);

                    setGraphic(hBox);
                }
            }
            private final javafx.beans.value.ChangeListener<Boolean> doneChangeListener = (obs, oldVal, newVal) -> {
                if (currentItem != null) {
                    if (newVal) {
                        if (!todoTableView.getItems().contains(currentItem)) {
                            todoTableView.getItems().add(currentItem);
                        }
                    } else {
                        todoTableView.getItems().remove(currentItem);
                    }
                }
            };
        });

        colTask.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTask()));
        colDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {
        todoTableView.refresh();
    }
}
