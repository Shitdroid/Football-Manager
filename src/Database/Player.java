package Database;

import java.io.Serializable;

public class Player implements Serializable {
    private String name, position;
    private Club playerClub;
    private Country playerCountry;
    private int age, number =-1;
    private double height, salary,price;

    public Player(){
        name=null;
        position=null;
        playerClub=null;
        playerCountry=null;
        height=salary=price=age=number=0;
    }

    public Player(Player p){
        this.name= p.name;
        this.position=p.position;
        this.playerClub=p.playerClub;
        this.playerCountry=p.playerCountry;
        this.age=p.age;
        this.number=p.number;
        this.height=p.height;
        this.salary=p.salary;
        this.price=p.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setSalary(double salary) {
        this.salary = salary;
        if(this.salary- playerClub.getMaxSalary() >.000001) playerClub.setMaxSalary(this.salary);
    }

    public double getSalary() {
        return salary;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPlayerCountry(Country playerCountry) {
        this.playerCountry = playerCountry;
    }

    public Country getPlayerCountry() {
        return playerCountry;
    }

    public void setPlayerClub(Club playerClub) {
        this.playerClub = playerClub;
    }

    public Club getPlayerClub() {
        return playerClub;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String str= name +','+ playerCountry.getName()+','+ age +','+ height +','+ playerClub.getName()+','+ position +','+ number +','+ salary +','+price;
        return str;
    }
}
