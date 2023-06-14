package br.axdber.rpggame.interfaces;

public interface NonPlayable {

    double easyDifficultyRate = -0.2;
    double hardDifficultyRate = 0.1;

    int getExpValue();

    void setExpValue(int xp);

    double getDifficultyRate();

    void setDifficultyRate(double rate);

    void calcStatsByDifficulty();

}
