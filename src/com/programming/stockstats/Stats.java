package com.programming.stockstats;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Stats {

    public static class StatisticsAggregatorImpl implements StatisticsAggregator {

        private static final class StockStat {

            private final String symbol;
            private double avgPrice;
            private int tickCount;

            public StockStat(String symbol) {
                this.symbol = symbol;
                avgPrice = 0.0;
                tickCount = 0;
            }

            public StockStat(String symbol, double avgPrice, int tickCount) {
                this.symbol = symbol;
                this.avgPrice = avgPrice;
                this.tickCount = tickCount;
            }

            public synchronized StockStat putNewPrice(double price) {
                avgPrice = ((avgPrice * tickCount) + price) / (tickCount + 1);
                tickCount++;
                return this;
            }

            public StockStat merge(StockStat other) {
                double otherTotal = other.tickCount * other.avgPrice;
                double thisTotal = tickCount * avgPrice;

                int newTickCount = this.tickCount + other.tickCount;
                double newAvgPrice = newTickCount == 0 ? 0.0 : (thisTotal + otherTotal) / (this.tickCount + other.tickCount);
                return new StockStat(this.symbol, newAvgPrice, newTickCount);
            }
        }

        private final Map<String, StockStat> stockStats = new HashMap<>();

        @Override
        public void putNewPrice(String symbol, double price) {
            // YOUR CODE HERE
            synchronized (stockStats) {
                if (stockStats.containsKey(symbol)) {
                    StockStat stockStat = stockStats.get(symbol);
                    stockStat.putNewPrice(price);
                } else {
                    stockStats.put(symbol, new StockStat(symbol, price, 1));
                }
            }
        }

        @Override
        public double getAveragePrice(String symbol) {
            // YOUR CODE HERE
            return stockStats.getOrDefault(symbol, new StockStat(symbol)).avgPrice;
        }

        @Override
        public int getTickCount(String symbol) {
            // YOUR CODE HERE
            return stockStats.getOrDefault(symbol, new StockStat(symbol)).tickCount;
        }
    }

    ////////////////// DO NOT MODIFY BELOW THIS LINE ///////////////////

    public interface StatisticsAggregator {

        // This is an input. Make note of this price.
        public void putNewPrice(String symbol, double price);

        // Get the average price
        public double getAveragePrice(String symbol);

        // Get the total number of prices recorded
        public int getTickCount(String symbol);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final StatisticsAggregator stats = new StatisticsAggregatorImpl();
            final Set<String> symbols = new TreeSet<>();

            String line = scanner.nextLine();
            String[] inputs = line.split(",");
            int threads = Integer.parseInt(inputs[0]);
            ExecutorService pool = Executors.newFixedThreadPool(threads);
            for (int i = 1; i < inputs.length; ++i) {
                String[] tokens = inputs[i].split(" ");
                final String symbol = tokens[0];
                symbols.add(symbol);
                final double price = Double.parseDouble(tokens[1]);
                pool.submit(new Runnable() {

                    @Override
                    public void run() {
                        stats.putNewPrice(symbol, price);
                    }
                });

            }
            pool.shutdown();
            try {
                pool.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (String symbol : symbols) {
                System.out.println(String.format("%s %.4f %d", symbol,
                    stats.getAveragePrice(symbol),
                    stats.getTickCount(symbol)));
            }
        }
        scanner.close();

    }
}