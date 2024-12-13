package TransferObjects;

import Database.Player;

import java.io.Serializable;

public class SellCancelRequest implements Serializable {
    Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
