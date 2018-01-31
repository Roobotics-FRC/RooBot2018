package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.ExperimentalRooDriveTrain;

/**
 * Created by derros on 1/27/18.
 */
public class DriveTrainAuton extends PIDCommand {


    private static final double COOLDOWN_THRESHOLD = 0.15;
    private static double kP = 0.0100d;
    private static double kI = 0.0000d;
    private static double kD = 0.0010d;
    private PIDController distancePIDController;
    private PIDSource distanceSource;
    private PIDOutput distanceOutput;
    private double setpoint;
    private double pidOutput = 1d;
    private double robotSpeed = 0.0d;
    private ExperimentalRooDriveTrain driveTrain;


    public DriveTrainAuton(double distance) {

        super("DriveDistanceAuton", kP, kI, kD);
        this.setpoint = distance;
        requires(this.driveTrain = ExperimentalRooDriveTrain.getInstance());

        // Distance PID initialization
        distanceSource = new PIDSource() {
            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                return;
            }

            @Override
            public double pidGet() {
                return driveTrain.getLeftPosition()
                        * ExperimentalRooDriveTrain.POSITION_CONVERSION_FACTOR;
            }
        };

        distanceOutput = output -> {
            SmartDashboard.putNumber("Distance PID Output", output);
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
        this.distancePIDController.setSetpoint(driveTrain.getLeftPosition()
                * ExperimentalRooDriveTrain.POSITION_CONVERSION_FACTOR + setpoint);
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
        this.driveTrain.setRight(robotSpeed - output);
        this.driveTrain.setLeft(robotSpeed + output);
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("L Pos", driveTrain.getLeftPosition());
        SmartDashboard.putNumber("L Vel", driveTrain.getLeftVelocity());
        SmartDashboard.putNumber("L Pos (in)", driveTrain.getLeftPosition()
                * ExperimentalRooDriveTrain.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("L Vel (in∕s)", driveTrain.getLeftVelocity()
                * ExperimentalRooDriveTrain.VELOCITY_CONVERSION_FACTOR);
        SmartDashboard.putNumber("Error: ", dri);
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
        this.driveTrain.setBoth(0);
    }

    @Override
    protected void end() {
        System.out.println("***DriveDistanceAuton ended***");
        this.getPIDController().reset();
        this.distancePIDController.reset();
        this.driveTrain.setBoth(0);
    }
}
