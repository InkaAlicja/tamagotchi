package Controller;

import Additions.AlertBox;
import Additions.SaddleBox;
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

    public void playOtherGame(){
        if(view.getDragonController().getDragonView().getMainView().getStoreView().getController().hasSaddle()){
            if(view.getDragonController().getDragonView().getMainView().getStoreView().getController().wearsSaddle())
                ;//play
            else SaddleBox.display("You need to wear your saddle to play");
        }
        else SaddleBox.display("You need to buy a saddle to play");
    }
}
