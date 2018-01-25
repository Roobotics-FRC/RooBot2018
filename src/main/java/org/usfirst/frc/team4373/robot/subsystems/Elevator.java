package org.usfirst.frc.team4373.robot.subsystems;

import static org.usfirst.frc.team4373.robot.input.hid.Motors.safetyCheckSpeed;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

    private WPI_TalonSRX motor;

    private static Elevator instance;

    public static Elevator getInstance() {
        return instance == null ? instance = new Elevator() : instance;
    }

    private Elevator() {
        this.motor.setNeutralMode(NeutralMode.Brake);
        this.motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.motor.setSensorPhase(false);
    }

    /**
     * Sets the elevator motor to the specified power.
     * Positive values will make the elevator ascend.
     *
     * @param power The power, from -1 to 1, to which to set the motor.
     *              This value is safety checked to make sure it is not out of this range.
     */
    public void set(double power) {
        power = safetyCheckSpeed(power);
        this.motor.set(power);
    }

    /**
     * Gets the position of the elevator.
     * @return the position of the elevator, in 'units'.
     */
    public double getPosition() {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * Gets the velocity of the elevator.
     * @return the velocity of the elevator, in 'units'.
     */
    public double getVelocity() {
        return motor.getSelectedSensorVelocity(0);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
