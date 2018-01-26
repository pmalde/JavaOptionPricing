package com.bushido;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class PriceAsianOption implements Runnable {

    private final CountDownLatch latch;
    private final ConcurrentMap<String, Double> results;
    private final double T, r, vol, dt, S0, K;
    private final int sims, threads;

    public PriceAsianOption(double T,
                            double r,
                            double vol,
                            double dt,
                            double S0,
                            double K,
                            int threadCount,
                            int simsPrThread) {

        results = new ConcurrentHashMap<>();

        //Setup new Latch
        latch = new CountDownLatch(threadCount);
        this.threads = threadCount;

        // Global read vars
        this.T = T;
        this.r = r;
        this.vol = vol;
        this.dt = dt;
        this.S0 = S0;
        this.K = K;
        this.sims = simsPrThread;
    }

    /**
     * Calculate the price of an asian option using
     * multi threaded Monte Carlo simulations
     *
     * @return Asian option price
     */
    public double getPrice() throws InterruptedException {
        long start = System.nanoTime();

        //For each thread start this process
        for (int i = 0; i < threads; i++) {
            new Thread(this).start();
        }

        latch.await();
        long end = System.nanoTime();

        System.out.println("Run time in milli seconds: " + (end-start));

        DecimalFormat formatter = new DecimalFormat("#.######");
        double result = 0.0;

        for (String name : results.keySet()) {
            System.out.println(name + ": " + formatter.format(results.get(name)));
            result += results.get(name);
        }

        return result/threads;
    }

    //This is where the monte carlo sim runs
    public void run() {
        Random random = new Random();

        // For the purpose of being efficient, let's define our constants
        // outside the main loop
        double a = (r - (0.5*Math.pow(vol, 2)))*dt;
        double b = vol*Math.sqrt(dt);
        double result = 0.0;
        for (int i = 0; i < sims; i++) {
            double avgPrice = 0.0;
            int steps = 0;
            double lastPrice = S0;
            for (double t = dt; t <= T; t += dt) {
                // This is our Geometric Brownian motion..
                lastPrice = lastPrice*Math.exp(a
                        + (b*random.nextGaussian()));
                avgPrice += lastPrice;
                steps++;
            }
            avgPrice = avgPrice/steps; // This is the average stock price
            result += Math.max(0, avgPrice-K);
        }
        result = result/sims; // This is the mean forward option price
        result = result*Math.exp(-r*T); // Now it's discounted..

        results.put(Thread.currentThread().getName(), result);

        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        double T = 1.0; // 1 year terminal time
        double r = 0.02; // 2% annual risk free rate
        double vol = 0.4; // 40% annual volatility
        double dt = 1.0/250.0; // Time step of one day (given 250 trading days)
        double S0 = 100.0; // Initial stock price
        double K = 100.0; // Option strike price

        // Define number of concurrent simulations
        // and number of simulation pr thread.
        int threadCount = Runtime.getRuntime().availableProcessors();
        int simsPrThread = 100000;

        System.out.print("Running " + threadCount + " threads ");
        System.out.println("with " + simsPrThread + " sims in each thread..");

        DecimalFormat formatter = new DecimalFormat("#.######");
        PriceAsianOption pao = new PriceAsianOption(T, r, vol, dt, S0, K, threadCount, simsPrThread);
        System.out.println("Option price: " + formatter.format(pao.getPrice()));
    }
}
