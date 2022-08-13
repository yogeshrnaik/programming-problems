package com.programming.shortestpath;

import java.util.Arrays;

import org.junit.Test;

public class TestQuickestMetroRoute {

    public static final String[] SAMPLE_INPUT = {"1-2-30#2-3-25#3-4-30#4-5-45#5-6-30#6-7-15#7-8-60#8-9-40",
        "10-11-45#11-4-60#4-12-60#12-13-45#13-14-30#14-15-35",
        "1-3-40#3-4-25#4-16-30#16-17-15#17-18-20#18-19-30#19-20-25",
        "21-12-30#12-17-180#17-22-45"};

    @Test
    public void testSampleInput() {
        String[] metroLines = SAMPLE_INPUT;
        String pathToFind = "12#18";
        String[] result = CandidateCode.quickestroute(metroLines, pathToFind);

        String[] expected = {"NC", "12-4-60", "4-16-30#16-17-15#17-18-20", "NC"};

        assert Arrays.equals(expected, result);

        System.out.println(result);

    }

    @Test
    public void testOneStop1() {
        String[] metroLines = SAMPLE_INPUT;
        String pathToFind = "10#11";
        String[] result = CandidateCode.quickestroute(metroLines, pathToFind);
        String[] expected = new String[] {"NC", "10-11-45", "NC"};
        assert Arrays.equals(expected, result);
    }

    @Test
    public void testOneStop2() {
        String[] metroLines = SAMPLE_INPUT;
        String pathToFind = "11#10";
        String[] result = CandidateCode.quickestroute(metroLines, pathToFind);
        String[] expected = new String[] {"NC", "11-10-45", "NC"};
        assert Arrays.equals(expected, result);
    }

    @Test
    public void testOneStop3() {
        String[] metroLines = SAMPLE_INPUT;
        String pathToFind = "4#16";
        String[] result = CandidateCode.quickestroute(metroLines, pathToFind);
        String[] expected = new String[] {"NC", "4-16-30", "NC"};
        assert Arrays.equals(expected, result);
    }

    @Test
    public void testDestinationOnSameLineWithoutTransfer() {
        String[] metroLines = SAMPLE_INPUT;
        String pathToFind = "12#15";
        String[] result = CandidateCode.quickestroute(metroLines, pathToFind);
        String[] expected = new String[] {"NC", "12-13-45#13-14-30#14-15-35", "NC"};
        assert Arrays.equals(expected, result);
    }

    @Test
    public void testMetroLineWithSameStartAndEnd() {
        String[] metroLines = {"1-2-30#2-3-25#3-4-30#4-5-45#5-1-15",
            "1-6-45#6-7-60#7-8-60",
            "8-9-40#9-10-25",
            "1-11-30#11-10-180"};
        String pathToFind = "5#10";
        String[] result = CandidateCode.quickestroute(metroLines, pathToFind);
        String[] expected = new String[] {"NC", "12-13-45#13-14-30#14-15-35", "NC"};
        assert Arrays.equals(expected, result);
    }
}
