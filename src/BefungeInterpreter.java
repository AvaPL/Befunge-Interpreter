import java.util.Stack;

public class BefungeInterpreter {

    private final Stack<Integer> stack = new Stack<>();

    private CodeArray codeArray;
    private CodePointer codePointer;
    private boolean isStringMode = false;
    private StringBuilder output = new StringBuilder();

    public String run(String code) {
        codeArray = CodeArray.of(code);
        codePointer = new CodePointer(codeArray.getColumnsNumber(), codeArray.getRowsNumber());
        while (interpretCharacter())
            codePointer.increment();
        return output.toString();
    }

    private boolean interpretCharacter() {
        char character = codeArray.charAt(codePointer);
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
                return true;
            case '|':
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
                return true;
            case 'p':
                return true;
            case 'g':
                return true;
            case '@':
                return false;
            default:
                pushIfDigit(character);
                return true;
        }
    }

    private void add() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(a + b);
    }

    private void subtract() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(b - a);
    }

    private void multiply() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(a * b);
    }

    private void divide() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(a == 0 ? 0 : b / a);
    }

    private void modulo() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(a == 0 ? 0 : b % a);
    }

    private void logicalNot() {
        int value = stack.pop();
        stack.push(value == 0 ? 1 : 0);
    }

    private void greaterThan() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(b > a ? 1 : 0);
    }

    private void duplicateLast() {
        if (stack.isEmpty())
            stack.push(0);
        else
            stack.push(stack.peek());
    }

    private void swapTop() {
        int a = stack.pop();
        int b = stack.isEmpty() ? 0 : stack.pop();
        stack.push(a);
        stack.push(b);
    }

    private void outputAsInt() {
        String integer = stack.pop().toString();
        output.append(integer);
    }

    private void outputAsAscii() {
        char character = (char) stack.pop().intValue();
        output.append(character);
    }

    private void setRandomDirection() {
        CodePointer.Direction direction = RandomEnum.of(CodePointer.Direction.class);
        codePointer.setDirection(direction);
    }

    private void pushIfDigit(char character) {
        if (Character.isDigit(character))
            stack.push(Character.getNumericValue(character));
    }

    public void reset() {
        stack.clear();
        codePointer.setLocation(0, 0);
        output.setLength(0);
    }

    public Stack<Integer> getStack() {
        return stack;
    }
}