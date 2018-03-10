package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
        super("Elevator", RobotMap.ELEVATOR_SAFE_BOTTOM,
                RobotMap.ELEVATOR_SAFE_TOP);  // bottom: 15, top: 58
        this.motor1 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_1);
        this.motor2 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_2);
        this.configureMotors();

        // Encoders
        this.motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.motor1.setSensorPhase(false);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ElevatorCommand());
    }
}
