/**
 *
 * @autor Valentyn Chepiha
 *
 */

package ua.edu.sumdu.j2se.chepiha.tasks;

public class TaskListFactory {

    /**
     *
     * @param taskList ListTask
     * @return type of ListTask, if unknown type of ListTask then returned null
     */
    public static ListTypes.types getTypeTaskList( AbstractTaskList taskList) throws IllegalArgumentException {
        if (taskList instanceof ArrayTaskList)
            return ListTypes.types.ARRAY;
        if (taskList instanceof LinkedTaskList)
            return ListTypes.types.LINKED;

        throw new IllegalArgumentException("Unknown type TaskList");
    }

    /**
     *
     * @param typeList type of ListTask
     * @return create new ListTask, if unknown type then returned null
     */
    public static AbstractTaskList createTaskList(ListTypes.types typeList) throws IllegalArgumentException {
        switch (typeList){
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default: throw new IllegalArgumentException("Unknown type list");
        }
    }
}
