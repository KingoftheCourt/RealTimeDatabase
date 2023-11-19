package com.example.realtimedatabase;

public class SensorValues
{

    private static float pH;
    private static float ppm;
    private static float temperature;

    public static float getpH() {
        return pH;
    }

    public void setpH(float pH) {
        this.pH = pH;
    }

    public static float getPpm() {
        return ppm;
    }

    public void setPpm(float ppm) {
        this.ppm = ppm;
    }

    public static float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }


    public SensorValues(float pH, float ppm, float temperature)
    {
        this.pH = pH;
        this.ppm = ppm;
        this.temperature = temperature;
    }

}
