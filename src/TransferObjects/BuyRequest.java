package TransferObjects;


import Database.Player;

import java.io.Serializable;

public class BuyRequest implements Serializable {
    Player player;
    String toClub;


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setToClub(String toClub) {
        this.toClub = toClub;
    }

    public String getToClub() {
        return toClub;
    }
}
