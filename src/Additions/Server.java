package Additions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private ServerSocket serverSocket;
    List<Thread> clientThreads;
    ConcurrentHashMap<String, DataOutputStream> map;
    ConcurrentHashMap<String, AtomicInteger> counters;
    int port;
    Double endOfRound;

    public void start() throws IOException {
        int i = 0;
        while(true){
            clientThreads.add(new Thread(new ClientThread(serverSocket.accept(), i)));
            clientThreads.get(i).start();
            i++;
        }
    }

    public Server(int port) throws IOException {
        this.port = port;
        endOfRound = Double.MAX_VALUE;
        map = new ConcurrentHashMap<>();
        counters = new ConcurrentHashMap<>();
        serverSocket = new ServerSocket(port);
        clientThreads = new ArrayList<>();
    }

    private class ClientThread implements Runnable{
        private Socket clientSocket;
        private DataOutputStream out;
        private DataInputStream in;
        private Thread session;
        String name;

        public ClientThread(Socket socket, int id){
            System.out.println("New client "+id);
            clientSocket=socket;
            try {
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String temp="";
            try {
                temp = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (temp.startsWith("NAME ")) {
                if (!map.containsKey(temp.substring(5))) {
                    map.put(name = temp.substring(5), out);
                    counters.put(name, new AtomicInteger(0));
                }
                else {
                    try {
                        out.writeUTF("TAKEN");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(name);
        }

        void begin(){
            for (String  s: map.keySet()){
                if (!s.equals(name) && counters.get(s).get()==0) {
                    try {
                        System.out.println(s);
                        DataOutputStream temp = map.get(s);
                        if (temp!=null) {
                            temp.writeUTF("ADD " + name);
                            out.writeUTF("ADD "+s);
                        }
                    } catch (IOException ignored) { }
                }
            }
        }

        void removeButtons(String opName){
            for (String  s: map.keySet()){
                if (!s.equals(name) && !s.equals(opName) && counters.get(s).get()==0) {
                    try {
                        DataOutputStream temp = map.get(s);
                        if (temp!=null) {
                            temp.writeUTF("REMOVE " + name);
                        }
                    } catch (IOException ignored) { }
                }
            }
        }

        void end(){
            System.out.println("Ending "+name);
            map.remove(name);
            counters.remove(name);
            removeButtons("");
        }

        @Override
        public void run() {
            if (name!=null)
                begin();
            while (true){
                String temp="";
                try {
                    temp = in.readUTF();
                    System.out.println(temp);
                }catch(Exception e){
                    break;
                }
                if (temp.equals("CLOSE")){
                    break;
                }
                if (temp.startsWith("CHALLENGE")){
                    try {
                        map.get(temp.substring(10)).writeUTF("INVITATION " + name);
                    } catch (IOException e) {
                        break;
                    }
                }
                if (temp.startsWith("ACCEPT") || temp.startsWith("GO")){
                    try {
                        String opName;
                        DataOutputStream opOut;
                        if (temp.startsWith("ACCEPT")) {
                            opName = temp.substring(7);
                            out.writeUTF("GO");
                            opOut = map.get(opName);
                            opOut.writeUTF("ACCEPTED " + name);
                        }
                        else {
                            opName = temp.substring(3);
                            opOut = map.get(opName);
                        }
                        removeButtons(opName);
                        temp = in.readUTF();
                        System.out.println(temp);
                        while(!temp.equals("STOP")) {
                            if (temp.equals("START")) {
                                counters.get(name).incrementAndGet();
                                counters.get(opName).incrementAndGet();
                                while (counters.get(name).get() % 2 != 0) { }
                                opOut.writeUTF("START");
                                Double d;
                                while (true) {
                                    d = in.readDouble();
                                    if (d.equals(endOfRound)) {
                                        opOut.writeDouble(-endOfRound);
                                        break;
                                    }
                                    try {
                                        opOut.writeDouble(d);
                                    } catch (NullPointerException e){
                                        out.writeDouble(endOfRound);
                                        end();
                                        break;
                                    }
                                    System.out.println(d);
                                }
                            }
                            temp = in.readUTF();
                        }
                        temp = in.readUTF();
                        if (temp.equals("LEAVE")) {
                            opOut.writeUTF("OPPONENT LEFT");
                            break;
                        }
                    } catch (IOException e) {
                        break;
                    }
                }
            }
            if (name!=null)
                end();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(25565);
        server.start();
    }
}
