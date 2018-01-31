package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.IntakeCommand;

/**
 * A programmatic representation of the intake VerticalExtender. This is the outermost,
 * (supposedly) faster one.
 *
 * @author aaplmath
 */
public class Intake extends VerticalExtender {

    private DoubleSolenoid intakePiston;
    private Compressor compressor;

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        super("Intake");
        this.motor = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR);
        this.bottomSwitch = new DigitalInput(RobotMap.INTAKE_LOWER_LIMIT_SWITCH);
        this.topSwitch = new DigitalInput(RobotMap.INTAKE_UPPER_LIMIT_SWITCH);

        this.configureMotor();

        this.compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
        this.intakePiston = new DoubleSolenoid(RobotMap.PCM_PORT, RobotMap.SOLENOID_BACKWARD_PORT,
                RobotMap.SOLENOID_FORWARD_PORT);
    }

    public void retainCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void releaseCube() {
        this.intakePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void setNeutral() {
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
