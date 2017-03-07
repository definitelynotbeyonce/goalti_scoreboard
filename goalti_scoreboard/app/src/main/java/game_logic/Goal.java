package game_logic;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by user_name on 2/26/2017.
 */

public class Goal {
    public final static int TEAM_SIZE = 4;

    // Primitives
    private int team;   // -1: neither, 0: home, 1: away
    private int points;
    private boolean complete;

    // Objects
    private Player thrower;
    private Player receiver;

    // Structured data
    private Player[] offense;
    private ArrayList<Player> offenseTeam;
    private Player[] defense;
    private ArrayList<Player> defenseTeam;

    // Blank constructor used for the goal being created.
    public Goal() {
        // Allocate as necessary.
        offense = new Player[TEAM_SIZE];
        defense = new Player[TEAM_SIZE];
        offenseTeam = new ArrayList<>();
        defenseTeam = new ArrayList<>();

        // Set all values to default.
        initParams();
    }

    public void initParams() {
        // init primitives
        team = -1;
        points = -1;
        complete = false;

        // init objects
        thrower = null;
        receiver = null;

        // init structured data
        for(int i = 0; i < 4; ++i){
            offense[i] = null;
            defense[i] = null;
        }

        offenseTeam.clear();
        defenseTeam.clear();
    }

    // Copy constructor
    public Goal(Goal other) {
        this();

        this.team = other.getTeam();
        this.points = other.getPoints();
        this.thrower = other.getThrower();
        this.receiver = other.getReceiver();

        // Copy arrays
        setOffense(other.getOffense(), other.getOffenseTeam());
        setDefense(other.getDefense(), other.getDefenseTeam());

        this.complete = true;
    }




    // SETTERS
    public void setTeam(int team){
        this.team = team;
    }

    public void setThrower(Player thrower) {
        this.thrower = thrower;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public void setOffense(Player[] active, ArrayList<Player> everyone) {
        for(int i = 0; i < TEAM_SIZE; ++i){
            this.offense[i] = active[i];
        }

        for(Player p: everyone){
            offenseTeam.add(p);
        }
    }

    public void setDefense(Player[] active, ArrayList<Player> everyone) {
        for (int i = 0; i < TEAM_SIZE; ++i) {
            this.defense[i] = active[i];
        }

        for(Player p: everyone){
            defenseTeam.add(p);
        }
    }

    //GETTERS
    public int getTeam(){
        return team;
    }

    public int getPoints(){
        return points;
    }

    public Player[] getOffense(){
        return offense;
    }

    public Player[] getDefense(){
        return defense;
    }

    public ArrayList<Player> getOffenseTeam(){
        return offenseTeam;
    }

    public ArrayList<Player> getDefenseTeam(){
        return defenseTeam;
    }

    public Player getThrower(){
        return thrower;
    }

    public Player getReceiver(){
        return receiver;
    }

    public Goal getCopy(){
        Goal retGoal = new Goal();

        retGoal.team = getTeam();
        retGoal.points = getPoints();
        retGoal.thrower = getThrower();
        retGoal.receiver = getReceiver();

        // Copy arrays
        retGoal.setOffense(getOffense(), getOffenseTeam());
        retGoal.setDefense(getDefense(), getDefenseTeam());

        retGoal.complete = true;

        return retGoal;
    }

    /*TODO: check to make sure the
    public boolean isValid(){
        return (team >= 0 && team < 2)
                && (points)
    }*/
}
