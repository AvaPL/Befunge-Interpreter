import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BefungeInterpreterProgramsTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
    }

    @Test
    void shouldOutputContinuousDigits() {
        //Source: https://www.codewars.com/kata/526c7b931666d07889000a3c/train/java
        String output = interpreter.run(">987v>.v\nv456<  :\n>321 ^ _@");
        assertEquals("123456789", output);
    }

    @Test
    void shouldOutputHelloWorld() {
        //Source: https://en.wikipedia.org/wiki/Befunge
        String output = interpreter.run(">              v\n" +
                                        "v  ,,,,,\"Hello\"<\n" +
                                        ">48*,          v\n" +
                                        "v,,,,,,\"World!\"<\n" +
                                        ">25*,@");
        assertEquals("Hello World!\n", output);
    }

    @Test
    void shouldOutputHelloWorld2() {
        //Source: https://en.wikipedia.org/wiki/Befunge
        String output = interpreter.run(" >25*\"!dlrow ,olleH\":v\n" +
                                        "                  v:,_@\n" +
                                        "                  >  ^");
        assertEquals("Hello, world!\n", output);
    }

    @Test
    void shouldCopyItselfToOutput() {
        //Source: http://www.quirkster.com/iano/js/befunge.html
        String output = interpreter.run(":0g,:93+`#@_1+");
        assertEquals(":0g,:93+`#@_1+", output);
    }
}