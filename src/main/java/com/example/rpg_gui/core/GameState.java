package com.example.rpg_gui.core;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.map.Map;
import javafx.stage.Stage;

public class GameState {
    public enum State {PLAYING, VICTORY, GAME_OVER}
    private State currentState = State.PLAYING;

    public void update(Hero hero, Map currentMap, Map dungeonMap, UImanager uImanager, Stage primaryStage) {
        if (currentState != State.PLAYING) return;

        if (!hero.isAlive()) {
            currentState = State.GAME_OVER;
            uImanager.showGameOver(this, primaryStage);
        } else if ((currentMap == dungeonMap) && (currentMap.aliveEnemies() == 0)) {
            currentState = State.VICTORY;
            uImanager.showVictory(this, primaryStage);
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public void reset() {
        currentState = State.PLAYING;
    }
}