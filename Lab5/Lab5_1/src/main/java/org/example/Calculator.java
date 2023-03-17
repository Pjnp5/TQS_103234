package org.example;

import static java.util.Arrays.asList;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Calculator {
    private final Deque<Number> stack = new LinkedList<Number>();
    private static final List<String> operations = asList("-", "+", "*", "/");

    public void push(Object arg) {
        if (operations.contains(arg)) {
            Number y = stack.isEmpty() ? 0 : stack.removeLast();
            Number x = stack.isEmpty() ? 0 : stack.removeLast();
            Double val = null;
            switch (arg.toString()){
                case "-":
                    val = x.doubleValue() - y.doubleValue();
                    break;
                case "+":
                    val = x.doubleValue() + y.doubleValue();
                    break;
                case "*":
                    val = x.doubleValue() * y.doubleValue();
                    break;
                case "/":
                    val = x.doubleValue() / y.doubleValue();
                    break;
            }
            push(val);
        } else {
            stack.add((Number) arg);
        }
    }

    public Number value() {
        return stack.getLast();
    }
}
