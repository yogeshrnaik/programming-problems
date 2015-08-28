package com.iceland;
import java.util.*;
import java.io.*;
public class CandidateCode
{
    private static Map<String, Iceland> icelandsMap;
    private static Map<String, Bridge> bridgesMap;

    private static class Iceland {
        String id;
        List<Iceland> connectedIcelands;
        boolean visited;

        public Iceland(String id) {
            this.id = id;
            this.visited = false;
            this.connectedIcelands = new ArrayList<Iceland>();
        }

        public String toString() {
            StringBuilder result = new StringBuilder();

            result.append(id).append(" connected to -> ");
            for (Iceland i : connectedIcelands) {
                result.append(i.id).append(",");
            }
            return result.toString();
        }
    };

    private static class Bridge implements Comparable {
        @Override
        public int compareTo(Object o) {
            return toString().compareTo(o.toString());
        }

        Iceland iceland1;
        Iceland iceland2;

        public Bridge(Iceland iceland1, Iceland iceland2) {
            this.iceland1 = iceland1;
            this.iceland2 = iceland2;
        }

        public String toString() {
            if (iceland1.id.compareTo(iceland2.id) < 0) {
                return "(" + iceland1.id + "," + iceland2.id + ")";
            }

            return "(" + iceland2.id + "," + iceland1.id + ")";
        }
    };

    public static String criticalBridges(int input1,String input2)
    {
        if (input1 == 1) {
            return findCriticalBridges(input2);
        }

        // more than one test case, split on )}),({
        String[] testCases = input2.split("\\}\\),\\(\\{");

        String result = "";
        for (String testCase : testCases) {
            result += result.length() > 0 ? "," : result;
            result += findCriticalBridges(testCase);
        }
        return "{" + result + "}";
    }

    public static String findCriticalBridges(String testCase) {
        parseTestCaseAndBuildDataStructure(testCase);

        List<Bridge> criticalBridges = new ArrayList<Bridge>();

        String result = "";

        for (Bridge bridge : bridgesMap.values()) {
            if (isBridgeCritical(bridge)) {
                result += result.length() > 0 ? "," : result;
                result += bridge.toString();
                criticalBridges.add(bridge);
            }
        }

        result = toStringBridgeList(criticalBridges);

        return result.length() == 0 ? "{NA}" : "{"  + result + "}";
    }

    private static String toStringBridgeList(List<Bridge> bridges) {
        String result = "";

        Collections.sort(bridges);

        for (Bridge bridge : bridges) {
            result += result.length() > 0 ? "," : result;
            result += bridge.toString();
        }

        return result;
    }

    private static void parseTestCaseAndBuildDataStructure(String testCase) {
        icelandsMap = new LinkedHashMap<String, Iceland>();
        bridgesMap = new LinkedHashMap<String, Bridge>();

        // split test case into list of icelands and list of bridges
        testCase = testCase.replace(",(NA)", "");
        String[] inputData = testCase.split("\\},\\{");

        String icelands = inputData[0].replace("({", "");
        String bridges = inputData[1].replace("})", "").replace("),(", "#").replace("(", "").replace(")", "");
        // System.out.println("icelands = "+ icelands);
        // System.out.println("bridges = "+ bridges);

        for (String iceland : icelands.split(",")) {
            if (iceland.equals("NA")) continue;
            icelandsMap.put(iceland, new Iceland(iceland));
        }

        for (String bridge : bridges.split("#")) {
            if ("NA".equals(bridges)) {
                continue;
            }
            String iceland1 = bridge.split(",")[0];
            String iceland2 = bridge.split(",")[1];
            bridgesMap.put("(" + bridge + ")", new Bridge(icelandsMap.get(iceland1), icelandsMap.get(iceland2)));
            icelandsMap.get(iceland1).connectedIcelands.add(icelandsMap.get(iceland2));
            icelandsMap.get(iceland2).connectedIcelands.add(icelandsMap.get(iceland1));
        }
    }

    private static boolean isBridgeCritical(Bridge bridge) {
        markAllIcelandsNotVisited();
        removeBridge(bridge);

        // start from any node of this bridge and try to traverse the graph and see if all nodes get visited
        visitIcelands(bridge.iceland1);
        boolean result = !isAllNodesVisited();

        // add bridge back
        addBridge(bridge);

        return result;
    }

    private static void markAllIcelandsNotVisited() {
        for (Iceland iceland : icelandsMap.values()) {
            iceland.visited = false;
        }
    }

    private static void removeBridge(Bridge bridge) {
        // simulate removal of this bride
        bridge.iceland1.connectedIcelands.remove(bridge.iceland2);
        bridge.iceland2.connectedIcelands.remove(bridge.iceland1);
    }

    private static void addBridge(Bridge bridge) {
        bridge.iceland1.connectedIcelands.add(bridge.iceland2);
        bridge.iceland2.connectedIcelands.add(bridge.iceland1);
    }

    private static void visitIcelands(Iceland iceland) {
        // mark the iceland visited
        iceland.visited = true;

        for (Iceland connectedIceland : iceland.connectedIcelands) {
            if (!connectedIceland.visited && !isAllNodesVisited()) {
                visitIcelands(connectedIceland);
            }
        }
    }

    private static boolean isAllNodesVisited() {
        for (Iceland iceland : icelandsMap.values()) {
            if (!iceland.visited) {
                return false;
            }
        }
        return true;
    }
}



