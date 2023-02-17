package org.example;

import java.util.LinkedList;
/**
 * Hello world!
 *
 */
public class TqsStack<T>
{
    private LinkedList<T> stack;

    public TqsStack() {
        this.stack = new LinkedList<>();;
    }

    public T pop(){
        System.out.println("Poping");
        return null;
    }
    public void push(T x){
        System.out.println("Pushing " + x);
    }
    public T peek(){
        System.out.println("Peeking First");
        return null;
    }

    public int size(){
        System.out.println("Sending Size");
        return 50;
    }

    public Boolean isEmpty(){
        return false;
    }


}
