package ua.edu.sumdu.j2se.chepiha.tasks.services;

import javafx.scene.control.Alert;

public class ModalWindow {

    public static void showAlertError(String msgError) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Task fields");
        alert.setHeaderText(null);
        alert.setContentText(msgError);
        alert.showAndWait();
    }

}
