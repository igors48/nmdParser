package util; /*
 /*
 * Classname: util.Assert
 *
 * Version: 1.0
 *
 * Author: Alex Usun
 */

/**
 * The utility class for various run-time verifications.
 * It is intended to use as standard "assert" keyword
 * for method parameter verification.
 * <p/>
 * DO NOT USE IT FOR OTHER PURPOSES!!!
 *
 * @author Alex Usun
 * @version 1.0
 */
public final class Assert {

    /**
     * Asserts that the specified reference is not <code>null</code>.
     *
     * @param obj the reference to check
     * @param msg the error message
     * @throws NullPointerException if the specified reference is <code>null</code>
     */
    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }

    /**
     * Asserts that the specified reference is not <code>null</code>.
     *
     * @param obj the reference to check
     * @throws NullPointerException if the specified reference is <code>null</code>
     */
    public static void notNull(Object obj) {
        notNull(obj, "null reference is not allowed");
    }

    /**
     * Asserts that the specified condition is truth.
     *
     * @param condition the condition to check
     * @param msg       the error message
     * @throws IllegalArgumentException if the specified condition is false
     */
    public static void isTrue(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Asserts that the specified condition is false.
     *
     * @param condition the condition to check
     * @param msg       the error message
     * @throws IllegalArgumentException if the specified condition is truth
     */
    public static void isFalse(boolean condition, String msg) {
        if (condition) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Asserts that the specified value is greater than the lower bound.
     *
     * @param val   the value to check
     * @param bound the value lower bound
     * @param msg   the error message
     * @throws IllegalArgumentException if the specified value is not greater
     *                                  than the lower bound
     */
    public static void greater(long val, long bound, String msg) {
        isTrue(val > bound, msg);
    }

    /**
     * Asserts that the specified value is greater or equal than the lower bound.
     *
     * @param val   the value to check
     * @param bound the value lower bound
     * @param msg   the error message
     * @throws IllegalArgumentException if the specified value is less
     *                                  than the lower bound
     */
    public static void greaterOrEqual(long val, long bound, String msg) {
        isTrue(val >= bound, msg);
    }

    /**
     * Asserts that the specified value is less that the upper bound.
     *
     * @param val   the value to check
     * @param bound the value upper bound
     * @param msg   the error message
     * @throws IllegalArgumentException if the specified value is not less
     *                                  than the upper bound
     */
    public static void less(long val, long bound, String msg) {
        isTrue(val < bound, msg);
    }

    /**
     * Asserts that the specified value is less or equal that the upper bound.
     *
     * @param val   the value to check
     * @param bound the value upper bound
     * @param msg   the error message
     * @throws IllegalArgumentException if the specified value is not greater
     *                                  than the upper bound
     */
    public static void lessOrEqual(long val, long bound, String msg) {
        isTrue(val <= bound, msg);
    }

    /**
     * Asserts that the specified value is in range inclusive.
     *
     * @param val        the value to check
     * @param lowerBound the value lower bound
     * @param upperBound the value upper bound
     * @param msg        the error message
     * @throws IllegalArgumentException if the specified value is not in
     *                                  range inclusive
     */
    public static void inRangeInclusive(long val,
                                        long lowerBound,
                                        long upperBound,
                                        String msg) {
        greaterOrEqual(val, lowerBound, msg);
        lessOrEqual(val, upperBound, msg);
    }

    /**
     * Asserts that the specified value is in range exclusive.
     *
     * @param val        the value to check
     * @param lowerBound the value lower bound
     * @param upperBound the value upper bound
     * @param msg        the error message
     * @throws IllegalArgumentException if the specified value is not in
     *                                  range exclusive
     */
    public static void inRangeExclusive(long val,
                                        long lowerBound,
                                        long upperBound,
                                        String msg) {
        greater(val, lowerBound, msg);
        less(val, upperBound, msg);
    }

    /**
     * Asserts that two specified values are equal.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @param msg  the error message
     * @throws IllegalArgumentException if the specified values are not equal
     */
    public static void areEqual(long val1, long val2, String msg) {
        isTrue(val1 == val2, msg);
    }

    /**
     * Asserts that two specified objects are equal.
     *
     * @param obj1 the first object
     * @param obj2 the second object
     * @param msg  the error message
     * @throws IllegalArgumentException if the specified objects are not equal
     * @throws NullPointerException     if some of the objects are <code>null</code>
     */
    public static void areEqual(Object obj1, Object obj2, String msg) {
        notNull(obj1);
        notNull(obj2);
        isTrue(obj1.equals(obj2), msg);
    }

    /**
     * Asserts that the specified array parameters are valid.
     *
     * @param array  the array to check
     * @param offset the array offset
     * @param len    the data length
     * @throws IllegalArgumentException if the offset or length parameters are invalid
     * @throws NullPointerException     if the array reference is <code>null</code>
     */
    public static void isValidArray(byte[] array, int offset, int len) {
        inRangeInclusive(offset, 0, array.length, "bad offset");
        greaterOrEqual(len, 0, "bad length");
        lessOrEqual(offset + len, array.length, "bad offset or length");
    }

    /**
     * Asserts that the specified string are valid.
     *
     * @param str string to check
     * @throws IllegalArgumentException if the string is empty
     * @throws NullPointerException     if the string reference is <code>null</code>
     */
    public static void isValidString(String str) {
        notNull(str);
        greater(str.length(), 0, "bad string");
    }

    /**
     * Asserts that the specified string are valid.
     *
     * @param str string to check
     * @param msg the error message
     * @throws IllegalArgumentException if the string is empty
     * @throws NullPointerException     if the string reference is <code>null</code>
     */
    public static void isValidString(String str, String msg) {
        notNull(str, msg);
        greater(str.length(), 0, msg);
    }

    /**
     * Checks if state condition is true.
     *
     * @param condition state condition to check
     * @param message   error message
     * @throws IllegalStateException if state condition is false
     */
    public static void isValidState(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    public static void isContained(Object value, Object[] array, String message) {
        for (int i = 0; i < array.length; ++i) {
            Object o = array[i];
            if (o == null && value == null) {
                return;
            }
            if (value != null && value.equals(o)) {
                return;
            }
        }

        throw new IllegalArgumentException(message);
    }

    /**
     * Private constructor to avoid unneccessary instantiation.
     */
    private Assert() {
        // empty
    }

}
