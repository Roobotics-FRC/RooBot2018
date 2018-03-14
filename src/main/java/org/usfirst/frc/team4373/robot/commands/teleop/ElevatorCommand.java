package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.Motors;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;

/**
 * This command lets the operator joystick control the elevator VerticalExtender.
 *
 * @author aaplmath
 */
public class ElevatorCommand extends Command {

    private Elevator elevator;

    /**
     * Main elevator command.
     */
    public ElevatorCommand() {
        requires(this.elevator = Elevator.getInstance());
        this.elevator.enableUpwardsLocking();
        setInterruptible(true);
    }

    /**
     * Initialize.
     */
    @Override
    protected void initialize() {
    }

    /**
     * execute method.
     */
    @Override
    protected void execute() {
        double axis = OI.getOI().getOperatorJoystick().getAxis(RobotMap.ELEVATOR_AXIS);
        if (Math.abs(axis) > RobotMap.THUMBSTICK_THRESHOLD) {
            this.elevator.set(Math.signum(axis) * -RobotMap.ELEVATOR_SPEED);
        } else {
            this.elevator.set(0);
        }
        // Logging
        SmartDashboard.putNumber("Elevator Rel Pos", elevator.getRelativePosition());
        SmartDashboard.putNumber("Elevator Abs Pos", elevator.getPosition());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.elevator.set(0);
    }

    @Override
    protected void interrupted() {
        this.elevator.set(0);
    }
}
