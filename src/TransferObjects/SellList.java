package TransferObjects;

import Database.Player;

import java.io.Serializable;
import java.util.List;

public class SellList implements Serializable {
    List<Player> sellList;

    public void setSellList(List<Player> sellList) {
        this.sellList = sellList;
    }

    public List<Player> getSellList() {
        return sellList;
    }
}
