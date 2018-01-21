package org.usfirst.frc.team4373.robot.input.filters;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by derros on 1/21/18.
 */
public class FineGrainedPiecewiseFilterTest {

    private final double eps = 0.001d;

    private FineGrainedPiecewiseFilter fgpFilter;

    /**
     * haha checkstyle.
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running FineGrainedPiecewiseFilterTest...");

        this.fgpFilter = new FineGrainedPiecewiseFilter();
    }

    @After
    public void tearDown() throws Exception {

    }

    private double getVal(double input) {
        return (Math.abs(input) <= 0.8) ? 0.5 * input :
                ((3 * input) - (Math.signum(input) * 2));
    }

    @Test
    public void applyFilter() throws Exception {
        // positive inductive assertions
        for (double d = 0.0d; d < 200.5d; d += 0.5d) {
            assertEquals(this.fgpFilter.applyFilter(d), getVal(d), eps);
        }
        // negative inductive assertions
        for (double d = 0.0d; d > -200.5d; d -= 0.5d) {
            assertEquals(this.fgpFilter.applyFilter(d), getVal(d), eps);
        }
        // random values chaos
        double d;
        for (int i = 0; i < 5; ++i) {
            d = Math.random();
            assertEquals(this.fgpFilter.applyFilter(d), getVal(d), eps);
        }
    }

}