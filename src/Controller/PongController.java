package Controller;

import Model.PongModel;
import View.PongView;
import javafx.scene.input.MouseEvent;

public class PongController implements PongControllerInterface{
    PongView view;
    PongModel model;
    public PongController(PongView view,PongModel model){
        this.view=view;
        this.model=model;
    }

    @Override
    public void start() {
        view.setStart(true);
    }

    @Override
    public void getMouse(MouseEvent e, double barHeight) {
        view.setLeftY(e.getY() - barHeight/2);
    }

    @Override
    public void getOtherPersonMove() {
        if (view.getBallX() < 500) {
            view.setRightY(view.getBallY() - view.getBarHeight() / 2);
        } else {
            if (view.getBallY()>(view.getRightY()+view.getBarHeight()/2))
                view.setRightY(view.getRightY()+1);
            else
                view.setRightY(view.getRightY()-1);
        }
    }

    @Override
    public void setRandomNumbers(int i) {
        double min=0.3,max=1.0;
        double ballYVector=0;
        double ballXVector=0;
        while(ballXVector==0 || ballYVector==0 || Math.abs(ballXVector/ballYVector)<2) {
            ballXVector = Math.random() * (max - min +1) + min;
            ballYVector = Math.random() * (max - min +1) + min;
        }
        int signumY= Math.random()*(1 + 1)  < 0.5f ? 1: -1;
        int signumX= Math.random()*(1 + 1)  < 0.5f ? 1: -1;
        ballYVector*=signumY;
        ballXVector*=signumX;
        view.setBallXVector(ballXVector);
        view.setBallYVector(ballYVector);
    }

    public void isIt3to0(int me, int bot){
        if(model.isIt3to0())return;
        if(bot==0 && me>=3) {
            view.getPlayView().getDragonController().getDragonView().getMainView().getAchievementsView().getController().achieve(3);
            model.itIs3to0();
        }
    }

    public void sendMyMove() {}
    public void endOfRound() { }
    public void setStart(boolean b) { }
    public boolean getStart() { return false; }
    public void backButton() { }
    public void endGame() { }

}
