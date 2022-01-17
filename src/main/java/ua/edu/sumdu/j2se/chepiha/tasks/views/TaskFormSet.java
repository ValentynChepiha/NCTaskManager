package ua.edu.sumdu.j2se.chepiha.tasks.views;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskFormSet extends TaskFormSetTmpl {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void setDisabled(Node... fields){
        setDisabled(true, fields);
    }

    public void setEnabled(Node... fields){
        setDisabled(false, fields);
    }

    public void setVisible(Node... fields) {
        super.setVisibled(true, fields);
    }

    public void setHide(Node... fields) {
        super.setVisibled(false, fields);
    }

    public void setSelected(CheckBox... fields){
        for (CheckBox field : fields) {
            field.setSelected(true);
        }
    }

    public void setUnselected(CheckBox... fields){
        for (CheckBox field : fields) {
            field.setSelected(false);
        }
    }

    public void setDateFormat(LocalDateTimeTextField... fields){
        for (LocalDateTimeTextField field : fields) {
            field.setDateTimeFormatter(DATE_FORMAT);
        }
    }

    public void setLocalDateTimeValue(LocalDateTime value, LocalDateTimeTextField... fields){
        for (LocalDateTimeTextField field : fields) {
            field.setLocalDateTime(value);
        }
    }

    public void setTextValue(String value, Label... fields){
        for (Label field : fields) {
            field.setText(value);
        }
    }

}
