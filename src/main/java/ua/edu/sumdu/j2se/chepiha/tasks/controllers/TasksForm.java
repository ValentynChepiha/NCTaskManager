package ua.edu.sumdu.j2se.chepiha.tasks.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.chepiha.tasks.models.*;
import ua.edu.sumdu.j2se.chepiha.tasks.services.Notificator;
import ua.edu.sumdu.j2se.chepiha.tasks.types.SaveTaskTypes;
import ua.edu.sumdu.j2se.chepiha.tasks.views.ModalWindow;
import ua.edu.sumdu.j2se.chepiha.tasks.views.TasksFormView;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class TasksForm {

    Logger logger = LoggerFactory.getLogger(TasksForm.class.getName());

    private AbstractTaskList tasks = new ArrayTaskList();
    private AbstractTaskList workTasks = new ArrayTaskList();
    private TasksFormView view = new TasksFormView();
    private TasksModel tasksModel = new TasksModel();
    int selectedItem = -1;
    SaveTaskTypes.types typeSave;

    private final Pattern INTERVAL_VALUE_FORMAT = Pattern.compile("^([1-9](\\d)*)?$");
    private final Pattern DATE_VALUE_FORMAT = Pattern.compile("^([2]\\d{3}-(0|1)\\d-[0123]\\d [012]\\d:[0-6]\\d:[0-6]\\d)?$");

    @FXML
    public CheckBox chkCalendar;

    @FXML
    public LocalDateTimeTextField dtStartCalendar;

    @FXML
    public LocalDateTimeTextField dtEndCalendar;

    @FXML
    public Button btnShowCalendar;

    @FXML
    public ListView<String> lvTasks;

    @FXML
    public Button btnCreate;

    @FXML
    public Button btnEdit;

    @FXML
    public Label tLabelName;

    @FXML
    public TextField tName;

    @FXML
    public CheckBox tRepeat;

    @FXML
    public CheckBox tActive;

    @FXML
    public Label tLabelStartTime;

    @FXML
    public Label tLabelEndTime;

    @FXML
    public LocalDateTimeTextField tStartTime;

    @FXML
    public LocalDateTimeTextField tEndTime;

    @FXML
    public Label tLabelInterval;

    @FXML
    public TextField tInterval;

    @FXML
    public Button btnDelete;

    @FXML
    public Button btnSave;

    @FXML
    public Button btnCancel;

    public void initModel(){
        logger.info("{} Start...", LocalDateTime.now());
        logger.info("Start load data from file");
        tasksModel.initTaskList(tasks, workTasks);
    }

    @FXML
    public void initialize() {
        try {
            initModel();
            view.startInit(this);
            view.loadTasksListToListView(workTasks, this);
            Notificator.run(tasks);

            tInterval.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!INTERVAL_VALUE_FORMAT.matcher(newValue).matches())
                    tInterval.setText(oldValue);
            });

            chkCalendar.setOnAction(event -> {
                view.calendarSwitch(this);
                tasksModel.insertDataToWorkList(tasks, workTasks);
                view.loadTasksListToListView(tasks, this);
            });

            btnShowCalendar.setOnAction(event -> {
                try{
                    if(view.verifyCalendar(this)){
                        tasksModel.getWorkTaskList(tasks, workTasks, dtStartCalendar.getLocalDateTime(),
                                dtEndCalendar.getLocalDateTime());
                        view.loadTasksListToListView(workTasks, this);
                    }
                }
                catch (Error e){
                    logger.error("Error calendar: ", e);
                }
            });

            lvTasks.setOnMouseClicked(event -> {
                selectedItem = view.getIndexSelectedTask(selectedItem, this);
                if(selectedItem >= 0){
                    Task task = workTasks.getTask(selectedItem);
                    if(task != null){
                        view.setBtnTaskOn(this);
                        view.setHideFormTasks(this);
                        view.setDisableFormTasks(this);
                        if(task.isRepeated()){
                            view.loadTaskRepeatEdit(task, this);
                        } else {
                            view.loadTaskOnceEdit(task, this);
                        }
                    }
                }
            });

            btnEdit.setOnAction(event -> {
                typeSave = SaveTaskTypes.types.EDIT;
                view.setBtnTaskOff(this);
                view.setBtnCRUDOn(this);
                view.setDisableListView(this);
                view.setBtnTaskOff(this);
                view.changeEditTask(this);
                view.setBtnCRUDVisible(this);
                view.setBtnCRUDOn(this);
            });

            tRepeat.setOnAction(event -> {
                view.setHideFormTasks(this);
                view.setDisableFormTasks(this);
                view.changeEditTask(this);
            });

            btnCreate.setOnAction(event -> {
                typeSave = SaveTaskTypes.types.CREATE;
                view.setBtnTaskOff(this);
                view.setBtnCreatVisible(this);
                view.setBtnCreateOn(this);
                view.setHideFormTasks(this);
                view.setDisableFormTasks(this);
                view.setEnableTaskOnce(this);
                view.clearFieldsFormTasks(this);
                view.setDefaultValuesTask(this);
            });

            btnCancel.setOnAction(event -> {
                view.setBtnTaskStart(this);
                view.setBtnCRUDHide(this);
                view.setHideFormTasks(this);
                view.clearFieldsFormTasks(this);
                selectedItem = -1;
                view.loadTasksListToListView(workTasks, this);
            });

            btnSave.setOnAction (event -> {
                try{
                    if (!view.verifyTaskBeforeSave(this))
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
                    view.refreshFormAfterCRUD(workTasks, this);
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
                        view.refreshFormAfterCRUD(workTasks, this);
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