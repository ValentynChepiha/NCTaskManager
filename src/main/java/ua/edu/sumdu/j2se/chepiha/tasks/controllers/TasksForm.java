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

    private AbstractTaskList tasks = new ArrayTaskList();
    private AbstractTaskList workTasks = new ArrayTaskList();
    private TasksFormModel tasksModel = new TasksFormModel();
    int selectedItem = -1;
    SaveTaskTypes.types typeSave;

    private final Pattern INTERVAL_VALUE_FORMAT = Pattern.compile("^([1-9](\\d)*)?$");
    private final Pattern DATE_VALUE_FORMAT = Pattern.compile("^([2]\\d{3}-(0|1)\\d-[0123]\\d [012]\\d:[0-6]\\d:[0-6]\\d)?$");


    public void initModel(){
        logger.info("{} Start...", LocalDateTime.now());
        logger.info("Start load data from file");
        tasksModel.initTaskList(tasks, workTasks);
    }

    @FXML
    public void initialize() {
        try {
            initModel();
            startInit();
            loadTasksListToListView(workTasks);
            Notificator.run(tasks);

            tInterval.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!INTERVAL_VALUE_FORMAT.matcher(newValue).matches())
                    tInterval.setText(oldValue);
            });

            chkCalendar.setOnAction(event -> {
                calendarSwitch();
                tasksModel.insertDataToWorkList(tasks, workTasks);
                loadTasksListToListView(tasks);
            });

            btnShowCalendar.setOnAction(event -> {
                try{
                    if(verifyCalendar()){
                        tasksModel.getWorkTaskList(tasks, workTasks, dtStartCalendar.getLocalDateTime(),
                                dtEndCalendar.getLocalDateTime());
                        loadTasksListToListView(workTasks);
                    }
                }
                catch (Error e){
                    logger.error("Error calendar: ", e);
                }
            });

            lvTasks.setOnMouseClicked(event -> {
                selectedItem = getIndexSelectedTask(selectedItem);
                if(selectedItem >= 0){
                    Task task = workTasks.getTask(selectedItem);
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
                loadTasksListToListView(workTasks);
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

                    tasksModel.saveTaskListToFile(tasks);
                    tasksModel.insertDataToWorkList(tasks, workTasks);
                    selectedItem = -1;
                    refreshFormAfterCRUD(workTasks);
                    logger.info("{} task: " + newTask.toString(), typeSave == SaveTaskTypes.types.CREATE ? "Added" : "Updated");
                }
                catch (Exception e){
                    logger.error("Save/update task :", e);
                }

            });

            btnDelete.setOnAction(event -> {
                try{
                    Task task = workTasks.getTask(selectedItem);
                    if(ModalWindow.showConfirmDelete(task.toString())){
                        tasks.remove(task);
                        tasksModel.saveTaskListToFile(tasks);
                        tasksModel.insertDataToWorkList(tasks, workTasks);
                        selectedItem = -1;
                        refreshFormAfterCRUD(workTasks);
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