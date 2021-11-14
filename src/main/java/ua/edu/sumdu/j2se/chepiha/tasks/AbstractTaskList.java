/**
 *
 * @autor Valentyn Chepiha
 *
 */
package ua.edu.sumdu.j2se.chepiha.tasks;

public abstract class AbstractTaskList {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);

    protected abstract void startPosition() ;
    protected abstract Task next();

    /**
     *
     * @param from start time
     * @param to end time
     * @return the list of tasks to be performed in the specified period of time
     * @throws IllegalArgumentException generated exception if 'from' or 'to' is wrong
     */
    public AbstractTaskList incoming(int from, int to) throws IllegalArgumentException {

        if(from<0) throw new IllegalArgumentException("Time 'from' must be above zero");
        if(to<0) throw new IllegalArgumentException("Time 'to' must be above zero");
        if(to<from) throw new IllegalArgumentException("Time 'to' must be above time 'from'");

        ListTypes.types typeLists = TaskListFactory.getTypeTaskList(this);

        if(typeLists == null) throw new IllegalArgumentException("Wrong type list");

        AbstractTaskList subTaskList = TaskListFactory.createTaskList( typeLists );

        int sizeList = this.size();
        this.startPosition();
        for(int i=0; i<sizeList; i++){
            Task currentTask = this.next();
            int timeNextStart = currentTask.nextTimeAfter(from);
            if(timeNextStart >= 0 && timeNextStart < to){
                subTaskList.add(currentTask);
            }
        }
        return subTaskList;
    };
}
