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
 * A command that sets the intake and elevator to the specified heights using concurrent PID loops.
 *
 * @author aaplmath
 */
public class DoublePIDTuning extends PIDCommand {

    /**
     * An enum containing preset values for common vertical extender positions.
     */
    public enum Preset {
        LOWER("LowerAllExtenders", 0d, 0d),
        SWITCH("RaiseToSwitch", 20d, 0d),
        CLIMB("RaiseToClimb", 33d, 0d);

        private String name;
        private double intakePreset;
        private double elevatorPreset;

        Preset(String name, double intakePreset, double elevatorPreset) {
            this.name = name;
            this.intakePreset = intakePreset;
            this.elevatorPreset = elevatorPreset;
        }

        public String getName() {
            return name;
        }

        public double getIntakePreset() {
            return intakePreset;
        }

        public double getElevatorPreset() {
            return elevatorPreset;
        }
    }

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
    public DoublePIDTuning(String name, double intakeSetpoint,
                           double elevatorSetpoint, double prop, double itg, double drv) {
        super(name, prop, itg, drv);
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
        this.elevatorController = new PIDController(this.getPIDController().getP(),
                this.getPIDController().getI(), this.getPIDController().getD(), 0.0d,
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
