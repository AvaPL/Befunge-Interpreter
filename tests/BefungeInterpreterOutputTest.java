import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BefungeInterpreterOutputTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
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
        assertEquals("5 ", output);
    }

    @Test
    void shouldOutput0ForEmptyStack() {
        String output = interpreter.run(".@");
        assertEquals("0 ", output);
    }

    @Test
    void shouldPopAndOutputMultipleValues() {
        String output = interpreter.run("12345.....@");
        assertEquals("5 4 3 2 1 ", output);
    }

    @Test
    void shouldOutputIntWithTwoDigits() {
        String output = interpreter.run("99+.@");
        assertEquals("18 ", output);
    }

    @Test
    void shouldOutputCharAsInt() {
        String output = interpreter.run("\"a\".@");
        assertEquals(String.valueOf((int) 'a') + ' ', output);
    }

    @Test
    void shouldOutputAsciiCharacter() {
        String output = interpreter.run("\"a\",@");
        assertEquals("a", output);
    }

    @Test
    void shouldOutputNullAsciiCharacterForEmptyStack() {
        String output = interpreter.run(",@");
        assertEquals("\0", output);
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