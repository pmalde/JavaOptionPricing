package com.bushido;

public class JumpDiffusionModelParameters extends ModelParameters {

    //Average number of jumps per year
    private double lambda;

    //Average jump size as a percentage of the asset price
    private double avgJumpSize;

    //jump std dev
    private double jumpSigma;

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getAvgJumpSize() {
        return avgJumpSize;
    }

    public void setAvgJumpSize(double avgJumpSize) {
        this.avgJumpSize = avgJumpSize;
    }

    public double getJumpSigma() {
        return jumpSigma;
    }

    public void setJumpSigma(double jumpSigma) {
        this.jumpSigma = jumpSigma;
    }
}
