package org.usfirst.frc.team4373.robot.input.utilities;

import java.util.Comparator;

/**
 * Created by thefangbear on 1/21/18.
 */
public final class DoubleComparator implements Comparator<Double> {

    private static final double eps = 0.001;

    private boolean isReversed = false;

    private DoubleComparator(boolean r) {
        super();
        isReversed = r;
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
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(Double o1, Double o2) {
        if (o1 == null || o2 == null)
            throw new NullPointerException("Compared objects can't be null.");

        if (eq(o1, o2))
            return 0;
        else if (gt(o1, o2))
            return isReversed ? -1 : 1;
        else    // lt
            return isReversed ? 1 : -1;
    }

    /**
     * Returns a comparator that imposes the reverse ordering of this
     * comparator.
     *
     * @return a comparator that imposes the reverse ordering of this
     * comparator.
     * @since 1.8
     */
    @Override
    public Comparator<Double> reversed() {
        return new DoubleComparator(true);
    }

}
