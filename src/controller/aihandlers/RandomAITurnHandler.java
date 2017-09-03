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
 * Handler code for running a full Easy AI turn. This AI just randomly
 * places its armies before ending its turn
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
public class RandomAITurnHandler {

    private AIPlayer player;
    private RiskController theController;
    ArrayList<Territory> territoryList;

    public RandomAITurnHandler(AIPlayer player, RiskController theController) {
        this.player = player;
        this.theController = theController;
        this.territoryList = this.theController.getTerritoryList();
    }

    /**
     * Handle a turn in the setup phase, determining where to place an army
     */
    public void setupTurn() {
        if (this.theController.allTerrFilled()) {
            Random randNum = new Random();
            int terrIndex = randNum.nextInt(
                    player.getTerritories().size());
            Territory terr = (Territory) player.getTerritories().get(
                    terrIndex);
            this.theController.handleAIFortifyTerr(terr);
        } else {
            Random randNum = new Random();
            while (true) {
                int terrIndex = randNum.nextInt(this.territoryList.size());
                Territory terr = this.territoryList.get(terrIndex);
                if (terr.getOwner() == null) {
                    this.theController.handleAIClaimTerr(terr);
                    break;
                }
            }
        }
        this.theController.handleEndTurn();
    }

    /**
     * Handle a standard turn for an AI player
     */
    public void standardTurn() {
        // Play a set of RISK cards, if we have a set of three that can be traded in
        this.theController.handleRedeemCards();
        // Place all armies on the board
        while (this.player.getArmies() > 0) {
            Random randNum = new Random();
            int terrIndex = randNum.nextInt(
                    player.getTerritories().size());
            Territory terr = (Territory) player.getTerritories().get(
                    terrIndex);
            this.theController.handleAIFortifyTerr(terr);
        }
        transferToBorder();
        // While there are territories capable of launching an attack, make available attacks
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
        } while (canAttack);

        // Do one more round of transferring armies
        transferToBorder();

        // End the turn
        this.theController.handleEndTurn();

    }

    public void transferToBorder() {
        // Move armies within one space of a border territory to a border territory
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
