package ua.edu.sumdu.j2se.chepiha.tasks.services;

import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.TaskIO;

import java.io.File;

public class TaskListIO {

    private final static String file = "tsklist.bin";

    public static void loadTaskList(AbstractTaskList tasks){
        TaskIO.readBinary(tasks, new File(file));
    }

    public static void saveTaskList(AbstractTaskList tasks) {
        TaskIO.writeBinary(tasks, new File(file));
    }
}
