package org.usfirst.frc.team4373.robot.input.utilities;

import java.util.Comparator;

/**
 * Created by thefangbear on 1/21/18.
 */
public final class DoubleComparator implements Comparator<Double> {

    private static final double eps = 0.000000001; /* 10^-9 */

    private boolean isReversed = false;

    private DoubleComparator(boolean reverse) {
        super();
        isReversed = reverse;
    }

    public DoubleComparator() {
        super();
    }

    public static boolean eq(double o1, double o2) {
        return (o1 <= o2 + eps) && (o1 >= o2 - eps);
    }

    public static boolean gt(double o1, double o2) {
        return (o1 > o2 + eps);
    }

    public static boolean lt(double o1, double o2) {
        return (o1 < o2 - eps);
    }

    public static boolean ge(double o1, double o2) {
        return eq(o1, o2) || gt(o1, o2);
    }

    public static boolean le(double o1, double o2) {
        return eq(o1, o2) || lt(o1, o2);
    }

    /**
     * (Comment: CheckStyle actually thinks Java's default JavaDoc has issues), Well...
     */
    @Override
    public int compare(Double o1, Double o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Compared objects can't be null.");
        }
        if (eq(o1, o2)) {
            return 0;
        } else if (gt(o1, o2)) {
            return isReversed ? -1 : 1;
        } else {  // lt
            return isReversed ? 1 : -1;
        }
    }

    /**
     * Returns a comparator that imposes the reverse ordering of this
     * comparator.
     */
    @Override
    public Comparator<Double> reversed() {
        return new DoubleComparator(true);
    }

}
