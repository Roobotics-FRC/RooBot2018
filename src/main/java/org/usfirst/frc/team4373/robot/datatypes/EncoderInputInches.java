package org.usfirst.frc.team4373.robot.datatypes;

/**
 * Created by derros on 3/10/18.
 */
public class EncoderInputInches extends EncoderInput {

    public EncoderInputInches(double val) {
        super (val);
    }
    @Override
    public double toInches() {
        return val;
    }

    @Override
    public double fromInches(double val) {
        return val;
    }
}
