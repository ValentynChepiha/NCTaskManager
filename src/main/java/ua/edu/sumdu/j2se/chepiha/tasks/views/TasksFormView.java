package ua.edu.sumdu.j2se.chepiha.tasks.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalDateTimeTextField;
import ua.edu.sumdu.j2se.chepiha.tasks.controllers.TasksFormModel;
import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;
import ua.edu.sumdu.j2se.chepiha.tasks.services.VerifyingData;

import java.time.LocalDateTime;

public class TasksFormView {

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

//    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TasksFormModel tasksModel = new TasksFormModel();
    private static final String DEFAULT_INTERVAL = "3600";

    public void startInit(){
        TaskFormInit.initFormCalendar(chkCalendar, dtStartCalendar,
                dtEndCalendar, btnShowCalendar);
        TaskFormInit.initFormTask(btnEdit, btnCreate, tStartTime, tEndTime,
                tLabelName, tName, tActive, tRepeat, tLabelStartTime,
                tLabelEndTime, tLabelInterval, tInterval, btnCancel,
                btnSave, btnDelete);
    }

    public void calendarSwitch(){
        if(chkCalendar.isSelected()){
            TaskFormInit.setCalendarOn(dtStartCalendar, dtEndCalendar, btnShowCalendar);
            TaskFormInit.formSet.setDisabled(btnCreate, btnEdit, lvTasks);
        } else {
            TaskFormInit.setCalendarOff(dtStartCalendar, dtEndCalendar, btnShowCalendar);
            TaskFormInit.formSet.setEnabled(btnCreate);
            TaskFormInit.formSet.setDisabled(btnEdit);
        }
        TaskFormInit.setHideTaskEditFields(btnEdit, btnCreate, tLabelName,
                tName, tActive, tRepeat, tLabelStartTime,
                tStartTime, tLabelEndTime, tEndTime, tLabelInterval,
                tInterval, btnCancel, btnSave, btnDelete);
    }

    public void loadTasksListToListView(AbstractTaskList taskList){
        lvTasks.getItems().clear();
        TaskFormInit.formSet.setDisabled(lvTasks);
        if(taskList.size()>0){
            ObservableList<String> names = FXCollections.observableArrayList();
            taskList.forEach(task -> names.add(task.toString()));
            lvTasks.setItems(names);
            TaskFormInit.formSet.setEnabled(lvTasks);
        }
    }

    public void setDisableListView(){
        TaskFormInit.formSet.setDisabled(lvTasks);
    }

    public boolean verifyCalendar(){
        boolean result = true;
        String error = VerifyingData.verifyCalendar(dtStartCalendar.getLocalDateTime(),
                dtEndCalendar.getLocalDateTime());
        if(error.length()>0){
            result = false;
            ModalWindow.showAlertError(error);
        }
        return result;
    }

    public int getIndexSelectedTask(int index){
        int currentItem = lvTasks.getSelectionModel().getSelectedIndex();
        if(currentItem >= 0 && currentItem != index ){
            index = currentItem;
        }
        return index;
    }

    public void setBtnTaskStart(){
        TaskFormInit.formSet.setEnabled(btnCreate);
        TaskFormInit.formSet.setDisabled(btnEdit);
    }

    public void setBtnTaskOn(){
        TaskFormInit.formSet.setEnabled(btnCreate, btnEdit);
    }

    public void setBtnTaskOff(){
        TaskFormInit.formSet.setDisabled(btnCreate, btnEdit);
    }

    public void setBtnCRUDOn(){
        TaskFormInit.formSet.setEnabled(btnDelete);
        setBtnCreateOn();
    }

    public void setBtnCreateOn(){
        TaskFormInit.formSet.setEnabled(btnCancel, btnSave);
    }

    public void setBtnCRUDOff(){
        TaskFormInit.formSet.setDisabled(btnCancel, btnSave, btnDelete);
    }

    public void setBtnCRUDVisible(){
        TaskFormInit.formSet.setVisible(btnDelete);
        setBtnCreatVisible();
    }

    public void setBtnCreatVisible(){
        TaskFormInit.formSet.setVisible(btnCancel, btnSave);
    }

    public void setBtnCRUDHide(){
        TaskFormInit.formSet.setHide(btnCancel, btnSave, btnDelete);
    }

    public void setHideFormTasks(){
        TaskFormInit.setHideFormTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime, tLabelEndTime,
                tEndTime, tLabelInterval, tInterval);
    }

    public void setDisableFormTasks(){
        TaskFormInit.setDisableFormTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime, tLabelEndTime,
                tEndTime, tLabelInterval, tInterval);
    }

    public void loadTaskOnceEdit(Task task){
        tName.setText(task.getTitle());
        tActive.setSelected(task.isActive());
        tRepeat.setSelected(false);
        tStartTime.setLocalDateTime(task.getStartTime());

        TaskFormInit.setVisibleFormOnceTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime);
        TaskFormInit.formSet.setTextValue("Time:", tLabelStartTime);
    }

    public void loadTaskRepeatEdit(Task task){
        tName.setText(task.getTitle());
        tActive.setSelected(task.isActive());
        tRepeat.setSelected(true);
        tStartTime.setLocalDateTime(task.getStartTime());
        tEndTime.setLocalDateTime(task.getEndTime());
        tInterval.setText("" + task.getRepeatInterval());

        TaskFormInit.setVisibleFormRepeatTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime, tLabelEndTime,
                tEndTime, tLabelInterval, tInterval);
        TaskFormInit.formSet.setTextValue("Start time:", tLabelStartTime);
    }

    public void clearFieldsFormTasks(){
        tName.setText("");
        tStartTime.setLocalDateTime(null);
        tEndTime.setLocalDateTime(null);
        tActive.setSelected(false);
        tRepeat.setSelected(false);
        tInterval.setText(DEFAULT_INTERVAL);
    }

    public void setDefaultValuesTask(){
        LocalDateTime NOW = LocalDateTime.now();
        if(tStartTime.getLocalDateTime() == null){
            tStartTime.setLocalDateTime(NOW);
        }
        if(tEndTime.getLocalDateTime() == null){
            tEndTime.setLocalDateTime(NOW);
        }
        if(tInterval.getText() == null
                || tInterval.getText().length() == 0){
            tInterval.setText(DEFAULT_INTERVAL);
        }
    }

    public void setEnableTaskOnce(){
        TaskFormInit.setVisibleFormOnceTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime);

        TaskFormInit.setEnableFormOnceTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime);
    }

    public void setEnableTaskRepeat(){
        TaskFormInit.setVisibleFormRepeatTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime, tLabelEndTime,
                tEndTime, tLabelInterval, tInterval);

        TaskFormInit.setEnableFormRepeatTasksFields(tLabelName, tName, tActive,
                tRepeat, tLabelStartTime, tStartTime, tLabelEndTime,
                tEndTime, tLabelInterval, tInterval);
    }

    public void changeEditTask(){
        setDefaultValuesTask();
        if(tRepeat.isSelected()){
            setEnableTaskRepeat();
        } else {
            setEnableTaskOnce();
        }
    }

    public void refreshFormAfterCRUD(AbstractTaskList tasks){
        loadTasksListToListView(tasks);
        clearFieldsFormTasks();
        setHideFormTasks();
        setBtnCRUDHide();
        setBtnTaskStart();
    }

    public boolean verifyTaskBeforeSave(){
        String msgError = "";

        if(tRepeat.isSelected()){
            msgError = VerifyingData.isVerifyTask(tName.getText(),
                    tStartTime.getLocalDateTime(), tEndTime.getLocalDateTime(),
                    tInterval.getText());
        } else {
            msgError = VerifyingData.isVerifyTask(tName.getText(),
                    tStartTime.getLocalDateTime());
        }

        if(msgError.length()>0){
            ModalWindow.showAlertError(msgError);
            return false;
        }

        return true;
    }

}
