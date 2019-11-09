import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BefungeInterpreterLogicalTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
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
}