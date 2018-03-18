package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * Drops a grabbed power cube.
 *
 * @author Samasaur
 */
public class ReleaseCubeAuton extends Command {

    private Intake intake;
    private boolean executed;

    /**
     * Creates a new ReleaseCubeAuton.
     */
    public ReleaseCubeAuton() {
        requires(this.intake = Intake.getInstance());
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
        executed = false;
    }

    @Override
    protected void execute() {
        this.intake.extendPusher();
        this.intake.releaseCube();
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
