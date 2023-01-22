/**
 * Linked Queue class to be used for Lab#5
 * Used code from COMP-10152 - Data Structures and Algorithms (pg.417-419)
 * Modified by Mark Yendt to be Generic
 */
public class LinkedQueue<E> {

    /**
     * Node class to be used by the LinkedQueue class
     *
     * @param <E>
     */
    private class Node<E> {

        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
    private int count;
    private Node<E> front;
    private Node<E> rear;

    /**
     * Constructor for a LinkedQueue
     */
    public LinkedQueue() {
        front = rear = null;
        count = 0;
    }

    /**
     * Add an item to the Queue
     *
     * @param value item to be added to the Queue
     */
    public void enqueue(E value) {
        if (rear != null) {
            rear.next = new Node(value, null);
            rear = rear.next;
        } else {
            rear = new Node(value, null);
            front = rear;
        }
        count++;
    }

    /**
     * Remove an item from the Queue - throws exception if queue is empty
     *
     * @return the item at the from of the Queue
     */
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue - Queue is empty");
        }

        E value = front.value;
        front = front.next;
        count--;

        if (front == null) {
            rear = null;
        }

        return value;
    }

    /**
     * Check is queue is empty
     *
     * @return true if empty, false if not
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Shows front of Queue
     * @return the value of the first item in the Queue
     */
    public E peek() {
        return front.value;
    }
    
    /**
     * Obtain the number of elements in the Queue
     * @return 
     */
    
    public int size() {
        return count;
    }
    
    /**
     * ToString method used to print each of the strings of the objects
     * @return string representing the class 
     */
    
    @Override
    public String toString() {
        String result = "[";
        Node<E> current = front;
        while (current != null) {
            result += current.value;
            if (current.next != null) {
                result += ",";
            }
            current = current.next;
        }
        result += "]";
        return result;
    }
}
