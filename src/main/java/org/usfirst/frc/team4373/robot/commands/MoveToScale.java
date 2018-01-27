package org.usfirst.frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * This command moves the elevator and intake until, together, they are at the height of the scale.
 */
public class MoveToScale extends PIDCommand {

    private static double kP = 0.01;
    private static double kI = 0.001;
    private static double kD = 0.005;

    private Elevator elevator;
    private Intake intake;

    private boolean finished = false;

    /**
     * Constructs a new MoveToSwitch command.
     */
    public MoveToScale() {
        super("MoveToScale", kP, kI, kD);
        requires(this.elevator = Elevator.getInstance());
        requires(this.intake = Intake.getInstance());
        this.getPIDController().setOutputRange(-RobotMap.VERTICAL_EXTENDER_SPEED,
                RobotMap.VERTICAL_EXTENDER_SPEED);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(78);
    }

    @Override
    protected double returnPIDInput() {
        return intake.getRelativePosition() + elevator.getRelativePosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.round(intake.getRelativePosition() + elevator.getRelativePosition()) == 20) {
            this.finished = true;
        } else {
            intake.set(output);
            elevator.set(output);
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        this.elevator.set(0);
        this.intake.set(0);
    }

    @Override
    protected void interrupted() {
        this.elevator.set(0);
        this.intake.set(0);
    }
}
