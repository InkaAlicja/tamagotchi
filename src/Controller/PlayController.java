package Controller;

import Additions.AlertBox;
import Additions.SaddleBox;
import View.PlayView;
import View.PongView;
import View.TicTacToeView;
import View.WalkingView;

public class PlayController {
    PlayView view;
    TicTacToeView ticTacToeView;
    PongView pongView;
    WalkingView walkingView;
    public PlayController(PlayView view){
        this.view=view;
    }

    public void back(){
        view.getStage().close();
    }

    public void playTicTacToe(){
        ticTacToeView = new TicTacToeView(view);
    }

    public void playOtherOtherGame(){ walkingView = new WalkingView(this.view);}

    public void playOtherGame(){
        if(view.getDragonController().getDragonView().getMainView().getStoreView().getController().hasSaddle()){
            if(view.getDragonController().getDragonView().getMainView().getStoreView().getController().wearsSaddle()) {
                pongView = new PongView(view);//play
                pongView.display();
                System.out.println(pongView.getScore());
                view.getDragonController().addMoney(pongView.getScore()*10);
            }
            else SaddleBox.display("You need to wear your saddle to play");
        }
        else SaddleBox.display("You need to buy a saddle to play");
    }
}
