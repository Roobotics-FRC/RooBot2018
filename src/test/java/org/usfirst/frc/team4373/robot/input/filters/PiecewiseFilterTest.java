package org.usfirst.frc.team4373.robot.input.filters;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by derros on 1/21/18.
 */
public class PiecewiseFilterTest {
    private final double eps = 0.001d;

    private PiecewiseFilter filter;

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running PiecewiseFilterTest...");
        this.filter = new PiecewiseFilter();
    }

    private double getVal(double input) {
        return (Math.abs(input) <= 0.4) ? 0.5 * input :
                (Math.abs(input) <= 0.8 ? 0.75 * input
                        - (Math.signum(input) * 0.1) :
                        (2.5 * input - Math.signum(input) * 1.5));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void applyFilter() throws Exception {
        double d;
        for (int i = 0; i < 100; ++i) {
            d = Math.random();
            assertEquals(filter.applyFilter(d), getVal(d), eps);
        }
        for (int i = -10; i < 10; ++i) {
            assertEquals(filter.applyFilter((double) i), getVal((double) i), eps);
        }
    }

}