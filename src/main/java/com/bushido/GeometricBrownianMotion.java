package com.bushido;

import java.util.Random;

public class GeometricBrownianMotion {

    public static double[] generateLogReturns(ModelParameters parameters){

        //Initialise an array of doubles to store the returns
        double[] returnsArray = new double[parameters.getTime()];

        //Generate an array of random numbers
        double sqrtDeltaTSigma = Math.sqrt(parameters.getTimeDelta()) * parameters.getVolatility();

        Random rng = new Random();
        for (int i = 0; i< parameters.getTime(); i++) {
            double wienerProcess = rng.nextGaussian() * sqrtDeltaTSigma;
            double deterministicProcess = (parameters.getInterestRate() - (0.5 * Math.pow(parameters.getVolatility(), 2))) * parameters.getTimeDelta();
            returnsArray[i] = deterministicProcess + wienerProcess;
        }

        return returnsArray;
    }

    public static double[] convertToPrices(double[] logReturns, ModelParameters parameters) {
        double[] returns = new double[logReturns.length];
        for (int i = 0; i< logReturns.length; i++) {
            returns[i] = Math.exp(logReturns[i]);
        }

        double[] prices = new double[logReturns.length];
        prices[0] = parameters.getInitialPrice();
        for (int i = 1; i< logReturns.length; i++) {
            prices[i] = prices[i-1] * returns[i-1];
        }

        return prices;
    }

    public static void main(String[] args) {
        ModelParameters parameters = new ModelParameters();
        parameters.setInitialPrice(100.0);
        parameters.setTime(252);
        parameters.setTimeDelta(1d/252d);
        parameters.setVolatility(0.2);

        double[] results  = generateLogReturns(parameters);
        double[] prices = convertToPrices(results, parameters);
        System.exit(0);
    }
}
