/**
 *
 * @autor Valentyn Chepiha
 *
 * Returns a ArrayTaskList object.
 * The object has methods to adding, removing element of Array.
 * You can get the size of the list, information about the task in the Array
 * and the Array of tasks it performs between two current moments.
 */
package ua.edu.sumdu.j2se.chepiha.tasks;

public class ArrayTaskList extends AbstractTaskList {

    private final float DELTA_SIZE_LIST = 1.5f;
    private final int START_LENGTH_LIST = 10;

    private Task[] taskList;
    private int sizeList = 0;

    private int current = 0;

    /**
     * constructor
     */
    public ArrayTaskList() {
        this.taskList = new Task[START_LENGTH_LIST];
    }

    /**
     * constructor
     * @param startLength size list
     * @throws IllegalArgumentException generated exception if 'startLength' below or equal zero
     */
    public ArrayTaskList(int startLength) throws IllegalArgumentException {

        if(startLength<=0) throw new IllegalArgumentException("Length must be above zero");

        this.taskList = new Task[startLength];
    }

    /**
     *
     * @param task new task to the list
     * @throws IllegalArgumentException generated exception if 'task' is null
     */
    @Override
    public void add(Task task) throws IllegalArgumentException {

        if(task == null) throw new IllegalArgumentException("Task must not equal null");

        if(sizeList+1 >= taskList.length){
            int newLength = (int)(taskList.length * DELTA_SIZE_LIST) + 1;
            Task[] newTaskList = new Task[newLength];
            System.arraycopy(taskList, 0, newTaskList, 0, sizeList);
            taskList = newTaskList;
        }
        taskList[sizeList] = task;
        sizeList++;
    }

    private Task[] createNewTaskList(int indexTask) throws IllegalArgumentException {

        if(indexTask<0 || indexTask>=sizeList) throw new IndexOutOfBoundsException("Index out of array");

        Task[] newTaskList = new Task[taskList.length];
        if(indexTask > 0){
            System.arraycopy(taskList, 0, newTaskList, 0, indexTask);
            System.arraycopy(taskList, indexTask+1, newTaskList, indexTask, taskList.length-indexTask - 1);
        } else {
            System.arraycopy(taskList, indexTask+1, newTaskList, 0, taskList.length-indexTask - 1);
        }
        return newTaskList;
    }

    /**
     *
     * @param task the task that need remove
     * @return if is done return true else return false
     * @throws IllegalArgumentException generated exception if 'task' is null
     */
    @Override
    public boolean remove(Task task) throws IllegalArgumentException {

        if(task == null) throw new IllegalArgumentException("Task must not equal null");

        for(int i=0; i<sizeList; i++){
            if(taskList[i].equals(task)){
                taskList = this.createNewTaskList(i);
                sizeList--;
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return amount tasks in the list
     */
    @Override
    public int size(){
        return sizeList;
    }

    /**
     *
     * @param index the index of the task in the list, starting with 0
     * @return return the task or null
     * @throws IndexOutOfBoundsException generated exception if 'index' is wrong
     */
    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {

        if(index<0 || index>=sizeList) throw new IndexOutOfBoundsException("Index out of array");

        return taskList[index];
    }

    @Override
    protected void startPosition() {
        this.current = 0;
    }

    @Override
    protected Task next(){
        Task resultTask = taskList[current];
        current++;
        return resultTask;
    }
}
