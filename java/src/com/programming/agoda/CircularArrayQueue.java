package com.programming.agoda;
public class CircularArrayQueue {
    private int[] queueArray;
    private int front, rear, size;

    // Constructor to initialize the queue
    public CircularArrayQueue(int capacity) {
        queueArray = new int[capacity];
        front = -1;
        rear = -1;
        size = 0;
    }

    // Method to check if the queue is full
    public boolean isFull() {
        return size == queueArray.length;
    }

    // Method to check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Method to add an element to the queue
    public void enqueue(int element) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue " + element);
        } else {
            if (front == -1) {
                front = 0;  // First element in the queue
            }
            rear = (rear + 1) % queueArray.length;
            queueArray[rear] = element;
            size++;
        }
    }

    // Method to remove and return an element from the queue
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return -1;
        } else {
            int dequeuedElement = queueArray[front];
            front = (front + 1) % queueArray.length;
            size--;
            return dequeuedElement;
        }
    }

    // Method to display the elements of the queue
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            System.out.print("Queue elements: ");
            for (int i = 0; i < size; i++) {
                System.out.print(queueArray[(front + i) % queueArray.length] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CircularArrayQueue queue = new CircularArrayQueue(5);  // Create a queue with capacity 5

        // Enqueue elements
        queue.enqueue(14);
        queue.enqueue(22);
        queue.enqueue(13);
        queue.enqueue(-6);

        // Display queue
        queue.displayQueue();
    }
}
