package com.example.user_name.goalti_scoreboard;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.EnumMap;

import game_logic.Game;

/**
 * Created by user_name on 2/27/2017.
 */

public class ButtonState {
    public static final int NUM_BUTTONS = 6;
    private Button[] buttons;   // 0: start game, 1: goal, 2: 2 pointer, 3: end save game, 4: sub home, 5: sub away

    public static final int START_GAME_INDEX = 0;
    public static final int GOAL_INDEX = 1;
    public static final int TWO_POINTER_INDEX = 2;
    public static final int END_GAME_INDEX = 3;
    public static final int SUB_HOME_INDEX = 4;
    public static final int SUB_AWAY_INDEX = 5;



    private EnumMap<Game.GAME_STATES, boolean[]> statesMap;

    public ButtonState(){
        boolean[] state;
        statesMap = new EnumMap<Game.GAME_STATES, boolean[]>(Game.GAME_STATES.class);

        // new game
        state = new boolean[]{true, false, false, false, false, false};
        statesMap.put(Game.GAME_STATES.NEW_GAME, state);

        // mid game
        state = new boolean[]{false, true,   true,   true,   true,   true};
        statesMap.put(Game.GAME_STATES.MID_GAME, state);

        // goal
        state = new boolean[]{false, true,   false,  false,  false,  false};
        statesMap.put(Game.GAME_STATES.GOAL, state);

        // two pointer
        state = new boolean[]{false, false,  true,   false,  false,  false};
        statesMap.put(Game.GAME_STATES.TWO_POINTER, state);

        // sub home
        state = new boolean[]{false, false,  false,  false,  true,   false};
        statesMap.put(Game.GAME_STATES.SUB_HOME, state);

        // sub away
        state = new boolean[]{false, false,  false,  false,  false,  true};
        statesMap.put(Game.GAME_STATES.SUB_AWAY, state);

        // Allocate the space for the buttons array
        buttons = new Button[NUM_BUTTONS];
    }

    public void setButton(Button b, int index){
        buttons[index] = b;
    }

    public void setButtonStates(Game.GAME_STATES state){
        boolean[] states = this.statesMap.get(state);
        for(int i = 0; i < buttons.length; ++i){
            buttons[i].setEnabled(states[i]);
        }
    }
}
