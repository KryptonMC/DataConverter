package ca.spottedleaf.dataconverter.util;

public final class MathUtil {

    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = {
            0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
    };

    public static int roundToward(final int value, final int target) {
        return (value + target - 1) / target * target;
    }

    public static int smallestEncompassingPowerOfTwo(final int input) {
        int result = input - 1;
        result |= result >> 1;
        result |= result >> 2;
        result |= result >> 4;
        result |= result >> 8;
        result |= result >> 16;
        return result + 1;
    }

    public static boolean isPowerOfTwo(final int input) {
        return input != 0 && (input & (input - 1)) == 0;
    }

    public static int ceillog2(int input) {
        input = isPowerOfTwo(input) ? input : smallestEncompassingPowerOfTwo(input);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int) (input * 0x077CB531L >> 27) & 0x1F];
    }

    public static int clamp(final int value, final int low, final int high) {
        if (value < low) return low;
        if (value > high) return high;
        return value;
    }
}
