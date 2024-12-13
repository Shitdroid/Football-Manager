package Server;



import Database.Club;
import Database.Country;
import Database.Player;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import util.NetworkUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    /**********************************************************/
    //Database management elements start
    /**********************************************************/
    private static final List<Player> players=new ArrayList<>();
    private static final List<Player> sellList=new ArrayList<>();
    private static final List<Club> clubs= new ArrayList<>();
    private static final List<Country> countries= new ArrayList<>();
    private static final String[] positions={"Goalkeeper","Defender","Midfielder","Forward"};
    private static final String FILE_NAME="players.txt";
    private static final String INFO_FILE_NAME="clubs.txt";
    public static void readFromFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Player p = new Player();
            p.setName(tokens[0]);
            Country cn=SearchCountry(tokens[1]);
            if(cn==null){
                cn=new Country(tokens[1]);
                countries.add(cn);
            }
            p.setPlayerCountry(cn);
            cn.Add_Player(p);
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));
            Club cl=SearchClub(tokens[4]);
            if(cl==null){
                cl=new Club(tokens[4]);
                clubs.add(cl);
            }
            p.setPlayerClub(cl);
            cl.Add_Player(p);
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setSalary(Double.parseDouble(tokens[7]));
            players.add(p);
        }
        br.close();
    }

    public static void readClientInfoFromFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INFO_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            ClientInfo info=new ClientInfo();
            String[] tokens = line.split(",");
            String cl=tokens[0];
            info.setPassword(tokens[1]);
            info.setBudget(Double.parseDouble(tokens[2]));
            Image img = new Image(new FileInputStream(tokens[3]) );
            //int w = (int)img.getWidth();
            //int h = (int)img.getHeight();
            byte[] buf = new FileInputStream(tokens[3]).readAllBytes();
            info.setImage(buf);
            info.setOnline(false);
            clientMap.put(cl,info);
        }
        br.close();

    }
    static Player addPlayer(String name, String countryName, String clubName, int age, double height,String position, int number, double salary){
        Player p=new Player();
        p.setName(name);
        p.setAge(age);
        p.setHeight(height);
        p.setNumber(number);
        p.setPosition(position);
        Club c=SearchClub(clubName);
        p.setPlayerClub(c);
        c.getPlayers().add(p);
        Country cn=SearchCountry(countryName);
        if(cn==null){
            cn=new Country(countryName);
            countries.add(cn);
        }
        p.setPlayerCountry(cn);
        cn.Add_Player(p);
        p.setSalary(salary);
        players.add(p);
        return p;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static List<Country> getCountries() {
        return countries;
    }

    public static List<Player> getSellList() {
        return sellList;
    }

    static Player SearchPlayer(String playerName){
        Player returnPlayer=null;
        for(var p:players){
            if(playerName.equalsIgnoreCase(p.getName()))returnPlayer=p;
        }
        return returnPlayer;
    }
    static Player SearchPlayer(Player p){
        Player returnPlayer=null;
        String playerName=p.getName();
        String clubName=p.getPlayerClub().getName();
        for(var player:players){
            if(playerName.equalsIgnoreCase(player.getName()) && clubName.equalsIgnoreCase(player.getPlayerClub().getName()))returnPlayer=player;
        }
        return returnPlayer;
    }
    static Club SearchClub(String clubName){
        Club returnClub = null;
        for(var c: clubs)if(clubName.equalsIgnoreCase(c.getName()))returnClub=c;
        return returnClub;
    }
    static Country SearchCountry(String countryName){
        Country returnCountry = null;
        for(var c: countries)if(countryName.equalsIgnoreCase(c.getName()))returnCountry=c;
        return returnCountry;
    }
    /**********************************************************/
    //Database management elements end Networking elements Start
    /**********************************************************/
    private ServerSocket serverSocket;
    public static HashMap<String, ClientInfo> clientMap=new HashMap<>();

    Server() {
        try {
            serverSocket = new ServerSocket(44444);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("server.Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ServerReadThread(clientMap, networkUtil);
    }

    public static void main(String args[]) {
        try {
            readFromFile();
            readClientInfoFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Server server = new Server();
    }
}
