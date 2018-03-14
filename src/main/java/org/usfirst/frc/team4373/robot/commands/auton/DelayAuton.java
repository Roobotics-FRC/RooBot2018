package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

public class DelayAuton extends Command {
    /**
     * Delay for time.
     * @param seconds time amount.
     */
    public DelayAuton(double seconds) {
        setTimeout(seconds);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
