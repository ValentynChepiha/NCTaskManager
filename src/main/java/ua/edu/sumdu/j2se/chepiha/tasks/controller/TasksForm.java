package ua.edu.sumdu.j2se.chepiha.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;

public class TasksForm {

    @FXML
    private CheckBox chkCalendar;

    @FXML
    private LocalDateTimeTextField dtStartCalendar;

    @FXML
    private LocalDateTimeTextField dtEndCalendar;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnEdit;

    @FXML
    private Label tLabelName;

    @FXML
    private TextField tName;

    @FXML
    private CheckBox tRepeat;

    @FXML
    private CheckBox tActive;

    @FXML
    private Label tLabelStartTime;

    @FXML
    private Label tLabelEndTime;

    @FXML
    private Label tLabelRepeat;

    @FXML
    private LocalDateTimeTextField tStartTime;

    @FXML
    private LocalDateTimeTextField tEndTime;

    @FXML
    private TextField tInterval;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;


    @FXML
    void initialize(){
//        btnEdit.setDisable(true);
//        btnCreate.setDisable(false);
//
//        dtStartCalendar.setDisable(true);
//        dtEndCalendar.setDisable(true);
//
//        dtStartCalendar.setValue(LocalDate.from(LocalDateTime.now()));
//        dtEndCalendar.setValue(LocalDate.from(LocalDateTime.now()));
//
//        chkCalendar.setOnMouseClicked(event -> {
//            dtStartCalendar.setDisable( !dtStartCalendar.isDisabled() );
//            dtEndCalendar.setDisable( !dtEndCalendar.isDisabled() );
//        });
    }

}