package game_logic;

import java.util.ArrayList;

/**
 * Created by user_name on 2/24/2017.
 */

public class Goaltimate {
    private ArrayList<Player> players;
    private ArrayList<Game> games;
    private Game currGame;


    public Goaltimate(){
        currGame = new Game();

        String[][] dummy_players = {
                {"knowles", "beyonce.jpg", "scott.runyon42@gmail.com"},
                {"Player 1", "dummy-profile-pic.png", "email@site.com"},
                {"Player 2", "dummy-profile-pic.png", "email@site.com"},
                {"Player 3", "dummy-profile-pic.png", "email@site.com"},
                {"Player 4", "dummy-profile-pic.png", "email@site.com"},
                {"Player 5", "dummy-profile-pic.png", "email@site.com"},
                {"Player 6", "dummy-profile-pic.png", "email@site.com"},
                {"Player 7", "dummy-profile-pic.png", "email@site.com"},
                {"Player 8", "dummy-profile-pic.png", "email@site.com"},
                {"Player 9", "dummy-profile-pic.png", "email@site.com"},
                {"Player 10", "dummy-profile-pic.png", "email@site.com"},
                {"Player 11", "dummy-profile-pic.png", "email@site.com"},
                {"Player 12", "dummy-profile-pic.png", "email@site.com"},
                {"Player 13", "dummy-profile-pic.png", "email@site.com"},
                {"Player 14", "dummy-profile-pic.png", "email@site.com"}
        };

        players = new ArrayList<Player>();
        for(String[] player: dummy_players) {
            System.out.println("created player");
            Player newPlayer = new Player(player[0], player[2], player[1]);
            players.add(newPlayer);

            // now add them to their teams.
            switch(newPlayer.getTeam()){
                case LIGHT:
                    currGame.getTeam(0).addPlayer(newPlayer);
                    break;
                case DARK:
                    currGame.getTeam(1).addPlayer(newPlayer);
                    break;
                default:
                    break;
            }
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public ArrayList<Player> getActivePlayers(){
        ArrayList<Player> activePlayers = new ArrayList<Player>();

        for(Player p: players){
            if(p.isActive())
                activePlayers.add(p);
        }

        System.out.println("active players: " + activePlayers.size());
        return activePlayers;
    }

    public boolean removePlayer(int playerID){
        for (Player p: players){
            if(p.matchID(playerID)){
                players.remove(p);
                return true;
            }
        }

        return false;
    }

    // team operations
    public void removeFromTeam(Player player){
        // just delete the player from both teams.
        currGame.getTeam(0).removePlayer(player);
        currGame.getTeam(1).removePlayer(player);
    }

    public void addPlayerToTeam(Player p, int team){
        currGame.getTeam(0).addPlayer(p);
    }

    public Game getCurrGame(){
        return currGame;
    }
}
