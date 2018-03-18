package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * Holds onto a grabbed power cube.
 *
 * @author Samasaur
 */
public class RetainCubeAuton extends Command {

    private Intake intake;
    private boolean executed;

    /**
     * Creates a new RetainCubeAuton.
     */
    public RetainCubeAuton() {
        requires(this.intake = Intake.getInstance());
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
        executed = false;
    }

    @Override
    protected void execute() {
        this.intake.withdrawPusher();
        this.intake.holdCube();
        executed = true;
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut() || this.executed;
    }

    @Override
    protected void interrupted() {

    }

    @Override
    protected void end() {

    }
}
