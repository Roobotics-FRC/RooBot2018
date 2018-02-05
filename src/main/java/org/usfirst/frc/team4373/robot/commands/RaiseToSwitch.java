package org.usfirst.frc.team4373.robot.commands;

/**
 * This command moves the elevator and intake until together they are at the height of the switch.
 *
 * @author aaplmath
 * @author Samasaur
 */
public class RaiseToSwitch extends VerticalExtenderSetter {

    /**
     * Constructs a new RaiseToSwitch command.
     */
    public RaiseToSwitch() {
        // Determine the setpoint for the intake
        // The target height is 20 inches (the edge of the barrier is 18.75in,
        // and we want some leeway). The intake moves 0-51in and the elevator 0-38in.
        // We will move the intake more than the elevator whenever possible.
        super("RaiseToSwitch", 20, 0);
    }


}
