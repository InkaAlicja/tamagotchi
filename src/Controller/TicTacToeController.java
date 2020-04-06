package Controller;

import Model.TicTacToeModel;
import View.TicTacToeView;

import java.util.Collection;
import java.util.Random;

import static java.lang.Thread.sleep;

public class TicTacToeController {
    TicTacToeView view;
    TicTacToeModel model;
    public TicTacToeController(TicTacToeView view, TicTacToeModel model){
        this.view=view;
        this.model=model;
        newGame();
    }

    public void newGame() {
        for (int i=0; i<9; i++) model.addToReminderSet(i);
        model.setUserIsFirst(new Random().nextBoolean());
        model.setTurn(0);
        model.clearPcSet();
        model.clearUserSet();
        view.setNewScene();
        if (!model.getUserIsFirst()){
            pcMove();
        }
    }

    boolean checkIfPcWon(){
        for (Collection<Integer> c: model.winScenarios){
            if (model.pcSetContainsAll(c))
                return true;
        }
        return false;
    }

    boolean checkIfUserWon(){
        for (Collection<Integer> c: model.winScenarios){
            if (model.userSetContainsAll(c))
                return true;
        }
        return false;
    }

    void userMove(int i){
        model.addToUserSet(i);
        model.removeFromReminderSet(i);
        view.getLabels()[i].setText("X");
        model.setTurn(model.getTurn()+1);
        view.getStack()[i].setOnMouseClicked(value-> System.out.println("don't"));
        if (checkIfUserWon())
            System.out.println("Nice");
    }

    void pcMove(){
        int rand = model.getRandomFromReminder();
        model.removeFromReminderSet(rand);
        model.addToPcSet(rand);
        view.getLabels()[rand].setText("O");
        model.setTurn(model.getTurn()+1);
        view.getStack()[rand].setOnMouseClicked(value-> System.out.println("don't"));
        if (checkIfPcWon())
            System.out.println("Loser");

    }

    public void exit(){

    }

    public void click(int i){
        if (model.getTurn()<9)
        userMove(i);
        if (model.getTurn()<9)
        pcMove();
        System.out.println(model.getTurn());
    }
}
