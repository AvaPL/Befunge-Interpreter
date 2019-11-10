import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BefungeInterpreterArithmeticTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
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
    void shouldPush0ForAddOnEmptyStack() {
        interpreter.run("+@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPushElementForAddOnSingleElement() {
        interpreter.run("5+@");
        assertEquals(5, interpreter.getStack().peek());
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
    void shouldPush0ForSubtractOnEmptyStack() {
        interpreter.run("-@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPushMinusElementForSubtractOnSingleElement() {
        interpreter.run("5-@");
        assertEquals(-5, interpreter.getStack().peek());
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
    void shouldPush0ForMultiplyOnEmptyStack() {
        interpreter.run("*@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForMultiplyOnSingleElement() {
        interpreter.run("5*@");
        assertEquals(0, interpreter.getStack().peek());
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
    void shouldPush0ForDivisionOnEmptyStack() {
        interpreter.run("/@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForDivisionOnSingleElement() {
        interpreter.run("5/@");
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
    void shouldPush0ForModuloOnEmptyStack() {
        interpreter.run("%@");
        assertEquals(0, interpreter.getStack().peek());
    }

    @Test
    void shouldPush0ForModuloOnSingleElement() {
        interpreter.run("5%@");
        assertEquals(0, interpreter.getStack().peek());
    }
}