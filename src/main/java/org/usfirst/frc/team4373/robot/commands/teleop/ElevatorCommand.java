package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Elevator;

public class ElevatorCommand extends Command {

    private Elevator elevator;

    public ElevatorCommand() {
        requires(this.elevator = Elevator.getInstance());
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double axis = OI.getOI().getOperatorJoystick().getAxis(RobotMap.ELEVATOR_AXIS);
        if (Math.abs(axis) > RobotMap.THUMBSTICK_THRESHOLD) {
            this.elevator.set(Math.signum(axis) * RobotMap.ELEVATOR_SPEED);
        }

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
