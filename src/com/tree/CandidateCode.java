package com.tree;
import java.io.*;
import java.util.*;

public class CandidateCode
{
    private static Map<String, Character> allCharacters = null;

    private static class Character implements Comparable {
        char value;
        int frequency;
        String binaryValue;

        Character(char ch) {
            this.value = ch;
            this.frequency = 0;
            this.binaryValue = "";
        }

        @Override
        public int compareTo(Object o) {
            Character ch = (Character)o;
            if (this.frequency != ch.frequency) {
                return ch.frequency - this.frequency;
            }
            return value - ch.value;
        }

        @Override
        public String toString() {
            return String.valueOf(value) + " -> Freq: " + frequency + ", binary: " + binaryValue;
        }
    };

    public static String constructTree(String input1)
    {
        String chars = input1;
        if (chars == null || chars.length() == 0) {
            return "0";
        }

        allCharacters = new LinkedHashMap<String, CandidateCode.Character>();

        parseInputAndCreateDataStructure(chars);

        String result = getBinaryValueForSequence(chars);

        return result;
    }



    private static void parseInputAndCreateDataStructure(String chars) {
        for (char ch : chars.toCharArray()) {
            Character character = getCharacter(ch);
        }

        List<Character> allChars = new ArrayList<Character>(allCharacters.values());
        Collections.sort(allChars);

        // put the sorted values back into the map
        allCharacters.clear();

        int index= -1;
        for (Character c : allChars) {
            index++;
            if (index == 0) {
                c.binaryValue = "0";
            }
            else {
                for (int i=1; i <= index; i++) {
                    c.binaryValue += "1";
                }
                if (index < allChars.size() - 1) {
                    c.binaryValue += "0";
                }
            }
            allCharacters.put(String.valueOf(c.value), c);
        }
    }

    private static String getBinaryValueForSequence(String chars) {
        StringBuilder result = new StringBuilder();

        for (char c : chars.toCharArray()) {
            result.append(allCharacters.get(String.valueOf(c)).binaryValue);
        }

        return result.toString();
    }

    private static Character getCharacter(char ch) {
        Character character = allCharacters.get(String.valueOf(ch));
        if (character == null) {
            character = new Character(ch);
            allCharacters.put(String.valueOf(ch), character);
        }
        character.frequency++;
        return character;
    }

}