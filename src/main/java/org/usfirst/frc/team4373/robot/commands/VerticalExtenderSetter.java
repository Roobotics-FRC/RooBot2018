package org.usfirst.frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

public abstract class VerticalExtenderSetter extends PIDCommand {

    private static double kP = 0.01;
    private static double kI = 0.001;
    private static double kD = 0.005;
    private static double displacementThreshold = 0.5;

    private PIDController elevatorController;
    private PIDSource elevatorSource;
    private PIDOutput elevatorOutput;

    private Elevator elevator;
    private Intake intake;

    private boolean intakeFinished = false;
    private boolean elevatorFinished = false;

    private double intakeSetpoint;
    private double elevatorSetpoint;

    /**
     * Constructs a new VerticalExtenderSetter command.
     * @param name the name of the command.
     * @param intakeSetpoint the setpoint for the intake.
     * @param elevatorSetpoint the setpoint for the elevator.
     */
    protected VerticalExtenderSetter(String name, double intakeSetpoint, double elevatorSetpoint) {
        super(name, kP, kI, kD);
        requires(this.elevator = Elevator.getInstance());
        requires(this.intake = Intake.getInstance());

        this.intakeSetpoint = intakeSetpoint;
        this.elevatorSetpoint = elevatorSetpoint;

        this.getPIDController().setOutputRange(-RobotMap.VERTICAL_EXTENDER_SPEED,
                RobotMap.VERTICAL_EXTENDER_SPEED);
    }

    @Override
    protected void initialize() {
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
        elevatorOutput = output -> {
            if (Math.abs(this.elevator.getRelativePosition() - this.elevatorSetpoint)
                    < displacementThreshold) {
                this.elevatorFinished = true;
            } else {
                this.elevator.set(output);
            }
        };
        this.elevatorController = new PIDController(kP, kI, kD, 0.0d,
                this.elevatorSource, this.elevatorOutput);
        this.elevatorController.setOutputRange(-RobotMap.VERTICAL_EXTENDER_SPEED,
                RobotMap.VERTICAL_EXTENDER_SPEED);
        this.elevatorController.setSetpoint(elevatorSetpoint);
        this.setSetpoint(intakeSetpoint);
    }

    @Override
    protected double returnPIDInput() {
        return intake.getRelativePosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.abs(this.intake.getRelativePosition() - this.intakeSetpoint)
                < displacementThreshold) {
            this.intakeFinished = true;
        } else {
            this.intake.set(output);
        }
    }

    @Override
    protected boolean isFinished() {
        return this.intakeFinished && this.elevatorFinished;
    }

}
