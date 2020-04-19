package Controller;

import View.SettingsView;

public class SettingsController {
    SettingsView view;

    public SettingsController(SettingsView settingsView){
        view = settingsView;
    }

    public void setBackground(int i){
        System.out.println("xd");
        view.getMainView().getMainModel().setMainBackgroundFill(view.getMainView().getMainModel().getBackgroundFills()[i]);
    }
}
