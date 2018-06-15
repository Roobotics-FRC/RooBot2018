package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * An auton that does a command after a specified delay.
 *
 * @author Samasaur
 */
public class DoLaterAuton extends CommandGroup {
    public DoLaterAuton(double secondsToDelay, Command followingCommand) {
        addSequential(new DelayAuton(secondsToDelay));
        addSequential(followingCommand);
    }
}
