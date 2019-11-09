import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

public class BefungeInterpreter {

    private final Stack<Integer> stack = new Stack<>();

    public String run(String code) {
        char[] codeArray = code.toCharArray();
        for (char character : codeArray)
            interpretCharacter(character);
        return "";
    }

    private void interpretCharacter(char character) {
        if (Character.isDigit(character))
            stack.push(Character.getNumericValue(character));
        switch (character) {
            case '+':
                add();
                break;
            case '-':
                subtract();
                break;
            case '*':
                multiply();
                break;
            case '/':
                divide();
                break;
            case '%':
                modulo();
                break;
            case '@':
            default:
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

    public void clearStack() {
        stack.clear();
    }

    public Stack<Integer> getStack() {
        return stack;
    }
}