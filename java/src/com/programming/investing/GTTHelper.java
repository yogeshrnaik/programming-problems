package com.programming.investing;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class GTTHelper {
    public static void main(String[] args) {
        List<GTT> orders = getGTTOrders(1000, 4, 1.0);
        int totalQuantity = orders.stream().map(it -> it.quantity).collect(Collectors.summingInt(Integer::intValue));
        System.out.println("totalQuantity: " + totalQuantity);
        System.out.println(orders);
    }

    private static List<GTT> getGTTOrders(int amountToInvest, int noOfPartialInvestments, double rateOfChangePercentage) {
        return Fibonacci.getQuantities(amountToInvest, noOfPartialInvestments)
                .stream()
                .map(it -> new GTT(it, 0.0)).collect(Collectors.toList());
    }
}

class GTT {
    double price;
    int quantity;

    public GTT(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "GTT{" +
                "price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}


class Fibonacci {
    public static List<Integer> getQuantities(int amountToInvest, int noOfPartialInvestments) {
        Queue<Integer> quantities = new LinkedList<>();

        int currSum = 0;
        int first = 0;
        int second = 1;
        int minDiff = Integer.MAX_VALUE;
        while (currSum < amountToInvest) {
            if (noOfPartialInvestments == quantities.size()) {
                // remove from head i.e. remove oldest
                currSum -= quantities.poll();
            }

            currSum += first;
            quantities.offer(first);

            int diff = Math.abs(amountToInvest - currSum);
            if (minDiff > diff) {
                int next = getNextFib(first, second);
                first = second;
                second = next;
                System.out.print(first + " ");

                minDiff = diff;
                System.out.println("currSum: " + currSum + ", minDiff: " + minDiff);
            }
        }
        System.out.println();
        return quantities.stream().collect(Collectors.toList());
    }

    private static int getNextFib(int prevToPrev, int prev) {
        return prevToPrev + prev;
    }
}