package ua.edu.sumdu.j2se.chepiha.tasks.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Tasks;
import ua.edu.sumdu.j2se.chepiha.tasks.services.TaskListIO;

import java.time.LocalDateTime;

public class TasksFormModel {

    Logger logger = LoggerFactory.getLogger(TasksFormModel.class.getName());

    public void insertDataToWorkList(AbstractTaskList tasks, AbstractTaskList workTasks){
        workTasks.clear();
        if(tasks.size()>0){
            tasks.forEach(workTasks::add);
        }
    }

    public void initTaskList(AbstractTaskList tasks, AbstractTaskList workTasks) {
        try {
            TaskListIO.loadTaskList(tasks);
            insertDataToWorkList(tasks, workTasks);
            logger.info("Loaded {} rows", tasks.size());
        } catch (Exception e){
            logger.error("Error load: ",  e);
        }
    }

    public void getWorkTaskList(AbstractTaskList originTasks, AbstractTaskList workTasks,
                                 LocalDateTime start, LocalDateTime end){
        Iterable<Task> calendarTasks = Tasks.incoming(originTasks, start, end);
        workTasks.clear();
        calendarTasks.forEach(workTasks::add);
    }

    public void saveTaskListToFile(AbstractTaskList tasks){
        try {
            TaskListIO.saveTaskList(tasks);
        } catch (Exception e) {
            logger.error("Save task list: ", e);
        }

    }

}
