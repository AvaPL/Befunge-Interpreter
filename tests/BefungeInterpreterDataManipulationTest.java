import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.Duration;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class BefungeInterpreterDataManipulationTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
    }

    @Test
    void shouldEndProgram() {
        assertEquals("", interpreter.run("@"));
    }

    @Test
    void shouldPushDigitOnStack() {
        interpreter.run("0@");
        assertEquals(0, interpreter.getStack().peek());
        interpreter.reset();
        interpreter.run("9@");
        assertEquals(9, interpreter.getStack().peek());
    }

    @Test
    void shouldPushAsciiCharacter() {
        interpreter.run("\"a\"@");
        assertEquals('a', interpreter.getStack().peek());
    }

    @Test
    void shouldPushMultipleAsciiCharacters() {
        interpreter.run("\"tset\"@");
        Stack<Integer> stack = interpreter.getStack();
        StringBuilder actual = new StringBuilder();
        while (!stack.isEmpty())
            actual.append((char) stack.pop().intValue());
        assertEquals("test", actual.toString());
    }

    @Test
    void shouldPushCommandAsAsciiCharacter() {
        interpreter.run("\"@\"@");
        assertEquals('@', interpreter.getStack().peek());
    }

    @Test
    void shouldPutNumberAsAsciiCharacter() {
        interpreter.run("\"2\"@");
        assertEquals('2', interpreter.getStack().peek());
    }

    @Test
    void shouldDuplicateLastValue() {
        interpreter.run("5:@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals(5, stack.pop());
        assertEquals(5, stack.pop());
    }

    @Test
    void shouldPutTwo0ForEmptyStackDuplication() {
        interpreter.run(":@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals(0, stack.pop());
        assertEquals(0, stack.pop());
    }

    @Test
    void shouldSwapTwoTopValues() {
        interpreter.run("123\\@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(3, stack.size());
        assertEquals(2, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void shouldSwapSingleElementWithExtra0() {
        interpreter.run("1\\@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals(0, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void shouldPushTwo0ForSwapOnEmptyStack() {
        interpreter.run("\\@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(2, stack.size());
        assertEquals(0, stack.pop());
        assertEquals(0, stack.pop());
    }

    @Test
    void shouldChangeCommandInCode() {
        /* Will end only by changing > at (0, 8) to v.
        835**2-11pv
        ><        <
         @
        */
        runWithTimeout("835**2-11pv\n><        <\n @");
    }

    private String runWithTimeout(String s) {
        ThrowingSupplier<String> command = () -> interpreter.run(s);
        return assertTimeoutPreemptively(Duration.ofSeconds(1), command);
    }

    @Test
    void shouldChangeCommandToNullCharacterForEmptyStack() {
        /* Will end only by changing v at (0, 3) to null character (comment).
        30pv@
           ^
        */
        runWithTimeout("30pv@\n   ^");
    }

    @Test
    void shouldGetAsciiValueOfCommandInCode() {
        interpreter.run(">00g@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(1, stack.size());
        assertEquals('>', stack.peek());
    }

    @Test
    void shouldPush0ForCommandInNonexistentColumn() {
        interpreter.run("90g@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(1, stack.size());
        assertEquals('\0', stack.peek());
    }

    @Test
    void shouldPush0ForCommandInNonexistentRow() {
        interpreter.run("09g@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(1, stack.size());
        assertEquals('\0', stack.peek());
    }

    @Test
    void shouldGetGetCommandForEmptyStack() {
        interpreter.run("g@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(1, stack.size());
        assertEquals('g', stack.peek());
    }
}