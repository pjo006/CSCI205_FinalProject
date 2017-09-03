/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 8, 2015
 * Time: 6:44:49 PM
 *
 * Project: csci205_finalproject
 * Package: model
 * File: RiskModel
 * Description:
 *
 * ****************************************
 */
package model;

import card.Card;
import card.CardUtility;
import java.util.ArrayList;
import java.util.Collections;
import players.AIPlayer;
import players.Difficulty;
import players.HumanPlayer;
import players.Player;
import players.PlayerColor;
import territories.ContinentChecker;
import territories.Territory;
import territories.TerritoryUtility;

/**
 *
 * @author akk009
 */
public class RiskModel {
    private ArrayList<Territory> territoryList;
    private ArrayList<Player> playerList;
    private ArrayList<Card> cardList;
    private ContinentChecker continentChecker; //used to calculate bonus troops from controlling a continent
    private int numPlayers = 6;
    private int numAI = 0;
    private Player currentPlayer;
    private Territory attackingTerr;
    private Territory defendingTerr;
    private Card lastCard;
    private boolean hasAttackableTerr;
    private boolean hasTransferrableTerr;
    private boolean isSetUp;
    private boolean isAttackFrom;
    private boolean isAttackOn;
    private int numAttackingArmies;
    private int numTransferArmies;
    private boolean isTransferFrom;
    private boolean isTransferTo;
    private Territory sendingTerr;
    private Territory recievingTerr;
    private boolean receivedCard;
    private int bonusPointer;

    /**
     * Constructor for the model class instantiates all the territories on the
     * board and all risk cards in the deck
     */
    public RiskModel() {
        this.territoryList = new TerritoryUtility().instantiateTerritories();
        this.cardList = new CardUtility().instantiateCards();
        Collections.shuffle(this.cardList);
        this.continentChecker = new ContinentChecker(this.territoryList);
        this.playerList = new ArrayList<Player>();
        this.receivedCard = false;
        this.bonusPointer = 0;
    }

    /**
     * Builds the list of players based on given names and number of human and
     * AI players
     *
     * @param names the list of names to be given to the players
     */
    public void setPlayers(String[] names) {
        int numHuman = numPlayers - numAI;
        PlayerColor[] colors = PlayerColor.values();
        for (int i = 0; i < numHuman; i++) {
            playerList.add(new HumanPlayer(colors[i], names[i]));
        }
        for (int i = numHuman; i < numPlayers; i++) {
            playerList.add(new AIPlayer(colors[i], Difficulty.HARD, names[i]));
        }
        startGame();
    }

    /**
     * Sets up the Model in the initial state of a new game of Risk.
     */
    public void startGame() {
        currentPlayer = playerList.get(0);
    }

    /**
     * Sets the current player to be the next player in the turn order.
     */
    public void nextTurn() {
        int currIndex = playerList.indexOf(currentPlayer);
        if (currIndex == playerList.size() - 1) {
            currentPlayer = playerList.get(0);
        } else {
            currentPlayer = playerList.get(currIndex + 1);
        }
    }
    /*
     private void handleAITurn() {
     refreshArmies();
     if (isSetUp && isTerritoryListOnes() == false) {
     claimAITerritory();
     } else if (isSetUp) {
     fortifyAITerritory();
     }
     if (isEndSetUp()) {
     this.isSetUp = false;
     }
     nextTurn();
     }

     private void claimAITerritory() {
     Random randNum = new Random();
     while (true) {
     int terrIndex = randNum.nextInt(this.territoryList.size());
     Territory terr = this.territoryList.get(terrIndex);
     if (terr.getOwner() == null) {
     terr.setOwner(currentPlayer);
     terr.increaseArmies(1);
     currentPlayer.decreaseArmies(1);
     currentPlayer.addTerritory(terr);
     break;
     }
     }
     }

     private void fortifyAITerritory() {
     Random randNum = new Random();
     int terrIndex = randNum.nextInt(currentPlayer.getTerritories().size());
     Territory terr = (Territory) currentPlayer.getTerritories().get(
     terrIndex);
     terr.increaseArmies(1);
     currentPlayer.decreaseArmies(1);
     }
     */

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    public ArrayList<Territory> getTerritoryList() {
        return territoryList;
    }

    /**
     * Returns the Territory object with name matching the given string
     *
     * @param name The name of the Territory to find
     * @return The territory with name matching the specified name
     */
    public Territory getTerritoryFromList(String name) {
        for (Territory terr : this.territoryList) {
            if (name.equals(terr.getName())) {
                return terr;
            }
        }
        return null;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setNumAI(int numAI) {
        this.numAI = numAI;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getNumAI() {
        return numAI;
    }

    /**
     * Returns the player object whose turn it currently is
     *
     * @return the Player currently taking their turn
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the index of the Player who is currently taking their turn in the
     * list of players
     *
     * @return The index of the Player whose turn it is
     */
    public int getCurrentPlayerIndex() {
        return playerList.indexOf(currentPlayer);
    }

    /**
     * At the start of the turn, this is called to set the armies a player has.
     * If the player has armies remaining, we are in the setup phase. Otherwise,
     * give the player armies to add to the board.
     */
    public void refreshArmies() {
        if (currentPlayer.getArmies() == 0) {
            currentPlayer.setArmies(calculateTurnArmies());
        }
    }

    /**
     * Returns the number of armies each player gets to start the game based on
     * the number of players in the game
     *
     * @return
     */
    public int calculateStartingArmies() {
        if (numPlayers == 2) {
            return 40;

            // Debugging only!
            // return 21;
        } else if (numPlayers == 3) {
            return 35;
        } else if (numPlayers == 4) {
            return 30;
        } else if (numPlayers == 5) {
            return 25;
        } else { //numPlayers == 6
            return 20;
        }
    }

    /**
     * Returns the number of armies a player should receive this turn. A player
     * receives 1 army for every 3 territories (rounded down) and bonus armies
     * for controlling entire continents (minimum 3 armies total)
     *
     * @return armies
     */
    private int calculateTurnArmies() {
        int armies = 0;
        armies += currentPlayer.getTerritories().size() / 3;

        if (armies < 3) {//minimum 3 armies
            armies = 3;
        }

        //code to check if the player gets bonuses from having an entire
        //continent under their control
        armies += continentChecker.calculateBonusArmies(currentPlayer);

        return armies;
    }

    /**
     * Handles rolling dice and calculating losses for an attack action
     *
     * @return An integer array representing the dice rolled
     */
    public int[] handleDiceRoll() {
        int numAttackDice = numAttackingArmies;
        int numDefenseDice;
        if (this.defendingTerr.getNumArmies() == 1) {
            numDefenseDice = 1;
        } else {
            numDefenseDice = 2;
        }
        int[] attackDice = DiceUtility.rollDice(numAttackDice);
        int[] defenseDice = DiceUtility.rollDice(numDefenseDice);
        // Compare the first set of dice
        int attackerLosses = 0;
        int defenderLosses = 0;
        if (attackDice[0] > defenseDice[0]) {
            defenderLosses++;
        } else {
            attackerLosses++;
        }
        // If there are enough armies for a second set of dice to compare:
        if (attackDice.length >= 2 && defenseDice.length >= 2) {
            if (attackDice[1] > defenseDice[1]) {
                defenderLosses++;
            } else {
                attackerLosses++;
            }
        }
        handleLosses(attackerLosses, defenderLosses);
        if (this.defendingTerr.getNumArmies() == 0) {
            conquerTerritory(numAttackDice - attackerLosses);
        }
        int[] totalDiceArray = new int[]{0, 0, 0, 0, 0};
        int i = 0;
        for (int d : attackDice) {
            totalDiceArray[i] = d;
            i++;
        }
        i = 3;
        for (int d : defenseDice) {
            totalDiceArray[i] = d;
            i++;
        }
        return totalDiceArray;
    }

    /**
     * Communicates losses from an attack to the attacking and defending
     * territories
     *
     * @param attackerLosses the number of armies lost by the attacking
     * territory
     * @param defenderLosses the number of armies lost by the defending
     * territory
     */
    public void handleLosses(int attackerLosses, int defenderLosses) {
        this.attackingTerr.decreaseArmies(attackerLosses);
        this.defendingTerr.decreaseArmies(defenderLosses);
    }

    /**
     * Transfers ownership of a territory between players if a territory has
     * been successfully conquered in an attack. Also adds and removes the
     * Territory from the appropriate Players' Territory Lists
     *
     * @param transferArmies The number of occupying armies being transfered
     * into the conquered territory
     */
    public void conquerTerritory(int transferArmies) {
        Player attacker = this.attackingTerr.getOwner();
        Player defender = this.defendingTerr.getOwner();
        defender.removeTerritory(defendingTerr);
        attacker.addTerritory(defendingTerr);
        defendingTerr.setOwner(attacker);
        defendingTerr.setArmies(transferArmies);
        attackingTerr.decreaseArmies(transferArmies);
        this.receivedCard = true;
    }

    /**
     * Communicates the transfer of armies to the sending and receiving
     * Territories.
     */
    public void handleTransfer() {
        sendingTerr.decreaseArmies(numTransferArmies);
        recievingTerr.increaseArmies(numTransferArmies);
    }

    /**
     * Checks to see if all players have exhausted their pool of armies in the
     * setup phase
     *
     * @return true, if all players have 0 armies remaining to place
     */
    public boolean isEndSetUp() {
        for (Player p : this.playerList) {
            if (p.getArmies() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if there are any AI players in the current game
     *
     * @return true, if there is at least one AI player in the game
     */
    public boolean hasAI() {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i) instanceof AIPlayer) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if all territories have been filled with armies
     *
     * @return true, if all territories on the board have been claimed by a
     * player
     */
    public boolean isTerritoryListOnes() {
        for (Territory terr : this.territoryList) {
            if (terr.getNumArmies() < 1) {
                return false;
            }
        }
        return true;
    }

    public boolean getHasAttackableTerr() {
        return hasAttackableTerr;
    }

    public void setHasAttackableTerr(boolean hasAttackableTerr) {
        this.hasAttackableTerr = hasAttackableTerr;
    }

    public boolean getHasTransferrableTerr() {
        return hasTransferrableTerr;
    }

    public void setHasTransferrableTerr(boolean hasTransferrableTerr) {
        this.hasTransferrableTerr = hasTransferrableTerr;
    }

    public boolean getIsSetUp() {
        return isSetUp;
    }

    public void setIsSetUp(boolean isSetUp) {
        this.isSetUp = isSetUp;
    }

    public boolean getIsAttackFrom() {
        return isAttackFrom;
    }

    public void setIsAttackFrom(boolean isAttackFrom) {
        this.isAttackFrom = isAttackFrom;
    }

    public Territory getAttackingTerr() {
        return attackingTerr;
    }

    public void setAttackingTerr(Territory attackingTerr) {
        this.attackingTerr = attackingTerr;
    }

    public boolean getIsAttackOn() {
        return isAttackOn;
    }

    public void setIsAttackOn(boolean isAttackOn) {
        this.isAttackOn = isAttackOn;
    }

    public boolean getIsTransferFrom() {
        return isTransferFrom;
    }

    public void setIsTransferFrom(boolean isTransferFrom) {
        this.isTransferFrom = isTransferFrom;
    }

    public boolean getIsTransferTo() {
        return isTransferTo;
    }

    public void setIsTransferTo(boolean isTransferTo) {
        this.isTransferTo = isTransferTo;
    }

    public Territory getSendingTerr() {
        return sendingTerr;
    }

    public void setSendingTerr(Territory sendingTerr) {
        this.sendingTerr = sendingTerr;
    }

    public Territory getRecievingTerr() {
        return recievingTerr;
    }

    public void setRecievingTerr(Territory recievingTerr) {
        this.recievingTerr = recievingTerr;
    }

    public Territory getDefendingTerr() {
        return defendingTerr;
    }

    public void setDefendingTerr(Territory defendingTerr) {
        this.defendingTerr = defendingTerr;
    }

    public int getNumAttackingArmies() {
        return numAttackingArmies;
    }

    public void setNumAttackingArmies(int numAttackingArmies) {
        this.numAttackingArmies = numAttackingArmies;
    }

    public int getNumTransferArmies() {
        return numTransferArmies;
    }

    public void setNumTransferArmies(int numTransferArmies) {
        this.numTransferArmies = numTransferArmies;
    }

    /**
     * At the end of a turn, a player receives a random RISK card if they conquered
     * at least one territory this turn
     */
    public void acquireCard() {
        if (this.receivedCard == true && this.currentPlayer.getCards().size()<=4) {

            if (this.lastCard == null) {
                this.lastCard = this.cardList.get(0);
                this.currentPlayer.addCard(lastCard);
            } else if ((this.cardList.indexOf(this.lastCard) + 1) % this.cardList.size() == 0) {
                Collections.shuffle(cardList);
                this.lastCard = this.cardList.get(0);
                this.currentPlayer.addCard(lastCard);
            } else {
                int index = this.cardList.indexOf(this.lastCard);
                this.lastCard = this.cardList.get(index + 1);
                this.currentPlayer.addCard(lastCard);
            }
        }
        this.receivedCard = false;
    }
    
    /**
     * When a player successfully turns in RISK cards, they receive bonus
     * armies based on the number of times RISK cards have been turn in
     * earlier this game.
     */
    private int calculateRiskCardsBonus() {
        int[] bonuses = {4,6,8,10,12};
        int bonusArmies = 0;
        if (this.bonusPointer < 5) {
            bonusArmies = bonuses[this.bonusPointer];
        } else {
            bonusArmies = 15 + (this.bonusPointer-5);
        }
        return bonusArmies;
    }
 
    /**
     * When a player turns cards in, they discard the valid 3 card combination
     * and gain armies according to the current bonus
     */
    public void handleCards() {
        this.currentPlayer.redeemCards();
        int bonus = calculateRiskCardsBonus();
        this.currentPlayer.increaseArmies(bonus);
    }
}
