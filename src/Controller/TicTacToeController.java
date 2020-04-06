package Controller;

import Additions.AlertBox;
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
    }

    void pcMove(){
        int rand = model.getRandomFromReminder();
        model.removeFromReminderSet(rand);
        model.addToPcSet(rand);
        view.getLabels()[rand].setText("O");
        model.setTurn(model.getTurn()+1);
        view.getStack()[rand].setOnMouseClicked(value-> System.out.println("don't"));

    }

    public void exit(){
        if (model.getGameCounter()>0) {
            view.getPlayView().getDragonController().addHealth(-0.2f);
            view.getPlayView().getDragonController().addHappiness(0.2f);
        }
        view.getPlayView().getDragonController().addMoney(10*model.getWinCounter());
        view.getPlayView().resetScene();
    }

    public void click(int i){
        userMove(i);
        if (checkIfUserWon()){
            AlertBox.display("You won!!!", "Continue");
            model.incGameCounter();
            model.incWinCounter();
            newGame();
            return;
        }
        if (model.getTurn()<9) {
            pcMove();
            if (checkIfPcWon()) {
                AlertBox.display("You lost :(((", "Continue");
                model.incGameCounter();
                newGame();
                return;
            }
        }
        if (model.getTurn()==9){
            AlertBox.display("Draw", "Continue");
            model.incGameCounter();
            newGame();
            return;
        }
        System.out.println(model.getTurn());
    }
}
