package ua.edu.sumdu.j2se.chepiha.tasks.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalDateTimeTextField;
import ua.edu.sumdu.j2se.chepiha.tasks.models.*;
import ua.edu.sumdu.j2se.chepiha.tasks.services.ModalWindow;
import ua.edu.sumdu.j2se.chepiha.tasks.services.Notificator;
import ua.edu.sumdu.j2se.chepiha.tasks.services.TaskListIO;
import ua.edu.sumdu.j2se.chepiha.tasks.types.SaveTaskTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class TasksForm {

    private final AbstractTaskList tasks = new ArrayTaskList();
    private AbstractTaskList workTasks = new ArrayTaskList();

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Pattern INTERVAL_VALUE_FORMAT = Pattern.compile("^([1-9](\\d)*)?$");
    private final Pattern DATE_VALUE_FORMAT = Pattern.compile("^([2]\\d{3}-(0|1)\\d-[0123]\\d [012]\\d:[0-6]\\d:[0-6]\\d)?$");
    private int selectedItem = -1;
    private boolean activatedCalendar = false;
    private SaveTaskTypes.types typeSave;

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
//      зробити щоб не можна було рухати панель
// mfSplitPane.setResizable(false);
//    @FXML
//    private SplitPane mfSplitPane;


// todo:
//      додати перевірку дат при введенні
    private void clearIndex(){
        selectedItem = -1;
    }

    private void calendarOn(){
        activatedCalendar = true;
    }

    private void calendarOff(){
        activatedCalendar = false;
    }

    @FXML
    private void initFormCalendar(){
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
    private void setVisibleFormTasksFields(){
        tLabelName.setVisible(true);
        tName.setVisible(true);
        tActive.setVisible(true);
        tRepeat.setVisible(true);
        tLabelStartTime.setVisible(true);
        tStartTime.setVisible(true);
        tLabelEndTime.setVisible(true);
        tEndTime.setVisible(true);
        tLabelInterval.setVisible(true);
        tInterval.setVisible(true);
    }

    @FXML
    private void setHideFormTasksFields(){
        tLabelName.setVisible(false);
        tName.setVisible(false);
        tActive.setVisible(false);
        tRepeat.setVisible(false);
        tLabelStartTime.setVisible(false);
        tStartTime.setVisible(false);
        tLabelEndTime.setVisible(false);
        tEndTime.setVisible(false);
        tLabelInterval.setVisible(false);
        tInterval.setVisible(false);
    }

    @FXML
    private void setDisableFormTasksFields(){
        tLabelName.setDisable(true);
        tName.setDisable(true);
        tActive.setDisable(true);
        tRepeat.setDisable(true);
        tLabelStartTime.setDisable(true);
        tStartTime.setDisable(true);
        tLabelEndTime.setDisable(true);
        tEndTime.setDisable(true);
        tLabelInterval.setDisable(true);
        tInterval.setDisable(true);
    }

    @FXML
    private void setEnableFormTasksFields(){
        tLabelName.setDisable(false);
        tName.setDisable(false);
        tActive.setDisable(false);
        tRepeat.setDisable(false);
        tLabelStartTime.setDisable(false);
        tStartTime.setDisable(false);
        tLabelEndTime.setDisable(false);
        tEndTime.setDisable(false);
        tLabelInterval.setDisable(false);
        tInterval.setDisable(false);
    }

    @FXML
    private void initTaskField(){
        btnEdit.setDisable(true);
        btnCreate.setDisable(false);

        setHideButtonsEdit();
        setHideFormTasksFields();
    }

    @FXML
    private void initFormTask(){
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
    private void setVisibleButtonsCreate(){
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
    }

    @FXML
    private void setHideButtonsCreate(){
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
    }

    @FXML
    private void setVisibleButtonsEdit(){
        setVisibleButtonsCreate();
        btnDelete.setVisible(true);
    }

    @FXML
    private void setHideButtonsEdit(){
        setHideButtonsCreate();
        btnDelete.setVisible(false);
    }

    @FXML
    private void setDisableListView(){
        lvTasks.setDisable(true);
    }

    @FXML
    private void initEmptyListView(AbstractTaskList taskList){
        lvTasks.setDisable(taskList.size() <= 0);
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

            if(dtStart != null && dtEnd != null){
                if(dtStart.isAfter(dtEnd)){
                    result = false;
                    msgError.append("start time must be before end time; ");
                }
            }
        }

        if(!result){
            ModalWindow.showAlertError("Error: " + msgError.toString());
        }
        return result;
    }

    @FXML
    private boolean isVerifyCalendar(){
        LocalDateTime dStartCalendar = dtStartCalendar.getLocalDateTime();
        LocalDateTime dEndCalendar = dtEndCalendar.getLocalDateTime();
        boolean result = true;
        StringBuilder msg = new StringBuilder();

        if(dStartCalendar == null){
            msg.append("wrong start time calendar;");
            result = false;
        }

        if(dEndCalendar == null){
            msg.append("wrong end time calendar;");
            result = false;
        }

        if(result){
            if(dStartCalendar.isAfter(dEndCalendar)){
                msg.append("start time calendar must be before end time calendar;");
                result = false;
            }
        }

        if(!result){
            ModalWindow.showAlertError(msg.toString());
        }

        return result;
    }

    @FXML
    private void loadTasksListToListView(AbstractTaskList taskList){
        lvTasks.getItems().clear();
        if(taskList.size()>0){
            ObservableList<String> names = FXCollections.observableArrayList();
            taskList.forEach(task -> names.add(task.toString()));
            lvTasks.setItems(names);
        }
        initEmptyListView(taskList);
    }

    @FXML
    private void getWorkTaskList(AbstractTaskList originTasks, AbstractTaskList workTasks){
        Iterable<Task> calendarTasks = Tasks.incoming(
                originTasks,
                dtStartCalendar.getLocalDateTime(),
                dtEndCalendar.getLocalDateTime()
        );
        workTasks.clear();
        calendarTasks.forEach(workTasks::add);
    }

    @FXML
    private void refreshListView(){
        if(activatedCalendar){
            getWorkTaskList(tasks, workTasks);
        } else {
            insertDataToWorkList();
        }
        loadTasksListToListView(workTasks);
    }

    @FXML
    private void loadTaskEdit(Task task){
        if (task == null)
            return;

        btnEdit.setDisable(false);
        btnCreate.setDisable(false);
        setVisibleFormTasksFields();
        setDisableFormTasksFields();

        tName.setText(task.getTitle());
        tActive.setSelected(task.isActive());
        if(task.isRepeated()){
            tRepeat.setSelected(true);
            tStartTime.setLocalDateTime(task.getStartTime());
            tEndTime.setLocalDateTime(task.getEndTime());
            tInterval.setText("" + task.getRepeatInterval());

            initFormTaskRepeat();
        }
        else {
            tRepeat.setSelected(false);
            tStartTime.setLocalDateTime(task.getStartTime());

            initFormTaskOnce();
        }
    }

    private void insertDataToWorkList(){
        workTasks.clear();
        tasks.forEach(workTasks::add);
    }

    private void initTaskList(){
        TaskListIO.loadTaskList(tasks);
        insertDataToWorkList();
    }

    @FXML
    private void initialize(){
        initFormCalendar();
        initFormTask();
        initTaskList();
        refreshListView();
//        Notificator.run(tasks);

        tInterval.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!INTERVAL_VALUE_FORMAT.matcher(newValue).matches())
                tInterval.setText(oldValue);
        });

        chkCalendar.setOnAction(event -> {
            btnShowCalendar.setDisable( !btnShowCalendar.isDisabled() );
            dtStartCalendar.setDisable( !dtStartCalendar.isDisabled() );
            dtEndCalendar.setDisable( !dtEndCalendar.isDisabled() );

            btnCreate.setDisable(chkCalendar.isSelected());
            btnEdit.setDisable(true);
            if(chkCalendar.isSelected()){
                setDisableListView();
                setHideFormTasksFields();
            } else {
                calendarOff();
                refreshListView();
            }

        });

        btnShowCalendar.setOnAction(event -> {
            if(isVerifyCalendar()){
                btnCreate.setDisable(false);
                calendarOn();
                refreshListView();
            }
        });

        lvTasks.setOnMouseClicked(event -> {
            int currentItem = lvTasks.getSelectionModel().getSelectedIndex();
            if(currentItem >= 0 && currentItem != selectedItem ){
                selectedItem = currentItem;
                loadTaskEdit(workTasks.getTask(selectedItem));
            }
        });

        btnEdit.setOnAction(event -> {
            btnCreate.setDisable(true);
            btnEdit.setDisable(true);
            typeSave = SaveTaskTypes.types.EDIT;

            setDisableListView();
            setVisibleButtonsEdit();
            setEnableFormTasksFields();
        });

        tRepeat.setOnAction(event -> {
            if (tRepeat.isSelected()) {
                initFormTaskRepeat();
            } else {
                initFormTaskOnce();
            }
        });

// todo:
//      зробити валідаціювведення дати
//
//      tStartTime.setValueValidationCallback( localDateTime -> {
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
            typeSave = SaveTaskTypes.types.CREATE;

            setDisableListView();
            setEnableFormTasksFields();
            setVisibleButtonsCreate();
            setVisibleFormTasksFields();
            initFormTaskOnce();
            setDefaultFormTasksFields();
        });

        btnCancel.setOnAction(event -> {
            btnEdit.setDisable(true);
            btnCreate.setDisable(false);

            setDefaultFormTasksFields();
            setHideFormTasksFields();
            setHideButtonsEdit();
            initEmptyListView(workTasks);
            clearIndex();
        });

        btnSave.setOnAction(event -> {
            if (!isVerifyTask())
                return;

            Task newTask;
            String taskName = tName.getText();
            LocalDateTime time = tStartTime.getLocalDateTime();
            if(typeSave == SaveTaskTypes.types.CREATE){
                newTask = new Task(taskName, time);
            } else {
                newTask = workTasks.getTask(selectedItem);
                newTask.setTitle(taskName);
                newTask.setTime(time);
            }
            newTask.setActive(tActive.isSelected());

            if(tRepeat.isSelected()){
                LocalDateTime timeStart = tStartTime.getLocalDateTime();
                LocalDateTime timeEnd = tEndTime.getLocalDateTime();
                int interval = Integer.parseInt(tInterval.getText());

                newTask.setTime(timeStart, timeEnd, interval);
            }

            if(typeSave == SaveTaskTypes.types.CREATE){
                tasks.add(newTask);
            }
            initTaskField();
            refreshListView();
            clearIndex();
            initEmptyListView(workTasks);
            TaskListIO.saveTaskList(tasks);
        });

        btnDelete.setOnAction(event -> {
            Task task = workTasks.getTask(selectedItem);
            if(ModalWindow.showConfirmDelete(task.toString())){
                tasks.remove(task);
                initTaskField();
                refreshListView();
                clearIndex();
                initEmptyListView(workTasks);
                TaskListIO.saveTaskList(tasks);
            }
        });
    }

}