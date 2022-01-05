package ua.edu.sumdu.j2se.chepiha.tasks.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalDateTimeTextField;
import ua.edu.sumdu.j2se.chepiha.tasks.models.LinkedTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;
import ua.edu.sumdu.j2se.chepiha.tasks.services.ModalWindow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class TasksForm {

    private final LinkedTaskList tasks = new LinkedTaskList();
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Pattern INTERVAL_VALUE_FORMAT = Pattern.compile("^([1-9](\\d)*)?$");
    private final Pattern DATE_VALUE_FORMAT = Pattern.compile("^([2]\\d{3}-(0|1)\\d-[0123]\\d [012]\\d:[0-6]\\d:[0-6]\\d)?$");

    @FXML
    private CheckBox chkCalendar;

    @FXML
    private LocalDateTimeTextField dtStartCalendar;

    @FXML
    private LocalDateTimeTextField dtEndCalendar;

    @FXML
    private Button btnShowCalendar;

    @FXML
    private ListView<String> lvTasks;

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
    private LocalDateTimeTextField tStartTime;

    @FXML
    private LocalDateTimeTextField tEndTime;

    @FXML
    private Label tLabelInterval;

    @FXML
    private TextField tInterval;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    private boolean localDateTime;

// todo:
//        mfSplitPane.setResizable(false);
//    @FXML
//    private SplitPane mfSplitPane;

    @FXML
    private void initCalendar(){
        LocalDateTime NOW = LocalDateTime.now();

        chkCalendar.setSelected(false);

        dtStartCalendar.setDisable(true);
        dtEndCalendar.setDisable(true);

        dtStartCalendar.setDateTimeFormatter(DATE_FORMAT);
        dtEndCalendar.setDateTimeFormatter(DATE_FORMAT);

        dtStartCalendar.setLocalDateTime(NOW);
        dtEndCalendar.setLocalDateTime(NOW);

        btnShowCalendar.setDisable(true);
    }

    @FXML
    private void setVisibleFormTasksFields(boolean visible){
        tLabelName.setVisible(visible);
        tName.setVisible(visible);
        tActive.setVisible(visible);
        tRepeat.setVisible(visible);
        tLabelStartTime.setVisible(visible);
        tStartTime.setVisible(visible);
        tLabelEndTime.setVisible(visible);
        tEndTime.setVisible(visible);
        tLabelInterval.setVisible(visible);
        tInterval.setVisible(visible);
    }

    @FXML
    private void initTaskField(){
        btnEdit.setDisable(true);
        btnCreate.setDisable(false);

        setVisibleButtonsEdit(false);
        setVisibleFormTasksFields(false);
    }

    @FXML
    private void initTask(){
        initTaskField();
        tStartTime.setDateTimeFormatter(DATE_FORMAT);
        tEndTime.setDateTimeFormatter(DATE_FORMAT);
    }

    @FXML
    private void initFormTaskOnce(){
        tLabelStartTime.setText("Time:");
        tLabelEndTime.setVisible(false);
        tEndTime.setVisible(false);
        tLabelInterval.setVisible(false);
        tInterval.setVisible(false);
    }

    @FXML
    private void initFormTaskRepeat(){
        tLabelStartTime.setText("Start time:");
        tLabelEndTime.setVisible(true);
        tEndTime.setVisible(true);
        tLabelInterval.setVisible(true);
        tInterval.setVisible(true);
    }

    @FXML
    private void setDefaultFormTasksFields(){
        tName.setText("");
        tRepeat.setSelected(false);
        tActive.setSelected(false);
        tStartTime.setLocalDateTime(LocalDateTime.now());
        tEndTime.setLocalDateTime(LocalDateTime.now());
        tInterval.setText("3600");
    }

    @FXML
    private void setVisibleButtonsCreate(boolean visible){
        btnSave.setVisible(visible);
        btnCancel.setVisible(visible);
    }
    
    @FXML
    private void setVisibleButtonsEdit(boolean visible){
        setVisibleButtonsCreate(visible);
        btnDelete.setVisible(visible);
    }
    
    @FXML
    private void initEmptyListView(){
        lvTasks.setDisable(tasks.size() <= 0);
    }

    @FXML
    private boolean isVerifyTask(){
        StringBuilder msgError = new StringBuilder();
        boolean result = true;

        String taskName = tName.getText();
        if( taskName == null || taskName.trim().length()==0){
            result = false;
            msgError.append("wrong name; ");
        }

        LocalDateTime dtStart = tStartTime.getLocalDateTime();
        if( dtStart == null ){
            result = false;
            msgError.append("wrong start time; ");
        }

        if(tRepeat.isSelected()){
            LocalDateTime dtEnd = tEndTime.getLocalDateTime();
            if( dtEnd == null ){
                result = false;
                msgError.append("wrong end time; ");
            }

            String interval = tInterval.getText();
            if( interval == null || interval.trim().length()==0){
                result = false;
                msgError.append("wrong interval; ");
            }
        }

        if(!result){
            ModalWindow.showAlertError("Error: " + msgError.toString());
        }
        return result;
    }

    @FXML
    private void loadTasksList(){
        ObservableList<String> names = FXCollections.observableArrayList();
        tasks.forEach(task ->names.add(task.toString()));

        lvTasks.setItems(names);
        initEmptyListView();
    }

    @FXML
    private void initialize(){
        initCalendar();
        initTask();
        initEmptyListView();

        chkCalendar.setOnAction(event -> {
            btnShowCalendar.setDisable( !btnShowCalendar.isDisabled() );
            dtStartCalendar.setDisable( !dtStartCalendar.isDisabled() );
            dtEndCalendar.setDisable( !dtEndCalendar.isDisabled() );
            btnCreate.setDisable(chkCalendar.isSelected());
        });

        btnShowCalendar.setOnAction(event -> {
            btnCreate.setDisable(false);
        });

        btnEdit.setOnAction(event -> {
            btnCreate.setDisable(true);

            setVisibleButtonsEdit(true);
        });

        tRepeat.setOnAction(event -> {
            if (tRepeat.isSelected()) {
                initFormTaskRepeat();
            } else {
                initFormTaskOnce();
            }
        });

        tInterval.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!INTERVAL_VALUE_FORMAT.matcher(newValue).matches())
                tInterval.setText(oldValue);
        });

        // todo:
//        tStartTime.setValueValidationCallback( localDateTime -> {
//
//            System.out.println("Inside ::" + localDateTime);
////            if(!DATE_VALUE_FORMAT.matcher(localDateTime.).matches()){
////
////            }
//            return true;
//        } );
//
//        tStartTime.valueValidationCallbackProperty();


        btnCreate.setOnAction(event -> {
            btnEdit.setDisable(true);
            btnCreate.setDisable(true);

            setVisibleButtonsCreate(true);
            setVisibleFormTasksFields(true);
            initFormTaskOnce();
            setDefaultFormTasksFields();
        });

        btnCancel.setOnAction(event -> {
            btnEdit.setDisable(true);
            btnCreate.setDisable(false);

            setDefaultFormTasksFields();
            setVisibleFormTasksFields(false);
            setVisibleButtonsCreate(false);
        });

        btnSave.setOnAction(event -> {
            if (!isVerifyTask())
                return;

            String taskName = tName.getText();
            Task newTask;
            if(tRepeat.isSelected()){
                LocalDateTime timeStart = tStartTime.getLocalDateTime();
                LocalDateTime timeEnd = tEndTime.getLocalDateTime();
                int interval = Integer.parseInt(tInterval.getText());
                newTask = new Task(taskName, timeStart, timeEnd, interval);
            } else {
                LocalDateTime time = tStartTime.getLocalDateTime();
                newTask = new Task(taskName, time);
            }
            newTask.setActive(tActive.isSelected());

            tasks.add(newTask);
            initTaskField();

//            todo:
            loadTasksList();
            lvTasks.refresh();

            System.out.println(tasks);
        });
    }

}