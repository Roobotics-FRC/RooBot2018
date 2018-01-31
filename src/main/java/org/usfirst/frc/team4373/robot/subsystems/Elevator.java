package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.DefaultCommand;
import org.usfirst.frc.team4373.robot.RobotMap;

/**
 * The elevator on the robot. This is on the inside track and is 'slower' (supposedly).
 *
 * @author aaplmath
 * @author Samasaur
 */
public class Elevator extends VerticalExtender {

    private static Elevator instance;

    public static Elevator getInstance() {
        return instance == null ? instance = new Elevator() : instance;
    }

    private Elevator() {
        super("Elevator");
        this.motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR);
        this.bottomSwitch = new DigitalInput(RobotMap.ELEVATOR_LOWER_LIMIT_SWITCH);
        this.topSwitch = new DigitalInput(RobotMap.ELEVATOR_UPPER_LIMIT_SWITCH);
        this.configureMotor();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(DefaultCommand.getInstance());
    }

    @Override
    public void set(double power) {
        super.set(power);
        SmartDashboard.putNumber("Elevator Motor Power", power);
    }
}