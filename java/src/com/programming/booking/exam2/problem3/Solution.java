package com.programming.booking.exam2.problem3;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result {

    /*
     * Complete the 'awardTopKHotels' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. STRING positiveKeywords
     *  2. STRING negativeKeywords
     *  3. INTEGER_ARRAY hotelIds
     *  4. STRING_ARRAY reviews
     *  5. INTEGER k
     */

    static class Hotel implements Comparable<Hotel> {
        int id;
        int score;
        public Hotel(int id, int score) {
            this.id = id;
            this.score = score;
        }
        public void addToScore(int score) {
            this.score += score;
        }

        @Override
        public int compareTo(Hotel o) {
            if (this.score == o.score) {
                return o.id - this.id;
            }
            return o.score - this.score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Hotel hotel = (Hotel) o;
            return id == hotel.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public static List<Integer> awardTopKHotels(String positiveKeywords, String negativeKeywords, List<Integer> hotelIds, List<String> reviews, int k) {
        // Write your code here
        Map<Integer, Hotel> hotels = new HashMap<>();

        Map<String, String> positive = generateKeywordsMap(positiveKeywords);
        Map<String, String> negative = generateKeywordsMap(negativeKeywords);

        for (int i=0; i < hotelIds.size(); i++) {
            int id = hotelIds.get(i);
            Hotel hotel = hotels.get(id);
            if (hotel == null) {
                hotel = new Hotel(id, 0);
                hotels.put(id, hotel);
            }
            int score = calcScore(positive, negative, reviews.get(i));
            hotel.addToScore(score);
        }

        List<Hotel> hotelsList = new ArrayList<>(hotels.values());
        Collections.sort(hotelsList);

        if (k > hotelsList.size()) {
            k = hotelsList.size();
        }

        List<Integer> result = new ArrayList<>();
        for (int i=0; i<k; i++) {
            result.add(hotelsList.get(i).id);
        }
        return result;
    }

    private static Map<String, String> generateKeywordsMap(String keywords) {
        Map<String, String> result = new HashMap<>();
        String[] keys = keywords.split(" ");
        for (String key : keys) {
            result.put(key.toLowerCase(), key.toLowerCase());
        }
        return result;
    }

    private static int calcScore(Map<String, String> positive, Map<String, String> negative, String review) {

        review = review.replaceAll("\\.", "").replaceAll(",", "");

        int score = 0;
        String[] reviewWords = review.split(" ");
        for (String word : reviewWords) {
            if (positive.containsKey(word)) {
                score += 3;
            } else if (negative.containsKey(word)) {
                score -= 1;
            }
        }
        return score;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String positiveKeywords = bufferedReader.readLine();

        String negativeKeywords = bufferedReader.readLine();

        int hotelIdsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> hotelIds = IntStream.range(0, hotelIdsCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int reviewsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> reviews = IntStream.range(0, reviewsCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .collect(toList());

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> result = Result.awardTopKHotels(positiveKeywords, negativeKeywords, hotelIds, reviews, k);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
