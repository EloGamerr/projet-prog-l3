package fr.prog.tablut.controller.game;

import fr.prog.tablut.model.Game;

public abstract class Player<Controller> {
    public abstract void play(Game game, Controller controller);
}
