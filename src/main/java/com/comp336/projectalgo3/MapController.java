package com.comp336.projectalgo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MapController implements Initializable {

    private static final Graph graph = new Graph();

    static Map<String, Vertex> mapForCountry = new HashMap<>();

    private static byte selected = 0;
    static TableEntry[] table;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private static ImageView map;
    @FXML
    private TextArea textAreaDistance;
    @FXML
    private TextArea textAreaPath;
    @FXML
    private ComboBox<String> countrySource;
    @FXML
    private ComboBox<String> countryTarget;

    @FXML
    void runOnAction(ActionEvent event) {

        //get value source and target from combo box
        String countrySourceValue = countrySource.getValue();
        String countryTargetValue = countryTarget.getValue();


        Dijkstra(mapForCountry.get(countrySourceValue), mapForCountry.get(countryTargetValue));

        StringBuilder path = new StringBuilder("");

        printPath(mapForCountry.get(countryTargetValue), path);

        textAreaPath.setText(path.toString());

        textAreaDistance.setText("Distance to go from " + countrySourceValue + " to " + countryTargetValue + "\n="
                + table[mapForCountry.get(countryTargetValue).getId()].getDistance() + "km");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        readData("Country.txt");

        table = new TableEntry[graph.adjacent.size() + 1];
        initializeTable();

        //add country name to compo box
        for (Map.Entry<String, Vertex> c : mapForCountry.entrySet()) {

            countrySource.getItems().add(c.getValue().getName());
            countryTarget.getItems().add(c.getValue().getName());

            c.getValue().countryNode.setOnMouseClicked(e -> {
                if (selected == 0) {
                    countrySource.setValue(c.getValue().getName());
                    selected++;
                } else {
                    countryTarget.setValue(c.getValue().getName());
                    selected = 0;
                }
            });
        }

        //add node on the image
        for (Map.Entry<String, Vertex> c : mapForCountry.entrySet()) {
            anchorPane.getChildren().add(c.getValue().countryNode);
            anchorPane.getChildren().add(c.getValue().line);
            c.getValue().line.setVisible(false);
        }

    }

    private void Dijkstra(Vertex start, Vertex end) {


        for (int i = 0; i < table.length; i++) {
            table[i].known = false;

            if (table[i].path != null) {
                table[i].path.line.setVisible(false);
                table[i].path.countryNode.setFill(Color.RED);
            }
            table[i].path = null;
            table[i].distance = Double.MAX_VALUE;
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));

        table[start.getId()].distance = 0;

        while (!pq.isEmpty()) {
            Vertex u = pq.poll().getNeighbourVertex();

            if (table[u.getId()].known)
                continue;

            //if arrived at vertex then it arrived using the least cost bath
            table[u.getId()].known = true;
            //if the arrived vertex equals end vertex stop
            if (table[end.getId()].known)
                break;


          edgeNeighbour(u, table, pq);
        }

    }
                  //current vertex source
    private void edgeNeighbour(Vertex u, TableEntry[] table, PriorityQueue pq) {

        double edgeDis = -1;
        double newDis = -1;
        // All neighbours of v

        for (Edge c : graph.adjacent.get(u)) {

            // if current vertix hasn't been processed
            if (!table[c.getNeighbourVertex().getId()].known) {
                edgeDis = c.getWeight();
                //distance of all previous edges add distance of new edge
                newDis = table[u.getId()].distance + edgeDis;
                //if c has many edges take edge has letel cost
                if (newDis < table[c.getNeighbourVertex().getId()].distance) {
                    table[c.getNeighbourVertex().getId()].distance = newDis;
                    table[c.getNeighbourVertex().getId()].path = u;
                }

                // Add current node to the queue
                pq.add(new Edge(c.getNeighbourVertex(), table[c.getNeighbourVertex().getId()].distance));
            }
        }
    }
    private void printPath(Vertex start, StringBuilder s) {

        if (table[start.getId()].path != null) {

            table[start.getId()].path.line.setEndX(start.countryNode.getTranslateX());
            table[start.getId()].path.line.setEndY(start.countryNode.getTranslateY());
            table[start.getId()].path.line.setVisible(true);
            table[start.getId()].path.countryNode.setFill(Color.BLUE);
            printPath(table[start.getId()].path, s);
            s.append("to :");
        }
        s.append(start).append(" Distance: ").append(table[start.getId()].getDistance()).append(" km\n");
        System.out.println(s);
    }

    private void initializeTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new TableEntry();
            table[i].known = false;
            table[i].path = null;
            table[i].distance = Double.MAX_VALUE;
        }
    }
    // Convert latitude to x
    private static double latlonToX(double longtidue) {

        double imageWidth = 885;
        return (imageWidth / 2.0) + (longtidue * 2.4611);
    }

    // Convert longitude to y
    private static double latlonToY(double lattitude) {
        double imageHeight = 750;
        return  (imageHeight / 2.0) - (lattitude * 4.1666);
    }

    private static void readData(String fileName) {

        File countryFile = new File(fileName);

        try (Scanner input = new Scanner(countryFile)) {

            String numberOfData = input.nextLine();
            String[] str = numberOfData.split(" ");

            int numberOfCountries = Integer.parseInt(str[0]);
            int numberOfEdges = Integer.parseInt(str[1]);

            int numOfCountryRead = 0;
            int numOdEdgeRead = 0;

            while (input.hasNext()) {

                //read name country and longitude and latitude
                   //read              //from header file
                if (numOfCountryRead < numberOfCountries) {

                    String countryData = input.nextLine();
                    String[] tmp = countryData.split(" ");

                    Vertex tmpVer = new Vertex(tmp[0], Double.parseDouble(tmp[1]),
                            Double.parseDouble(tmp[2]));

                    graph.addVertices(tmpVer);

                    mapForCountry.put(tmpVer.getName(), tmpVer);
                    numOfCountryRead++;

                    //read country edge and calculate distance using longitude and latitude
                          //read          //from header file
                } else if (numOdEdgeRead < numberOfEdges) {
                    String edgesData = input.nextLine();
                    String[] tmp = edgesData.split(" ");

                    //add
                    graph.addEdge(mapForCountry.get(tmp[0]), mapForCountry.get(tmp[1]),
                            distance(mapForCountry.get(tmp[0]), mapForCountry.get(tmp[1])));

                    numOdEdgeRead++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final double R = 6371.0;
    private static double distance(Vertex source , Vertex target ) {
        double lon1 = Math.toRadians(source.getLongitude());
        double lat1 = Math.toRadians(source.getLatitude());
        double lon2 = Math.toRadians(target.getLongitude());
        double lat2 = Math.toRadians(target.getLatitude());
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2.0), 2);
        double c = 2.0 * Math.asin(Math.sqrt(a));
        double r = 6371;
        return (c * r);
    }
}
