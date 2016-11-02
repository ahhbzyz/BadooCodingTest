package com.badoo.test.transactionviewer.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yaozhong on 02/11/2016.
 */

public class Graph {
    private List<String> nodes = new ArrayList<>();
    private Map<String, Map<String, Float>> map = new HashMap<>();

    public Map<String, Map<String, Float>> getMap() {
        return map;
    }

    public Graph(){


    }

    public void addEdge(String from, String to, float rate) {

        if (!map.containsKey(from)) {
            Map<String, Float> neighbours = new HashMap<>();
            neighbours.put(to, rate);
            map.put(from, neighbours);
        } else {
            // Updates a path.
            Map<String, Float> oldMap = map.get(from);
            if (!oldMap.containsKey(to)) {
                oldMap.put(to, rate);
                map.put(from, oldMap);
            }
        }
        storeNodes(from, to);
    }

    private void storeNodes(String from, String to) {
        if (!from.equals(to)) {
            if (!nodes.contains(to)) {
                nodes.add(to);
            }
        }
        if (!nodes.contains(from)) {
            nodes.add(from);
        }
    }

    public Map<String, Float> getNeighbours(String node) {
        if (map.containsKey(node)) {
            return new HashMap<>(map.get(node));
        }
        return new HashMap<>();
    }

    public boolean memberOf(String node) {
        return nodes.contains(node);
    }
}
