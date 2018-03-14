package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.datatypes.Position;

public class CaptureSwitchFromCenterAuton extends CommandGroup {

    /**
     * Constructs a new CaptureSwitchFromCenterAuton command.
     */
    public CaptureSwitchFromCenterAuton(Position switchPos) {
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
    }
}
