package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * A command that can move the intake up and down.
 *
 * @author Samasaur
 */
public class MoveIntakeAuton extends Command {

    private double power;
    private Intake intake;

    /**
     * Creates a MoveIntakeAuton that moves the intake.
     * @param seconds The time to move the intake for.
     * @param power The power to move the intake at.
     */
    public MoveIntakeAuton(double seconds, double power) {
        requires(this.intake = Intake.getInstance());
        this.setTimeout(seconds);
        this.power = power;
    }

    @Override
    protected void execute() {
        this.intake.set(power);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {
        this.intake.set(0);
    }

    @Override
    protected void interrupted() {
        this.intake.set(0);
    }
}
