package org.usfirst.frc.team4373.robot.input.filters;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by derros on 1/21/18.
 */
public class ThirdFilterTest {
    private final double eps = 0.001d;
    private ThirdFilter filter;

    /**
     * Hahahahaha checkstyle.
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running ThirdFilterTest...");
        this.filter = new ThirdFilter();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void applyFilter() throws Exception {
        double d;
        for (int i = 0; i < 100; ++i) {
            d = Math.random();
            assertEquals(filter.applyFilter(d), d / 3, eps);
        }
        for (int i = -10; i < 10; ++i) {
            assertEquals((filter.applyFilter((double) i)), (double) i / 3, eps);
        }
    }

}