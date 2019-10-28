package com.hlbd.electric.widget.chart;

public class ChartData {

    public ChartData(String xText, float y) {
        this.xText = xText;
        this.y = y;
    }

    public String xText;
    public float y;

    @Override
    public String toString() {
        return "ChartData{" +
                "xText='" + xText + '\'' +
                ", y=" + y +
                '}';
    }
}
