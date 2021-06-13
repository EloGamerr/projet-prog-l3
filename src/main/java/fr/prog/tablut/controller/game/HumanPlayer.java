package fr.prog.tablut.controller.game;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;

public class HumanPlayer extends Player {
    public HumanPlayer(PlayerEnum playerEnum) {
        super(playerEnum, PlayerTypeEnum.HUMAN);
    }

    public boolean play(Game game, Point from, Point to) {
        if(from == null || to == null || Game.getInstance() == null)
            return false;

        return (from.x != to.x || from.y != to.y) && Game.getInstance().move(from.x, from.y, to.x, to.y) && Game.getInstance().confirmMove();
    }
}
