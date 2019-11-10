import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BefungeInterpreterProgramsTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
    }

    @Test
    void shouldRunOnceWithoutReset() {
        String output = interpreter.run("1.@");
        assertEquals("1 ", output);
        output = interpreter.run();
        assertEquals("", output);
    }

    @Test
    void shouldRunTwiceWithReset() {
        String output = interpreter.run("1.@");
        assertEquals("1 ", output);
        interpreter.reset();
        output = interpreter.run();
        assertEquals("1 ", output);
    }

    @Test
    void shouldRunTwiceWithoutReset() {
        String output = interpreter.run("1.@");
        assertEquals("1 ", output);
        output = interpreter.run("2.@");
        assertEquals("2 ", output);
    }

    @Test
    void shouldRunTwiceWithoutResetWithSetCode() {
        String output = interpreter.run("1.@");
        assertEquals("1 ", output);
        interpreter.setCode("2.@");
        output = interpreter.run();
        assertEquals("2 ", output);
    }

    @Test
    void shouldRunTwiceWithDifferentCodeWithReset() {
        String output = interpreter.run("1.@");
        assertEquals("1 ", output);
        interpreter.reset();
        output = interpreter.run("2.@");
        assertEquals("2 ", output);
    }

    @Test
    void shouldRunTwiceWithResetAndSetCode() {
        String output = interpreter.run("1.@");
        assertEquals("1 ", output);
        interpreter.reset();
        interpreter.setCode("2.@");
        output = interpreter.run();
        assertEquals("2 ", output);
    }

    @Test
    void shouldPreserveStackWithoutReset() {
        String output = interpreter.run("143.@");
        assertEquals("3 ", output);
        interpreter.setCode("+.@");
        output = interpreter.run();
        assertEquals("5 ", output);
    }

    @Test
    void shouldOutputContinuousDigits() {
        //Source: https://www.codewars.com/kata/526c7b931666d07889000a3c/train/java
        String output = interpreter.run(">987v>.v\nv456<  :\n>321 ^ _@");
        assertEquals("1 2 3 4 5 6 7 8 9 ", output);
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

    @Test
    void shouldPrintPrimeNumbers() {
        //Source: http://www.quirkster.com/iano/js/befunge.html
        String output = interpreter.run("2>:3g\" \"-!v\\  g30          <\n" +
                                        " |!`\"O\":+1_:.:03p>03g+:\"O\"`|\n" +
                                        " @               ^  p3\\\" \":<\n" +
                                        "2 234567890123456789012345678901234567890123456789012345678901234567890123456789");
        assertEquals("2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 ", output);
    }

    @Test
    void shouldCopyItselfToOutput2() {
        //Source: https://esolangs.org/wiki/Befunge
        String output = interpreter.run("01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@");
        assertEquals("01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@", output);
    }

    @Test
    void shouldCopyItselfToOutput3() {
        //Source: https://esolangs.org/wiki/Befunge
        String output = interpreter.run(":0g,:\"~\"`#@_1+0\"Quines are Fun\">_");
        assertEquals(":0g,:\"~\"`#@_1+0\"Quines are Fun\">_", output);
    }
}