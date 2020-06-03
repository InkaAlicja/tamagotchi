package Controller;

import View.LobbyView;
import View.PongView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class LobbyController {
    Socket clientSocket;
    LobbyView lobbyView;
    Thread thread;
    Listener listener;
    PongView pongView;
    private DataOutputStream out;
    private DataInputStream in;
    boolean amIChallenging;
    String whoAmIChallenging;
    public LobbyController(LobbyView lobbyView, String name) throws IOException {
        this.lobbyView = lobbyView;
        amIChallenging = false;
        clientSocket = new Socket("localhost", 25565); //23.100.59.188
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
        out.writeUTF("NAME "+name);
        listener = new Listener();
        thread = new Thread(listener);
        thread.setDaemon(true);
        thread.start();
    }

    public void back(){
        try {
            out.writeUTF("CLOSE");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (Exception e){}
        lobbyView.getPlayView().resetScene();
    }

    public void challenge(String i){
        try {
            out.writeUTF("CHALLENGE "+i);
            whoAmIChallenging = i;
            amIChallenging = true;
            lobbyView.disableOrEnableButtons(i, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptInvitation(String i){
        try {
            out.writeUTF("ACCEPT "+i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Listener extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            String temp = "";
            while(true){
                System.out.println("LobbyControllerLooping");
                System.out.println(temp);
                try {
                    System.out.println("got out");
                    temp = in.readUTF();
                    if (temp.equals("TAKEN")){
                        Platform.runLater(()->back());
                        break;
                    }
                    if (temp.startsWith("ADD")) {
                        String finalTemp = temp;
                        Platform.runLater(()-> {
                            try {
                                lobbyView.addButton(finalTemp.substring(4));
                            } catch (FileNotFoundException e) { }
                        });
                    }
                    if (temp.startsWith("REMOVE")) {
                        String finalTemp1 = temp;
                        Platform.runLater(()->lobbyView.removeButton(finalTemp1.substring(7)));
                    }
                    if (temp.startsWith("INVITATION")){
                        String finalTemp2 = temp;
                        Platform.runLater(()-> {
                            try {
                                lobbyView.showInvitation(finalTemp2.substring(11));
                            } catch (FileNotFoundException e) { }
                        });
                    }
                    if (temp.startsWith("ACCEPTED")){
                        out.writeUTF("GO "+temp.substring(9));
                        pongView = new PongView(lobbyView.getPlayView(), in, out, clientSocket, 1);
                        Platform.runLater(()-> {
                            try {
                                pongView.display();
                            } catch (Exception e){}
                        });
                        break;
                    }
                    if (temp.equals("GO")){
                        pongView = new PongView(lobbyView.getPlayView(), in, out, clientSocket, 0);
                        Platform.runLater(()-> {
                            try {
                                pongView.display();
                            } catch (Exception e){}
                        });
                        break;
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    return null;
                }
            }
            return null;
        }
    }

    public boolean getAmIChallenging(){
        return amIChallenging;
    }

    public String getWhoAmIChallenging(){
        return whoAmIChallenging;
    }

    public void setAmIChallenging(boolean b){
        amIChallenging = b;
    }
}

