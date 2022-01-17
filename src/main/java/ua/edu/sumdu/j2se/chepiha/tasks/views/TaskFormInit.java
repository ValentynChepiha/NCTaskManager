package ua.edu.sumdu.j2se.chepiha.tasks.views;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.time.LocalDateTime;

public class TaskFormInit {

    static TaskFormSet formSet = new TaskFormSet();

    static void initFormCalendar(CheckBox chkCalendar, LocalDateTimeTextField dtStartCalendar,
                          LocalDateTimeTextField dtEndCalendar, Button btnShowCalendar){

        formSet.setDisabled(dtStartCalendar, dtEndCalendar, btnShowCalendar);
        formSet.setDateFormat(dtStartCalendar, dtEndCalendar);
        formSet.setLocalDateTimeValue(LocalDateTime.now(), dtStartCalendar, dtEndCalendar);
        formSet.setUnselected(chkCalendar);
    }


    static void initFormTask(Button btnEdit, Button btnCreate,
                             LocalDateTimeTextField tStartTime, LocalDateTimeTextField tEndTime,
                             Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                             Label tLabelStartTime, Label tLabelEndTime, Label tLabelInterval, TextField tInterval,
                             Button btnCancel, Button btnSave, Button btnDelete){
        initTaskButton(btnEdit, btnCreate, btnCancel, btnSave, btnDelete);
        setHideFormTasksFields(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime, tLabelEndTime, tEndTime, tLabelInterval, tInterval);
        formSet.setDateFormat(tStartTime, tEndTime);
    }

    static void initTaskButton(Button btnEdit, Button btnCreate,
                               Button btnCancel, Button btnSave, Button btnDelete){
        formSet.setDisabled(btnEdit);
        formSet.setEnabled(btnCreate);
        setHideButtonsEdit(btnCancel, btnSave, btnDelete);
    }

    static void setHideTaskEditFields(Button btnEdit, Button btnCreate, Label tLabelName, TextField tName, CheckBox tActive,
                                      CheckBox tRepeat, Label tLabelStartTime, LocalDateTimeTextField tStartTime,
                                      Label tLabelEndTime, LocalDateTimeTextField tEndTime, Label tLabelInterval, TextField tInterval,
                                      Button btnCancel, Button btnSave, Button btnDelete){
        formSet.setDisabled(btnEdit, btnCreate);
        setHideButtonsEdit(btnCancel, btnSave, btnDelete);
        setHideFormTasksFields(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                               tStartTime, tLabelEndTime, tEndTime, tLabelInterval, tInterval);
    }

    static void setHideFormTasksFields(Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                                       Label tLabelStartTime, LocalDateTimeTextField tStartTime, Label tLabelEndTime,
                                       LocalDateTimeTextField tEndTime, Label tLabelInterval, TextField tInterval){
        formSet.setHide(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime, tLabelEndTime, tEndTime, tLabelInterval, tInterval);
    }

    static void setDisableFormTasksFields(Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                                       Label tLabelStartTime, LocalDateTimeTextField tStartTime, Label tLabelEndTime,
                                       LocalDateTimeTextField tEndTime, Label tLabelInterval, TextField tInterval){
        formSet.setDisabled(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime, tLabelEndTime, tEndTime, tLabelInterval, tInterval);
    }

    static void setVisibleFormOnceTasksFields(Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                                       Label tLabelStartTime, LocalDateTimeTextField tStartTime){
        formSet.setVisible(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime);
    }

    static void setEnableFormOnceTasksFields(Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                                       Label tLabelStartTime, LocalDateTimeTextField tStartTime){
        formSet.setEnabled(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime);
    }

    static void setVisibleFormRepeatTasksFields(Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                                       Label tLabelStartTime, LocalDateTimeTextField tStartTime, Label tLabelEndTime,
                                       LocalDateTimeTextField tEndTime, Label tLabelInterval, TextField tInterval){
        formSet.setVisible(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime, tLabelEndTime, tEndTime, tLabelInterval, tInterval);
    }

    static void setEnableFormRepeatTasksFields(Label tLabelName, TextField tName, CheckBox tActive, CheckBox tRepeat,
                                       Label tLabelStartTime, LocalDateTimeTextField tStartTime, Label tLabelEndTime,
                                       LocalDateTimeTextField tEndTime, Label tLabelInterval, TextField tInterval){
        formSet.setEnabled(tLabelName, tName,  tActive,  tRepeat, tLabelStartTime,
                tStartTime, tLabelEndTime, tEndTime, tLabelInterval, tInterval);
    }

    static void setHideButtonsEdit(Button btnCancel, Button btnSave, Button btnDelete){
        setHideButtonsCreate(btnCancel, btnSave);
        formSet.setHide(btnDelete);
    }

    static void setHideButtonsCreate(Button btnCancel, Button btnSave){
        formSet.setHide(btnCancel, btnSave);
    }

    static void setCalendarOn(LocalDateTimeTextField dtStartCalendar,
                              LocalDateTimeTextField dtEndCalendar, Button btnShowCalendar){
        formSet.setEnabled(dtStartCalendar, dtEndCalendar, btnShowCalendar);
        formSet.setLocalDateTimeValue(LocalDateTime.now(), dtStartCalendar, dtEndCalendar);
    }

    static void setCalendarOff(LocalDateTimeTextField dtStartCalendar,
                               LocalDateTimeTextField dtEndCalendar, Button btnShowCalendar){
        formSet.setDisabled(dtStartCalendar, dtEndCalendar, btnShowCalendar);
    }
}
