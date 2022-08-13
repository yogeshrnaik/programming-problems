package com.programming.stone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CandidateCode {

    private static List<Pot> allPots = new ArrayList<Pot>();
    private static int totalStonesFinalResult = 0;
    private static int noOfPotsOverflown = 0;

    private static class Pot {

        final int potSize;
        int noOfStonesInPot;
        Pot largestPotLessThanMe;
        List<Integer> possiblePots = new ArrayList<Integer>();

        boolean isPotSizeKnown() {
            return possiblePots.size() == 1;
        }

        public boolean isOverflown() {
            return potSize == noOfStonesInPot;
        }

        public Pot(int potSize) {
            this.potSize = potSize;
        }

        @Override
        public String toString() {
            return "Size = " + potSize + ",noOfStonesInPot = " + noOfStonesInPot + ", largestPotLessThanMe: "
                + (largestPotLessThanMe != null ? largestPotLessThanMe.potSize : null)
                + ", Possible pots: " + possiblePots.toString() + "";
        }
    }

    public static int ThirstyCrowProblem(int[] input1, int input2, int input3) {
        // reset values
        totalStonesFinalResult = 0;
        noOfPotsOverflown = 0;
        allPots.clear();

        int pots[] = input1;
        int totalPots = input2;
        int potsToOverflow = input3;

        if (pots == null || pots.length == 0 || pots.length != totalPots || totalPots <= 0 || potsToOverflow <= 0 || invalidOValues(pots)) {
            return -1;
        }

        // in case only 1 pot is there
        if (totalPots == 1 && potsToOverflow == 1) {
            return pots[0];
        }

        // sort array of pot sizes
        Arrays.sort(pots);

        // if all pots are same size
        if (pots[0] == pots[pots.length - 1]) {
            return potsToOverflow * pots[0];
        }

        // in case all pots to overflow
        if (totalPots == potsToOverflow) {
            int result = 0;
            for (int pot : pots) {
                result += pot;
            }
            return result;
        }

        // in case two pots are there and one pot to overflow
        if (totalPots == 2 && potsToOverflow == 1) {
            if ((pots[1] - pots[0]) <= pots[0]) {
                return pots[1];
            } else {
                return 2 * pots[0];
            }
        }

        parseInputAndCreateDataStructure(pots, totalPots, potsToOverflow);

        // **************************************************************
        // while there are pots remaining to overflow
        // take the smallest pot not yet overflown
        // starting point:
        // if all pots know their size
        // -- for each underflown pot, calculate the number of stones required to overflow that pot
        // -- and take the lowest number and add to total stones used
        // else
        // -- put stones = smallest underflown pot size - stones already added in that pot,
        // -- into largest pot not overflown yet in which possible pot list >= 2
        // **************************************************************
        solve(potsToOverflow);

        // logic

        // put stonesToAdd stones in current pot
        // currentPot.stoneCount += stonesToAdd
        // if currentPot.overflew {
        // -- increment no of pot overflow
        // -- truncate the possible pots list
        // -- remove this pot reference from all other pot's possible pot list
        // -- return
        // }
        // else pot not overflown
        // then remove all pot sizes from currentPot's possible pot size list where totalStonesInCurrentPot >= possiblePotSize

        // calculate diff = smallest possible pot - no of stones in current pot
        // --- if the difference is > (no of possible pots * stones required to overflow smallest pot not yet overflown)
        // ------ recursive call - put stones = smallest not overflown pot's size in largest pot < currentPot
        // ---- else if no of stones required to overflow the currentPot <= diff
        // ------- put the stones required to overflow in currentPot
        // -------- else put the diff in the same pot

        // **************************************************************

        return totalStonesFinalResult;
    }

    private static boolean invalidOValues(int[] pots) {
        for (int p : pots) {
            if (p < 0) {
                return true;
            }
        }
        return false;
    }

    private static void solve(int potsToOverflow) {
        // **************************************************************
        // while there are pots remaining to overflow
        while (noOfPotsOverflown != potsToOverflow) {
            // take the smallest pot not yet overflown
            Pot smallestPotNotYetOverflown = findSmallestPotNotYetOverflown();

            // starting point:
            // if all pots know their size
            if (checkIfAllPotsKnowTheirSize()) {
                handleAllPotsKnowTheirSize();

            } else {
                // else
                // -- put stones = smallest underflown pot size - stones already added in that pot,
                int stonesToAdd = smallestPotNotYetOverflown.potSize - smallestPotNotYetOverflown.noOfStonesInPot;
                // -- into largest pot not overflown yet in which possible pot list >= 2
                Pot potToAddStoneTo = findLargestNotYetOverflownPotWithPossiblePotListMoreThanEqualTo2();
                putStones(potToAddStoneTo, stonesToAdd);
            }
        }
        // **************************************************************
    }

    private static void handleAllPotsKnowTheirSize() {
        // -- for each underflown pot, calculate the number of stones required to overflow that pot
        // -- and take the lowest number and add that many stones to the pot and to the total stones used
        int lowestStonesToAdd = Integer.MAX_VALUE;
        Pot nextPotToAddStoneTo = null;
        for (Pot potNotYetOverflown : allPots) {
            if (!potNotYetOverflown.isOverflown()) {
                int stonesRequiredForCurrentPotToOverflow = potNotYetOverflown.potSize - potNotYetOverflown.noOfStonesInPot;
                if (nextPotToAddStoneTo == null || stonesRequiredForCurrentPotToOverflow < lowestStonesToAdd) {
                    nextPotToAddStoneTo = potNotYetOverflown;
                    lowestStonesToAdd = stonesRequiredForCurrentPotToOverflow;
                }
            }
        }
        // add stoes to the pot and make it overflow
        nextPotToAddStoneTo.noOfStonesInPot += lowestStonesToAdd;
        totalStonesFinalResult += lowestStonesToAdd;
        noOfPotsOverflown++;
    }

    private static Pot findLargestNotYetOverflownPotWithPossiblePotListMoreThanEqualTo2() {
        for (int i = allPots.size() - 1; i > 0; i--) {
            Pot p = allPots.get(i);
            if (!p.isOverflown() && p.possiblePots.size() >= 2) {
                return p;
            }
        }
        return null;
    }

    private static void putStones(Pot currentPot, int stonesToAdd) {
        // put stonesToAdd stones in current pot
        currentPot.noOfStonesInPot += stonesToAdd;
        totalStonesFinalResult += stonesToAdd;

        // if currentPot.overflew {
        if (currentPot.isOverflown()) {
            // -- increment no of pot overflow
            noOfPotsOverflown++;

            // -- truncate the possible pots list
            currentPot.possiblePots.clear();

            // -- remove this pot reference from all other pot's possible pot list
            removeFromPossiblePotListOfAllOtherPots(currentPot);

            // add the final pot size known = no of stones as the pot is overflown
            currentPot.possiblePots.add(currentPot.noOfStonesInPot);

            return;
        } else {
            // else pot not overflown
            // then remove all pot sizes from currentPot's possible pot size list where totalStonesInCurrentPot >= possiblePotSize
            Iterator<Integer> itr = currentPot.possiblePots.iterator();
            while (itr.hasNext()) {
                // modify actual list using itr.remove()
                if (currentPot.noOfStonesInPot >= itr.next()) {
                    itr.remove();
                }
            }
        }

        if (checkIfAllPotsKnowTheirSize()) {
            handleAllPotsKnowTheirSize();
            return;
        }

        // calculate diff = current pot size - no of stones in current pot
        // int difference = currentPot.potSize - currentPot.noOfStonesInPot;
        // OR
        // calculate diff = smallest possible pot - no of stones in current pot
        int difference = currentPot.possiblePots.get(0) - currentPot.noOfStonesInPot;

        // calculate the no of stones required to check if the current pot size = next possible pot size in the possible pot list
        int stonesRequiredIfCurrentPotIsLargestPot =
            currentPot.possiblePots.get(currentPot.possiblePots.size() - 1) - currentPot.noOfStonesInPot;

        // --- if the difference is > (no of possible pots * stones required to overflow smallest pot not yet overflown)
        int stonesRequiredToOverflowSmallestPotNotYetOverflown = findStonesRequiredToOverflowSmallestPotNotYetOverflown();
        if (difference > currentPot.possiblePots.size() * stonesRequiredToOverflowSmallestPotNotYetOverflown) {
            // ------ recursive call - put stones = smallest not overflown pot's size minus noOfStonesInPot into the largest pot <
            // currentPot
            Pot smallestPotNotYetOverFlown = findSmallestPotNotYetOverflown();
            putStones(currentPot.largestPotLessThanMe, smallestPotNotYetOverFlown.potSize - smallestPotNotYetOverFlown.noOfStonesInPot);
        } else if (currentPot.potSize - currentPot.noOfStonesInPot <= difference) {
            // ---- else if no of stones required to overflow the currentPot <= diff (extra condition to avoid adding stones more than
            // required to overflow the pot)
            // ------- put the stones required to overflow in currentPot
            putStones(currentPot, currentPot.potSize - currentPot.noOfStonesInPot);
        } else {
            // -------- else put the diff in the same pot
            putStones(currentPot, difference);
        }
    }

    private static int findStonesRequiredToOverflowSmallestPotNotYetOverflown() {
        Pot smallestPotNotYetOverflown = findSmallestPotNotYetOverflown();
        return smallestPotNotYetOverflown.potSize - smallestPotNotYetOverflown.noOfStonesInPot;
    }

    private static void removeFromPossiblePotListOfAllOtherPots(Pot currentPot) {
        for (Pot pot : allPots) {
            Iterator<Integer> itr = pot.possiblePots.iterator();
            while (itr.hasNext()) {
                // modify actual list using itr.remove()
                if (currentPot.noOfStonesInPot == itr.next()) {
                    itr.remove();
                }
            }
        }
    }

    private static boolean checkIfAllPotsKnowTheirSize() {
        for (Pot pot : allPots) {
            if (pot.possiblePots.size() > 1) {
                return false;
            }
        }
        return true;
    }

    private static Pot findSmallestPotNotYetOverflown() {
        for (Pot p : allPots) {
            if (!p.isOverflown()) {
                return p;
            }
        }
        return null;
    }

    public static void parseInputAndCreateDataStructure(int[] pots, int totalPots, int potsToOverflow) {
        Arrays.sort(pots);
        allPots.clear();

        for (int i = 0; i < totalPots; i++) {
            Pot pot = new Pot(pots[i]);
            allPots.add(pot);

            for (int j = 0; j < totalPots; j++) {
                pot.possiblePots.add(pots[j]);
            }
        }

        // assign largest pot < currentPot for each pot
        for (int i = totalPots - 1; i > 0; i--) {
            allPots.get(i).largestPotLessThanMe = allPots.get(i - 1);
        }

        return;
    }
}