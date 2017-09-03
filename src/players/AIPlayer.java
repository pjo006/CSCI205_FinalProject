/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 8, 2015
 * Time: 6:09:56 PM
 *
 * Project: csci205_finalproject
 * Package: players
 * File: AIPlayer
 * Description:
 * An extension of the Player class that represents an AI controlled Player
 * ****************************************
 */
package players;

/**
 *
 * @author dah054
 */
public class AIPlayer extends Player {

    private Difficulty difficulty;

    public AIPlayer(PlayerColor color, Difficulty difficulty, String name) {
        super(color);
        this.difficulty = difficulty;
        this.name = name + " (AI)";
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

}
