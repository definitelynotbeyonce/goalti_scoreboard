package com.example.user_name.goalti_scoreboard;

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
import java.util.Random;

import game_logic.Goaltimate;
import game_logic.Player;

/**
 * Created by user_name on 2/23/2017.
 */

public class TeamsFragment extends Fragment {
    private Goaltimate game_logic;
    private View rootView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static TeamsFragment newInstance(int position, Goaltimate game_logic){
        TeamsFragment fragment = new TeamsFragment();
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
        rootView = inflater.inflate(R.layout.teams_layout, container, false);

        //
        LinearLayout unassignedPlayers = (LinearLayout)rootView.findViewById(R.id.unassigned_players);
        LinearLayout lightPlayers = (LinearLayout)rootView.findViewById(R.id.light_players);
        LinearLayout darkPlayers = (LinearLayout)rootView.findViewById(R.id.dark_players);

        Random rand = new Random();
        for(Player player: game_logic.getActivePlayers()){
            // inflate.
            View playerView = inflater.inflate(R.layout.team_list_player_template, null);

            // image
            ImageView img = (ImageView)playerView.findViewById(R.id.team_list_profile_img);
            try{
                InputStream ims = getActivity().getAssets().open("images/" + player.getImgPath());
                Drawable d = Drawable.createFromStream(ims, null);
                img.setImageDrawable(d);
            } catch(IOException ex) {
                System.out.println("file not found");
            }

            // name
            TextView textView = (TextView)playerView.findViewById(R.id.team_list_name);
            textView.setText(player.getName());

            // buttons!
            Button upper = (Button)playerView.findViewById(R.id.upper_button);
            upper.setOnClickListener(new teamBL((LinearLayout)playerView, player));
            Button lower = (Button)playerView.findViewById(R.id.lower_button);
            lower.setOnClickListener(new teamBL((LinearLayout)playerView, player));

            // add it to the view
            switch(player.getTeam())
            {
                case UNASSIGNED:
                    upper.setText(R.string.light);
                    lower.setText(R.string.dark);
                    unassignedPlayers.addView(playerView);
                    break;
                case LIGHT:
                    upper.setText(R.string.unassign);
                    lower.setVisibility(View.INVISIBLE);
                    lightPlayers.addView(playerView);
                    break;
                case DARK:
                    upper.setText(R.string.unassign);
                    lower.setVisibility(View.INVISIBLE);
                    darkPlayers.addView(playerView);
                    break;
                default:
                    break;
            }
        }

        return rootView;
    }

    public void changeTeamLayout(LinearLayout playerContainer, String operation){
        // Remove from the old view.
        ((ViewGroup)playerContainer.getParent()).removeView(playerContainer);

        // Move it to the new one.
        int id;
        LinearLayout buttonsContainer = (LinearLayout)playerContainer.findViewById(R.id.team_list_buttons);
        switch(operation.toLowerCase()){
            case "light":
                id = R.id.light_players;
                ((Button)buttonsContainer.findViewById(R.id.upper_button)).setText("unassign");
                buttonsContainer.findViewById(R.id.lower_button).setVisibility(View.INVISIBLE);
                break;
            case "dark":
                id = R.id.dark_players;
                ((Button)buttonsContainer.findViewById(R.id.upper_button)).setText("unassign");
                buttonsContainer.findViewById(R.id.lower_button).setVisibility(View.INVISIBLE);
                break;
            default:
                id = R.id.unassigned_players;
                ((Button)buttonsContainer.findViewById(R.id.upper_button)).setText("light");
                ((Button)buttonsContainer.findViewById(R.id.lower_button)).setText("dark");
                buttonsContainer.findViewById(R.id.lower_button).setVisibility(View.VISIBLE);
                break;
        }

        LinearLayout destinationList = (LinearLayout)rootView.findViewById(id);
        destinationList.addView(playerContainer);
    }

    public void changeTeamBackend(String team, Player player){
        game_logic.removeFromTeam(player);
        switch(team){
            case "light":
                game_logic.addPlayerToTeam(player, 0);
                player.setTeam(Player.TEAM.LIGHT);
                break;
            case "dark":
                game_logic.addPlayerToTeam(player, 1);
                player.setTeam(Player.TEAM.DARK);
                break;
            default:
                player.setTeam(Player.TEAM.UNASSIGNED);
                break;
        }
    }

    private class teamBL implements View.OnClickListener{
        private LinearLayout playerLayout;
        private Player player;

        public teamBL(LinearLayout playerLayout, Player player){
            this.playerLayout = playerLayout;
            this.player = player;
        }

        @Override
        public void onClick(View v) {
            // Cast to button.
            Button b = (Button) v;

            String operation = b.getText().toString();
            changeTeamLayout(playerLayout, operation);
            changeTeamBackend(operation, this.player);
        }

    }

}
