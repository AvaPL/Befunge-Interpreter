import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldPush5OnStack() {
        interpreter.run("5@");
        assertEquals(5, interpreter.getStack().peek());
    }
}