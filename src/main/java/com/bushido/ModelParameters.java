package com.bushido;

public class ModelParameters {

    private double initialPrice;
    private int time;
    private double timeDelta;
    private double volatility;
    private double interestRate;
    private double drift;

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getTimeDelta() {
        return timeDelta;
    }

    public void setTimeDelta(double timeDelta) {
        this.timeDelta = timeDelta;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getDrift() {
        return drift;
    }

    public void setDrift(double drift) {
        this.drift = drift;
    }
}
