import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeArrayTest {

    @Test
    void shouldCreateSingleRowArray() {
        String code = "12345@";
        CodeArray codeArray = CodeArray.of(code);
        assertEquals(1, codeArray.getRowsNumber());
        assertEquals(6, codeArray.getColumnsNumber());
        assertCodeArrayEqualsCode(codeArray, code);
    }

    void assertCodeArrayEqualsCode(CodeArray codeArray, String code) {
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++)
            for (int j = 0; j < lines[i].length(); j++)
                assertEquals(lines[i].charAt(j), codeArray.getCharAt(i, j));
    }

    @Test
    void shouldCreateTwoRowArray() {
        String code = "12345v\n@9876<";
        CodeArray codeArray = CodeArray.of(code);
        assertEquals(2, codeArray.getRowsNumber());
        assertEquals(6, codeArray.getColumnsNumber());
        assertCodeArrayEqualsCode(codeArray, code);
    }

    @Test
    void shouldCreate5By2ArrayForOneShorterRow() {
        String code = "v\n>   @";
        CodeArray codeArray = CodeArray.of(code);
        assertEquals(2, codeArray.getRowsNumber());
        assertEquals(5, codeArray.getColumnsNumber());
        assertCodeArrayEqualsCode(codeArray, code);
    }

    @Test
    void shouldCreate0By1ArrayForEmptyCode() {
        String code = ""; //1 row, 0 columns
        CodeArray codeArray = CodeArray.of(code);
        assertEquals(1, codeArray.getRowsNumber());
        assertEquals(0, codeArray.getColumnsNumber());
        assertCodeArrayEqualsCode(codeArray, code);
    }

    @Test
    void shouldGetCharByCoordinates() {
        String code = "v\n>   @";
        CodeArray codeArray = CodeArray.of(code);
        assertEquals('v', codeArray.getCharAt(0, 0));
        assertEquals('@', codeArray.getCharAt(1, 4));
    }

    @Test
    void shouldGetCharByCodePointer() {
        String code = "v\n>   @";
        CodeArray codeArray = CodeArray.of(code);
        CodePointer codePointer = new CodePointer(codeArray.getColumnsNumber(), codeArray.getRowsNumber());
        assertEquals('v', codeArray.getCharAt(codePointer));
        codePointer.setDirection(CodePointer.Direction.DOWN);
        codePointer.increment();
        assertEquals('>', codeArray.getCharAt(codePointer));
    }

    @Test
    void shouldSetCharByCoordinates() {
        String code = "v\n>   @";
        CodeArray codeArray = CodeArray.of(code);
        codeArray.setCharAt(1, 1, '#');
        assertEquals('#', codeArray.getCharAt(1, 1));
    }

    @Test
    void shouldSetCharByCodePointer() {
        String code = "v\n>   @";
        CodeArray codeArray = CodeArray.of(code);CodePointer codePointer = new CodePointer(codeArray.getColumnsNumber(), codeArray.getRowsNumber());
        codePointer.setDirection(CodePointer.Direction.DOWN);
        codePointer.increment();
        codeArray.setCharAt(codePointer, '#');
        assertEquals('#', codeArray.getCharAt(codePointer));
    }
}