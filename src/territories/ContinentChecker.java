/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 12, 2015
 * Time: 9:47:33 PM
 *
 * Project: csci205_finalproject
 * Package: territories
 * File: ContinentChecker
 * Description: The continent checker looks to see if a player controls whole
 * continents, then returns the number of bonus armies they will receive
 *
 * ****************************************
 */
package territories;

import java.util.ArrayList;
import players.Player;

/**
 *
 * @author akk009
 */
public class ContinentChecker {
    private ArrayList<Territory> tList;

    /**
     * Constructor for the ContinentChecker class takes the list of all
     * territories that RiskModel knows
     *
     * @param territoryList
     */
    public ContinentChecker(ArrayList<Territory> territoryList) {
        this.tList = territoryList;
    }

    /**
     * Return the number of bonus armies the given player receives for
     * controlling entire continents
     *
     * @param Player p
     * @return the number of bonus armies received
     */
    public int calculateBonusArmies(Player p) {
        int bonus = 0;
        bonus += nAmericaBonus(p);
        bonus += sAmericaBonus(p);
        bonus += africaBonus(p);
        bonus += europeBonus(p);
        bonus += asiaBonus(p);
        bonus += australiaBonus(p);
        return bonus;
    }

    /**
     * The 0-8th territories in tList are the North America territories, so
     * check if the given player owns all of them. If they do, they get a bonus
     * 5 armies
     *
     * @param Player p
     * @return the number of bonus armies the player gets from North America
     */
    private int nAmericaBonus(Player p) {
        for (int i = 0; i <= 8; i++) {
            if (tList.get(i).getOwner().equals(p) == false) {
                return 0;
            }
        }
        return 5;
    }

    /**
     * The 9-12th territories in tList are the South America territories, so
     * check if the given player owns all of them. If they do, they get a bonus
     * 2 armies
     *
     * @param Player p
     * @return the number of bonus armies the player gets from South America
     */
    private int sAmericaBonus(Player p) {
        for (int i = 9; i <= 12; i++) {
            if (tList.get(i).getOwner().equals(p) == false) {
                return 0;
            }
        }
        return 2;
    }

    /**
     * The 13-18th territories in tList are the Africa territories, so check if
     * the given player owns all of them. If they do, they get a bonus 3 armies
     *
     * @param PLayer p
     * @return the number of bonus armies the player gets from Africa
     */
    private int africaBonus(Player p) {
        for (int i = 13; i <= 18; i++) {
            if (tList.get(i).getOwner().equals(p) == false) {
                return 0;
            }
        }
        return 3;
    }

    /**
     * The 19-25th territories in tList are the Europe territories, so check if
     * the given player owns all of them. If they do, they get a bonus 5 armies
     *
     * @param Player p
     * @return the number of bonus armies the player gets from Europe
     */
    private int europeBonus(Player p) {
        for (int i = 19; i <= 25; i++) {
            if (tList.get(i).getOwner().equals(p) == false) {
                return 0;
            }
        }
        return 5;
    }

    /**
     * The 26-37th territories in tList are the Asia territories, so check if
     * the given player owns all of them. If they do, they get a bonus 3 armies
     *
     * @param Player p
     * @return the number of bonus armies the player gets from Asia
     */
    private int asiaBonus(Player p) {
        for (int i = 26; i <= 37; i++) {
            if (tList.get(i).getOwner().equals(p) == false) {
                return 0;
            }
        }
        return 7;
    }

    /**
     * The 38-41st territories in tList are the Australia territories, so check
     * if the given player owns all of them. If they do, they get a bonus 2
     * armies
     *
     * @param Player p
     * @return the number of bonus armies the player gets from Australia
     */
    private int australiaBonus(Player p) {
        for (int i = 38; i <= 41; i++) {
            if (tList.get(i).getOwner().equals(p) == false) {
                return 0;
            }
        }
        return 2;
    }
}
