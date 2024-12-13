package sample;


import Database.Club;
import Database.Country;
import Database.Player;
import TransferObjects.LoginResponse;
import TransferObjects.SellList;
import TransferObjects.SellUpdate;
import javafx.application.Platform;
import javafx.scene.image.Image;
import util.NetworkUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static java.lang.System.exit;

public class ClientReadThread implements Runnable{
    private Thread thr;
    private NetworkUtil networkUtil;
    private Client client;

    public ClientReadThread(Client client) {
        this.networkUtil = client.getNetworkUtil();
        this.thr = new Thread(this);
        this.client = client;
        thr.start();
    }


    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o instanceof LoginResponse) {
                    // Login Response
                    LoginResponse obj=(LoginResponse) o;
                    String s = obj.getMessage();
                    if (s.equals("success")) {
                        System.out.println("Login Successful");
                        client.clubs.clear();
                        Client.clubs.add(obj.getClub());
                        Client.players=(obj.getClub().getPlayers());
                        client.budget=(obj.getBudget());
                        List<Player> list=(obj.getSellList());
                        client.img=obj.getImage();
                        setList(list);
                        for(var x: Client.players){
                            Country cn= Client.SearchCountry(x.getPlayerCountry().getName());
                            if(cn==null){
                                cn=new Country(x.getPlayerCountry().getName());
                                Client.countries.add(cn);
                            }
                            x.setPlayerCountry(cn);
                            cn.Add_Player(x);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    client.launchHome();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        });
                    }
                    else if(s.equals("already online")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                client.showAlert("online");
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                client.showAlert("login");
                            }
                        });
                    }
                }
                else if(o instanceof SellList){
                    List<Player> list=((SellList) o).getSellList();
                    setList(list);
                }
                else if(o instanceof SellUpdate){
                    client.budget=((SellUpdate) o).getBudget();
                    if(client.inMarket) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    client.launchMarket();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                networkUtil.closeConnection();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client.showAlert("crash");
                            exit(0);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setList(List<Player> list) throws Exception {
        Client.sellList.clear();
        Client.buyList.clear();
        Club c=Client.clubs.get(0);
        for(var p:list){
            System.out.println("found ---"+p);
            if(p.getPlayerClub().getName().equals(c.getName()))Client.sellList.add(p);
            else Client.buyList.add(p);
        }
        if(client.inMarket){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.launchMarket();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
        }
    }
}
