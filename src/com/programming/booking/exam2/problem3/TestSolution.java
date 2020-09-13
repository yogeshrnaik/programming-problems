package com.programming.booking.exam2.problem3;

import org.apache.commons.collections.ArrayStack;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestSolution {

    @Test
    public void testSample() {
        String review = "This hotel has a nice view of the citycenter. The location is perfect.";
        System.out.println(review.replaceAll("\\.", "").replaceAll(",", ""));
    }

    @Test
    public void test() {
        List<Integer> hotelIds =  Arrays.asList(1, 2, 1, 1, 2);
        List<String> reviews = Arrays.asList("This hotel has a nice view of the citycenter. The location is perfect.",
                "The breakfast is ok. Regarding location, it is quite far from citycenter but price is cheap so it is worth.",
                "Location is excellent, 5 minutes from citycenter. There is also a metro station very close to the hotel.",
                "They said I couldn't take my dog and there were other guests with dogs! That is not fair.",
                "Very friendly staff and good cost-benefit ratio. Its location is a bit far from citycenter.");
        List<Integer> result = Result.awardTopKHotels("breakfast beach citycenter location metro view staff price", "not",
                hotelIds, reviews, 2);
        System.out.println(result);
    }

    @Test
    public void test2_failing() {
        List<Integer> hotelIds =  Arrays.asList(1, 2, 1, 1, 2);
        List<String> reviews = Arrays.asList("This hotel has a nice view of the citycenter. The location is perfect.",
                "The breakfast is ok. Regarding location, it is quite far from citycenter but price is cheap so it is worth.",
                "Location is excellent, 5 minutes from citycenter. There is also a metro station very close to the hotel.",
                "Good price but I couldn't take my dog and there were other guests with dogs!",
                "Very friendly staff and good cost-benefit ratio. Its location is a bit far from citycenter.");
        List<Integer> result = Result.awardTopKHotels("breakfast beach citycenter location metro view staff price", "not",
                hotelIds, reviews, 2);
        System.out.println(result);
    }
}
