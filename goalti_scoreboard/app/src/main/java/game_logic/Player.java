package game_logic;

import java.util.EnumMap;

/**
 * Created by user_name on 2/17/2017.
 */

public class Player {
    //TODO: unique ID
    private static int globalID = 0;
    private int id;

    // Static indices for
    public static enum STATS {
        GAMES_PLAYED, WINS, LOSSES, GOALS_FOR, GOALS_AGAINST, GOALS, ASSISTS, PLUS, MINUS
    }

    public static enum TEAM{
        UNASSIGNED, LIGHT, DARK
    }

    private EnumMap<STATS, Integer> stats;


    private boolean active;
    private String name;
    private String email;
    private String profile_img;
    private TEAM team;

    //TODO:
    public Player(String name, String email, String profile_img)
    {
        id = globalID++;
        this.name = name;
        this.email = email;
        this.profile_img = profile_img;

        team = TEAM.UNASSIGNED;
        active = false;

        if(id < 12) {
            active = true;
            if(id < 5)
                this.team = TEAM.LIGHT;
            else if(id < 10)
                this.team = TEAM.DARK;
        }
    }

    public void setTeam(TEAM team){
        this.team = team;
    }

    public TEAM getTeam() {
        return team;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getImgPath(){
        return profile_img;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
        System.out.println(name + " is active?: " + active);
    }

    public boolean matchID(int playerID){
        return playerID == id;
    }
}
