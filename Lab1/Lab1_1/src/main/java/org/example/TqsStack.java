package org.example;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Hello world!
 *
 */
public class TqsStack<T>
{
    private final LinkedList<T> stack;
    private int bound = -1;

    public TqsStack() {
        this.stack = new LinkedList<>();
    }
    public TqsStack(int bound) {
        this.stack = new LinkedList<>();
        this.bound = bound;
    }

    public T pop(){
        if (this.stack.isEmpty())throw  new NoSuchElementException();
        return this.stack.pop();
    }
    public void push(T x){
        if (bound > 0 && bound == this.stack.size()) throw new IllegalStateException();
        this.stack.push(x);
    }
    public T peek(){
        if (this.stack.isEmpty()) throw new NoSuchElementException();
        return this.stack.peek();
    }

    public int size(){
        return this.stack.size();
    }

    public Boolean isEmpty(){
        return this.stack.isEmpty();
    }


}
