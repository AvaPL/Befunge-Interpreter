import java.util.Arrays;

public class CodeArray {

    private char[][] array;

    private CodeArray(char[][] array) {
        this.array = array;
    }

    public static CodeArray of(String code) {
        String[] lines = code.split("\n");
        char[][] array = getArrayFromLines(lines);
        return new CodeArray(array);
    }

    private static char[][] getArrayFromLines(String[] codeLines) {
        int maxWidth = Arrays.stream(codeLines).mapToInt(String::length).max().getAsInt();
        char[][] codeArray = new char[codeLines.length][];
        for (int i = 0; i < codeArray.length; ++i)
            codeArray[i] = Arrays.copyOf(codeLines[i].toCharArray(), maxWidth);
        return codeArray;
    }

    public char getCharAt(CodePointer codePointer) {
        int column = codePointer.x;
        int row = codePointer.y;
        return getCharAt(row, column);
    }

    public char getCharAt(int row, int column) {
        if (row >= getRowsNumber() || column >= getColumnsNumber()) return '\0';
        return array[row][column];
    }

    public void setCharAt(CodePointer codePointer, char character) {
        int column = codePointer.x;
        int row = codePointer.y;
        setCharAt(row, column, character);
    }

    public void setCharAt(int row, int column, char character) {
        array[row][column] = character;
    }

    public int getColumnsNumber() {
        return array[0].length;
    }

    public int getRowsNumber() {
        return array.length;
    }
}
