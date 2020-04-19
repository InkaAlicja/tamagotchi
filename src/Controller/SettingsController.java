package Controller;

import View.SettingsView;
import javafx.scene.paint.Color;

public class SettingsController {
    SettingsView view;

    public SettingsController(SettingsView settingsView){
        view = settingsView;
    }

    public void setBackground(int i){
        view.getMainView().getMainModel().setMainBackgroundFill(i, view.getMainView().getMainModel().getBackgroundFills()[i]);
        view.resetBackground();
        view.getMainView().getStoreView().resetBackground();
        view.getMainView().getDragonView().resetBackground();
        view.getMainView().getMainMenuView().resetBackground();
        view.getMainView().getAchievementsView().resetBackground();
    }
}
