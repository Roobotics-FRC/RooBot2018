package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

/**
 * An auton command to drive a set distance in a straight line.
 */
public class DriveDistanceAuton extends PIDCommand {

    private PIDController distancePIDController;
    private PIDSource distanceSource;
    private PIDOutput distanceOutput;

    private double setpoint;
    private double pidOutput = 1d;
    private double robotSpeed = 0.25d;
    private double cooldownThreshold = 0.0625;

    private Drivetrain drivetrain;

    /**
     * Constructs a new DriveDistanceAuton command and initializes the secondary PID controller.
     * @param distance The distance, in inches, that the robot should drive.
     */
    public DriveDistanceAuton(double distance, double speed) {
        super("DriveDistanceAuton", RobotMap.DRIVETRAIN_P, RobotMap.DRIVETRAIN_I,
                RobotMap.DRIVETRAIN_D);
        this.setpoint = distance;
        this.robotSpeed = speed;
        this.cooldownThreshold = this.robotSpeed * 0.25;
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
                return drivetrain.getConvertedAveragePosition();
            }
        };

        distanceOutput = output -> {
            this.pidOutput = output;
        };
        this.distancePIDController = new PIDController(RobotMap.DRIVETRAIN_P,
                RobotMap.DRIVETRAIN_I, RobotMap.DRIVETRAIN_D, 0, distanceSource, distanceOutput);
    }

    @Override
    protected void initialize() {
        // Distance PID configuration
        this.distancePIDController.setOutputRange(-robotSpeed, robotSpeed);
        this.distancePIDController.setSetpoint(drivetrain.getConvertedAveragePosition()
                + setpoint);
        this.distancePIDController.enable();

        // Angular PID configuration
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-robotSpeed, robotSpeed);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        this.drivetrain.setRight(-pidOutput - output);
        this.drivetrain.setLeft(-pidOutput + output);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(this.pidOutput) < cooldownThreshold;
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