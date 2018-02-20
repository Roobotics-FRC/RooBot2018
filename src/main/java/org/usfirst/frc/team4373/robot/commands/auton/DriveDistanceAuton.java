package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.Motors;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;

public class DriveDistanceAuton extends PIDCommand {

    private PIDController distancePIDController;
    private PIDSource distanceSource;
    private PIDOutput distanceOutput;

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
        super("DriveDistanceAuton", SmartDashboard.getNumber("Drivetrain P",
                RobotMap.DRIVETRAIN_P), SmartDashboard.getNumber("Drivetrain I",
                RobotMap.DRIVETRAIN_I), SmartDashboard.getNumber("Drivetrain D",
                RobotMap.DRIVETRAIN_D));
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
                return drivetrain.getConvertedAveragePosition();
            }
        };

        distanceOutput = output -> {
            this.pidOutput = output;
            robotSpeed = -output;
        };
        this.distancePIDController = new PIDController(SmartDashboard.getNumber("Drivetrain P",
                RobotMap.DRIVETRAIN_P), SmartDashboard.getNumber("Drivetrain I",
                RobotMap.DRIVETRAIN_I), SmartDashboard.getNumber("Drivetrain D",
                RobotMap.DRIVETRAIN_D), 0, distanceSource, distanceOutput);
    }

    @Override
    protected void initialize() {
        // Distance PID configuration
        this.distancePIDController.setOutputRange(-RobotMap.AUTON_DRIVE_SPEED,
                RobotMap.AUTON_DRIVE_SPEED);
        this.distancePIDController.setSetpoint(drivetrain.getConvertedAveragePosition()
                + setpoint);
        this.distancePIDController.enable();

        // Angular PID configuration
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-RobotMap.AUTON_DRIVE_SPEED,
                RobotMap.AUTON_DRIVE_SPEED);
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