package org.usfirst.frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * This command moves the elevator and intake until together they are at the height of the switch.
 *
 * @author aaplmath
 * @author Samasaur
 */
public class MoveToSwitch extends PIDCommand {

    private static double kP = 0.01;
    private static double kI = 0.001;
    private static double kD = 0.005;

    private PIDController elevatorController;
    private PIDSource elevatorSource;
    private PIDOutput elevatorOutput;

    private Elevator elevator;
    private Intake intake;

    private boolean finished = false;

    /**
     * Constructs a new MoveToSwitch command.
     */
    public MoveToSwitch() {
        super("MoveToSwitch", kP, kI, kD);
        requires(this.elevator = Elevator.getInstance());
        requires(this.intake = Intake.getInstance());
        this.getPIDController().setOutputRange(-RobotMap.VERTICAL_EXTENDER_SPEED,
                RobotMap.VERTICAL_EXTENDER_SPEED);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        // Determine the setpoint for the intake
        // The target height is 20 inches (the edge of the barrier is 18.75in,
        // and we want some leeway). The intake moves 0-51in and the elevator 0-38in.
        // We will move the intake more than the elevator whenever possible.
        if (Math.round(intake.getRelativePosition() + elevator.getRelativePosition()) == 20) {
            this.finished = true;
        } else {
            elevatorSource = new PIDSource() {
                @Override
                public void setPIDSourceType(PIDSourceType pidSource) {
                    return;
                }

                @Override
                public PIDSourceType getPIDSourceType() {
                    return PIDSourceType.kDisplacement;
                }

                @Override
                public double pidGet() {
                    return elevator.getRelativePosition();
                }
            };
            this.elevatorOutput = output -> elevator.set(output);
            this.elevatorController = new PIDController(kP, kI, kD, 0.0d,
                    this.elevatorSource, this.elevatorOutput);
            this.elevatorController.setOutputRange(-RobotMap.VERTICAL_EXTENDER_SPEED,
                    RobotMap.VERTICAL_EXTENDER_SPEED);
            this.elevatorController.setSetpoint(0);
            this.setSetpoint(20);
        }
    }

    @Override
    protected double returnPIDInput() {
        return intake.getRelativePosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.round(intake.getRelativePosition() + elevator.getRelativePosition()) == 20) {
            this.finished = true;
        } else {
            intake.set(output);
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
