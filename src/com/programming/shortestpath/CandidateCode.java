package com.programming.shortestpath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CandidateCode {

    private static class Station {

        String id;
        boolean visited = false;

        Map<String, MetroLine> metroLines = new LinkedHashMap<String, MetroLine>();

        Map<Station, Integer> connectedStations = new LinkedHashMap<Station, Integer>();

        public Station(String id) {
            this.id = id;
        }

        public boolean isTransferPossible() {
            return metroLines.size() > 1;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Station))
                return false;
            Station station = (Station)o;
            return (station.id.equals(this.id));
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return id;
        }
    };

    private static class MetroLineItem {

        Station station1;
        Station station2;
        int time;

        public MetroLineItem(Station s1, Station s2, int time) {
            station1 = s1;
            station2 = s2;
            this.time = time;
        }
    }

    private static class MetroLine {

        String lineName;

        LinkedList<MetroLineItem> lineItems = new LinkedList<MetroLineItem>();

        Map<String, Station> stationsMap = new LinkedHashMap<String, Station>();

        public MetroLine(String lineName) {
            this.lineName = lineName;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof MetroLine))
                return false;
            MetroLine line = (MetroLine)o;
            return (line.lineName.equals(this.lineName));
        }

        @Override
        public int hashCode() {
            return lineName.hashCode();
        }

        @Override
        public String toString() {
            String result = "Metro line " + lineName + ", stations: ";
            for (Station station : stationsMap.values()) {
                result += station + ",";
            }
            return result;
        }
    };

    private static class PathItem {

        Station station1;
        Station station2;
        int time;
        boolean transfer;
        String metroLineName;

        public PathItem(Station s1, Station s2, int time, boolean transfer, String metroLineName) {
            this.station1 = s1;
            this.station2 = s2;
            this.time = time;
            this.transfer = transfer;
            this.metroLineName = metroLineName;
        }

        public String format() {
            return station1.id + "-" + station2 + "-" + time;
        }

        @Override
        public String toString() {
            String result = "";
            result += station1.id + "-" + station2.id + "-" + time + " on line: " + metroLineName;
            return result;
        }
    }

    private static class Path implements Comparable {

        private int transfers;
        private int halts;
        private int waitTime;
        private int totalTime;

        List<PathItem> stationsInPath = new ArrayList<PathItem>();

        public Path() {

        }

        public Path(Path path) {
            this.transfers = path.transfers;
            this.halts = path.halts;
            this.waitTime = path.waitTime;
            this.totalTime = path.totalTime;

            for (PathItem item : path.stationsInPath) {
                this.stationsInPath.add(item);
            }
        }

        public void addToPath(Station s1, Station s2, int time, boolean transfer, String metroLineName) {
            totalTime += time + 15; // actual time + halt time
            halts++;
            if (transfer) {
                totalTime += 30; // time for transfer
                transfers++;
                waitTime += 30; // time for transfer;
            }
            s2.visited = true;
            stationsInPath.add(new PathItem(s1, s2, time, transfer, metroLineName));

            // System.out.println("Added to Path -> " + s1 + ", " + s2 + ", " + time + ", " + transfer + ", " + metroLineName);
        }

        public void removeFromPath(Station s1, Station s2, int time, boolean transfer, String metroLineName) {
            totalTime -= (time + 15); // actual time + halt time
            halts--;
            if (transfer) {
                totalTime -= 30; // time for transfer
                transfers--;
                waitTime -= 30; // time for transfer
            }
            s2.visited = false;
            stationsInPath.remove(stationsInPath.size() - 1);
            // System.out.println("Removed from Path -> " + s1 + ", " + s2 + ", " + time + ", " + transfer + ", " + metroLineName);
        }

        @Override
        public int compareTo(Object o) {
            Path path = (Path)o;

            if (totalTime == path.totalTime) {
                if (transfers == path.transfers) {
                    if (halts == path.halts) {
                        return waitTime - path.waitTime;
                    } else {
                        return halts - path.halts;
                    }
                } else {
                    return transfers - path.transfers;
                }
            }
            return totalTime - path.totalTime;
        }
    };

    private static Map<Integer, String> metroLineNames = new HashMap<Integer, String>();
    private static Map<String, MetroLine> metroLines = new LinkedHashMap<String, MetroLine>();
    private static Map<String, Station> allStationMaps = new LinkedHashMap<String, Station>();
    private static List<Path> allPaths = new ArrayList<Path>();

    public static String[] quickestroute(String[] input1, String input2) {
        reset();
        parseMetroLines(input1);

        String[] pathToFind = input2.split("#");
        findAllPaths(pathToFind[0], pathToFind[1]);

        Collections.sort(allPaths);

        return covertPathToEndResult(allPaths.get(0));
    }

    private static void reset() {
        metroLineNames = new HashMap<Integer, String>();
        metroLines = new LinkedHashMap<String, MetroLine>();
        allStationMaps = new LinkedHashMap<String, Station>();
        allPaths = new ArrayList<Path>();

        metroLineNames.put(1, "Black");
        metroLineNames.put(2, "Blue");
        metroLineNames.put(3, "Red");
        metroLineNames.put(4, "Green");
    }

    private static String[] covertPathToEndResult(Path path) {
        List<String> result = new ArrayList<String>();
        result.add("NC");

        String partialResult = "";

        // add stations from the path
        for (PathItem item : path.stationsInPath) {
            if (item.transfer) {
                // line changed so add the last result into end result
                result.add(partialResult);
                partialResult = "";
            }
            partialResult += partialResult.length() == 0 ? item.format() : "#" + item.format();
        }
        if (partialResult.length() != 0) {
            result.add(partialResult);
        }

        result.add("NC");
        return result.toArray(new String[result.size()]);
    }

    private static void findAllPaths(String start, String end) {
        Station startStation = getStation(start);
        Station endStation = getStation(end);

        startStation.visited = true;

        for (MetroLine line : startStation.metroLines.values()) {
            Path path = new Path();
            boolean found = getPath(line, startStation, startStation, endStation, path);
            if (found) {
                // path found
                allPaths.add(new Path(path));
            }

        }
    }

    private static boolean getPath(MetroLine currentLine, Station currStation, Station start, Station end, Path path) {
        // check if path found
        if (currStation.equals(end)) {
            return true;
        }

        Collection<MetroLine> linesToCheck = new ArrayList<MetroLine>();
        if (currStation.id.equals(start.id)) {
            linesToCheck.add(currentLine);
        } else {
            linesToCheck.addAll(currStation.metroLines.values());
        }

        // for each metro line of the current station try to go to the connected station
        // until path is found
        for (MetroLine line : linesToCheck) {
            boolean transfer = !line.lineName.equals(currentLine.lineName);

            Set<Map.Entry<Station, Integer>> connectedStationsOnThisLine = findPreviousAndNextStationForCurrentStation(line, currStation);

            for (Map.Entry<Station, Integer> connectedStation : connectedStationsOnThisLine) {
                if (connectedStation.getKey().visited)
                    continue;
                // add to path
                path.addToPath(currStation, connectedStation.getKey(), connectedStation.getValue(), transfer, line.lineName);

                boolean found = getPath(line, connectedStation.getKey(), start, end, path);
                if (found) {
                    // path found
                    allPaths.add(new Path(path));
                }

                // before going to next iteration, remove last item from path
                path.removeFromPath(currStation, connectedStation.getKey(), connectedStation.getValue(), transfer, line.lineName);
            }
        }
        return false;
    }

    private static Set<Map.Entry<Station, Integer>> findPreviousAndNextStationForCurrentStation(MetroLine line, Station currStation) {
        Map<Station, Integer> connectedStationsOnThisLine = new LinkedHashMap<Station, Integer>();

        List<Station> allStationsOnLine = new ArrayList<Station>(line.stationsMap.values());

        Station previous = null, next = null;

        // find the index of current station in the array list and then find the previous and next stations
        int index = 0;
        int currStationIndex = -1;
        for (Station s : allStationsOnLine) {
            if (s.id.equals(currStation.id)) {
                currStationIndex = index;
                break;
            } else {
                index++;
            }
        }

        if (currStationIndex != -1) {
            previous = currStationIndex == 0 ? null : allStationsOnLine.get(currStationIndex - 1);
            next = currStationIndex == allStationsOnLine.size() - 1 ? null : allStationsOnLine.get(currStationIndex + 1);
        }

        if (previous != null)
            connectedStationsOnThisLine.put(previous, currStation.connectedStations.get(previous));

        if (next != null)
            connectedStationsOnThisLine.put(next, currStation.connectedStations.get(next));

        // new LOGIC
        connectedStationsOnThisLine.clear();
        for (Map.Entry<Station, Integer> connectedStation : currStation.connectedStations.entrySet()) {
            if (connectedStation.getKey().metroLines.containsValue(line)) {
                connectedStationsOnThisLine.put(connectedStation.getKey(), connectedStation.getValue());
            }
        }
        return connectedStationsOnThisLine.entrySet();
    }

    private static void parseMetroLines(String[] lines) {
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            MetroLine metroLine = new MetroLine(metroLineNames.get(lineNumber));

            for (String link : line.split("#")) {
                String linkSplit[] = link.split("-");
                String s1 = linkSplit[0];
                String s2 = linkSplit[1];
                int time = Integer.parseInt(linkSplit[2]);

                Station station1 = getStation(s1);
                Station station2 = getStation(s2);
                station1.connectedStations.put(station2, time);
                station2.connectedStations.put(station1, time);

                metroLine.stationsMap.put(s1, station1);
                metroLine.stationsMap.put(s2, station2);
                metroLine.lineItems.add(new MetroLineItem(station1, station2, time));

                station1.metroLines.put(lineNumber + "", metroLine);
                station2.metroLines.put(lineNumber + "", metroLine);
            }

            metroLines.put(metroLineNames.get(lineNumber), metroLine);
        }
    }

    private static Station getStation(String id) {
        Station station = allStationMaps.get(id);
        if (station == null) {
            station = new Station(id);
            allStationMaps.put(id, station);
        }
        return station;
    }

}
