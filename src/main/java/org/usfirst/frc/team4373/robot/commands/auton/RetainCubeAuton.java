package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

public class RetainCubeAuton extends Command {

    private Intake intake;
    private Elevator elevator;

    /**
     * Does stuff.
     */
    public RetainCubeAuton() {
        requires(this.intake = Intake.getInstance());
        requires(this.elevator = Elevator.getInstance());
        setTimeout(0.2);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.intake.retainCube();
        this.elevator.set(0.5);

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
