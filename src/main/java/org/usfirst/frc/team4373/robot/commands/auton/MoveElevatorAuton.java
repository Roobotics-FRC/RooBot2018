package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;

/**
 * A command that can move the elevator up and down.
 *
 * @author Samasaur
 */
public class MoveElevatorAuton extends Command {

    private double power;
    private Elevator elevator;

    /**
     * Creates a MoveElevatorAuton that moves the elevator.
     * @param seconds The time to move the elevator for.
     * @param power The power to move the elevator at.
     */
    public MoveElevatorAuton(double seconds, double power) {
        requires(this.elevator = Elevator.getInstance());
        this.setTimeout(seconds);
        this.power = power;
    }

    @Override
    protected void execute() {
        this.elevator.set(power);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {
        this.elevator.set(0);
    }

    @Override
    protected void interrupted() {
        this.elevator.set(0);
    }
}
