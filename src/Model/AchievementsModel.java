package Model;

import View.AchievementsView;
import java.io.Serializable;

public class AchievementsModel implements Serializable {
    boolean [] Ach;

    public AchievementsModel(){
        Ach = new boolean[7];
        for(int i=0;i<7;i++)Ach[i]=false;
    }
    public void setAchToTrue(int id){Ach[id]=true;}
    public boolean isAch(int id){return Ach[id];}
}
