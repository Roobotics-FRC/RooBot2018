
package org.usfirst.frc.team4373.robot.input.filters;

/**
 * CubeFilter cubes input numbers.
 * @author Henry Pitcairn
 * @author Rui-Jie Fang
 */
public class CubeFilter extends DoubleTypeFilter {

    @Override
    public Double applyFilter(Double input) {
        return input * input * input;
    }
}
