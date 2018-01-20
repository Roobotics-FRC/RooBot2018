package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.commands.teleop.DrivetrainCommand2017;

public class Drivetrain2017 extends Subsystem {
    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;
    private WPI_TalonSRX middle1;
    private WPI_TalonSRX middle2;

    // Conversion factors to inches or inches/second
    // wheels are 6 inches in diameter, 4096 units = 1 revolution, velocity is in units/0.1sec
    public static final double POSITION_CONVERSION_FACTOR = 6 * Math.PI / 4096;
    public static final double VELOCITY_CONVERSION_FACTOR = 10 * 6 * Math.PI / 4096;

    private Drivetrain2017() {
        this.left1 = new WPI_TalonSRX(3);
        this.left2 = new WPI_TalonSRX(5);
        this.right1 = new WPI_TalonSRX(4);
        this.right2 = new WPI_TalonSRX(8);
        this.middle1 = new WPI_TalonSRX(6);
        this.middle2 = new WPI_TalonSRX(7);

        this.left1.setNeutralMode(NeutralMode.Brake);
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.right1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);
        this.middle1.setNeutralMode(NeutralMode.Brake);
        this.middle2.setNeutralMode(NeutralMode.Brake);

        this.right2.follow(right1);
        this.left2.follow(left1);
        this.middle2.follow(middle1);

        this.middle1.setInverted(true);
        this.middle2.setInverted(true);

        this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.right1.setSensorPhase(false);

        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.left1.setSensorPhase(false);
    }

    private static Drivetrain2017 instance;

    public static Drivetrain2017 getInstance() {
        return instance == null ? instance = new Drivetrain2017() : instance;
    }

    /**
     * Sets.
     * @param power powers.
     */
    public void setLeft(double power) {
        if (power > 1) {
            power = 1;
        } else if (power < -1) {
            power = -1;
        }
        this.left1.set(power);
    }

    /**
     * Sets stuff.
     * @param power thing.
     */
    public void setRight(double power) {
        if (power > 1) {
            power = 1;
        } else if (power < -1) {
            power = -1;
        }
        this.right1.set(power);
    }

    /**
     * Sets middle power.
     * @param power power -1-1.
     */
    public void setMiddle(double power) {
        if (power > 1) {
            power = 1;
        } else if (power < -1) {
            power = -1;
        }
        this.middle1.set(power);
    }

    /**
     * Sets stuff.
     * @param power things.
     */
    public void setBoth(double power) {
        setLeft(power);
        setRight(power);
    }

    public int[] getRightEncoder() {
        return new int[]{right1.getSelectedSensorPosition(0), right1.getSelectedSensorVelocity(0)};
    }

    public int[] getLeftEncoder() {
        return new int[]{left1.getSelectedSensorPosition(0), left1.getSelectedSensorVelocity(0)};
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainCommand2017());
    }
}
