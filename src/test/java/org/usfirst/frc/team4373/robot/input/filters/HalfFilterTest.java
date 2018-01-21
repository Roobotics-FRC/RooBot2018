package org.usfirst.frc.team4373.robot.input.filters;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by derros on 1/21/18.
 */
public class HalfFilterTest {

    private final double eps = 0.001d;

    private HalfFilter filter;

    /**
     * DEPRESSED.
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running HalfFilterTest...");

        this.filter = new HalfFilter();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void applyFilter() throws Exception {
        // randomized testing
        double d;
        for (int i = 0; i < 100; ++i) {
            d = Math.random();
            assertEquals(filter.applyFilter(d), d / 2, eps);
        }
        for (int i = 0; i < 100; ++i) {
            d = -Math.random();
            assertEquals(filter.applyFilter(d), d / 2, eps);
        }
        for (int i = -10; i < 10; ++i) {
            assertEquals(Math.floor(filter.applyFilter((double) i)), (double) (i >> 1), eps);
        }
    }

}