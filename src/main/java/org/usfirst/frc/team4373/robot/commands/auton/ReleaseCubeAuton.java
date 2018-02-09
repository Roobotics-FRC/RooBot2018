package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

public class ReleaseCubeAuton extends Command {

    private Intake intake;

    public ReleaseCubeAuton() {
        requires(this.intake = Intake.getInstance());
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.intake.releaseCube();
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void interrupted() {

    }

    @Override
    protected void end() {

    }
}
