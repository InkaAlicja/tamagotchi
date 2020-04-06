package Model;

import View.TicTacToeView;

import java.util.*;

public class TicTacToeModel {
    TicTacToeView view;
    int turn, winCounter, gameCounter;
    boolean userIsFirst;
    HashSet<Integer> pcSet;
    HashSet<Integer> userSet;
    HashSet<Integer> reminderSet;
    public List<List<Integer>> winScenarios = Arrays.asList(Arrays.asList(0,1,2), Arrays.asList(3,4,5),
            Arrays.asList(6,7,8), Arrays.asList(0,3,6), Arrays.asList(1,4,7), Arrays.asList(2,5,8), Arrays.asList(0,4,8), Arrays.asList(2,4,6));
    public TicTacToeModel(TicTacToeView view){
        winCounter = 0;
        gameCounter = 0;
        pcSet = new HashSet<>();
        userSet = new HashSet<>();
        reminderSet = new HashSet<>();
        this.view = view;
    }

    public boolean pcSetContainsAll(Collection<?> c){
        return pcSet.containsAll(c);
    }

    public boolean userSetContainsAll(Collection<?> c){
        return userSet.containsAll(c);
    }

    public void setUserIsFirst(boolean b){
        userIsFirst = b;
    }

    public boolean getUserIsFirst(){
        return userIsFirst;
    }

    public void addToReminderSet(int i){
        reminderSet.add(i);
    }

    public void removeFromReminderSet(int i){
        reminderSet.remove(i);
    }

    public int getRandomFromReminder(){
        int rand = new Random().nextInt(reminderSet.size());
        int i=0;
        for (int k: reminderSet){
            if (i==rand){
                reminderSet.remove(k);
                return k;
            }
            i++;
        }
        return 0;
    }

    public void addToPcSet(int i){
        pcSet.add(i);
    }

    public void addToUserSet(int i){
        userSet.add(i);
    }

    public void clearPcSet(){
        pcSet.clear();
    }

    public void clearUserSet(){
        userSet.clear();
    }

    public void incWinCounter(){
        winCounter++;
    }

    public void incGameCounter(){
        gameCounter++;
    }

    public int getWinCounter(){
        return winCounter;
    }

    public int getGameCounter(){
        return gameCounter;
    }

    public int getTurn(){
        return turn;
    }
    public void setTurn(int i){
        turn = i;
    }
}
