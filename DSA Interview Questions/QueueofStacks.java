/* Implement a queue with two stacks so that each queue operations
takes a constant amortized number of stack operations.*/

import edu.princeton.cs.algs4.Stack;

public class QueueofStacks {

    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public QueueofStacks() {
        this.stack1 = new Stack<>();
        this.stack2 = new Stack<>();
    }

    // to enqueue, just push items to stack 1
    public void enqueue(int x) {
        stack1.push(x);
    }

    // to dequeue, check if stack 2 is empty, then populate stack 2 using stack1.pop(). This will reverse the order of items.
    // then pop stack 2 to return a FIFO item.
    public int dequeue() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    // check if both stacks are empty, then the queue will be empty.
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public static void main(String[] args) {

    }
}
