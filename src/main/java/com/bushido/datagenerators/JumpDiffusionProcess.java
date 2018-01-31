package com.bushido.datagenerators;

import com.bushido.JumpDiffusionModelParameters;
import com.bushido.ModelParameters;
import org.apache.commons.math3.distribution.LogNormalDistribution;

public class JumpDiffusionProcess extends GeometricBrownianMotion {

    public double[] generateLogReturns(ModelParameters parameters){

        //parameters = (JumpDiffusionModelParameters) parameters;

        //Initialise an array of doubles to store the returns
        double[] returnsArray = super.generateLogReturns(parameters);

        //Compute an array of jumps
        double jump = nextJump(((JumpDiffusionModelParameters) parameters).getLambda());
        double[] jumps = new double[returnsArray.length];
        LogNormalDistribution lnd = new LogNormalDistribution(((JumpDiffusionModelParameters) parameters).getAvgJumpSize(), ((JumpDiffusionModelParameters) parameters).getJumpSigma());
        for (int i=0; i < jumps.length; i++) {
            if (i >= jump) {
                jumps[i] = (lnd.sample() - 1);
                jump = nextJump(((JumpDiffusionModelParameters) parameters).getLambda());
            }
        }

        for (int i=0; i < jumps.length; i++) {
            returnsArray[i] = returnsArray[i] + jumps[i];
        }

        return returnsArray;
    }

    private double nextJump(double rateParameter) {
        return -Math.log(1.0 - Math.random()) /rateParameter;
    }
}
