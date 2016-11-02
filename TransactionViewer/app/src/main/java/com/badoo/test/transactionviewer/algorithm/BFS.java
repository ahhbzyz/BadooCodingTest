package com.badoo.test.transactionviewer.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by yaozhong on 02/11/2016.
 */

public class BFS {

    private static ArrayList<String> shortestPath = new ArrayList<>();

    public static ArrayList<String> breadthFirstSearch(Graph graph, String from,
                                                       String to) {
        shortestPath.clear();

        // A list stores the path.
        ArrayList<String> path = new ArrayList<>();

        if (from.equals(to) && graph.memberOf(from)) {
            path.add(from);
            return path;
        }

        // A queue to store the visited nodes.
        Queue<String> queue = new LinkedList<>();

        // A queue to store the visited nodes.
        Queue<String> visited = new LinkedList<>();

        queue.offer(from);
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            visited.offer(vertex);

            Map<String, Float> neighboursMap = graph.getNeighbours(vertex);

            for (Map.Entry<String, Float> entry : neighboursMap.entrySet()) {
                String neighbour = entry.getKey();

                path.add(neighbour);
                path.add(vertex);

                if (neighbour.equals(to)) {
                    return processPath(from, to, path);
                } else {
                    if (!visited.contains(neighbour)) {
                        queue.offer(neighbour);
                    }
                }
            }
        }
        return null;
    }

    // process the shortest path
    private static ArrayList<String> processPath(String from, String to,
                                                 ArrayList<String> path) {
        int index = path.indexOf(to);
        String source = path.get(index + 1);

        shortestPath.add(0, to);

        if (source.equals(from)) {
            shortestPath.add(0, from);
            return shortestPath;
        } else {
            return processPath(from, source, path);
        }
    }

    // calculate the rate for the shortest path
    public static float calculateRate(Graph graph, String from,
                                        String to) {
        Map<String, Map<String, Float>> map = graph.getMap();
        List<String> shortestPath = breadthFirstSearch(graph, from, to);
        float rate = 1;
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            String first = shortestPath.get(i);
            String second = shortestPath.get(i+1);
            rate *= map.get(first).get(second);
        }
        return rate;
    }
}
