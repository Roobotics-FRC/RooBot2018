package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team4373.robot.RobotMap;

public class WheeledIntake extends VerticalExtender {

    private WPI_TalonSRX motor1;
    private WPI_TalonSRX motor2;
    private DoubleSolenoid releasePiston;

    private static WheeledIntake instance;

    public static WheeledIntake getInstance() {
        return instance == null ? instance = new WheeledIntake() : instance;
    }

    private WheeledIntake() {
        super("WheeledIntake");
        this.releasePiston = new DoubleSolenoid(RobotMap.PCM_PORT,
                RobotMap.RELEASE_SOLENOID_FORWARD_PORT, RobotMap.RELEASE_SOLENOID_BACKWARD_PORT);
        this.motor1 = new WPI_TalonSRX(RobotMap.WHEELED_INTAKE_MOTOR_1);
        this.motor2 = new WPI_TalonSRX(RobotMap.WHEELED_INTAKE_MOTOR_2);
        this.motor2.follow(this.motor1);
        this.motor2.setInverted(true);
    }

    public void releaseIntake() {
        this.releasePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void retractIntake() {
        this.releasePiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void neutralizeRelease() {
        this.releasePiston.set(DoubleSolenoid.Value.kOff);
    }

    public void intakeCube() {
        this.motor1.set(RobotMap.WHEELED_INTAKE_SPEED);
    }

    public void retainCube() {
        this.motor1.set(0);
    }

    public void releaseCube() {
        this.motor1.set(-RobotMap.WHEELED_INTAKE_SPEED);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
