package TransferObjects;

import Database.Club;
import Database.Player;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {
    String message;
    Club club;
    double budget=0;
    List<Player> sellList;
    byte[] image;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<Player> getSellList() {
        return sellList;
    }

    public void setSellList(List<Player> sellList) {
        this.sellList = sellList;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
