package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private String name;
    private List<Player> players;
    private int count;
    public Country(String Name){
        this.name =Name;
        players=new ArrayList();
    }

    public Country(Country cn){
        this.name=cn.name;
        for(var p:cn.players)this.players.add(p);
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void Add_Player(Player p){
        count++;
        players.add(p);
    }
    public int getCount(){
        return count;
    }

    @Override
    public String toString(){
        return name;
    }

}
