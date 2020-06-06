package Controller;

import Model.PongModel;
import View.PongView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PongMultiController implements PongControllerInterface {
    PongView view;
    PongModel model;
    double endOfRound = Double.MAX_VALUE;
    volatile double lastMove = 0d;
    private DataOutputStream out;
    private DataInputStream in;
    Thread check;
    int id;
    boolean start;
    Socket clientSocket;
    public PongMultiController(PongView view, PongModel model, DataInputStream in, DataOutputStream out, Socket clientSocket, int id) {
        this.view=view;
        this.model=model;
        check = new Thread(new checkThread());
        this.out = out;
        this.in = in;
        this.id = id;
        this.clientSocket = clientSocket;
        start = false;
        check.setDaemon(true);
        check.start();
    }

    public void start(){
        try {
            if (!start) {
                System.out.println("Start");
                out.writeUTF("START");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getMouse(MouseEvent e, double barHeight){
        if (id==0)
            view.setLeftY(e.getY() - barHeight/2);
        else
            view.setRightY(e.getY() - barHeight/2);
    }

    public synchronized void setStart(boolean start){
        this.start = start;
    }

    public synchronized boolean getStart(){
        return start;
    }

    private class checkThread extends Task<Void> {
        @Override
        public Void call() {
            while (true) {
                try {
                    String str = in.readUTF();
                    System.out.println(str);
                    if (str.equals("START")) {
                        view.setStart(true);
                        setStart(true);
                        while (getStart()){
                            double d = in.readDouble();
                            if (d==-endOfRound) {
                                setStart(false);
                                break;
                            }
                            lastMove=d;
                        }
                    }
                    if (str.equals("OPPONENT LEFT")){
                        //out.writeDouble(endOfRound);
                        out.writeUTF("STOP");
                        out.writeUTF("LEAVE");
                        out.writeUTF("CLOSE");
                        break;
                    }
                } catch (IOException e) {
                    break;
                }
            }
            return null;
        }
    }

    public void sendMyMove(){
        try {
            if (id==0) {
                out.writeDouble(view.getLeftY());
            }
            else{
                out.writeDouble(view.getRightY());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getOtherPersonMove() {
        try {
            if (id==0)
                view.setRightY(lastMove);
            else
                view.setLeftY(lastMove);
        } catch (Exception e){
            e.printStackTrace();
            if (id==0)
                view.setRightY(lastMove);
            else
                view.setLeftY(lastMove);
        }
    }

    public void endOfRound(){
        try {
            out.writeDouble(endOfRound);
            if (view.getLeftScore()==5 || view.getRightScore()==5)
                endGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backButton(){
        try {
            if (getStart()) {

                setStart(false);
                out.writeDouble(endOfRound);
            }
            out.writeUTF("STOP");
            out.writeUTF("LEAVE");
            out.writeUTF("CLOSE");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.getPlayView().resetScene();
    }

    public void endGame(){
        try {
            out.writeUTF("STOP");
            if ((view.getLeftScore()>view.getRightScore() && id==0)||(view.getLeftScore()<view.getRightScore() && id==1))
                view.setText("You won");
            else
                view.setText("You lost");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setRandomNumbers(int i){
        double root = Math.sqrt(Math.abs(i)+1);
        if (i<0) {
            view.setBallXVector(5*root);
            view.setBallYVector(6*root);
        }
        else{
            view.setBallXVector(-5*root);
            view.setBallYVector(-6*root);
        }
    }

    public void isIt3to0(int me,int bot){

    }
}
