package Controller;

import Additions.AlertBox;
import Model.TicTacToeModel;
import View.TicTacToeView;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Random;

import static java.lang.Thread.sleep;

public class TicTacToeController {
    TicTacToeView view;
    TicTacToeModel model;
    public TicTacToeController(TicTacToeView view, TicTacToeModel model) throws FileNotFoundException {
        this.view=view;
        this.model=model;
        view.setDifficultyLevelScene();
    }

    public void newGame() throws FileNotFoundException {
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

    public void setDifficulty(boolean difficulty) throws FileNotFoundException {
            model.setDifficulty(difficulty);
            newGame();
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
        view.getStack()[i].getChildren().add(model.getUserImageView());
        model.setTurn(model.getTurn()+1);
        view.getStack()[i].setOnMouseClicked(value-> System.out.println("don't"));
    }

    void pcMove(){
        int move = model.getPcMove();
        model.removeFromReminderSet(move);
        model.addToPcSet(move);
        view.getStack()[move].getChildren().add(model.getPcImageView());
        model.setTurn(model.getTurn()+1);
        view.getStack()[move].setOnMouseClicked(value-> System.out.println("don't"));

    }

    public void exit(){
        if (model.getGameCounter()>0) {
            view.getPlayView().getDragonController().addHealth(-0.2f);
            view.getPlayView().getDragonController().addHappiness(0.2f);
        }
        int prize;
        if (model.getDifficulty())
            prize = 10*model.getWinCounter();
        else
            prize = 30*model.getWinCounter();
        view.getPlayView().getDragonController().addMoney(prize);
        view.getPlayView().resetScene();
    }

    public void click(int i) throws FileNotFoundException {
        userMove(i);
        if (checkIfUserWon()){
            AlertBox.display("You won!!!", "Continue", model.getBackground());
            model.incGameCounter();
            model.incWinCounter();
            newGame();
            if(model.getWinCounter()==model.getGameCounter() && model.getWinCounter()>=3){
               if(model.getDifficulty()) wonThreeTimes(false);
               else wonThreeTimes(true);
            }
            return;
        }
        if (model.getTurn()<9) {
            pcMove();
            if (checkIfPcWon()) {
                AlertBox.display("You lost :(((", "Continue", model.getBackground());
                model.incGameCounter();
                newGame();
                return;
            }
        }
        if (model.getTurn()==9){
            AlertBox.display("Draw", "Continue", model.getBackground());
            model.incGameCounter();
            newGame();
            return;
        }
        System.out.println(model.getTurn());
    }
    private void wonThreeTimes(boolean hard){
        view.getPlayView().getDragonController().getDragonView().getMainView().getAchievementsView().getController().achieve(5);
        if(hard) view.getPlayView().getDragonController().getDragonView().getMainView().getAchievementsView().getController().achieve(6);
    }
}

