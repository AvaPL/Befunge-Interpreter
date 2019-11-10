import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodePointerTest {

    @Test
    void shouldStartAtX0Y0() {
        CodePointer codePointer = new CodePointer(1,1);
        assertCodePointerLocationEquals(codePointer, 0, 0);
    }

    void assertCodePointerLocationEquals(CodePointer codePointer, int x, int y){
        assertEquals(codePointer.x, x);
        assertEquals(codePointer.y, y);
    }

    @Test
    void shouldStartWithDirectionRight() {
        CodePointer codePointer = new CodePointer(1,1);
        assertEquals(codePointer.getDirection(), CodePointer.Direction.RIGHT);
    }

    @Test
    void shouldIncrementRight() {
        CodePointer codePointer = new CodePointer(2,2);
        assertEquals(codePointer.getDirection(), CodePointer.Direction.RIGHT);
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 1, 0);
    }

    @Test
    void shouldIncrementLeft() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.increment();                              //Setup
        codePointer.setDirection(CodePointer.Direction.LEFT); //
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 0, 0);
    }

    @Test
    void shouldIncrementDown() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.setDirection(CodePointer.Direction.DOWN);
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 0, 1);
    }

    @Test
    void shouldIncrementUp() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.setDirection(CodePointer.Direction.DOWN); //Setup
        codePointer.increment();                              //
        codePointer.setDirection(CodePointer.Direction.UP);
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 0, 0);
    }

    @Test
    void shouldWrapRight() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.increment();
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 0, 0);
    }

    @Test
    void shouldWrapLeft() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.setDirection(CodePointer.Direction.LEFT);
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 1, 0);
    }

    @Test
    void shouldWrapDown() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.setDirection(CodePointer.Direction.DOWN);
        codePointer.increment();
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 0, 0);
    }

    @Test
    void shouldWrapUp() {
        CodePointer codePointer = new CodePointer(2,2);
        codePointer.setDirection(CodePointer.Direction.UP);
        codePointer.increment();
        assertCodePointerLocationEquals(codePointer, 0, 1);
    }
}