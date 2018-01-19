package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.commands.teleop.DrivetrainCommand2017;

public class Drivetrain2017 extends Subsystem {
    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;

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

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainCommand2017());
    }
}
