/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Dec 6, 2015
 * Time: 9:11:04 PM
 *
 * Project: csci205_finalproject
 * Package: controller
 * File: AITurnHandler
 * Description:
 * Handler code for running a full Hard AI turn. This AI has some grasp about
 * what territories to fortify and attack rather than simply placing armies
 * randomly. The "stacker" refers to this method of stacking armies on territories
 * more likely to be attacked
 * ****************************************
 */
package controller.aihandlers;

import controller.RiskController;
import java.util.ArrayList;
import java.util.Random;
import players.AIPlayer;
import territories.Territory;

/**
 *
 * @author dah054
 */
public class StackerAITurnHandler {

    private AIPlayer player;
    private RiskController theController;
    ArrayList<Territory> territoryList;

    public StackerAITurnHandler(AIPlayer player, RiskController theController) {
        this.player = player;
        this.theController = theController;
        this.territoryList = this.theController.getTerritoryList();
    }

    /**
     * Handle a turn in the setup phase, determining where to place an army
     */
    public void setupTurn() {
        // If all territories have been claimed
        if (this.theController.allTerrFilled()) {
            // Select a random border territory to place the army
            while (true) {
                Random randNum = new Random();
                int terrIndex = randNum.nextInt(
                        player.getTerritories().size());
                Territory terr = (Territory) player.getTerritories().get(
                        terrIndex);
                if (isBorderTerritory(terr)) {
                    this.theController.handleAIFortifyTerr(terr);
                    break;
                }
            }
        } // Else, claim a new territory
        else {
            // If we are placing our first army, select an unoccupied continent
            if (player.getTerritories().isEmpty()) {
                Random randNum = new Random();
                while (true) {
                    int terrIndex = randNum.nextInt(this.territoryList.size());
                    Territory terr = this.territoryList.get(terrIndex);
                    if (terr.getOwner() == null) {
                        boolean isUnoccupiedContinent = true;
                        for (Territory otherTerr : territoryList) {
                            if (terr.getContinent() == otherTerr.getContinent() && otherTerr.getOwner() != null) {
                                isUnoccupiedContinent = false;
                                break;
                            }
                        }
                        if (isUnoccupiedContinent) {
                            this.theController.handleAIClaimTerr(terr);
                            break;
                        }
                    }
                }
            } // If we have no territories available next to the ones we own,
            // select a random one on the board
            else if (noAvailableAdjTerr()) {
                Random randNum = new Random();
                while (true) {
                    int terrIndex = randNum.nextInt(this.territoryList.size());
                    Territory terr = this.territoryList.get(terrIndex);
                    if (terr.getOwner() == null) {
                        this.theController.handleAIClaimTerr(terr);
                        break;
                    }
                }
            } // If we can claim a territory next to our own, do so
            else {
                ArrayList<Territory> ownedTerrs = player.getTerritories();
                for (Territory terr : ownedTerrs) {
                    boolean hasClaimed = false;
                    for (String adjTerrName : terr.getAdjTerritories()) {
                        Territory adjTerr = getTerrFromName(adjTerrName);
                        if (adjTerr.getOwner() == null) {
                            this.theController.handleAIClaimTerr(adjTerr);
                            hasClaimed = true;
                            break;
                        }
                    }
                    if (hasClaimed) {
                        break;
                    }
                }
            }
        }
        this.theController.handleEndTurn();
    }

    /**
     * Helper method for setupTurn(), checks to see if all territories adjacent
     * to ours are already claimed
     *
     * @return true, if all territories adjacent to any owned territory are
     * already claimed
     */
    private boolean noAvailableAdjTerr() {
        boolean noneAvailable = true;
        ArrayList<Territory> ownedTerrs = player.getTerritories();
        for (Territory terr : ownedTerrs) {
            for (String adjTerrName : terr.getAdjTerritories()) {
                Territory adjTerr = getTerrFromName(adjTerrName);
                if (adjTerr.getOwner() == null) {
                    noneAvailable = false;
                }
            }
        }
        return noneAvailable;
    }

    /**
     * Check to see if an owned territory borders at least one enemy-owned
     * territory
     *
     * @param terr the owned territory to check
     * @return true, if the territory borders an enemy-owned territory
     */
    private boolean isBorderTerritory(Territory terr) {
        for (String adjTerrName : terr.getAdjTerritories()) {
            Territory adjTerr = getTerrFromName(adjTerrName);
            if (adjTerr.getOwner() != player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handle a standard turn for an AI player
     */
    public void standardTurn() {
        // Play a set of RISK cards, if we have a set of three that can be traded in
        this.theController.handleRedeemCards();
        // Choose a border territory, and stack all of the round's new troops there
        Territory placementTerr = null;
        while (true) {
            Random randNum = new Random();
            int terrIndex = randNum.nextInt(
                    player.getTerritories().size());
            Territory terr = (Territory) player.getTerritories().get(
                    terrIndex);
            if (isBorderTerritory(terr)) {
                placementTerr = terr;
                break;
            }
        }
        while (this.player.getArmies() > 0) {
            this.theController.handleAIFortifyTerr(placementTerr);
        }
        // Move armies within one space of a border territory to a border territory
        transferToBorder();
        // While there are territories capable of launching an attack, make available attacks
        // After each attack, make sure troops have been moved to border territories
        ArrayList<Territory> ownedTerrs = player.getTerritories();
        boolean canAttack = false;
        do {
            canAttack = false;
            // For each territory the player owns
            for (Territory terr : ownedTerrs) {
                // Does this territory have enough armies to launch a meaningful attack?
                if (terr.getNumArmies() > 2) {
                    // Does this territory have an adjacent enemy territory?
                    for (String adjTerrName : terr.getAdjTerritories()) {
                        Territory adjTerr = getTerrFromName(adjTerrName);
                        if (adjTerr.getOwner() != player) {
                            // If it is owned by an enemy, we can make an attack
                            canAttack = true;
                            startAttack(terr, adjTerr);
                            break;
                        }
                    }
                    if (canAttack) {
                        break;
                    }
                }
            }
            transferToBorder();
        } while (canAttack);

        // End the turn
        this.theController.handleEndTurn();

    }

    /**
     * Moves all troops within one move of a border territory to that border
     * territory
     */
    public void transferToBorder() {
        ArrayList<Territory> borderTerrs = new ArrayList<>();
        ArrayList<Territory> nonBorderTerrs = new ArrayList<>();
        ArrayList<Territory> ownedTerrs = player.getTerritories();
        for (Territory terr : ownedTerrs) {
            boolean isBorderTerr = false;
            for (String adjTerrName : terr.getAdjTerritories()) {
                Territory adjTerr = getTerrFromName(adjTerrName);
                if (adjTerr.getOwner() != player) {
                    isBorderTerr = true;
                }
            }
            if (isBorderTerr) {
                borderTerrs.add(terr);
            } else {
                nonBorderTerrs.add(terr);
            }
        }
        for (Territory terr : nonBorderTerrs) {
            for (String adjTerrName : terr.getAdjTerritories()) {
                Territory adjTerr = getTerrFromName(adjTerrName);
                if (borderTerrs.contains(adjTerr)) {
                    int numArmiesToTransfer = terr.getNumArmies() - 1;
                    theController.handleAITransferArmies(terr, adjTerr,
                                                         numArmiesToTransfer);
                }
            }
        }
    }

    /**
     * Returns the territory object with name matching the string given
     *
     * @param name the name of the territory to look for
     * @return the territory with matching name
     */
    private Territory getTerrFromName(String name) {
        Territory terr = null;
        for (Territory t : territoryList) {
            if (t.getName() == name) {
                terr = t;
            }
        }
        return terr;
    }

    /**
     * Gathers all information needed to run an attack and sends it to the
     * controller to be done
     *
     * @param attacker The territory owned by this AI, doing the attacking
     * @param defender The territory to be attacked
     */
    private void startAttack(Territory attacker, Territory defender) {
        int numArmies = 3;
        if (attacker.getNumArmies() == 3) {
            numArmies = 2;
        }
        theController.handleAIAttack(attacker, defender, numArmies);
    }
}
