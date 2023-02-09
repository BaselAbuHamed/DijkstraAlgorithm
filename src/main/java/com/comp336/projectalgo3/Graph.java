package com.comp336.projectalgo3;

import java.util.*;

public class Graph {

    Map<Vertex, LinkedList<Edge>> adjacent = new HashMap<>();
    static int i = 0;

    public Graph() {
    }

    public void addVertices(Vertex vertices) {
        adjacent.putIfAbsent(vertices, new LinkedList<>());
        ((Vertex) vertices).newVertex(this.i);
        i++;

    }

    // Add edges includes adding a node
    public void addEdge(Vertex a, Vertex b, double w) {

        adjacent.putIfAbsent(a, new LinkedList<>());// Adding a node
        adjacent.putIfAbsent(b, new LinkedList<>());
        Edge edge1 = new Edge(b, w);
        adjacent.get(a).add(edge1);// Adding edge from a to b
    }

    // find edge between two nodes, T(n)=O(n), space =O(c),n=#of neighbors
    private Edge findEdgeByVertex(Vertex a, Vertex b) {
        LinkedList<Edge> neighbourEdges = adjacent.get(a);
        for (Edge edge : neighbourEdges) {// Iterate through neighbours
            if (edge.getNeighbourVertex().equals(b)) {
                return edge;//
            }
        }
        return null;// b isn't a neighbour of a
    }

    // Remove direct edge between two vertices Time=O(n), Space=O(c)
    // Directed graph so only remove edge from a to b
    public void removeEdge(Vertex a, Vertex b) {
        LinkedList<Edge> neighbourEdge1 = adjacent.get(a);
        if (neighbourEdge1 == null)
            return;
        Edge edgeFromAToB = findEdgeByVertex(a, b);
        if (edgeFromAToB != null)
            neighbourEdge1.remove(edgeFromAToB);
    }

    // Remove a vertices from directed graph
    public void removeVertics(Vertex v) {
        for (Vertex key : adjacent.keySet()) {// Traverse through keySets of map
            // Findes edge from current vertices to
            // the vertices we want to remove and remove it
            Edge edge2 = findEdgeByVertex(key, v);
            if (edge2 != null)// if such an edge exists
                adjacent.get(key).remove(edge2);
        }
        // After removing all edges going to V
        // remove V
        adjacent.remove(v);
    }

    // Search a vertices by its key
    public boolean hasNode(Vertex key) {
        return adjacent.containsKey(key);
    }

    // Check whether there is direct edge between two nodes
    public boolean hasEdge(Vertex a, Vertex b) {
        Edge edge1 = findEdgeByVertex(a, b);
        return edge1 != null;
    }

    // Print graph as hashmap Time O(V+E)
    @Override
    public String toString() {
        String s = "Distance in kilometers";
        for (Vertex key : adjacent.keySet()) {
            s += key + "," + adjacent.get(key) + "\n";
        }
        return s;
    }
}

