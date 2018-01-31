package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;
import org.usfirst.frc.team4373.robot.subsystems.Intake;

/**
 * The default command to handle all subsystems in 'debug mode'
 *
 * <p>This command requires all subsystems, and sets them to values from the SmartDashboard.
 *
 * @author Samasaur
 */
public class DefaultCommand extends Command {
    private static DefaultCommand instance;

    public static DefaultCommand getInstance() {
        return instance == null ? instance = new DefaultCommand() : instance;
    }

    private static Drivetrain drivetrain;
    private static Elevator elevator;
    private static Intake intake;

    private DefaultCommand() {
        super("Default Command");
        requires(this.drivetrain = Drivetrain.getInstance());
        requires(this.elevator = Elevator.getInstance());
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        drivetrain.setAll(0);
        elevator.set(0);
        intake.set(0);
        intake.retainCube();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        drivetrain.setLeft1(SmartDashboard.getNumber("Left 1 Power", 0));
        drivetrain.setLeft2(SmartDashboard.getNumber("Left 2 Power", 0));
        drivetrain.setRight1(SmartDashboard.getNumber("Right 1 Power", 0));
        drivetrain.setRight2(SmartDashboard.getNumber("Right 2 Power", 0));
        drivetrain.setMiddle(SmartDashboard.getNumber("Middle Power", 0));
        SmartDashboard.putNumber("L Encoder Pos", drivetrain.getLeftPosition());
        SmartDashboard.putNumber("L Encoder Vel", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("R Encoder Pos", drivetrain.getRightPosition());
        SmartDashboard.putNumber("R Encoder Vel", drivetrain.getRightVelocity());
        SmartDashboard.putNumber("M Encoder Pos", drivetrain.getMiddlePosition());
        SmartDashboard.putNumber("M Encoder Vel", drivetrain.getMiddleVelocity());

        elevator.set(SmartDashboard.getNumber("Elevator Motor Power", 0));
        SmartDashboard.putNumber("Elevator Pos", elevator.getPosition());
        SmartDashboard.putNumber("Elevator Vel", elevator.getVelocity());
        SmartDashboard.putBoolean("Elevator At Top", elevator.atTop());
        SmartDashboard.putBoolean("Elevator At Bottom", elevator.atBottom());

        intake.set(SmartDashboard.getNumber("Intake Motor Power", 0));
        switch (SmartDashboard.getData("Intake State").getName()) {
            case "On":
                intake.releaseCube();
                break;
            case "Off":
                intake.retainCube();
                break;
            case "Neutral":
                intake.setNeutral();
                break;
            default:
                intake.retainCube();
        }
        SmartDashboard.putNumber("Intake Pos", intake.getPosition());
        SmartDashboard.putNumber("Intake Vel", intake.getVelocity());
        SmartDashboard.putBoolean("Intake At Top", intake.atTop());
        SmartDashboard.putBoolean("Intake At Bottom", intake.atBottom());

        SmartDashboard.putNumber("Gyro Heading", OI.getOI().getAngleRelative());
    }
}
