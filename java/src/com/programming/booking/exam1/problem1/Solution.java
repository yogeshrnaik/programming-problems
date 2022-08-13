package com.programming.booking.exam1.problem1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {

    static private class Hotel {

        private int id;
        private List<Integer> scores;

        public Hotel(int id, int score) {
            super();
            scores = new ArrayList<>();
            scores.add(score);
            this.id = id;
        }

        public void addScore(int s) {
            this.scores.add(s);
        }

        private double getAvg() {
            return scores.stream().mapToDouble(s -> new Double(s)).average().orElse(0.0);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Hotel other = (Hotel)obj;
            if (id != other.id)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Hotel [id=" + id + ", scores=" + scores + ", avgScore=" + getAvg() + "]";
        }

    }

    // Complete the best_hotels function below.
    static void best_hotels() {
        Map<Integer, Hotel> hotels = new HashMap<>();

        try (Scanner s = new Scanner(System.in)) {
            int hotelCount = s.nextInt();

            for (int h = 1; h <= hotelCount; h++) {
                int hotelId = s.nextInt();
                int score = s.nextInt();

                Hotel hotel = hotels.get(hotelId);
                if (hotel == null) {
                    hotels.put(hotelId, new Hotel(hotelId, score));
                } else {
                    hotel.addScore(score);
                }
            }
        }

        // take the max scored hotels
        List<Hotel> hotelValues = new ArrayList<>(hotels.values());
        Collections.sort(hotelValues, (h1, h2) -> {
            return Double.compare(h2.getAvg(), h1.getAvg()) * 10 + Integer.compare(h1.id, h2.id);
        });

        for (int i = 0; i < hotelValues.size(); i++) {
            System.out.println(hotelValues.get(i).id);
        }
    }

    public static void main(String[] args) {

        best_hotels();

    }
}
