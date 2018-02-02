package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.input.hid.Motors;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

public class DriveDistanceAuton extends PIDCommand {

    private PIDController distancePIDController;
    private PIDSource distanceSource;
    private PIDOutput distanceOutput;

    private static double kP = 0.0100d;
    private static double kI = 0.0000d;
    private static double kD = 0.0010d;

    private double setpoint;
    private double pidOutput = 1d;
    private double robotSpeed = 0.0d;
    private static final double COOLDOWN_THRESHOLD = 0.15;

    private Drivetrain drivetrain;

    /**
     * Constructs a new DriveDistanceAuton command and initializes the secondary PID controller.
     * @param distance The distance, in inches, that the robot should drive.
     */
    public DriveDistanceAuton(double distance) {
        super("DriveDistanceAuton", kP, kI, kD);
        this.setpoint = distance;
        requires(this.drivetrain = Drivetrain.getInstance());

        // Distance PID initialization
        distanceSource = new PIDSource() {
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
                return drivetrain.getLeftPosition()
                        * Motors.POSITION_CONVERSION_FACTOR;
            }
        };

        distanceOutput = output -> {
            this.pidOutput = output;
            robotSpeed = -output;
        };
        this.distancePIDController = new PIDController(kP, kI, kD, 0, distanceSource,
                distanceOutput);
    }

    @Override
    protected void initialize() {
        // Distance PID configuration
        this.distancePIDController.setOutputRange(-0.5, 0.5);
        this.distancePIDController.setSetpoint(drivetrain.getLeftPosition()
                * Motors.POSITION_CONVERSION_FACTOR + setpoint);
        this.distancePIDController.enable();

        // Angular PID configuration
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-0.5, 0.5);
        this.getPIDController().setPID(kP, kI, kD);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        this.drivetrain.setRight(robotSpeed - output);
        this.drivetrain.setLeft(robotSpeed + output);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(this.pidOutput) < COOLDOWN_THRESHOLD;
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.distancePIDController.reset();
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void end() {
        this.getPIDController().reset();
        this.distancePIDController.reset();
        this.drivetrain.setBoth(0);
    }
}