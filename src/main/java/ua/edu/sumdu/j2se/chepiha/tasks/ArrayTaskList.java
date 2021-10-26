package ua.edu.sumdu.j2se.chepiha.tasks;

public class ArrayTaskList {

    private final float DELTA_SIZE_LIST = 1.5f;
    private final int START_LENGTH_LIST = 10;

    private Task[] taskList;
    private int sizeList = 0;

    /**
     * constructor
     */
    public ArrayTaskList() {
        this.taskList = new Task[START_LENGTH_LIST];
    }

    /**
     * constructor
     * @param startLength size list
     */
    public ArrayTaskList(int startLength) {
        this.taskList = new Task[startLength];
    }

    /**
     *
     * @param task new task to the list
     */
    public void add(Task task){
        if(sizeList+1 >= taskList.length){
            int newLength = (int)(taskList.length * DELTA_SIZE_LIST) + 1;
            Task[] newTaskList = new Task[newLength];
            System.arraycopy(taskList, 0, newTaskList, 0, sizeList);
            taskList = newTaskList;
            newTaskList = null;
        }
        taskList[sizeList] = task;
        sizeList++;
    }

    /**
     *
     * @param task the task that need remove
     * @return if is done return true else return false
     */
    public boolean remove(Task task){
        int indexTask = -1;

        for(int i=0; i<sizeList; i++){
            if(taskList[i].equals(task)){
                indexTask = i;
                break;
            }
        }

        if(indexTask==-1){
            return false;
        }

        Task[] newTaskList = new Task[taskList.length];
        if(indexTask > 0){
            System.arraycopy(taskList, 0, newTaskList, 0, indexTask);
            System.arraycopy(taskList, indexTask+1, newTaskList, indexTask, taskList.length-indexTask - 1);
        } else {
            System.arraycopy(taskList, indexTask+1, newTaskList, 0, taskList.length-indexTask - 1);
        }

        taskList = newTaskList;
        newTaskList = null;
        sizeList--;
        return true;
    }

    /**
     *
     * @return amount tasks in the list
     */
    public int size(){
        return sizeList;
    }

    /**
     *
     * @param index the index of the task in the list, starting with 0
     * @return return the task or null
     */
    public Task getTask(int index){
        if(index >= sizeList)
            return null;
        return taskList[index];
    }

    /**
     *
     * @param from start time
     * @param to end time
     * @return the list of tasks to be performed in the specified period of time
     */
    public ArrayTaskList incoming(int from, int to){
        ArrayTaskList subTaskList = new ArrayTaskList(sizeList);
        for(int i=0; i<sizeList; i++){
            int timeNextStart = taskList[i].nextTimeAfter(from);
            if(timeNextStart>=0 && timeNextStart <= to){
                subTaskList.add(taskList[i]);
            }
        }
        return subTaskList;
    }
}
