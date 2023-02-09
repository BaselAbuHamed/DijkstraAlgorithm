package com.comp336.projectalgo3;

import java.util.LinkedList;
import java.util.List;

public class TableEntry {
	List<Vertex> header = new LinkedList<>();
	boolean known;
	double distance;
	Vertex path;

	//2 digits after ex--> 2.00
	public double getDistance() {
		return (int) (distance * 100) / 100.0;
	}

	@Override
	public String toString() {
		return path + " ";
	}

}
