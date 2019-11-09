import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.Duration;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class BefungeInterpreterDirectionsTest {

    private BefungeInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new BefungeInterpreter();
    }

    @Test
    void shouldRespectArrows() {
        /* Will end only by choosing right directions.
        > v
        @5<
        */
        runWithTimeout("> v\n@5<");
        Stack<Integer> stack = interpreter.getStack();
        assertEquals(1, stack.size());
        assertEquals(5, stack.pop());
    }

    private String runWithTimeout(String s) {
        ThrowingSupplier<String> command = () -> interpreter.run(s);
        return assertTimeoutPreemptively(Duration.ofSeconds(1), command);
    }

    @Test
    void shouldCompleteWithEmptyRow() {
        /* Will end only by choosing right directions.
        > v

        @ <
        */
        runWithTimeout("> v\n\n@ <");
    }

    @Test
    void shouldWrapAroundRow() {
        // Will end only by wrapping around row.
        runWithTimeout("<@");
    }

    @Test
    void shouldWrapAroundMultipleRows() {
        /* Will end only by wrapping around rows.
           <v
        @ >
          ^ <
        */
        runWithTimeout("   <v\n@ >  \n  ^ <");
    }

    @Test
    void shouldWrapAroundColumns() {
        /* Will end only by wrapping around column.
        ^
         @
        >^
        */
        runWithTimeout("^\n @\n>^");
    }

    @Test
    void shouldOutputIntForOperationsWithWrapping() {
        /* (3+2)*5
        v>5*v
         ^>+
        3   .
        >2^ @
        */
        String output = runWithTimeout("v>5*v\n ^>+\n3   .\n>2^ @");
        assertEquals("25", output);
    }

    @Test
    void shouldPickRandomDirection() {
        /* Will end only by randomly picking direction.
         v
        @?@
         ^
        */
        runWithTimeout(" v\n@?@\n ^");
    }

    @Test
    void shouldEndForHorizontalDirectionChoosing() {
        /* Will end only by choosing right directions.
          v
          0
         ^_v
           1
        @  _^
        */
        runWithTimeout("  v\n  0\n ^_v\n   1\n@  _^");
    }

    @Test
    void shouldEndForVerticalDirectionChoosing() {
        /* Will end only by choosing right directions.
        v   @
          <
        >0|
          >1|
            <
        */
        runWithTimeout("v   @\n  <\n>0|\n  >1|\n    <");
    }
}