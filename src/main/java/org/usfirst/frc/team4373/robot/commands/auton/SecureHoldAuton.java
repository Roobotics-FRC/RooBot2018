package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command group that secures the robot's hold on the cube before proceeding.
 *
 * @author Samasaur
 */
public class SecureHoldAuton extends CommandGroup {

    /**
     * Creates a new SecureHoldAuton that holds on to the cube and then does another.
     * @param followingCommand the command to run after this one. 
     */
    public SecureHoldAuton(Command followingCommand) {
        addSequential(new RetainCubeAuton());
        addSequential(followingCommand);
    }
}
