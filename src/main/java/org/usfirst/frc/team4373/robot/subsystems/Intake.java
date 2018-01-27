package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.IntakeCommand;

public class Intake extends VerticalExtender {

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        this.motor = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR);
        this.bottomSwitch = new DigitalInput(RobotMap.INTAKE_LOWER_LIMIT_SWITCH);
        this.topSwitch = new DigitalInput(RobotMap.INTAKE_UPPER_LIMIT_SWITCH);

        this.configureMotor();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeCommand());
    }
}
