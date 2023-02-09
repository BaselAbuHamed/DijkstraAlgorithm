package com.comp336.projectalgo3;


public class Edge implements Comparable<Edge> {

    private Vertex neighbourVertex;// Neighbour node
    private double weight;
    private static int index = 0;

    public Edge(Vertex v, double w) {
        this.neighbourVertex = v;
        this.weight = w;
        index++;
    }

    public Vertex getNeighbourVertex() {
        return neighbourVertex;
    }

    public void setNeighbourVertex(Vertex neighbourVertex) {
        this.neighbourVertex = neighbourVertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "(" + neighbourVertex + ";" + weight + ":In kilometers)\n";
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.getWeight());
    }

}
