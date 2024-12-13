package Server;

import Database.Club;
import Database.Country;
import Database.Player;
import TransferObjects.*;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ServerReadThread implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    public HashMap<String, ClientInfo> clientMap;


    public ServerReadThread(HashMap<String, ClientInfo> map, NetworkUtil networkUtil) {
        this.clientMap = map;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void broadcast() throws IOException {
        List<Player> players=new ArrayList<>();
        SellList refresh= new SellList();
        for(var p:Server.getSellList())players.add(p);
        System.out.println("Sending players-----");
        for(var p:players) System.out.println(p);
        refresh.setSellList(players);
        Iterator<String> iterator = clientMap.keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            ClientInfo clientInfo = clientMap.get(name);
            if (clientInfo.isOnline()) {
                clientInfo.getNetworkUtil().write(refresh);
            }
        }
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o instanceof LoginRequest) {
                    LoginRequest obj = (LoginRequest) o;
                    ClientInfo clientInfo = clientMap.get(obj.getName());
                    if (clientInfo != null && clientInfo.getPassword().equals(obj.getPassword())) {
                        clientInfo.setOnline(true);
                        clientInfo.setNetworkUtil(networkUtil);
                        LoginResponse loginResponse=new LoginResponse();
                        loginResponse.setMessage("success");
                        loginResponse.setClub(Server.SearchClub(obj.getName()));
                        loginResponse.setBudget(clientInfo.getBudget());
                        loginResponse.setSellList(Server.getSellList());
                        loginResponse.setImage(clientInfo.getImage());
                        clientInfo.setOnline(true);
                        networkUtil.write(loginResponse);
                    }
                    else if(clientInfo.isOnline()){
                        LoginResponse loginResponse=new LoginResponse();
                        loginResponse.setMessage("already online");
                        loginResponse.setClub(Server.SearchClub(obj.getName()));
                        networkUtil.write(loginResponse);
                    }
                    else{
                        LoginResponse loginResponse=new LoginResponse();
                        loginResponse.setMessage("failure");
                        loginResponse.setClub(Server.SearchClub(obj.getName()));
                        networkUtil.write(loginResponse);
                    }
                }
                if(o instanceof AddRequest){
                    Player pl= ((AddRequest) o).getPlayer();
                    Player p=Server.addPlayer(pl.getName(),pl.getPlayerCountry().getName(),pl.getPlayerClub().getName(), pl.getAge(), pl.getHeight(),pl.getPosition(),pl.getNumber(),pl.getSalary());
                }
                if(o instanceof SellRequest){
                    Player p= Server.SearchPlayer(((SellRequest) o).getPlayer());
                    if(p==null){
                        Player pl=((SellRequest) o).getPlayer();
                        p=Server.addPlayer(pl.getName(),pl.getPlayerCountry().getName(),pl.getPlayerClub().getName(), pl.getAge(), pl.getHeight(),pl.getPosition(),pl.getNumber(),pl.getSalary());
                    }
                    p.setPrice(((SellRequest) o).getPrice());
                    Club c=Server.SearchClub(p.getPlayerClub().getName());
                    c.getPlayers().remove(p);
                    Server.getSellList().add(p);
                    Club cl=Server.SearchClub("Arsenal");
                    for(var pl: cl.getPlayers())System.out.println(pl);
                    for(var pl: Server.getSellList())System.out.println(pl);
                    broadcast();
                }
                if(o instanceof SellCancelRequest){
                    Player p= Server.SearchPlayer(((SellCancelRequest) o).getPlayer());
                    Club c=p.getPlayerClub();
                    c.getPlayers().add(p);
                    Server.getSellList().remove(p);
                    Club cl=Server.SearchClub("Arsenal");
                    for(var pl: cl.getPlayers())System.out.println(pl);
                    for(var pl: Server.getSellList())System.out.println(pl);
                    broadcast();
                }
                if(o instanceof BuyRequest){
                    Player p=Server.SearchPlayer(((BuyRequest) o).getPlayer());
                    Server.getSellList().remove(p);
                    Club seller =p.getPlayerClub();
                    seller.getPlayers().remove(p);
                    System.out.println("bought player-----");
                    System.out.println(p);
                    broadcast();
                    System.out.println("sell list------");
                    for(var pl: Server.getSellList())System.out.println(pl);
                    ClientInfo sellerInfo = clientMap.get(seller.getName());
                    sellerInfo.setBudget(sellerInfo.getBudget()+p.getPrice());
                    SellUpdate update=new SellUpdate();
                    update.setBudget(sellerInfo.getBudget());
                    sellerInfo.getNetworkUtil().write(update);
                    Club buyer=Server.SearchClub(((BuyRequest) o).getToClub());
                    buyer.getPlayers().add(p);
                    p.setPlayerClub(buyer);
                    ClientInfo buyerInfo = clientMap.get(buyer.getName());
                    buyerInfo.setBudget(buyerInfo.getBudget()-p.getPrice());
                    update.setBudget(buyerInfo.getBudget());
                    buyerInfo.getNetworkUtil().write(update);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
