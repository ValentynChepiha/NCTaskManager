package ua.edu.sumdu.j2se.chepiha.tasks.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.edu.sumdu.j2se.chepiha.tasks.controllers.TasksForm;
import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;
import ua.edu.sumdu.j2se.chepiha.tasks.services.VerifyingData;

import java.time.LocalDateTime;

public class TasksFormView {

//    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String DEFAULT_INTERVAL = "3600";

    public void startInit(TasksForm tasksForm){
        TaskFormInit.initFormCalendar(tasksForm.chkCalendar, tasksForm.dtStartCalendar,
                tasksForm.dtEndCalendar, tasksForm.btnShowCalendar);
        TaskFormInit.initFormTask(tasksForm.btnEdit, tasksForm.btnCreate, tasksForm.tStartTime, tasksForm.tEndTime,
                tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive, tasksForm.tRepeat, tasksForm.tLabelStartTime,
                tasksForm.tLabelEndTime, tasksForm.tLabelInterval, tasksForm.tInterval, tasksForm.btnCancel,
                tasksForm.btnSave, tasksForm.btnDelete);
    }

    public void calendarSwitch(TasksForm tasksForm){
        if(tasksForm.chkCalendar.isSelected()){
            TaskFormInit.setCalendarOn(tasksForm.dtStartCalendar, tasksForm.dtEndCalendar, tasksForm.btnShowCalendar);
            TaskFormInit.formSet.setDisabled(tasksForm.btnCreate, tasksForm.btnEdit, tasksForm.lvTasks);
        } else {
            TaskFormInit.setCalendarOff(tasksForm.dtStartCalendar, tasksForm.dtEndCalendar, tasksForm.btnShowCalendar);
            TaskFormInit.formSet.setEnabled(tasksForm.btnCreate);
            TaskFormInit.formSet.setDisabled(tasksForm.btnEdit);
        }
        TaskFormInit.setHideTaskEditFields(tasksForm.btnEdit, tasksForm.btnCreate, tasksForm.tLabelName,
                tasksForm.tName, tasksForm.tActive, tasksForm.tRepeat, tasksForm.tLabelStartTime,
                tasksForm.tStartTime, tasksForm.tLabelEndTime, tasksForm.tEndTime, tasksForm.tLabelInterval,
                tasksForm.tInterval, tasksForm.btnCancel, tasksForm.btnSave, tasksForm.btnDelete);
    }

    public void loadTasksListToListView(AbstractTaskList taskList, TasksForm tasksForm){
        tasksForm.lvTasks.getItems().clear();
        TaskFormInit.formSet.setDisabled(tasksForm.lvTasks);
        if(taskList.size()>0){
            ObservableList<String> names = FXCollections.observableArrayList();
            taskList.forEach(task -> names.add(task.toString()));
            tasksForm.lvTasks.setItems(names);
            TaskFormInit.formSet.setEnabled(tasksForm.lvTasks);
        }
    }

    public void setDisableListView(TasksForm tasksForm){
        TaskFormInit.formSet.setDisabled(tasksForm.lvTasks);
    }

    public boolean verifyCalendar(TasksForm tasksForm){
        boolean result = true;
        String error = VerifyingData.verifyCalendar(tasksForm.dtStartCalendar.getLocalDateTime(),
                tasksForm.dtEndCalendar.getLocalDateTime());
        if(error.length()>0){
            result = false;
            ModalWindow.showAlertError(error);
        }
        return result;
    }

    public int getIndexSelectedTask(int index, TasksForm tasksForm){
        int currentItem = tasksForm.lvTasks.getSelectionModel().getSelectedIndex();
        if(currentItem >= 0 && currentItem != index ){
            index = currentItem;
        }
        return index;
    }

    public void setBtnTaskStart(TasksForm tasksForm){
        TaskFormInit.formSet.setEnabled(tasksForm.btnCreate);
        TaskFormInit.formSet.setDisabled(tasksForm.btnEdit);
    }

    public void setBtnTaskOn(TasksForm tasksForm){
        TaskFormInit.formSet.setEnabled(tasksForm.btnCreate, tasksForm.btnEdit);
    }

    public void setBtnTaskOff(TasksForm tasksForm){
        TaskFormInit.formSet.setDisabled(tasksForm.btnCreate, tasksForm.btnEdit);
    }

    public void setBtnCRUDOn(TasksForm tasksForm){
        TaskFormInit.formSet.setEnabled(tasksForm.btnDelete);
        setBtnCreateOn(tasksForm);
    }

    public void setBtnCreateOn(TasksForm tasksForm){
        TaskFormInit.formSet.setEnabled(tasksForm.btnCancel, tasksForm.btnSave);
    }

    public void setBtnCRUDOff(TasksForm tasksForm){
        TaskFormInit.formSet.setDisabled(tasksForm.btnCancel, tasksForm.btnSave, tasksForm.btnDelete);
    }

    public void setBtnCRUDVisible(TasksForm tasksForm){
        TaskFormInit.formSet.setVisible(tasksForm.btnDelete);
        setBtnCreatVisible(tasksForm);
    }

    public void setBtnCreatVisible(TasksForm tasksForm){
        TaskFormInit.formSet.setVisible(tasksForm.btnCancel, tasksForm.btnSave);
    }

    public void setBtnCRUDHide(TasksForm tasksForm){
        TaskFormInit.formSet.setHide(tasksForm.btnCancel, tasksForm.btnSave, tasksForm.btnDelete);
    }

    public void setHideFormTasks(TasksForm tasksForm){
        TaskFormInit.setHideFormTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime, tasksForm.tLabelEndTime,
                tasksForm.tEndTime, tasksForm.tLabelInterval, tasksForm.tInterval);
    }

    public void setDisableFormTasks(TasksForm tasksForm){
        TaskFormInit.setDisableFormTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime, tasksForm.tLabelEndTime,
                tasksForm.tEndTime, tasksForm.tLabelInterval, tasksForm.tInterval);
    }

    public void loadTaskOnceEdit(Task task, TasksForm tasksForm){
        tasksForm.tName.setText(task.getTitle());
        tasksForm.tActive.setSelected(task.isActive());
        tasksForm.tRepeat.setSelected(false);
        tasksForm.tStartTime.setLocalDateTime(task.getStartTime());

        TaskFormInit.setVisibleFormOnceTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime);
        TaskFormInit.formSet.setTextValue("Time:", tasksForm.tLabelStartTime);
    }

    public void loadTaskRepeatEdit(Task task, TasksForm tasksForm){
        tasksForm.tName.setText(task.getTitle());
        tasksForm.tActive.setSelected(task.isActive());
        tasksForm.tRepeat.setSelected(true);
        tasksForm.tStartTime.setLocalDateTime(task.getStartTime());
        tasksForm.tEndTime.setLocalDateTime(task.getEndTime());
        tasksForm.tInterval.setText("" + task.getRepeatInterval());

        TaskFormInit.setVisibleFormRepeatTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime, tasksForm.tLabelEndTime,
                tasksForm.tEndTime, tasksForm.tLabelInterval, tasksForm.tInterval);
        TaskFormInit.formSet.setTextValue("Start time:", tasksForm.tLabelStartTime);
    }

    public void clearFieldsFormTasks(TasksForm tasksForm){
        tasksForm.tName.setText("");
        tasksForm.tStartTime.setLocalDateTime(null);
        tasksForm.tEndTime.setLocalDateTime(null);
        tasksForm.tActive.setSelected(false);
        tasksForm.tRepeat.setSelected(false);
        tasksForm.tInterval.setText(DEFAULT_INTERVAL);
    }

    public void setDefaultValuesTask(TasksForm tasksForm){
        LocalDateTime NOW = LocalDateTime.now();
        if(tasksForm.tStartTime.getLocalDateTime() == null){
            tasksForm.tStartTime.setLocalDateTime(NOW);
        }
        if(tasksForm.tEndTime.getLocalDateTime() == null){
            tasksForm.tEndTime.setLocalDateTime(NOW);
        }
        if(tasksForm.tInterval.getText() == null
                || tasksForm.tInterval.getText().length() == 0){
            tasksForm.tInterval.setText(DEFAULT_INTERVAL);
        }
    }

    public void setEnableTaskOnce(TasksForm tasksForm){
        TaskFormInit.setVisibleFormOnceTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime);

        TaskFormInit.setEnableFormOnceTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime);
    }

    public void setEnableTaskRepeat(TasksForm tasksForm){
        TaskFormInit.setVisibleFormRepeatTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime, tasksForm.tLabelEndTime,
                tasksForm.tEndTime, tasksForm.tLabelInterval, tasksForm.tInterval);

        TaskFormInit.setEnableFormRepeatTasksFields(tasksForm.tLabelName, tasksForm.tName, tasksForm.tActive,
                tasksForm.tRepeat, tasksForm.tLabelStartTime, tasksForm.tStartTime, tasksForm.tLabelEndTime,
                tasksForm.tEndTime, tasksForm.tLabelInterval, tasksForm.tInterval);
    }

    public void changeEditTask(TasksForm tasksForm){
        setDefaultValuesTask(tasksForm);
        if(tasksForm.tRepeat.isSelected()){
            setEnableTaskRepeat(tasksForm);
        } else {
            setEnableTaskOnce(tasksForm);
        }
    }

    public void refreshFormAfterCRUD(AbstractTaskList tasks, TasksForm tasksForm){
        loadTasksListToListView(tasks, tasksForm);
        clearFieldsFormTasks(tasksForm);
        setHideFormTasks(tasksForm);
        setBtnCRUDHide(tasksForm);
        setBtnTaskStart(tasksForm);
    }

    public boolean verifyTaskBeforeSave(TasksForm tasksForm){
        String msgError = "";

        if(tasksForm.tRepeat.isSelected()){
            msgError = VerifyingData.isVerifyTask(tasksForm.tName.getText(),
                    tasksForm.tStartTime.getLocalDateTime(), tasksForm.tEndTime.getLocalDateTime(),
                    tasksForm.tInterval.getText());
        } else {
            msgError = VerifyingData.isVerifyTask(tasksForm.tName.getText(),
                    tasksForm.tStartTime.getLocalDateTime());
        }

        if(msgError.length()>0){
            ModalWindow.showAlertError(msgError);
            return false;
        }

        return true;
    }

}
