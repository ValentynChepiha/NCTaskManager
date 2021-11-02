/**
 *
 * @autor Valentyn Chepiha
 *
 * Returns a LinkedTaskList object.
 * The object has methods to adding, removing element of List.
 * You can get the size of the list, information about the task in the list
 * and the List of tasks it performs between two current moments.
 */
package ua.edu.sumdu.j2se.chepiha.tasks;

import java.util.LinkedList;

public class LinkedTaskList {

    private static class Node {
        Task value;
        Node prev;
        Node next;

        public Node(Node prev, Task value, Node next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private int sizeList = 0;
    private Node first;
    private Node last;

    /**
     * constructor
     */
    public LinkedTaskList() {
    }

    /**
     *
     * @param task new task to the list
     */
    public void add(Task task) throws IllegalArgumentException {

        if(task == null) throw new IllegalArgumentException("Task must not equal null");

        switch (sizeList){
            case 0:
                first = new Node(null, task,null);
                break;
            case 1:
                last = new Node(first, task, null);
                first.next = last;
                break;
            default:
                Node oldLast = last;
                last = new Node(oldLast, task, null);
                oldLast.next = last;
        }
        sizeList++;
    }

    /**
     *
     * @param task the task that need remove
     * @return if is done return true else return false
     */
    public boolean remove(Task task) throws IllegalArgumentException {

        if(task == null) throw new IllegalArgumentException("Task must not equal null");

        if(sizeList==0) return false;

        Node currentNode = first;
        do {
            if(currentNode.value.equals(task)){

                if(currentNode.prev != null){
                    currentNode.prev.next = currentNode.next;
                } else {
                    first = currentNode.next;
                }


                if(currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    last = currentNode.prev;
                }

                sizeList--;
                return true;
            }
            currentNode = currentNode.next;
        } while (currentNode != null);

        return false;
    }

    /**
     *
     * @return amount tasks in the list
     */
    public int size(){
        return sizeList;
    }

    private Task searchUp(int index){
        int maxI = (int)sizeList/2 + 1;
        Node currentNode = first;
        for(int i=0; i<maxI; i++){
            if(i == index)
                return currentNode.value;
            currentNode = currentNode.next;
        }
        return null;
    }

    private Task searchDown(int index){
        int minI = (int)sizeList/2 - 1;
        Node currentNode = last;
        for(int i=sizeList-1; i>minI; i--){
            if(i == index)
                return currentNode.value;
            currentNode = currentNode.prev;
        }
        return null;
    }

    /**
     *
     * @param index the index of the task in the list, starting with 0
     * @return return the task or null
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {

        if(index<0 || index>=sizeList) throw new IndexOutOfBoundsException("Index out of list");

        return ( index < sizeList / 2) ? this.searchUp(index) : this.searchDown(index);
    }

    /**
     *
     * @param from start time
     * @param to end time
     * @return the list of tasks to be performed in the specified period of time
     */
    public LinkedTaskList incoming(int from, int to) throws IllegalArgumentException {

        if(from<0) throw new IllegalArgumentException("Time 'from' must be above zero");
        if(to<0) throw new IllegalArgumentException("Time 'to' must be above zero");
        if(to<from) throw new IllegalArgumentException("Time 'to' must be above time 'from'");

        LinkedTaskList subTaskList = new LinkedTaskList();

        Node currentNode = first;
        for(int i=0; i<sizeList; i++){
            int timeNextStart = currentNode.value.nextTimeAfter(from);
            if(timeNextStart>=0 && timeNextStart <= to){
                subTaskList.add(currentNode.value);
            }
            currentNode = currentNode.next;
        }

        return subTaskList;
    }
}
