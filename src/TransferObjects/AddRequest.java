package TransferObjects;

import Database.Player;

import java.io.Serializable;

public class AddRequest implements Serializable {
    Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = new Player(player);
    }
}
