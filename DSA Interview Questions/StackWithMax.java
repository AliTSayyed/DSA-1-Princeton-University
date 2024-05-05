/* Stack with max. Create a data structure that efficiently supports the stack operations (push and pop) and
also a return-the-maximum operation. Assume the elements are real numbers so that you can compare them.
*/
import edu.princeton.cs.algs4.Stack;

public class StackWithMax {
    private Stack<Integer> stack;
    private Stack<Integer> maxStack;

    public StackWithMax(){
        stack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(int x){
        stack.push(x);
        if(maxStack.isEmpty() || x > maxStack.peek()) maxStack.push(x);
    }

    public int pop(){
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        if (stack.pop().equals(maxStack.peek())) maxStack.pop();
        return stack.pop();
    }

    public int getMax(){
        if (maxStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return maxStack.peek();
    }

    public static void main(String[] args) {

    }
}
