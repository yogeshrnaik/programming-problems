package com.programming.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Vertex {

    int id;
    int time;
    Vertex next;

    Vertex(int d) {
        id = d;
        time = 0;
        next = null;
    }

    Vertex(int d, int t) {
        id = d;
        time = t;
        next = null;
    }
} // Vertex

/***************************************************************/
class Path {

    int size;
    int total_time;
    int[] visited;

    Path() {
        size = 0;
        total_time = 0;
        visited = new int[20];
    }
} // Path

/***************************************************************/
public class ShortestPath {

    int edges, vertices;
    Vertex[] vertexArray;
    int start, destination;
    Path path;
    Path shortest;

    ShortestPath(BufferedReader sin) throws IOException {
        edges = Integer.parseInt(sin.readLine());
        vertices = Integer.parseInt(sin.readLine());
        vertexArray = new Vertex[vertices + 1];
        for (int i = 0; i <= vertices; i++)
            vertexArray[i] = new Vertex(i);

        for (int i = 1; i <= edges; i++) // reading & creating Graph
        {
            int c1 = Integer.parseInt(sin.readLine());
            int c2 = Integer.parseInt(sin.readLine());
            int t = Integer.parseInt(sin.readLine());
            add(c1, new Vertex(c2, t));
            add(c2, new Vertex(c1, t));
        } // for
        /***************************************************************/
        start = Integer.parseInt(sin.readLine());
        destination = Integer.parseInt(sin.readLine());
        path = new Path();
        shortest = new Path();

        Vertex current = vertexArray[start];
        boolean flag = getPath(current);

        printShortestPath();
    } // constructor

    public static void main(String[] args) throws IOException {
        ShortestPath graph = new ShortestPath(new BufferedReader(new InputStreamReader(System.in)));
    } // main

    public void add(int c, Vertex newVertex) {
        if (vertexArray[c].next == null)
            vertexArray[c].next = newVertex;
        else {
            Vertex curr = vertexArray[c], prev = null;
            for (; curr != null && newVertex.time > curr.time; curr = curr.next)
                prev = curr;
            newVertex.next = prev.next;
            prev.next = newVertex;
        } // else
    } // add

    /***************************************************************/
    public boolean getPath(Vertex curr) {
        if (!isVisited(curr.id)) {
            addToPath(curr.id);
            path.total_time += curr.time;
            if (destination == curr.id)
                return true;

            Vertex temp = search(curr.id);
            temp = temp.next;
            for (; temp != null; temp = temp.next) {
                boolean flag = getPath(temp);
                if (flag) {
                    if (shortest.size == 0 || shortest.total_time > path.total_time) {
                        shortest.size = path.size;
                        shortest.total_time = path.total_time;
                        for (int i = 0; i < path.size; i++)
                            shortest.visited[i] = path.visited[i];
                    }
                    removeFromPath(temp.id);
                    path.total_time -= temp.time;
                }
            }
            path.total_time -= curr.time;
            removeFromPath(curr.id);
        }
        return false;
    } // getPath

    /***************************************************************/
    public boolean isVisited(int id) {
        for (int i = 0; i < path.size; i++)
            if (path.visited[i] == id)
                return true;
        return false;
    }

    public Vertex search(int id) {
        for (int i = 1; i <= vertices; i++)
            if (vertexArray[i].id == id)
                return vertexArray[i];
        return null;
    }

    public void addToPath(int id) {
        path.visited[path.size++] = id;
    }

    public void removeFromPath(int id) {
        path.visited[path.size] = -1;
        if (path.size != 0)
            path.size--;
    }

    /***************************************************************/
    public void printShortestPath() {
        System.out.print("Shortest path = ");
        for (int j = 0; j < shortest.size; j++)
            System.out.print(shortest.visited[j] + " ");
        System.out.println("\nTime required = " + shortest.total_time);
    }
} // ShortestPath
/***************************************************************/
