package com.bushido.datagenerators;

import com.bushido.JumpDiffusionModelParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JumpDiffusionProcessTest {

    private JumpDiffusionModelParameters modelParameters;
    @BeforeEach
    void setUp() {
        modelParameters = new JumpDiffusionModelParameters();
        modelParameters.setInitialPrice(100.0);
        modelParameters.setDrift(0.05);
        modelParameters.setVolatility(0.2);
        modelParameters.setLambda(0.0156);
        modelParameters.setAvgJumpSize(0.0075);
        modelParameters.setJumpSigma(0.05);
        modelParameters.setTime(252);
        modelParameters.setTimeDelta(1.0);
    }

    @Test
    void generateLogReturns() {
        JumpDiffusionProcess jdp = new JumpDiffusionProcess();
        double[] returns = jdp.generateLogReturns(modelParameters);
        System.out.println(returns);
    }
}