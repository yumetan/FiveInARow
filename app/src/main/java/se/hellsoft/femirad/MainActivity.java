package se.hellsoft.femirad;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new FemIRadFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class FemIRadFragment extends Fragment implements View.OnClickListener {

        public static final int PLAYER_ONE = 1;
        public static final int PLAYER_TWO = 2;
        public static final int[] BUTTON_BOARD_MAP = new int[] {
                R.id.cell_1_1, R.id.cell_1_2, R.id.cell_1_3, R.id.cell_1_4, R.id.cell_1_5, 
                R.id.cell_2_1, R.id.cell_2_2, R.id.cell_2_3, R.id.cell_2_4, R.id.cell_2_5, 
                R.id.cell_3_1, R.id.cell_3_2, R.id.cell_3_3, R.id.cell_3_4, R.id.cell_3_5, 
                R.id.cell_4_1, R.id.cell_4_2, R.id.cell_4_3, R.id.cell_4_4, R.id.cell_4_5, 
                R.id.cell_5_1, R.id.cell_5_2, R.id.cell_5_3, R.id.cell_5_4, R.id.cell_5_5, 
        };

        private int mCurrentPlayer = PLAYER_ONE;
        private int[] mGameState = new int[25];

        public FemIRadFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            GridLayout gridLayout = (GridLayout) rootView;

            int childCount = gridLayout.getChildCount();
            for(int i = 0; i < childCount; i++) {
                Button btn = (Button) gridLayout.getChildAt(i);
                btn.setOnClickListener(this);
            }

            return rootView;
        }

        @Override
        public void onClick(View v) {
            int buttonID = v.getId();
            updateGameStateFromButton(buttonID);

            String btnText = "";
            if(mCurrentPlayer == PLAYER_ONE) {
                btnText = "O";
            } else if(mCurrentPlayer == PLAYER_TWO) {
                btnText = "X";
            }

            ((Button) v).setText(btnText);

            if(isWinner(PLAYER_ONE)) {
                Toast.makeText(getActivity(),
                        "Player " + PLAYER_ONE + " wins!",
                        Toast.LENGTH_LONG).show();
            } else if(isWinner(PLAYER_TWO)) {
                Toast.makeText(getActivity(),
                        "Player " + PLAYER_TWO + " wins!",
                        Toast.LENGTH_LONG).show();
            }
        }

        public void updateGameStateFromButton(int buttonID) {
            int position = -1;
            for (int i = 0; i < BUTTON_BOARD_MAP.length; i++) {
                int mapId = BUTTON_BOARD_MAP[i];
                if(mapId == buttonID) {
                    position = i;
                    break;
                }
            }

            if(position != -1) {
                mGameState[position] = mCurrentPlayer;
            }

            mCurrentPlayer = mCurrentPlayer == PLAYER_ONE
                    ? PLAYER_TWO : PLAYER_ONE;
        }

        public boolean isWinner(int playerId) {

            for(int row = 0; row < mGameState.length; row+=5) {
                boolean winnerRow = true;
                for(int col = 0; col < 5; col++) {
                    if(mGameState[row + col] == playerId) {
                        winnerRow = true;
                    } else {
                        winnerRow = false;
                        break;
                    }
                }

                if (winnerRow) return true;
            }

            for (int col = 0; col < 5; col++) {
                boolean winnerCol = true;
                for( int row = 0; row < mGameState.length; row+=5) {
                    if(mGameState[col + row] == playerId) {
                        winnerCol = true;
                    } else {
                        winnerCol = false;
                        break;
                    }
                }

                if(winnerCol) return true;
            }

            return false;
        }

        public int[] getGameState() {
            return mGameState;
        }

        public void setGameState(int[] gameState) {
            mGameState = gameState;
        }
    }

}
