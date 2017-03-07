package game_logic;

import java.util.ArrayList;

/**
 * Created by user_name on 2/26/2017.
 */

public class Game {
    private ArrayList<Goal> goals;
    private Team home;
    private Team away;

    public enum GAME_STATES{
        NEW_GAME, MID_GAME, GOAL, TWO_POINTER, SUB_HOME, SUB_AWAY
    }
    private GAME_STATES gameState;

    public Game(){
        goals = new ArrayList<Goal>();
        home = new Team();
        away = new Team();
        gameState = GAME_STATES.NEW_GAME;
    }

    // Returns: [home goals, away goals]
    public int[] getScore(){
        int[] score = new int[2];

        for(Goal g: goals){
            score[g.getTeam()] += g.getPoints();
        }

        return score;
    }

    //TODO:
    public void undoGoal(){
        goals.remove(0);
    }

    //TODO:
    public void addGoal(Goal g){
        // Add goals to the front so the undo works more better.
        System.out.println("goal scored");
        goals.add(0, g);
    }

    //TODO:
    public boolean substitute(int subbingTeam, Player player0, Player player1){
        Team team = getTeam(subbingTeam);

        // now that I have the team, swap the players.

        // are they both in?  then do nothing.
        int index0 = team.getActivePlayerIndex(player0);
        int index1 = team.getActivePlayerIndex(player1);

        if((index0 >= 0) && (index1 == -1)){
            // player0 is active and is being swapped for player 1.
            return team.subPlayer(index0, player1);
        }
        else if((index1 >= 0) && (index0 == -1)) {
            // player1 is active and is being swapped for player 0.
            return team.subPlayer(index1, player0);
        }
        // else do nothing.


        return true; // successful substitution.
    }

    public Team getTeam(int teamIndex){
        Team team;
        switch(teamIndex){
            case 0:
                team = home;
                break;
            case 1:
                team = away;
                break;
            default:
                team = null;
                break;
        }

        return team;
    }

    public Team getOtherTeam(int teamIndex){
        Team team;
        switch(teamIndex){
            case 0:
                team = away;
                break;
            case 1:
                team = home;
                break;
            default:
                team = null;
                break;
        }

        return team;
    }

    public GAME_STATES getGameState(){
        return gameState;
    }

    public void setGameState(GAME_STATES state){
        gameState = state;
    }

    public Game getCopy(){
        Game g = new Game();


        return g;
    }
}

