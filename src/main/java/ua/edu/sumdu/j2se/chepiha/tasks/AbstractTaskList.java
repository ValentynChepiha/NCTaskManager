/**
 *
 * @autor Valentyn Chepiha
 *
 */
package ua.edu.sumdu.j2se.chepiha.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);

    public abstract Iterator<Task> iterator();

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
        AbstractTaskList subTaskList = TaskListFactory.createTaskList( typeLists );

        for (Task task: this) {
            int timeNextStart = task.nextTimeAfter(from);
            if(timeNextStart >= 0 && timeNextStart < to){
                subTaskList.add(task);
            }
        }
        return subTaskList;
    };

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        String[] classInfoArray = this.getClass().toString().split(" ")[1].split("\\.");

        result.append("ListTask type of ").append(classInfoArray[classInfoArray.length-1]).append("\n").append("Elements:\n");
        for (Task task: this) {
            result.append(task).append("\n");
        }
        return result.toString();
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTaskList taskList = (AbstractTaskList) o;

        if(this.size() != taskList.size()) return false;

        boolean result = true;
        for(int i = 0; i<this.size(); i++){
            result = this.getTask(i).equals(taskList.getTask(i));
            if(!result)
                break;
        }
        return result;
    };

    @Override
    public int hashCode() {
        int result = 0;
        for (Task task: this) {
            result = result + task.hashCode();
        }
        return result;
    }

    @Override
    public AbstractTaskList clone() {
        ListTypes.types typeLists = TaskListFactory.getTypeTaskList(this);
        AbstractTaskList cloneTaskList = TaskListFactory.createTaskList( typeLists );

        for (Task task: this) {
            cloneTaskList.add(task.clone());
        }
        return cloneTaskList;
    }
}
