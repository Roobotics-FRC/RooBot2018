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

    // Conversion factors to inches or inches/second
    // wheels are 6 inches in diameter, 4096 units = 1 revolution, velocity is in units/0.1sec
    public static final double POSITION_CONVERSION_FACTOR = 6 * Math.PI / 4096;
    public static final double VELOCITY_CONVERSION_FACTOR = 10 * 6 * Math.PI / 4096;

    private Drivetrain2017() {
        left1 = new WPI_TalonSRX(3);
        left2 = new WPI_TalonSRX(5);
        right1 = new WPI_TalonSRX(4);
        right2 = new WPI_TalonSRX(8);

        left1.setNeutralMode(NeutralMode.Brake);
        left2.setNeutralMode(NeutralMode.Brake);
        right1.setNeutralMode(NeutralMode.Brake);
        right2.setNeutralMode(NeutralMode.Brake);

        left1.setInverted(true);

        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        right1.setSensorPhase(false);

        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        left1.setSensorPhase(false);
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
        left1.set(power);
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
        right1.set(power);
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
