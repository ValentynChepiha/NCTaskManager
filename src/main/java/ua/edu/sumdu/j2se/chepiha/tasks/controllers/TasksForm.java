package ua.edu.sumdu.j2se.chepiha.tasks.controllers;

import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.chepiha.tasks.models.*;
import ua.edu.sumdu.j2se.chepiha.tasks.services.Notificator;
import ua.edu.sumdu.j2se.chepiha.tasks.types.SaveTaskTypes;
import ua.edu.sumdu.j2se.chepiha.tasks.views.ModalWindow;
import ua.edu.sumdu.j2se.chepiha.tasks.views.TasksFormView;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class TasksForm extends TasksFormView {

    Logger logger = LoggerFactory.getLogger(TasksForm.class.getName());

    int selectedItem = -1;
    SaveTaskTypes.types typeSave;

    private final Pattern INTERVAL_VALUE_FORMAT = Pattern.compile("^([1-9](\\d)*)?$");
    private final Pattern DATE_VALUE_FORMAT = Pattern.compile("^([2]\\d{3}-(0|1)\\d-[0123]\\d [012]\\d:[0-6]\\d:[0-6]\\d)?$");


    public void initModel(){
        logger.info("{} Start...", LocalDateTime.now());
        logger.info("Start load data from file");
        tasksModel.initTaskList();
    }

    @FXML
    public void initialize() {
        try {
            initModel();
            startInit();
            loadTasksListToListView(tasksModel.getWorkTasks());
            Notificator.run(tasksModel.getTasks());

            tInterval.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!INTERVAL_VALUE_FORMAT.matcher(newValue).matches())
                    tInterval.setText(oldValue);
            });

            chkCalendar.setOnAction(event -> {
                calendarSwitch();
                tasksModel.insertDataToWorkList();
                loadTasksListToListView(tasksModel.getWorkTasks());
            });

            btnShowCalendar.setOnAction(event -> {
                try{
                    if(verifyCalendar()){
                        tasksModel.getWorkTaskList(dtStartCalendar.getLocalDateTime(), dtEndCalendar.getLocalDateTime());
                        loadTasksListToListView(tasksModel.getWorkTasks());
                    }
                }
                catch (Error e){
                    logger.error("Error calendar: ", e);
                }
            });

            lvTasks.setOnMouseClicked(event -> {
                selectedItem = getIndexSelectedTask(selectedItem);
                if(selectedItem >= 0){
                    Task task = tasksModel.getWorkTasks().getTask(selectedItem);
                    if(task != null){
                        setBtnTaskOn();
                        setHideFormTasks();
                        setDisableFormTasks();
                        if(task.isRepeated()){
                            loadTaskRepeatEdit(task);
                        } else {
                            loadTaskOnceEdit(task);
                        }
                    }
                }
            });

            btnEdit.setOnAction(event -> {
                typeSave = SaveTaskTypes.types.EDIT;
                setBtnTaskOff();
                setBtnCRUDOn();
                setDisableListView();
                setBtnTaskOff();
                changeEditTask();
                setBtnCRUDVisible();
                setBtnCRUDOn();
            });

            tRepeat.setOnAction(event -> {
                setHideFormTasks();
                setDisableFormTasks();
                changeEditTask();
            });

            btnCreate.setOnAction(event -> {
                typeSave = SaveTaskTypes.types.CREATE;
                setBtnTaskOff();
                setBtnCreatVisible();
                setBtnCreateOn();
                setHideFormTasks();
                setDisableFormTasks();
                setEnableTaskOnce();
                clearFieldsFormTasks();
                setDefaultValuesTask();
            });

            btnCancel.setOnAction(event -> {
                setBtnTaskStart();
                setBtnCRUDHide();
                setHideFormTasks();
                clearFieldsFormTasks();
                selectedItem = -1;
                loadTasksListToListView(tasksModel.getWorkTasks());
            });

            btnSave.setOnAction (event -> {
                try{
                    if (!verifyTaskBeforeSave())
                        return;

                    Task newTask;

                    String taskName = tName.getText();
                    LocalDateTime time = tStartTime.getLocalDateTime();
                    if(typeSave == SaveTaskTypes.types.CREATE){
                        newTask = new Task(taskName, time);
                    } else {
                        newTask = tasksModel.getWorkTasks().getTask(selectedItem);
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
                        tasksModel.addTask(newTask);
                    }

                    tasksModel.saveTaskListToFile();
                    tasksModel.insertDataToWorkList();
                    selectedItem = -1;
                    refreshFormAfterCRUD(tasksModel.getWorkTasks());
                    logger.info("{} task: " + newTask.toString(), typeSave == SaveTaskTypes.types.CREATE ? "Added" : "Updated");
                }
                catch (Exception e){
                    logger.error("Save/update task :", e);
                }

            });

            btnDelete.setOnAction(event -> {
                try{
                    Task task = tasksModel.getWorkTasks().getTask(selectedItem);
                    if(ModalWindow.showConfirmDelete(task.toString())){
                        tasksModel.removeTask(task);
                        tasksModel.saveTaskListToFile();
                        tasksModel.insertDataToWorkList();
                        selectedItem = -1;
                        refreshFormAfterCRUD(tasksModel.getWorkTasks());
                        logger.info("Deleted task: " + task.toString());
                    }
                }
                catch (Exception e) {
                    logger.error("Delete tasks: ", e);
                }
            });

        }
        catch (Exception e) {
            logger.error("Error: ",  e);
        }
    }
}