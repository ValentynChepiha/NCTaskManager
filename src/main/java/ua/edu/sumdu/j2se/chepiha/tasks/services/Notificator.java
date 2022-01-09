package ua.edu.sumdu.j2se.chepiha.tasks.services;

import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class Notificator {

    public static void run(AbstractTaskList taskList){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime now = LocalDateTime.now();
                StringBuilder taskNow = new StringBuilder("Task: ");
                AtomicBoolean flag = new AtomicBoolean(false);
                for (Task task: taskList) {
                    LocalDateTime nextTime = task.nextTimeAfter(now);
                    System.out.println("task :: " + nextTime + " :: " + now + " :: " + task);
                    if (nextTime!= null &&
                            nextTime.format(DATE_FORMAT).equals(now.format(DATE_FORMAT)) ){
                        flag.set(true);
                        taskNow.append(task.getTitle()).append("; ");
                    }
                }
                if(flag.get()){
                    ModalWindow.showAlertInformation(taskNow.toString());
                }
            }
        }, 0, 60000);
    }

    public static void stop(){
        Timer timer = new Timer();
        timer.cancel();
        timer.purge();
    }

}
