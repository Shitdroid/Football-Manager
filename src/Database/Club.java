package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Club implements Serializable {
    private String name;
    private double maxHeight, maxSalary;
    private int maxAge;
    private List<Player> players;

    public Club(String Name){
        this.name =Name;
        players=new ArrayList<>();
        maxAge =0;
        maxHeight =0;
        maxSalary =0;
    }

    public Club(Club c){
        this.name=c.name;
        this.maxAge=c.maxAge;
        this.maxSalary=c.maxSalary;
        this.maxHeight=c.maxHeight;
        for(var p:c.players){
            Player player=new Player(p);
            this.players.add(player);
        }
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getCount(){
        return players.size();
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void Add_Player(Player p){
        players.add(p);
        if(p.getAge()> maxAge) maxAge = p.getAge();
        if(p.getHeight()> maxHeight) maxHeight = p.getHeight();
    }

    public double Total_Salary(){
        double weekly=0;
        for(var x: players) weekly += x.getSalary();
        return (weekly/7.0)*365;
    }
    public List<Player> Max_Salary_Players(){
        List<Player> Rich_Players=new ArrayList<>();
        for(var x: players) if (abs(x.getSalary()-maxSalary) <= .0000001) Rich_Players.add(x);
        return Rich_Players;
    }
    public List<Player> Max_Height_Players(){
        List<Player> Tall_Players=new ArrayList<>();
        for(var x: players) if (abs(x.getHeight()-maxHeight) <= .0000001) Tall_Players.add(x);
        return Tall_Players;
    }
    public List<Player> Max_Age_Players(){
        List<Player> Veteran_Players=new ArrayList<>();
        for(var x: players) if (x.getAge() == maxAge) Veteran_Players.add(x);
        return Veteran_Players;
    }
    @Override
    public String toString(){
        return name;
    }
}
