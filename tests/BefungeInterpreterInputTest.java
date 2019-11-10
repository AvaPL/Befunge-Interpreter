import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BefungeInterpreterInputTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
    }

    @Test
    void shouldReadSingleDigit() {
        interpreter.run("&@", "5");
        assertEquals(5, interpreter.getStack().peek());
    }

    @Test
    void shouldReadNumberWithMultipleDigits() {
        interpreter.run("&@", "123");
        assertEquals(123, interpreter.getStack().peek());
    }

    @Test
    void shouldIgnoreStringsWhenReadingInt() {
        interpreter.run("&@", "abc $ 123");
        assertEquals(123, interpreter.getStack().peek());
    }

    @Test
    void shouldReadMultipleInts() {
        interpreter.run("&&@", "5 123");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals(123, stack.pop());
        assertEquals(5, stack.pop());
    }

    @Test
    void shouldReadMultipleIntsSeparatedByStrings() {
        interpreter.run("&&@", "abc 5 $ 123");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals(123, stack.pop());
        assertEquals(5, stack.pop());
    }

    @Test
    void shouldReadAsciiCharacter() {
        interpreter.run("~@", "a");
        assertEquals('a', interpreter.getStack().peek());
    }

    @Test
    void shouldReadOneAsciiCharacterFromString() {
        interpreter.run("~@", "abc");
        assertEquals('a', interpreter.getStack().peek());
    }

    @Test
    void shouldReadMultipleAsciiCharacters() {
        interpreter.run("~~@", "abc cba");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals('c', stack.pop());
        assertEquals('a', stack.pop());
    }

    @Test
    void shouldReadIntsAsAsciiCharacter() {
        interpreter.run("~@", "1");
        assertEquals('1', interpreter.getStack().peek());
    }

    @Test
    void shouldReadMultipleIntsAsAsciiCharacters() {
        interpreter.run("~~@", "1 25");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals('2', stack.pop());
        assertEquals('1', stack.pop());
    }

    @Test
    void shouldReadMixedIntsAndAsciiCharacters() {
        interpreter.run("~&&~~@", "a 5 abc 123 cba 321");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(5, stack.size());
        assertEquals('3', stack.pop());
        assertEquals('c', stack.pop());
        assertEquals(123, stack.pop());
        assertEquals(5, stack.pop());
        assertEquals('a', stack.pop());
    }

    @Test
    void shouldReadMinus1ForEmptyAsciiInput() {
        interpreter.run("~@");
        assertEquals(-1, interpreter.getStack().peek());
    }

    @Test
    void shouldRead0ForEmptyIntInput() {
        interpreter.run("&@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldCatOutput() {
        String output = interpreter.run("~:1+!#@_,", "a b c d e");
        assertEquals("abcde", output);
    }
}