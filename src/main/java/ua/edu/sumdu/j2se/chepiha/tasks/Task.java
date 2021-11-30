/**
 *
 * @autor Valentyn Chepiha
 *
 * Returns a Task object.
 * The object has methods to adding, enabling, disabling the task.
 * You can change parameters the task: name, start, end or interval.
 */
package ua.edu.sumdu.j2se.chepiha.tasks;

public class Task {
    private static final int DEFAULT_TIME_VALUE = -1;
    private static final int RESULT_WRONG = -1;
    private static final int RESULT_ZERO = 0;

    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean activeTask = false;

    /**
     *
     * @param title     task name
     * @param time      the time for a one-time task
     */
    public Task(String title, int time) throws IllegalArgumentException {

        if(time<0) throw new IllegalArgumentException("Time must be above zero");

        setDefaultRepeatedTime();

        this.title = title;
        this.time = time;
    }

    /**
     *
     * @param title task name
     * @param start the start time of the recurring task
     * @param end the end time of the recurring task
     * @param interval a period of time to repeat the task
     */
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {

        if(start<0) throw new IllegalArgumentException("Time start must be above zero");
        if(end<0) throw new IllegalArgumentException("Time end must be above zero");
        if(interval<0) throw new IllegalArgumentException("Interval must be above zero");
        if(end<start) throw new IllegalArgumentException("Time end must be above time start");

        setDefaultNotRepeatedTime();

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    private void setDefaultNotRepeatedTime()
    {
        time = DEFAULT_TIME_VALUE;
    }

    private void setDefaultRepeatedTime() {
        start = DEFAULT_TIME_VALUE;
        end = DEFAULT_TIME_VALUE;
        interval = DEFAULT_TIME_VALUE;
    }

    /**
     *
     * @return returning name of task
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title task name
     */
    public void setTitle(String title) throws IllegalArgumentException {

        if(title.trim().length()<=0) throw new IllegalArgumentException("Name task must not equal null");

        this.title = title;
    }

    /**
     *
     * @return returning true if task is enabled, else returning false
     */
    public boolean isActive() {
        return activeTask;
    }

    /**
     *
     * @param activeTask flag to enable the task: true - enabled, false - disabled
     */
    public void setActive(boolean activeTask) {
        this.activeTask = activeTask;
    }

    /**
     *
     * @return time to start a one-time task
     */
    public int getTime() {
        return (start > DEFAULT_TIME_VALUE) ? start : time;
    }

    /**
     *
     * @param time the time for a one-time task
     */
    public void setTime(int time) throws IllegalArgumentException {

        if(time <0) throw new IllegalArgumentException("Time must be above zero");

        setDefaultRepeatedTime();
        this.time = time;
    }

    /**
     *
     * @return return time to start the task
     */
    public int getStartTime() {
        return (start > DEFAULT_TIME_VALUE) ? start : time;
    }

    /**
     *
     * @return return time to end the task
     */
    public int getEndTime() {
        return (end > DEFAULT_TIME_VALUE) ? end : time;
    }

    /**
     *
     * @return return the interval to repeat the task, if task runs once return 0
     */
    public int getRepeatInterval() {
        return (interval > DEFAULT_TIME_VALUE) ? interval : RESULT_ZERO ;
    }

    /**
     *
     * @param start the start time of the recurring task
     * @param end the end time of the recurring task
     * @param interval a period of time to repeat the task
     */
    public void setTime(int start, int end, int interval) throws IllegalArgumentException {

        if(start<0) throw new IllegalArgumentException("Time start must be above zero");
        if(end<0) throw new IllegalArgumentException("Time end must be above zero");
        if(interval<0) throw new IllegalArgumentException("Interval must be above zero");
        if(end<start) throw new IllegalArgumentException("Time end must be above time start");

        setDefaultNotRepeatedTime();

        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     *
     * @return return true if the task is recurring else return false
     */
    public boolean isRepeated() {
        return start > DEFAULT_TIME_VALUE;
    }

    private int calculateNextStart (int current) {
        int deltaTime = current - start;
        int wholePart = (int)deltaTime / interval;

        if(wholePart * interval <= deltaTime )
            wholePart++;

        return wholePart * interval + start;
    }

    private int getNextRepeatedTime(int current) {
        if(current < start)
            return start;
        if(current > end)
            return RESULT_WRONG;

        int nextTime = calculateNextStart(current);

        return (nextTime > end) ? RESULT_WRONG : nextTime;
    }

    private int getNextNotRepeatedTime(int current) {
        return (current >= time) ? RESULT_WRONG : time;
    }

    /**
     *
     * @param current time to next start the task
     * @return return the time to next start the task, or -1 if the task never will be starting
     */
    public int nextTimeAfter (int current) throws IllegalArgumentException {

        if(current <0) throw new IllegalArgumentException("Current time must be above zero");

        if(!activeTask)
            return RESULT_WRONG;

        return (time > DEFAULT_TIME_VALUE) ? getNextNotRepeatedTime(current) : getNextRepeatedTime(current);
    }

    /**
     *
     * @return string line with text info about task
     */
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                (time < 0 ? "" : ", time=" + time) +
                (start < 0 ? "" : ", start=" + start) +
                (end < 0 ? "" : ", end=" + end) +
                (interval < 0 ? "" : ", interval=" + interval) +
                ", activeTask=" + activeTask +
                '}';
    }

    /**
     *
     * @param o object to compare
     * @return if current task equals o return true else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time
                && start == task.start
                && end == task.end
                && interval == task.interval
                && activeTask == task.activeTask
                && title.equals(task.title);
    }

    /**
     *
     * @return hash code current task
     */
    @Override
    public int hashCode() {
        int salt = 31;
        int result = 7;
        result = salt * result + title.hashCode();
        result = salt * result + time;
        result = salt * result + start;
        result = salt * result + end;
        result = salt * result + interval;
        result = salt * result + (activeTask ? 1 : 0);
        return result;
    }

    /**
     *
     * @return clone current task
     */
    @Override
    public Task clone(){
        Task cloneTask = new Task(title, time);

        if(isRepeated()){
            cloneTask.setTime(start, end, interval);
        }
        cloneTask.setActive(isActive());

        return cloneTask;
    };
}
