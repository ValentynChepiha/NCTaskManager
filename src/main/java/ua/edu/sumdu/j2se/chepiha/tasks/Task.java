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
    private final int DEFAULT_TIME_VALUE = -1;
    private final int RESULT_WRONG = -1;
    private final int RESULT_ZERO = 0;

    private String title = "";
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

        this.setDefaultRepeatedTime();

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

        this.setDefaultNotRepeatedTime();

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    private void setDefaultNotRepeatedTime() {
        this.time = DEFAULT_TIME_VALUE;
    }

    private void setDefaultRepeatedTime() {
        this.start = DEFAULT_TIME_VALUE;
        this.end = DEFAULT_TIME_VALUE;
        this.interval = DEFAULT_TIME_VALUE;
    }

    /**
     *
     * @return returning name of task
     */
    public String getTitle() {
        return this.title;
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
        return this.activeTask;
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
        if (this.start > DEFAULT_TIME_VALUE)
            return this.start;
        return this.time;
    }

    /**
     *
     * @param time the time for a one-time task
     */
    public void setTime(int time) throws IllegalArgumentException {

        if(time <0) throw new IllegalArgumentException("Time must be above zero");

        this.setDefaultRepeatedTime();

        this.time = time;
    }

    /**
     *
     * @return return time to start the task
     */
    public int getStartTime() {
        if(this.start > DEFAULT_TIME_VALUE)
            return this.start;
        return this.time;
    }

    /**
     *
     * @return return time to end the task
     */
    public int getEndTime() {
        if(this.end > DEFAULT_TIME_VALUE)
            return this.end;
        return this.time;
    }

    /**
     *
     * @return return the interval to repeat the task, if task runs once return 0
     */
    public int getRepeatInterval() {
        if(this.interval > DEFAULT_TIME_VALUE)
            return this.interval;
        return RESULT_ZERO;
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

        this.setDefaultNotRepeatedTime();

        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     *
     * @return return true if the task is recurring else return false
     */
    public boolean isRepeated() {
        return this.start > DEFAULT_TIME_VALUE;
    }

    private int calculateNextStart (int current) {
        int deltaTime = current - this.start;
        int wholePart = (int)(deltaTime / this.interval);

        if(wholePart * this.interval <= deltaTime )
            wholePart++;

        return wholePart * this.interval + this.start;
    }

    private int getNextRepeatedTime(int current) {
        if(current < this.start)
            return this.start;
        if(current > this.end)
            return RESULT_WRONG;

        int nextTime = this.calculateNextStart(current);
        if(nextTime > this.end)
            return RESULT_WRONG;

        return nextTime;
    }

    private int getNextNotRepeatedTime(int current) {
        if(current >= this.time)
            return RESULT_WRONG;

        return this.time;
    }

    /**
     *
     * @param current time to next start the task
     * @return return the time to next start the task, or -1 if the task never will be starting
     */
    public int nextTimeAfter (int current) throws IllegalArgumentException {

        if(current <0) throw new IllegalArgumentException("Current time must be above zero");

        if(!this.activeTask)
            return RESULT_WRONG;
        if(this.time > DEFAULT_TIME_VALUE)
            return this.getNextNotRepeatedTime(current);
        return this.getNextRepeatedTime(current);
    }
}
