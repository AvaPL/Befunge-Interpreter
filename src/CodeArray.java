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

    public char charAt(CodePointer codePointer) {
        int column = codePointer.x;
        int row = codePointer.y;
        return array[row][column];
    }

    public int getColumnsNumber() {
        return array[0].length;
    }

    public int getRowsNumber() {
        return array.length;
    }
}
