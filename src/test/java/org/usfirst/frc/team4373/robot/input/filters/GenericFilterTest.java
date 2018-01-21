package org.usfirst.frc.team4373.robot.input.filters;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by thefangbear on 1/21/18.
 */
public class GenericFilterTest {

    // for generic filters and classes, we just test proper instantiation/destruction
    // by subclassing/implementing it

    private TFilter filter;

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp(): Running GenericFilterTest...");
        filter = new TFilter();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void applyFilter() throws Exception {
        assertEquals(filter.applyFilter(null), null);
    }

    class TFilter implements GenericFilter<Object> {

        @Override
        public Object applyFilter(Object input) {
            return null;
        }
    }

}