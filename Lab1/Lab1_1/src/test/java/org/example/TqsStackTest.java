package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class TqsStackTest {
    private TqsStack<Integer> stack;
    private TqsStack<Integer> bstack;

    @BeforeEach
    void setup(){
        this.stack = new TqsStack<>();
        this.bstack = new TqsStack<>(1);
    }

    @AfterEach
    void tearDown(){

    }


    // a)
    @Test
    void isEmpty(){
        assertTrue(stack.isEmpty(), "Stack must be empty when created.");
    }

    // b)
    @Test
    void size_zero(){
        assertEquals(0, stack.size(), "Stack size must be 0 when created.");
    }

    // c)
    @Test
    void size(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size(), () -> "The stack size should be 3 but is " + stack.size());
    }

    // d)
    @Test
    void pop_first(){
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop(), "The value 2 should have been poped, but a diferent value was returned.");
    }

    // e)
    @Test
    void peek_first(){
        stack.push(1);
        stack.push(2);
        int size = stack.size();
        assertEquals(2, stack.peek(), "The value 2 should have been peeked, but a diferent value was returned.");
        assertEquals(size, stack.size(), "After using peek() the stack size should stay the same.");
    }

    // f)
    @Test
    void push_pop_size(){
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();
        assertEquals(0, stack.size(), "After 2 push and 2 pop the stack size should be 0.");
    }

    // g)
    @Test
    void PopNosuchElem(){
        Assertions.assertThrows(NoSuchElementException.class, () -> stack.pop(), "A 'NoSuchElementException' should appear when pop is used on an empty stack ");
    }

    // h)
    @Test
    void PeekNoSuchElem(){
        Assertions.assertThrows(NoSuchElementException.class, () -> stack.peek(), "A 'NoSuchElementException' should appear when peek is used on an empty stack ");
    }

    // i)
    @Test
    void BoundStack(){
        bstack.push(1);
        Assertions.assertThrows(IllegalStateException.class, () -> bstack.push(2), "A 'IllegalStateException' should appear when you push an element to a full stack");
    }

}
