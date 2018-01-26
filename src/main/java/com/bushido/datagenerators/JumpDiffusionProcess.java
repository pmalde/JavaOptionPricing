package com.bushido.datagenerators;

import com.bushido.ModelParameters;

import java.util.Random;

public class JumpDiffusionProcess extends GeometricBrownianMotion {

    public double[] generateLogReturns(ModelParameters parameters){

        //Initialise an array of doubles to store the returns
        double[] returnsArray = super.generateLogReturns(parameters);

        //Compute an array of jumps
        double[] jumps = new double[returnsArray.length];

        for (int i=0; i < jumps.length; i++) {

        }

        return returnsArray;
    }

    public double[] convertToPrices(double[] logReturns, ModelParameters parameters) {
        double[] returns = new double[logReturns.length];
        double[] prices = new double[logReturns.length];
        return prices;
    }
}
