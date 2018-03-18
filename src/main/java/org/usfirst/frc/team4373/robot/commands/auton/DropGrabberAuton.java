package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * Un-sticks the grabber from the hooking mechanism and then goes back up.
 *
 * @author Samasaur
 */
public class DropGrabberAuton extends CommandGroup {

    public DropGrabberAuton() {
        addSequential(new MoveIntakeAuton(0.4, -0.7));
        addSequential(new MoveIntakeAuton(0.4, 0.85));
    }
}