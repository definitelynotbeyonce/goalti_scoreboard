package com.example.user_name.goalti_scoreboard;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import game_logic.Goaltimate;

/**
 * Created by user_name on 2/23/2017.
 */

public class StatsFragment extends Fragment {
    private Goaltimate game_logic;

    static private String[][] dummy_players = {
            {"knowles", "beyonce.jpg", "scott.runyon42@gmail.com"},
            {"game_logic.Player 1", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 2", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 3", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 4", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 5", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 6", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 7", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 8", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 9", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 10", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 11", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 12", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 13", "dummy-profile-pic.png", "email@site.com"},
            {"game_logic.Player 14", "dummy-profile-pic.png", "email@site.com"}
    };

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static StatsFragment newInstance(int position, Goaltimate game_logic){
        StatsFragment fragment = new StatsFragment();
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
        View v = inflater.inflate(R.layout.stats_layout, container, false);

        return v;
    }

}
