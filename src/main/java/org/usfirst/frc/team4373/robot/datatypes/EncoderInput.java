package org.usfirst.frc.team4373.robot.datatypes;

/**
 * Created by derros on 3/10/18.
 */
public abstract class EncoderInput {

    double val;

    public EncoderInput(double val) {
        this.val = val;
    }

    public void set (double val) {
        this.val = val;
    }

    public double get(double val) {
        return this.val;
    }

    public abstract double toInches();
    public abstract double fromInches(double val);
}
