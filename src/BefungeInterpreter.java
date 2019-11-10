import java.util.Stack;

public class BefungeInterpreter {

    private final Stack<Integer> stack = new Stack<>();

    private CodeArray codeArray;
    private CodePointer codePointer;
    private boolean isStringMode = false;
    private StringBuilder output = new StringBuilder();

    public String run(String code) {
        setCode(code);
        return run();
    }

    public void setCode(String code) {
        codeArray = CodeArray.of(code);
        codePointer = new CodePointer(codeArray.getColumnsNumber(), codeArray.getRowsNumber());
    }

    public String run() {
        while (interpretCharacter())
            codePointer.increment();
        return output();
    }

    private boolean interpretCharacter() {
        char character = codeArray.getCharAt(codePointer);
        return isStringMode ? interpretAsAscii(character) : interpretCommand(character);
    }

    private boolean interpretAsAscii(char character) {
        if (character == '"')
            isStringMode = !isStringMode;
        else
            stack.push((int) character);
        return true;
    }

    private boolean interpretCommand(char character) {
        switch (character) {
            case '+':
                add();
                return true;
            case '-':
                subtract();
                return true;
            case '*':
                multiply();
                return true;
            case '/':
                divide();
                return true;
            case '%':
                modulo();
                return true;
            case '!':
                logicalNot();
                return true;
            case '`':
                greaterThan();
                return true;
            case '>':
                codePointer.setDirection(CodePointer.Direction.RIGHT);
                return true;
            case '<':
                codePointer.setDirection(CodePointer.Direction.LEFT);
                return true;
            case '^':
                codePointer.setDirection(CodePointer.Direction.UP);
                return true;
            case 'v':
                codePointer.setDirection(CodePointer.Direction.DOWN);
                return true;
            case '?':
                setRandomDirection();
                return true;
            case '_':
                popAndMoveHorizontally();
                return true;
            case '|':
                popAndMoveVertically();
                return true;
            case '"':
                isStringMode = !isStringMode;
                return true;
            case ':':
                duplicateLast();
                return true;
            case '\\':
                swapTop();
                return true;
            case '$':
                stack.pop();
                return true;
            case '.':
                outputAsInt();
                return true;
            case ',':
                outputAsAscii();
                return true;
            case '#':
                codePointer.increment();
                return true;
            case 'p':
                putCommand();
                return true;
            case 'g':
                getCommand();
                return true;
            case '@':
                return false;
            default:
                pushIfDigit(character);
                return true;
        }
    }

    private void add() {
        int a = pop();
        int b = pop();
        stack.push(a + b);
    }

    private Integer pop() {
        return stack.isEmpty() ? 0 : stack.pop();
    }

    private void subtract() {
        int a = pop();
        int b = pop();
        stack.push(b - a);
    }

    private void multiply() {
        int a = pop();
        int b = pop();
        stack.push(a * b);
    }

    private void divide() {
        int a = pop();
        int b = pop();
        stack.push(a == 0 ? 0 : b / a);
    }

    private void modulo() {
        int a = pop();
        int b = pop();
        stack.push(a == 0 ? 0 : b % a);
    }

    private void logicalNot() {
        int value = pop();
        stack.push(value == 0 ? 1 : 0);
    }

    private void greaterThan() {
        int a = pop();
        int b = pop();
        stack.push(b > a ? 1 : 0);
    }

    private void duplicateLast() {
        if (stack.isEmpty())
            stack.push(0);
        stack.push(stack.peek());
    }

    private void swapTop() {
        int a = pop();
        int b = pop();
        stack.push(a);
        stack.push(b);
    }

    private void outputAsInt() {
        /*TODO: According to language specification int should be followed by space.
        *       It isn't respected in Codewars kata, will be fixed later.*/
        String integer = pop().toString();
        output.append(integer);
    }

    private void outputAsAscii() {
        char character = (char) pop().intValue();
        output.append(character);
    }

    private void setRandomDirection() {
        CodePointer.Direction direction = RandomEnum.of(CodePointer.Direction.class);
        codePointer.setDirection(direction);
    }

    private void popAndMoveHorizontally() {
        int value = pop();
        codePointer.setDirection(value == 0 ? CodePointer.Direction.RIGHT : CodePointer.Direction.LEFT);
    }

    private void popAndMoveVertically() {
        int value = pop();
        codePointer.setDirection(value == 0 ? CodePointer.Direction.DOWN : CodePointer.Direction.UP);
    }

    private void putCommand() {
        int row = pop();
        int column = pop();
        char command = (char) pop().intValue();
        codeArray.setCharAt(row, column, command);
    }

    private void getCommand() {
        int row = pop();
        int column = pop();
        char command = codeArray.getCharAt(row, column);
        stack.push((int) command);
    }

    private void pushIfDigit(char character) {
        if (Character.isDigit(character))
            stack.push(Character.getNumericValue(character));
    }

    private String output() {
        String outputString = output.toString();
        output.setLength(0);
        return outputString;
    }

    public void reset() {
        stack.clear();
        codePointer.setLocation(0, 0);
        isStringMode = false;
        output.setLength(0);
    }

    public Stack<Integer> getStack() {
        return stack;
    }
}