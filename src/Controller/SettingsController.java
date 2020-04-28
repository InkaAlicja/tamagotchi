package Controller;

import Model.MainModel;
import View.SettingsView;
import javafx.scene.paint.Color;

public class SettingsController {
    SettingsView view;

    public SettingsController(SettingsView settingsView){
        view = settingsView;
    }

    public void setBackground(int i){
        int old = view.getMainView().getMainModel().getMainBackgroundFillId();
        view.getMainView().getMainModel().setMainBackgroundFill(i, view.getMainView().getMainModel().getBackgroundFills()[i]);
        view.resetBackground(i, old);
        view.getMainView().getStoreView().resetBackground();
        view.getMainView().getDragonView().resetBackground();
        view.getMainView().getMainMenuView().resetBackground();
        view.getMainView().getAchievementsView().resetBackground();
        view.getMainView().getAchievementsView().getController().achieve(4);
    }

    public void mute(){
        view.getMainView().getMainModel().setIsMuted(true);
        view.getMainView().getDragonView().getModel().getMediaPlayerHeart().setMute(true);
        MainModel.ClickButton.setMute(true);
    }

    public void unmute(){
        view.getMainView().getMainModel().setIsMuted(false);
        view.getMainView().getDragonView().getModel().getMediaPlayerHeart().setMute(false);
        MainModel.ClickButton.setMute(false);
    }
}
