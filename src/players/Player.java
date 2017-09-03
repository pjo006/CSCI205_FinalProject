/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 8, 2015
 * Time: 5:03:25 PM
 *
 * Project: csci205_finalproject
 * Package: players
 * File: Player
 * Description:
 * An abstraction of a single player in a game of Risk.  Designed to be extended
 * by HumanPlayer and AIPlayer classes.
 * ****************************************
 */
package players;

import card.Card;
import card.CardType;
import java.util.ArrayList;
import java.util.LinkedList;
import territories.Territory;

/**
 *
 * @author dah054, akk009
 */
public abstract class Player {

    PlayerColor color;
    String name;
    int armies;
    LinkedList<Card> myCards = new LinkedList<>();
    ArrayList<Territory> myTerritories = new ArrayList<>();

    public Player(PlayerColor color) {
        this.color = color;
        this.armies = 0;
    }

    /**
     * Getter method for the color attribute of this Player
     *
     * @return the color assigned to this player at creation
     */
    public PlayerColor getColor() {
        return this.color;
    }

    /**
     * Getter method for the Territories owned by this Player
     *
     * @return the Territories currently under this Player's control
     */
    public ArrayList getTerritories() {
        return myTerritories;
    }

    /**
     * Adds a specified territory to this Player's list of owned Territories
     *
     * @param t the territory to add
     */
    public void addTerritory(Territory t) {
        myTerritories.add(t);
    }

    /**
     * Removes a specified territory from this Player's
     *
     * @param t the territory lost
     */
    public void removeTerritory(Territory t) {
        myTerritories.remove(t);
    }

    /**
     * Getter method for the name assigned to this Player
     *
     * @return the name of this Player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the number of armies a player needs to add
     *
     * @param a
     */
    public void setArmies(int a) {
        this.armies = a;
    }

    /**
     * Returns the number of armies the player needs to add
     *
     * @return
     */
    public int getArmies() {
        return this.armies;
    }

    /**
     * Decrease the number of armies the player has left to add.
     * Called each time the player places an army
     */
    public void decreaseArmies(int num) {
        this.armies -= num;
    }

    /**
     * Increase the number of armies the player has left to add.
     * Called when bonus armies are earned from RISK cards
     */
    public void increaseArmies(int num) {
        this.armies += num;
    }

    /**
     * Getter method for the Risk cards currently in this Player's hand
     *
     * @return a list of the Risk cards this player currently holds
     */
    public LinkedList getCards() {
        return myCards;
    }

    /**
     * Adds a new Risk card to this Player's hand
     *
     * @param c the Card to add
     */
    public void addCard(Card c) {
        myCards.add(c);
    }

    /**
     * Removes the specified Risk card from this Player's hand
     *
     * @param c the Card to remove
     */
    public void removeCard(Card c) {
        myCards.remove(c);
    }

    /**
     * Checks if this player is the same as a different player by comparing
     * their colors
     *
     * @param o player
     * @return true if equal, false if unequal
     */
    public boolean equals(Player o) {
        return this.color == o.color;
    }

    /**
     * Determines if a player has a valid combination of RISK cards to turn in
     *
     * @return
     */
    public boolean canRedeemCards() {
        if (this.myCards.size() < 3) {
            return false;
        }

        int[] numTypes = numEachCardType();
        int numSoldier = numTypes[0];
        int numHorse = numTypes[1];
        int numCannon = numTypes[2];
        if (numSoldier > 2 || numHorse > 2 || numCannon > 2) {
            return true;
        } else if (numSoldier > 0 && numHorse > 0 && numCannon > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The player removes a valid combination of 3 RISK cards to gain more
     * armies this turn
     *
     * @return
     */
    public void redeemCards() {
        int[] numTypes = numEachCardType();
        int numSoldier = numTypes[0];
        int numHorse = numTypes[1];
        int numCannon = numTypes[2];

        if (numSoldier > 2) {
            removeSoldierCards();
        } else if (numHorse > 2) {
            removeHorseCards();
        } else if (numCannon > 2) {
            removeCannonCards();
        } else { //1 of each
            removeSetCards();
        }
    }
    
    /**
     * Returns the number of each card type a player has. This info is used to
     * determine if it is possible to turn in RISK cards
     *
     * @return
     */
    private int[] numEachCardType() {
        int numSoldier = 0;
        int numHorse = 0;
        int numCannon = 0;

        for (Card c : this.myCards) {
            if (c.getType() == CardType.CANNON) {
                numCannon++;
            } else if (c.getType() == CardType.HORSE) {
                numHorse++;
            } else if (c.getType() == CardType.SOLDIER) {
                numSoldier++;
            }
        }
        int[] types = {numSoldier, numHorse, numCannon};
        return types;
    }

    /**
     * Removes 3 soldier cards
     */
    private void removeSoldierCards() {
        int index = 0;
        int removed = 0;
        while (removed < 3) {
            if (this.myCards.get(index).getType() == CardType.SOLDIER) {
                this.myCards.remove(index);
                removed++;
            } else {
                index++;
            }
        }
    }

    /**
     * Removes 3 horse cards
     */
    private void removeHorseCards() {
        int index = 0;
        int removed = 0;
        while (removed < 3) {
            if (this.myCards.get(index).getType() == CardType.HORSE) {
                this.myCards.remove(index);
                removed++;
            } else {
                index++;
            }
        }
    }

    /**
     * Removes 3 cannon cards
     */
    private void removeCannonCards() {
        int index = 0;
        int removed = 0;
        while (removed < 3) {
            if (this.myCards.get(index).getType() == CardType.CANNON) {
                this.myCards.remove(index);
                removed++;
            } else {
                index++;
            }
        }
    }

    /**
     * Remove one of each card type (soldier, horse, and cannon)
     */
    private void removeSetCards() {
        int removed = 0;
        int index = 0;
        while (removed < 1) {
            if (this.myCards.get(index).getType() == CardType.SOLDIER) {
                this.myCards.remove(index);
                removed++;
            }
            index++;
        }
        index = 0;
        while (removed < 2) {
            if (this.myCards.get(index).getType() == CardType.HORSE) {
                this.myCards.remove(index);
                removed++;
            }
            index++;
        }
        index = 0;
        while (removed < 3) {
            if (this.myCards.get(index).getType() == CardType.CANNON) {
                this.myCards.remove(index);
                removed++;
            }
            index++;
        }
    }
}
