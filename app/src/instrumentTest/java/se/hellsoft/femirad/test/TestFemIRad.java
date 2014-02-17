package se.hellsoft.femirad.test;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import se.hellsoft.femirad.MainActivity;
import se.hellsoft.femirad.R;

/**
 * @author Erik Hellman
 */
public class TestFemIRad extends AndroidTestCase {

    private MainActivity.FemIRadFragment mFemIRadFragment;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mFemIRadFragment = new MainActivity.FemIRadFragment();
    }

    public void testIsWinnerColumn() throws Exception {
        int[] gameState = mFemIRadFragment.getGameState();

        gameState[0] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[5] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[10] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[15] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[20] = MainActivity.FemIRadFragment.PLAYER_ONE;

        Assert.assertTrue("Expected player one to be winner!",
                mFemIRadFragment.isWinner(MainActivity
                        .FemIRadFragment.PLAYER_ONE));
    }

    public void testIsWinnerRow() throws Exception {
        int[] gameState = mFemIRadFragment.getGameState();

        gameState[5] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[6] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[7] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[8] = MainActivity.FemIRadFragment.PLAYER_ONE;
        gameState[9] = MainActivity.FemIRadFragment.PLAYER_ONE;

        Assert.assertTrue("Expected player one to be winner!",
                mFemIRadFragment.isWinner(MainActivity
                        .FemIRadFragment.PLAYER_ONE));
    }

    public void testUpdateGameStateFromButton() throws Exception {
        mFemIRadFragment.updateGameStateFromButton(R.id.cell_1_1);
        mFemIRadFragment.updateGameStateFromButton(R.id.cell_1_2);
        int[] gameState = mFemIRadFragment.getGameState();
        Assert.assertEquals(gameState[0],
                MainActivity.FemIRadFragment.PLAYER_ONE);
        Assert.assertEquals(gameState[1],
                MainActivity.FemIRadFragment.PLAYER_TWO);
    }
}
