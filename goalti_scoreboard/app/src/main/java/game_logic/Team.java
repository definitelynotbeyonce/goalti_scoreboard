package game_logic;

import java.util.ArrayList;

/**
 * Created by user_name on 2/17/2017.
 */

public class Team {
    private ArrayList<Player> players;
    private int[] activePlayers;

    private int points;
    private String color;

    //TODO:
    public Team(){
        players = new ArrayList<>();
        activePlayers = new int[4];
        init();
    }

    public void init(){
        for(int i = 0; i < 4; ++i){
            activePlayers[i] = -10;
        }
    }

    public Team(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean subPlayer(int activePlayerIndex, Player incomingPlayer){
        activePlayers[activePlayerIndex] = players.indexOf(incomingPlayer);

        return lineupIsValid();
    }

    public boolean lineupIsValid(){
        int size = players.size();
        for(int i: activePlayers){
            if(i < 0 || i >= size)
                return false;
        }

        return true;
    }

    public int getActivePlayerIndex(Player player){
        int playerIndex = players.indexOf(player);
        System.out.println("Player: " + player.getName() + " index " + playerIndex);
        printActive();
        for(int i = 0; i < 4; i++){
            if(activePlayers[i] == playerIndex) {
                return i;
            }
        }

        return -1;
    }

    public void printActive(){
        String ret = "";
        for(int i: activePlayers)
            ret += Integer.toString(i) + " ";

        System.out.println("Active players " + ret);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player[] getActivePlayers(){
        Player[] retArr = new Player[4];
        for(int i = 0; i < 4; ++i){
            retArr[i] = players.get(activePlayers[i]);
        }

        return retArr;
    }

    //TODO: implement this to protect from a player being removed or having their team changed while they're playing.
    public boolean playerIsActive(Player p){
        return getActivePlayerIndex(p) != -1;
    }


    //TODO: Modify the team.
    public void removePlayer(Player p){
        players.remove(p);
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void resetTeam(){
        players.clear();
    }

    public void dummyFillActive(){
        for(int i = 0; i < 4; ++i){
            activePlayers[i] = i;
        }
    }

}
