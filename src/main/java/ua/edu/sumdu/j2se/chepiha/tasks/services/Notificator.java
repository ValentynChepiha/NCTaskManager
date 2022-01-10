package ua.edu.sumdu.j2se.chepiha.tasks.services;

import javafx.application.Platform;
import ua.edu.sumdu.j2se.chepiha.tasks.models.AbstractTaskList;
import ua.edu.sumdu.j2se.chepiha.tasks.models.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class Notificator {
// todo:
//    можлива варто зробити синглтон
    public static void run(AbstractTaskList taskList){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
                           @Override
                           public void run() {
                               DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                               LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                               StringBuilder taskNow = new StringBuilder("Task: ");
                               AtomicBoolean flag = new AtomicBoolean(false);
                               for (Task task: taskList) {
                                   LocalDateTime nextTime = task.nextTimeAfter(now);
                                   if (nextTime!= null){
                                       nextTime = nextTime.truncatedTo(ChronoUnit.MINUTES);
                                       if( nextTime.format(DATE_FORMAT).equals(now.format(DATE_FORMAT)) ){
                                           flag.set(true);
                                           taskNow.append(task.getTitle()).append("; ");
                                       }
                                   }
                               }
                               if(flag.get()){
                                   Platform.runLater(()->ModalWindow.showAlertInformation(taskNow.toString()));
                               }
                           }
                       },
                    java.sql.Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)),
                    60000);
    }

}
