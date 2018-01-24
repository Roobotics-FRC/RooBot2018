package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DummyGroup extends CommandGroup {

    /**
     * Constructs a new DummyGroup autonomous command.
     */
    public DummyGroup() {
        addSequential(new DriveDistanceAuton(12 * 10));
        addSequential(new TurnToPosition(90));
        addSequential(new DriveDistanceAuton(12 * 5));
        addSequential(new TurnToPosition(-90));
    }
}
