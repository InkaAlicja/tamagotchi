package Controller;

import View.PlayView;
import View.TicTacToeView;

public class PlayController {
    PlayView view;
    TicTacToeView ticTacToeView;
    public PlayController(PlayView view){
        this.view=view;
    }

    public void back(){
        view.getStage().close();
    }

    public void playTicTacToe(){
        ticTacToeView = new TicTacToeView(view);
    }
}
