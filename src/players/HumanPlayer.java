/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 8, 2015
 * Time: 6:06:35 PM
 *
 * Project: csci205_finalproject
 * Package: players
 * File: HumanPlayer
 * Description:
 * An extension of the Player class that represents a Human Player
 * ****************************************
 */
package players;

/**
 *
 * @author dah054
 */
public class HumanPlayer extends Player {

    public HumanPlayer(PlayerColor color, String name) {
        super(color);
        this.name = name;
    }

}
