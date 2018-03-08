package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4373.robot.RobotMap;

public class CaptureSwitchAuton extends CommandGroup {

    /**
     * Constructs a new CaptureSwitchAuton command group.
     * @param startsOnLeft whether the robot starts on the left side of the field.
     */
    public CaptureSwitchAuton(boolean startsOnLeft) {
        addSequential(new DriveDistanceAuton(RobotMap.AUTON_SWITCH_DISTANCE));
        addSequential(new TurnToAngleAuton(startsOnLeft ? 90 : -90));
        addSequential(new ReleaseCubeAuton());
    }
}
