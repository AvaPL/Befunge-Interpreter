import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 2)
class BefungeInterpreterTest {

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
    void shouldAddTwoDigits() {
        interpreter.run("34+@");
        assertEquals(7, interpreter.getStack().peek());
    }

    @Test
    void shouldAddFourDigits() {
        interpreter.run("1111+++@");
        assertEquals(4, interpreter.getStack().peek());
    }

    @Test
    void shouldSubtractTwoDigits() {
        interpreter.run("43-@");
        assertEquals(1, interpreter.getStack().peek());
    }

    @Test
    void shouldSubtractFourDigits() {
        interpreter.run("9321---@");
        assertEquals(7, interpreter.getStack().peek());
    }

    @Test
    void shouldMultiplyTwoDigits() {
        interpreter.run("43*@");
        assertEquals(12, interpreter.getStack().peek());
    }

    @Test
    void shouldMultiplyFourDigits() {
        interpreter.run("1234***@");
        assertEquals(24, interpreter.getStack().peek());
    }

    @Test
    void shouldDivideTwoDigits() {
        interpreter.run("63/@");
        assertEquals(2, interpreter.getStack().peek());
    }

    @Test
    void shouldDivideFourDigits() {
        interpreter.run("8631///@");
        assertEquals(4, interpreter.getStack().peek());
    }

    @Test
    void shouldDivideTwoDigitsRoundingDown() {
        interpreter.run("83/@");
        assertEquals(2, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForDivisionBy0() {
        interpreter.run("50/@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldModuloTwoDigits() {
        interpreter.run("43%@");
        assertEquals(1, interpreter.getStack().peek());
    }

    @Test
    void shouldModuloThreeDigits() {
        interpreter.run("9353%%%@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForModulo0() {
        interpreter.run("50%@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush1ForLogicalNotWith0() {
        interpreter.run("0!@");
        assertEquals(1, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForLogicalNotWithNon0() {
        interpreter.run("7!@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush1For5GreaterThan1() {
        interpreter.run("51`@");
        assertEquals(1, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0For1LessThan5() {
        interpreter.run("15`@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForEqualNumbers() {
        interpreter.run("11`@");
        assertEquals(0, interpreter.getStack().peek());
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
    void shouldPut0ForEmptyStackDuplication() {
        interpreter.run(":@");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(1, stack.size());
        assertEquals(0, stack.peek());
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
    void shouldPopAndDiscardSingleValue() {
        String output = interpreter.run("1$@");
        assertEquals(output, "");
        assertTrue(interpreter.getStack().isEmpty());
    }

    @Test
    void shouldPopAndOutputSingleValue() {
        String output = interpreter.run("5.@");
        assertEquals("5", output);
    }

    @Test
    void shouldPopAndOutputMultipleValues() {
        String output = interpreter.run("12345.....@");
        assertEquals("54321", output);
    }

    @Test
    void shouldOutputIntWithTwoDigits() {
        String output = interpreter.run("99+.@");
        assertEquals("18", output);
    }

    @Test
    void shouldOutputCharAsInt() {
        String output = interpreter.run("\"a\".@");
        assertEquals(String.valueOf((int)'a'), output);
    }

    @Test
    void shouldOutputAsciiCharacter() {
        String output = interpreter.run("\"a\",@");
        assertEquals("a", output);
    }

    @Test
    void shouldOutputMultipleAsciiCharacters() {
        String output = interpreter.run("\"12abc34\",,,,,,,@");
        assertEquals("43cba21", output);
    }

    @Test
    void shouldOutputIntAsChar() {
        String output = interpreter.run("257**,@"); //7*5*2=70 ('F')
        assertEquals("F", output);
    }
}