package org.usfirst.frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

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
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        // Determine the setpoint for the intake
        // The target height is 20 inches (the edge of the barrier is 18.75in,
        // and we want some leeway). The intake moves 0-51in and the elevator 0-38in.
        // We will move the intake more than the elevator whenever possible.
        double intakePos = intake.getRelativePosition();
        double elevatorPos = elevator.getRelativePosition();
        double curPos = intakePos + elevatorPos;
        if (elevatorPos <= 20) {
            //The intake should be the only thing moving
            this.setSetpoint(20 - elevatorPos);
        } else if (Math.round(curPos) == 20) {
            this.finished = true;
        } else { //This happens if elevator > 20
            //Move elevator to 20, move intake to 0
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
            elevatorOutput = output -> elevator.set(output);
            elevatorController = new PIDController(kP, kI, kD, 0.0d,
                    elevatorSource, elevatorOutput);
            elevatorController.setSetpoint(20);
            this.setSetpoint(0);
        }
    }

    @Override
    protected double returnPIDInput() {
        return intake.getRelativePosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        // check limit switches for safety!
        intake.set(output);
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
