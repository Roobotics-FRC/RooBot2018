package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * A Javadoc template. You're welcome. TODO: Update me.
 *
 * @author Samasaur
 */
public class DropGrabberAuton extends Command {

    private Intake intake;

    public DropGrabberAuton() {
        requires(this.intake = Intake.getInstance());
        setTimeout(0.2);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.intake.set(-0.5);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void interrupted() {
        this.intake.set(0);
    }

    @Override
    protected void end() {
        this.intake.set(0);
    }
}