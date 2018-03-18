package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * An auton command group to capture the scale from the corresponding side.
 *
 * @author aaplmath
 */
public class CaptureScaleAuton extends CommandGroup {
    /**
     * Constructs a new CaptureScaleAuton command.
     * @param startsOnLeft a boolean indicating whether the robot starts on the left side.
     */
    public CaptureScaleAuton(boolean startsOnLeft) {
        addSequential(new RetainCubeAuton());
        addSequential(new DriveDistanceAuton(315, 1));
        addSequential(new DropGrabberAuton());
        addParallel(new MoveElevatorAuton(3, 0.7));
        addParallel(new MoveIntakeAuton(3, 0.85));
        addSequential(new TurnToAngleAuton(startsOnLeft ? 90 : -90));
        addSequential(new DriveDistanceAuton(6, 0.25));
        addSequential(new ReleaseCubeAuton());
        addSequential(new DriveDistanceAuton(-6, 0.25));
        addParallel(new MoveElevatorAuton(3, -0.7));
        addParallel(new MoveIntakeAuton(3, -0.85));
        addSequential(new TurnToAngleAuton(startsOnLeft ? -90 : 90));
    }
}
