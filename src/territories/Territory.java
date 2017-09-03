/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 8, 2015
 * Time: 4:18:19 PM
 *
 * Project: csci205_finalproject
 * Package: risk
 * File: Territory
 * Description: A territory keeps track of the owner, number of armies,
 * the names of all adjacent countries, and what continent it's in
 *
 * ****************************************
 */
package territories;

import players.Player;

/**
 *
 * @author akk009
 */
public class Territory {

    private final String name;
    private final String continent;
    private final String[] adjTerritories;
    private Player owner;
    private int numArmies;

    /**
     * The constructor for a territory
     *
     * @param name
     * @param continent
     * @param adjTerritories
     */
    public Territory(String name, String continent, String[] adjTerritories) {
        this.name = name;
        this.continent = continent;
        this.adjTerritories = adjTerritories;
        this.owner = null;
        this.numArmies = 0;
    }

    /**
     * Returns the String name of the territory
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the String continent the territory belongs to
     *
     * @return continent
     */
    public String getContinent() {
        return this.continent;
    }

    /**
     * Return the list of the string names of the adjacent territories
     *
     * @return adjTerritories
     */
    public String[] getAdjTerritories() {
        return this.adjTerritories;
    }

    /**
     * Returns the player that currently controls the territory
     *
     * @return Player this.owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner of the territory
     *
     * @param player
     */
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Sets the number of armies in the territory
     *
     * @param armies
     */
    public void setArmies(int armies) {
        this.numArmies = armies;
    }

    /**
     * Decreases the number of armies in the territory, whether they were
     * defeated in battle or moved to a new territory
     *
     * @param decrease
     */
    public void decreaseArmies(int decrease) {
        this.numArmies -= decrease;
        //possibly throw exception if <1 army on territory to indicate
        //either removed too many armies or territory is captured
    }

    /**
     * Increases the number of armies in the territory, whether they were
     * received as new armies or moved from a different territory
     *
     * @param increase
     */
    public void increaseArmies(int increase) {
        this.numArmies += increase;
    }

    public int getNumArmies() {
        return numArmies;
    }
}
