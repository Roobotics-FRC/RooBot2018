package org.usfirst.frc.team4373.robot.input.filters;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by thefangbear on 1/21/18.
 */
public class CubeFilterTest {

    static final double eps = 0.001;
    CubeFilter cubeFilter;

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running CubeFilterTest...");
        this.cubeFilter = new CubeFilter();
    }

    @After
    public void tearDown() throws Exception {
        // do nothing
    }

    @Test
    public void applyFilter() throws Exception {
        // inductive assertions
        for (double d = 0; d < 200.5; d += 0.5) {
            assertEquals(cubeFilter.applyFilter(d), Math.pow(d, 3), eps);
        }
        // negative inductive assertions
        for (double d = 0; d > -200.5; d -= 0.5) {
            assertEquals(cubeFilter.applyFilter(d), Math.pow(d, 3), eps);
        }
        // random value tests * 3
        double d;
        for (int i = 0; i < 3; ++i) {
            // chaos monkey!
            d = Math.random();
            assertEquals(cubeFilter.applyFilter(d), Math.pow(d, 3), eps);
        }
    }

}