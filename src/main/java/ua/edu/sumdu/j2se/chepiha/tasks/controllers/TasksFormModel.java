package ua.edu.sumdu.j2se.chepiha.tasks.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Tasks;
import ua.edu.sumdu.j2se.chepiha.tasks.services.TaskListIO;

import java.time.LocalDateTime;

public class TasksFormModel {

    Logger logger = LoggerFactory.getLogger(TasksFormModel.class.getName());

    private AbstractTaskList tasks = null;
    private AbstractTaskList workTasks = null;

    public TasksFormModel() {
        this.tasks = new ArrayTaskList();
        this.workTasks = new ArrayTaskList();
    }

    public AbstractTaskList getTasks() {
        return tasks;
    }

    public AbstractTaskList getWorkTasks() {
        return workTasks;
    }

    public void addTask(Task task){
        if(task != null){
            tasks.add(task);
        }
    }

    public void removeTask(Task task){
        if(task != null){
            tasks.remove(task);
        }
    }

    public void insertDataToWorkList(){
        workTasks.clear();
        if(tasks.size()>0){
            tasks.forEach(workTasks::add);
        }
    }

    public void initTaskList() {
        try {
            TaskListIO.loadTaskList(tasks);
            insertDataToWorkList();
            logger.info("Loaded {} rows", tasks.size());
        } catch (Exception e){
            logger.error("Error load: ",  e);
        }
    }

    public void getWorkTaskList(LocalDateTime start, LocalDateTime end){
        Iterable<Task> calendarTasks = Tasks.incoming(tasks, start, end);
        workTasks.clear();
        calendarTasks.forEach(workTasks::add);
    }

    public void saveTaskListToFile(){
        try {
            TaskListIO.saveTaskList(tasks);
        } catch (Exception e) {
            logger.error("Save task list: ", e);
        }

    }


}
