package TransferObjects;

import Database.Player;

import java.io.Serializable;

public class SellRequest implements Serializable {
    Player player;
    double price;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
