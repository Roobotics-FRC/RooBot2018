package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * Un-sticks the grabber from the hooking mechanism and then goes back up.
 *
 * @author Samasaur
 */
public class DropGrabberAuton extends Command {

    private Intake intake;
    private long startTime;

    public DropGrabberAuton() {
        requires(this.intake = Intake.getInstance());
        setTimeout(0.8);
    }

    @Override
    protected void initialize() {
        OI.getOI().getGyro().reset();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        this.intake.releaseIntake();
        if (System.currentTimeMillis() - startTime > 400) {
            this.intake.set(0.85);
        } else {
            this.intake.set(-0.7);
        }
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