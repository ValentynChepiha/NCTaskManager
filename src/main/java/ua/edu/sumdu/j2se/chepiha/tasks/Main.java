package ua.edu.sumdu.j2se.chepiha.tasks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

	public static void main(String[] args) {
		System.out.println("This is a Main from Task ;)");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Platform.setImplicitExit(true);
		URL resource = getClass().getClassLoader().getResource("javaFX/TasksForm.fxml");
		Parent root = FXMLLoader.load(resource);
		primaryStage.setTitle("Tasks");
		primaryStage.setScene(new Scene(root, 600, 480));
		primaryStage.setResizable(false);
		primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
	}
}

//      https://habr.com/ru/company/naumen/blog/228279/



