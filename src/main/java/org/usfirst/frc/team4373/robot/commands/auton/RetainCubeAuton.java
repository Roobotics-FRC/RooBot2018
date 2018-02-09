package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

public class RetainCubeAuton extends Command {

    private Intake intake;

    public RetainCubeAuton() {
        requires(this.intake = Intake.getInstance());
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.intake.retainCube();
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
