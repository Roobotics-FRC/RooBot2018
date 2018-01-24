package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain2017;

public class DriveDistanceAuton extends PIDCommand {

    PIDController distancePIDController;
    PIDSource distanceSource;
    PIDOutput distanceOutput;

    private static double kP = 0.0100d;
    private static double kI = 0.0000d;
    private static double kD = 0.0010d;

    private double setpoint;
    private double pidOutput = 1d;
    private double robotSpeed = 0.0d;
    private static final double COOLDOWN_THRESHOLD = 0.15;

    private Drivetrain2017 drivetrain;

    /**
     * Constructs a new DriveDistanceAuton command and initializes the secondary PID controller.
     * @param distance The distance, in inches, the robot should drive.
     */
    public DriveDistanceAuton(double distance) {
        super("DriveDistanceAuton", kP, kI, kD);
        this.setpoint = distance;
        requires(this.drivetrain = Drivetrain2017.getInstance());
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
                        * Drivetrain2017.POSITION_CONVERSION_FACTOR;
            }
        };
        distanceOutput = output -> {
            SmartDashboard.putNumber("Distance PID Output", output);
            this.pidOutput = output;
            robotSpeed = -output;
        };
        this.distancePIDController = new PIDController(kP, kI, kD, 0, distanceSource,
                distanceOutput);
        this.distancePIDController.setOutputRange(-0.5, 0.5);
        this.distancePIDController.setSetpoint(drivetrain.getLeftPosition()
                * Drivetrain2017.POSITION_CONVERSION_FACTOR + setpoint);
        this.distancePIDController.enable();
        System.out.println("PID IS ENABLED: " + distancePIDController.isEnabled());
        System.out.println("SETPOINT FOR DIST: " + distancePIDController.getSetpoint());
    }

    @Override
    protected void initialize() {
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
    protected void execute() {
        SmartDashboard.putNumber("L Pos", drivetrain.getLeftPosition());
        SmartDashboard.putNumber("L Vel", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("L Pos (in)", drivetrain.getLeftPosition()
                * Drivetrain2017.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("L Vel (in∕s)", drivetrain.getLeftVelocity()
                * Drivetrain2017.VELOCITY_CONVERSION_FACTOR);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(this.pidOutput) < COOLDOWN_THRESHOLD;
    }

    @Override
    protected void interrupted() {
        System.out.println("***DriveDistanceAuton interrupted***");
        this.getPIDController().reset();
        this.distancePIDController.reset();
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void end() {
        System.out.println("***DriveDistanceAuton ended***");
        this.getPIDController().reset();
        this.distancePIDController.reset();
        this.drivetrain.setBoth(0);
    }
}
