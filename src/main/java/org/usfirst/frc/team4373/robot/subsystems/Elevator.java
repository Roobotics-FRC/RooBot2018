package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.ElevatorCommand;

/**
 * A programmatic representation of the elevator VerticalExtender. This is the outermost,
 * (supposedly) slower one.
 *
 * @author aaplmath
 */
public class Elevator extends VerticalExtender {

    private static Elevator instance;

    public static Elevator getInstance() {
        return instance == null ? instance = new Elevator() : instance;
    }

    private Elevator() {
        super("Elevator");
        this.motor1 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_1);
        this.motor2 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_2);
        //this.bottomSwitch = new DigitalInput(RobotMap.ELEVATOR_LOWER_LIMIT_SWITCH);
        //this.topSwitch = new DigitalInput(RobotMap.ELEVATOR_UPPER_LIMIT_SWITCH);
        //this.configureMotors();
        this.motor1.setNeutralMode(NeutralMode.Brake);
        this.motor2.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ElevatorCommand());
    }
}
