import java.util.Random;

public class RandomEnum {

    private static final Random random = new Random();

    public static <E extends Enum<E>> E of(Class<E> c) {
        E[] enums = c.getEnumConstants();
        int index = random.nextInt(enums.length);
        return enums[index];
    }
}
