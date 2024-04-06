package ru.npo6ka.sleepingbag;

import java.util.Random;

public final class Utils {

    public static final Random RAND;

    private Utils() {}

    public static String toString(final Object obj) {
        return (obj != null) ? obj.toString() : "(null)";
    }

    public static String toString(final Object... objs) {
        if (objs == null || objs.length == 0) {
            return "(empty)";
        }
        if (objs.length != 1) {
            final StringBuilder buf = new StringBuilder();
            for (final Object obj : objs) {
                if (obj instanceof Object[]) {
                    buf.append(toString((Object[]) obj));
                } else {
                    buf.append(toString(obj));
                }
            }
            return buf.toString();
        }
        if (objs[0] instanceof Object[]) {
            return toString((Object[]) objs[0]);
        }
        return toString(objs[0]);
    }

    public static void debug(final Object... objs) {
        System.out.println(toString(objs));
    }

    static {
        RAND = new Random();
    }
}
