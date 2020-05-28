package Controller;

import javafx.scene.input.MouseEvent;

public interface PongControllerInterface {
    void start();
    void getMouse(MouseEvent e, double barHeight);
    void sendMyMove();
    void getOtherPersonMove();
    void endOfRound();
    void setStart(boolean b);
    boolean getStart();
    void backButton();
    void endGame();
    void setRandomNumbers(int i);
    void isIt3to0(int me, int bot);
}

