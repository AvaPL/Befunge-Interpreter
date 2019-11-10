import java.awt.*;

public class CodePointer extends Point {

    public enum Direction {
        RIGHT(1, 0),
        LEFT(-1, 0),
        UP(0, -1),
        DOWN(0, 1);

        int x;
        int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Direction direction = Direction.RIGHT;
    private int maxX;
    private int maxY;

    /**
     * @param maxX Maximum x coordinate, exclusive.
     * @param maxY Maximum y coordinate, exclusive.
     */
    public CodePointer(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void increment() {
        int newX = Math.floorMod(x + direction.x, maxX);
        int newY = Math.floorMod(y + direction.y, maxY);
        move(newX, newY);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
