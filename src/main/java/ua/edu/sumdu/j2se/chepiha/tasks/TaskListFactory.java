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
    public static ListTypes.types getTypeTaskList( AbstractTaskList taskList){
        if (taskList instanceof ArrayTaskList)
            return ListTypes.types.ARRAY;
        if (taskList instanceof LinkedTaskList)
            return ListTypes.types.LINKED;
        return null;
    }

    /**
     *
     * @param typeList type of ListTask
     * @return create new ListTask, if unknown type then returned null
     */
    public static AbstractTaskList createTaskList(ListTypes.types typeList){
        AbstractTaskList resultTaskList;
        switch (typeList){
            case ARRAY:
                resultTaskList = new ArrayTaskList();
                break;
            case LINKED:
                resultTaskList = new LinkedTaskList();
                break;
            default: resultTaskList = null;
        }
        return resultTaskList;
    }
}
