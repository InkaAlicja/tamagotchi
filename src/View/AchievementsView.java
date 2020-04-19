package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AchievementsView {
    MainView mainView;
    Scene scene;
    ScrollPane pane;
    VBox mainBox;
    Achievement ach1,ach2,ach3;
    Button backButton;

    public AchievementsView(MainView mainView){
        this.mainView = mainView;

        ach1 = new Achievement("ach1","do this");
        backButton = new Button("back");
        backButton.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));

        mainBox = new VBox(backButton,ach1.box);
        mainBox.setSpacing(8);

        pane= new ScrollPane();
        pane.setContent(mainBox);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        final double SPEED = 0.01;
        pane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            pane.setVvalue(pane.getVvalue() - deltaY);
        });

        scene = new Scene(pane,400,500);
    }

    public class Achievement{
        HBox box;
        String name;
        boolean isAchieved;
        ComboBox<String> comBox;
        Label label;
        public Achievement(String name,String description){
            this.name=name;
            isAchieved=false;
            comBox = new ComboBox<>();
            comBox.getItems().add(description);
            //comBox.
            label = new Label(name);
            box = new HBox(label);//,comBox);
        }
    }
}
