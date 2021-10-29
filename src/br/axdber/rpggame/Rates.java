package br.axdber.rpggame;

import lombok.Data;

@Data
public class Rates {

    private double atpIncreaseRate;
    private double dfpIncreaseRate;
    private double healthIncreaseRate;

    public Rates(double atpIncreaseRate, double dfpIncreaseRate, double healthIncreaseRate) {
        this.atpIncreaseRate = atpIncreaseRate;
        this.dfpIncreaseRate = dfpIncreaseRate;
        this.healthIncreaseRate = healthIncreaseRate;
    }
}
