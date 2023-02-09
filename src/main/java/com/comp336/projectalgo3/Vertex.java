package com.comp336.projectalgo3;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Vertex{

     private int Id;
     private String name;
     private double latitude;
     private double longitude;
    Line line;
    Tooltip toolTipTxt;
    Circle countryNode;

    public Vertex(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude=latitude;
        this.longitude=longitude;
        createCircle();
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void newVertex(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }


    private void createCircle() {

        line = new Line();
        line.toFront();
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);

        countryNode = new Circle(3);
        countryNode.setFill(Color.GREEN);
        countryNode.setTranslateZ(4);

        setLongitudeAndLatitude() ;
        line.setStartX(countryNode.getTranslateX());
        line.setStartY(countryNode.getTranslateY());
        Tooltip toolTipTxt = new Tooltip(this.name);
        // Setting the tool tip to the text field
        Tooltip.install(countryNode, toolTipTxt);
        countryNode.setOnMouseEntered(e -> {
            countryNode.setRadius(10);
        });
        countryNode.setOnMouseExited(e -> {
            countryNode.setRadius(3);
        });

    }

    private void setLongitudeAndLatitude() {

        int FE = 180; // false easting
        double radius = 1035 / (2 * Math.PI);

        double latRad = degreesToRadians(this.latitude);
        double lonRad = degreesToRadians(this.longitude + FE);

        double x = (lonRad * radius)-50;

        double yFromEquator = radius * Math.log(Math.tan(Math.PI / 4 + latRad / 2));
        double y;
        if(yFromEquator>0)
         y= (605.0 / 2 - yFromEquator) +96;
        else{
            x+=10;
            y=(605.0 / 2 - yFromEquator) +70;
        }

        countryNode.setTranslateX(x);
        countryNode.setTranslateY(y);
    }
    private double degreesToRadians(double degrees) {
        return (degrees * Math.PI) / 180;
    }

    @Override
    public String toString() {
        return name;
    }

}