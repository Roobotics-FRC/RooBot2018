package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.IntakeCommand;

/**
 * A programmatic representation of the intake VerticalExtender. This is the innermost,
 * (supposedly) faster one.
 *
 * @author aaplmath
 */
public class Intake extends VerticalExtender {

    private DoubleSolenoid intakePiston;
    private DoubleSolenoid releasePiston;
    private Compressor compressor;

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        super("Intake");
        this.motor1 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_1);
        this.motor2 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_2);
        //this.bottomSwitch = new DigitalInput(RobotMap.INTAKE_LOWER_LIMIT_SWITCH);
        //this.topSwitch = new DigitalInput(RobotMap.INTAKE_UPPER_LIMIT_SWITCH);

        //this.configureMotors();
        this.motor1.setNeutralMode(NeutralMode.Brake);
        this.motor2.setNeutralMode(NeutralMode.Brake);

        this.compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
        //this.intakePiston = new DoubleSolenoid(RobotMap.PCM_PORT,
        //        RobotMap.GRABBER_SOLENOID_FORWARD_PORT, RobotMap.GRABBER_SOLENOID_BACKWARD_PORT);
        //this.releasePiston = new DoubleSolenoid(RobotMap.PCM_PORT,
        //        RobotMap.RELEASE_SOLENOID_FORWARD_PORT, RobotMap.RELEASE_SOLENOID_BACKWARD_PORT);
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

    public void retainCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void releaseCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void neutralizePiston() {
        this.intakePiston.set(DoubleSolenoid.Value.kOff);
    }

    public void startCompressor() {
        this.compressor.start();
    }

    public void stopCompressor() {
        this.compressor.stop();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeCommand());
    }
}
