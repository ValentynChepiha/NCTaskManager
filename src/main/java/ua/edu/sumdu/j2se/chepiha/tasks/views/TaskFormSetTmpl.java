package ua.edu.sumdu.j2se.chepiha.tasks.views;

import javafx.scene.Node;

public class TaskFormSetTmpl {

    protected void setVisibled(Boolean flag, Node... fields ){
        for (Node field : fields) {
            field.setVisible(flag);
        }
    }

    protected void setDisabled(Boolean flag, Node... fields ){
        for (Node field : fields) {
            field.setDisable(flag);
        }
    }

}
