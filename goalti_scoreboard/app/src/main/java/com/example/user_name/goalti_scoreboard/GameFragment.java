package com.example.user_name.goalti_scoreboard;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import game_logic.Game;
import game_logic.Goal;
import game_logic.Goaltimate;
import game_logic.Player;
import game_logic.Team;

/**
 * Created by user_name on 2/23/2017.
 */

public class GameFragment extends Fragment {
    private Goaltimate game_logic;
    private int scoringMode;


    private enum PlayerSelectMode{
        GOAL, SUB, NA
    }
    private PlayerSelectMode playerSelectMode;
    private int playersSelected;

    // Subbing
    private PlayerPair playerPair;

    private Goal pendingGoal;

    // Buttons for visibility.
    ButtonState buttonState;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public GameFragment(){
        // create the new player fragment.
        scoringMode = 0;
        playerSelectMode = PlayerSelectMode.NA;
        playersSelected = 0;
        playerPair = new PlayerPair();
        pendingGoal = new Goal();
        buttonState = new ButtonState();
    }

    public static GameFragment newInstance(int position, Goaltimate game_logic){
        GameFragment fragment = new GameFragment();
        fragment.setGameLogic(game_logic);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void setGameLogic(Goaltimate game_logic){
        this.game_logic = game_logic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_layout, container, false);

        // user helper functions to fill content.
        fillTeams(v, inflater);
        registerListeners(v);

        // set the state.
        buttonState.setButtonStates(game_logic.getCurrGame().getGameState());

        return v;
    }

    private void fillTeams(View rootView, LayoutInflater inflater){
        // get the team
        int[] activePlayers = {R.id.game_light_active, R.id.game_dark_active};
        int[] benchPlayers = {R.id.game_light_bench, R.id.game_dark_bench};
        for(int i = 0; i < 2; ++i)
        {
            Team light = game_logic.getCurrGame().getTeam(i);
            ArrayList<Player> players = light.getPlayers();


            ViewGroup activeContainer = (ViewGroup)rootView.findViewById(activePlayers[i]);
            ViewGroup benchContainer = (ViewGroup)rootView.findViewById(benchPlayers[i]);
            for(Player p: players){
                // create the player view.
                LinearLayout playerView = (LinearLayout)inflater.inflate(R.layout.game_player_template, null);

                // image
                ImageView img = (ImageView)playerView.findViewById(R.id.game_player_img);
                try{
                    InputStream ims = getActivity().getAssets().open("images/" + p.getImgPath());
                    Drawable d = Drawable.createFromStream(ims, null);
                    img.setImageDrawable(d);
                } catch(IOException ex) {
                    System.out.println("file not found");
                }

                // name
                TextView textView = (TextView)playerView.findViewById(R.id.game_player_name);
                textView.setText(p.getName());

                // place it in the right container.
                if(light.playerIsActive(p)){
                    activeContainer.addView(playerView);
                }
                else{
                    benchContainer.addView(playerView);
                }

                // add the player listener.
                playerView.setOnClickListener(new playerClick(i, p, playerView));
            }
        }
    }

    private void registerListeners(View v){
        // subs
        Button b = (Button)v.findViewById(R.id.game_light_sub);
        b.setOnClickListener(new subBL(0));
        buttonState.setButton(b, ButtonState.SUB_HOME_INDEX);
        b = (Button)v.findViewById(R.id.game_dark_sub);
        b.setOnClickListener(new subBL(1));
        buttonState.setButton(b, ButtonState.SUB_AWAY_INDEX);


        // points
        b = (Button)v.findViewById(R.id.game_goal);
        b.setOnClickListener(new scoreBL(1));
        buttonState.setButton(b, ButtonState.GOAL_INDEX);
        b = (Button)v.findViewById(R.id.game_two_pointer);
        b.setOnClickListener(new scoreBL(2));
        buttonState.setButton(b, ButtonState.TWO_POINTER_INDEX);

        //TODO: undo
        b = (Button)v.findViewById(R.id.game_save_end);
        b.setOnClickListener(new TODOListener(v, "undo"));
        buttonState.setButton(b, ButtonState.END_GAME_INDEX);

        //TODO: start/end game
        b = (Button)v.findViewById(R.id.game_start);
        b.setOnClickListener(new startBL());
        buttonState.setButton(b, ButtonState.START_GAME_INDEX);
    }

    private class scoreBL implements View.OnClickListener{
        private int points;
        public scoreBL(int points){
            this.points = points;
        }

        @Override
        public void onClick(View v) {
            switch(game_logic.getCurrGame().getGameState()){
                case MID_GAME:
                    scoringMode = points;
                    playerPair.setTeam(PlayerPair.PENDING);
                    if(points == 1)
                        game_logic.getCurrGame().setGameState(Game.GAME_STATES.GOAL);
                    else if(points == 2)
                        game_logic.getCurrGame().setGameState(Game.GAME_STATES.TWO_POINTER);
                    break;
                case GOAL:
                case TWO_POINTER:
                    scoringMode = 0;
                    game_logic.getCurrGame().setGameState(Game.GAME_STATES.MID_GAME);
                    break;
                default:
                    break;

            }

            // set the button states.
            buttonState.setButtonStates(game_logic.getCurrGame().getGameState());
        }
    }

    private class endgameBL implements View.OnClickListener{
        public endgameBL(){
        }

        @Override
        public void onClick(View v) {
            //TODO: save the game.
            //TODO: create a new game.
            //TODO: change game state.
        }
    }

    private class subBL implements View.OnClickListener{
        int team;
        public subBL(int team){
            this.team = team;
        }

        @Override
        public void onClick(View v) {
            System.out.println("mode: sub");

            switch(game_logic.getCurrGame().getGameState()){
                case SUB_HOME:
                case SUB_AWAY:
                    // cancel the substitution
                    playerSelectMode = PlayerSelectMode.NA;
                    game_logic.getCurrGame().setGameState(Game.GAME_STATES.MID_GAME);
                    buttonState.setButtonStates(Game.GAME_STATES.MID_GAME);
                    break;
                case MID_GAME:
                    // start a substitution
                    playerSelectMode = PlayerSelectMode.SUB;
                    playerPair.init();
                    if(team == 0){
                        playerPair.setTeam(PlayerPair.HOME);
                        game_logic.getCurrGame().setGameState(Game.GAME_STATES.SUB_HOME);
                        buttonState.setButtonStates(Game.GAME_STATES.SUB_HOME);
                    }
                    else if(team == 1){
                        playerPair.setTeam(PlayerPair.AWAY);
                        game_logic.getCurrGame().setGameState(Game.GAME_STATES.SUB_AWAY);
                        buttonState.setButtonStates(Game.GAME_STATES.SUB_AWAY);
                    }
                    break;
                default:
                    break;
            }

        }
    }

    private class playerClick implements View.OnClickListener{
        private int team;
        private Player player;
        private View container;

        public playerClick(int team, Player player, View container){
            this.team = team;
            this.player = player;
            this.container = container;
        }

        @Override
        public void onClick(View v) {

            // We're only going to do things when it's appropriate.
            if(playerPair.getTeam() != -1){
                System.out.println("player " + player.getName() + " clicked.");

                if(playerPair.canAddPlayer(player, team)){
                    System.out.println("adding player");
                    // Add this player to the pending pair.
                    playerPair.setTeam(this.team);
                    playerPair.setPlayer(player, container);

                    // Is the action complete?
                    if(playerPair.isFull()){
                        doAction();
                        playerPair.init();
                        //TODO: refresh everything?
                    }
                }
                else{
                    System.out.println("not adding player");
                }
            }
            else{
                System.out.println("ignoring player clicked.");
            }
        }
    }

    public void doAction(){
        System.out.println("go go action broncoe!");
        switch(playerSelectMode){
            case GOAL:
                //TODO: fill missing pieces of the goal
                // team
                int teamIndex = playerPair.getTeam();
                pendingGoal.setTeam(teamIndex);
                // offense players
                Team t = game_logic.getCurrGame().getTeam(teamIndex);
                pendingGoal.setOffense(t.getActivePlayers(), t.getPlayers());
                // defense
                t = game_logic.getCurrGame().getOtherTeam(teamIndex);
                pendingGoal.setDefense(t.getActivePlayers(), t.getPlayers());
                // copy the goal
                Goal newGoal = pendingGoal.getCopy();
                game_logic.getCurrGame().addGoal(newGoal);
                pendingGoal.initParams();
                break;
            case SUB:
                //TODO:
                subLayout();
                subBackend();
                break;
            default:
                break;
        }

        // Reset fields
        game_logic.getCurrGame().setGameState(Game.GAME_STATES.MID_GAME);
        buttonState.setButtonStates(Game.GAME_STATES.MID_GAME);
        playerPair.init();
    }

    private class TODOListener implements View.OnClickListener {
        View rootView;
        String title;

        public TODOListener(View v, String title){
            rootView = v;
            this.title = title;
        }

        @Override
        public void onClick(View v) {
            // Cast to button.
            AlertDialog alertDialog = new AlertDialog.Builder(rootView.getContext()).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage("TODO");

            alertDialog.show();
        }

    }

    private class PlayerPair {
        private int playerIndex;
        private int team;
        public static final int PENDING = -2;
        public static final int NA = -1;
        public static final int HOME = 0;
        public static final int AWAY = 1;



        public Player[] players;
        public View[] containers;

        public PlayerPair(){
            init();
        }

        //TODO:
        public boolean canAddPlayer(Player p, int team){
            switch(this.team) {
                case -2:
                    return true;
                case HOME:
                case AWAY:
                    return (this.team == team) && (!playerIsAlreadySelected(p));
                default:
                    return false;
            }
        }

        // check if a player is already selected.
        private boolean playerIsAlreadySelected(Player player){
            for(Player p: players){
                if(player == p)
                    return true;
            }

            return false;
        }

        public void init(){
            playerIndex = -1;
            team = NA;

            players = new Player[2];
            players[0] = players[1] = null;
            containers = new View[2];
            containers[0] = containers[1] = null;
        }

        public void setPlayer(Player p, View v){
            if(playerIndex++ != 1){
                System.out.println("setting player: " + playerIndex);
                // not full.  let's fill it!
                players[playerIndex] = p;
                containers[playerIndex] = v;
            }
        }

        public boolean isFull(){
            return playerIndex == 1;
        }

        public int getTeam(){
            return this.team;
        }

        public void setTeam(int team){
            this.team = team;
        }
    }

    private void subLayout(){
        // Get parents
        ViewGroup firstParent = (ViewGroup) playerPair.containers[0].getParent();
        ViewGroup secondParent = (ViewGroup) playerPair.containers[1].getParent();

        // remove from parents
        firstParent.removeView(playerPair.containers[0]);
        secondParent.removeView(playerPair.containers[1]);

        // assign to new parents
        firstParent.addView(playerPair.containers[1]);
        secondParent.addView(playerPair.containers[0]);
    }

    private void subBackend(){
        // tell the game that the sub is happening.
        game_logic.getCurrGame().substitute(playerPair.getTeam(), playerPair.players[0], playerPair.players[1]);
    }

    private class startBL implements View.OnClickListener{
        @Override
        public void onClick(View v){

            game_logic.getCurrGame().getTeam(0).dummyFillActive();
            game_logic.getCurrGame().getTeam(1).dummyFillActive();

            game_logic.getCurrGame().setGameState(Game.GAME_STATES.MID_GAME);
            buttonState.setButtonStates(Game.GAME_STATES.MID_GAME);
        }
    }
}
