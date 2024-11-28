package org.example;

import org.example.controllers.GameController;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class Main extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(848);
        settings.setHeight(560);
        settings.setTitle("Basic Game App");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        GameController gameController = new GameController();
        gameController.run();
    }
}
