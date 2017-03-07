package com.example.user_name.goalti_scoreboard;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import game_logic.Goaltimate;
import game_logic.Player;

/**
 * Created by user_name on 2/23/2017.
 */

public class PlayersFragment extends Fragment {
    private Goaltimate game_logic;

    private LinearLayout newPlayetLayout;
    private LinearLayout editPlayerLayout;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlayersFragment(){
        // create the new player fragment.
    }

    public static PlayersFragment newInstance(int position, Goaltimate game_logic){
        PlayersFragment fragment = new PlayersFragment();
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
        View v = inflater.inflate(R.layout.players_layout, container, false);

        // Get the container.
        LinearLayout player_list = (LinearLayout)v.findViewById(R.id.player_list);

        // Add the edit player to the top.
        View newPlayer = inflater.inflate(R.layout.edit_player_template, null);
        Button newB = (Button)newPlayer.findViewById(R.id.edit_player_cancel);
        newB.setOnClickListener(new TODOListener(v, "cancel"));
        newB = (Button)newPlayer.findViewById(R.id.edit_player_save);
        newB.setOnClickListener(new TODOListener(v, "save"));

        player_list.addView(newPlayer);


        // TODO: get this from the player list.
        for(Player player: game_logic.getPlayers()){
            // inflate.
            View playerView = inflater.inflate(R.layout.player_list_player_template, null);

            // Checkbox
            CheckBox cb = (CheckBox)playerView.findViewById(R.id.player_list_playing);
            cb.setChecked(player.isActive());
            cb.setOnClickListener(new PlayingCBL(player));

            // image
            ImageView img = (ImageView)playerView.findViewById(R.id.player_list_profile_img);
            try{
                InputStream ims = getActivity().getAssets().open("images/" + player.getImgPath());
                Drawable d = Drawable.createFromStream(ims, null);
                img.setImageDrawable(d);
            } catch(IOException ex) {
                System.out.println("file not found");
            }

            // name
            TextView textView = (TextView)playerView.findViewById(R.id.player_list_name);
            textView.setText(player.getName());

            // email
            textView = (TextView)playerView.findViewById(R.id.player_list_email);
            textView.setText(player.getEmail());

            // buttons
            Button playerB = (Button)playerView.findViewById(R.id.player_list_edit);
            playerB.setOnClickListener(new TODOListener(v, "edit"));
            playerB = (Button)playerView.findViewById(R.id.player_list_remove);
            playerB.setOnClickListener(new TODOListener(v, "remove"));

            // add it to the view
            player_list.addView(playerView);
        }

        return v;
    }
    public void editPlayer(){
        // move player into the proper spot
        // Change visibility

    }

    public void cancelEdit(){
        // clear fields
        // change visibility
    }

    public void savePlayer(){
        // modify proper fields
        // clear fields
        // change visibility
    }

    public void removePlayer(){
        //TODO: are you sure?
        // remove player from view
        // remove player from player list
    }

    private class editPlayerBL implements View.OnClickListener{
        public editPlayerBL(LinearLayout editingPlayer){

        }

        @Override
        public void onClick(View v) {
            // Cast to button.
            Button b = (Button) v;

            String operation = b.getText().toString().toLowerCase();
            if("save".equals(operation)){

            }else if("cancel".equals(operation)){
                //
            }else if("edit".equals(operation)){

            }else if("remove".equals(operation)){

            }
        }
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

    private class PlayingCBL implements View.OnClickListener {
        private Player player;

        public PlayingCBL(Player player){
            this.player = player;
        }

        @Override
        public void onClick(View v) {
            player.setActive(((CheckBox)v).isChecked());
        }
    }
}
