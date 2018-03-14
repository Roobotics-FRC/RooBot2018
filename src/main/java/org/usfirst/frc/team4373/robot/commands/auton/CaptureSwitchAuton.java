package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.datatypes.Position;

/**
 * An auton group to capture the close switch.
 *
 * @author Samasaur
 */
public class CaptureSwitchAuton extends CommandGroup {

    /**
     * Creates a new CaptureSwitchAuton that may or may not capture the switch.
     * @param startPos The starting position of the robot.
     * @param switchPos The position of 'our' scale.
     */
    public CaptureSwitchAuton(Position startPos, Position switchPos) {
        //addSequential(new DriveDistanceAuton(RobotMap.AUTON_SWITCH_DISTANCE));
        if (startPos == Position.Center) {
            addSequential(new RetainCubeAuton());
            addSequential(new DelayAuton(1));
            addSequential(new TimedDriveAuton(0.8, 250, 0.5));
            addParallel(new DropGrabberAuton());
            if (switchPos == Position.Left) {
                addSequential(new TurnToAngleAuton(-63));
                addSequential(new TimedDriveAuton(1.1, 250, 0.5));
                addSequential(new TurnToAngleAuton(63));
            } else {
                addSequential(new TurnToAngleAuton(57));
                addSequential(new TimedDriveAuton(1, 250, 0.5));
                addSequential(new TurnToAngleAuton(-57));
            }
            addSequential(new TimedDriveAuton(2, 250, 0.3));
            addParallel(new MoveElevatorAuton(1, 0.5));
            addSequential(new ReleaseCubeAuton());
        } else {
            if (startPos == switchPos) {
                addSequential(new TimedDriveAuton(2.5, 250, 0.5));
                addParallel(new DropGrabberAuton());
                addSequential(new TurnToAngleAuton(startPos == Position.Left ? 90 : -90));
                addSequential(new TimedDriveAuton(1, 250, 0.2));
                addSequential(new ReleaseCubeAuton());
            } else {
                System.out.println("Can't get switch; opposite sides!");
                System.out.println("THIS SHOULD NEVER OCCUR. TEST BEFORE CALLING CaptureSwitchAuton!");
            }
        }
    }
}
