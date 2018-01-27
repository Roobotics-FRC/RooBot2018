package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * This command lets the operator joystick control the intake VerticalExtender.
 */
public class IntakeCommand extends Command {

    private Intake intake;

    public IntakeCommand() {
        requires(this.intake = Intake.getInstance());
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double axis = OI.getOI().getOperatorJoystick().getAxis(RobotMap.INTAKE_AXIS);
        if (Math.abs(axis) > RobotMap.THUMBSTICK_THRESHOLD) {
            this.intake.set(Math.signum(axis) * RobotMap.VERTICAL_EXTENDER_SPEED);
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
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
