/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 12, 2015
 * Time: 6:11:39 PM
 *
 * Project: csci205_finalproject
 * Package: controller
 * File: RiskController
 * Description: The controller handles communication between the view and
 * the model
 *
 * ****************************************
 */
package controller;

import card.Card;
import controller.aihandlers.RandomAITurnHandler;
import controller.aihandlers.StackerAITurnHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.RiskModel;
import players.AIPlayer;
import players.Difficulty;
import players.Player;
import players.PlayerColor;
import territories.Territory;
import territories.TerritoryUtility;
import territories.TerritoryVisuals;
import view.MainView;

/**
 * Controller for the GUI of the game of Risk. Acts as a communicator between
 * the View and the Model
 *
 * @author Dale
 */
public class RiskController implements ActionListener {

    /**
     * The view component of the MVC setup
     */
    private MainView theView;
    private RiskModel theModel;
    private ArrayList<TerritoryVisuals> territoryVisualList = new ArrayList<TerritoryVisuals>();
    private ArrayList<JButton> terrBtnList = new ArrayList<JButton>();
    private ArrayList<JLabel> playerArmyLabels = new ArrayList<JLabel>();

    public RiskController(MainView view, RiskModel model) {
        this.theView = view;
        this.theModel = model;
        this.territoryVisualList = new TerritoryUtility().getTerritoryInfo(
                this.theView);
        getButtonList();
        initActionListeners();
        setTerrActionListeners();
    }

    /**
     * Create all action listeners that this controller will have to pay
     * attention to.
     */
    private void initActionListeners() {
        this.theView.getButtonStartGame().addActionListener(this);
        this.theView.getButtonArmies().addActionListener(this);
        this.theView.getButtonAttack().addActionListener(this);
        this.theView.getButtonOpenNumPlayers().addActionListener(this);
        this.theView.getButtonConfirmNumPlayers().addActionListener(this);
        this.theView.getButtonConfirmStartGame().addActionListener(this);
        this.theView.getButtonEndTurn().addActionListener(this);
        this.theView.getButtonRollDice().addActionListener(this);
        this.theView.getButtonConfrimAttackTransfer().addActionListener(this);
        this.theView.getButtonRiskCards().addActionListener(this);
        this.theView.getButtonExitCards().addActionListener(this);
        this.theView.getButtonTradeIn().addActionListener(this);
    }

    /**
     * Enables and disables text boxes on the Start Game menu based on the
     * number of players set for the game.
     */
    public void updateNameSelectionBoxes() {
        int numPlayers = theModel.getNumPlayers();
        this.theView.getTextP3Name().setEnabled(false);
        this.theView.getTextP4Name().setEnabled(false);
        this.theView.getTextP5Name().setEnabled(false);
        this.theView.getTextP6Name().setEnabled(false);
        if (numPlayers >= 3) {
            this.theView.getTextP3Name().setEnabled(true);
        }
        if (numPlayers >= 4) {
            this.theView.getTextP4Name().setEnabled(true);
        }
        if (numPlayers >= 5) {
            this.theView.getTextP5Name().setEnabled(true);
        }
        if (numPlayers == 6) {
            this.theView.getTextP6Name().setEnabled(true);
        }
    }

    /**
     * Set up the players in the Model, and update the View to reflect the
     * current players.
     */
    public void startGame() {
        this.theView.getButtonStartGame().setEnabled(false);
        int numPlayers = theModel.getNumPlayers();
        String[] names = new String[numPlayers];
        String p1Name = theView.getTextP1Name().getText();
        if ("".equals(p1Name)) {
            p1Name = NameGenUtility.generateName();
        }
        names[0] = p1Name;
        String p2Name = theView.getTextP2Name().getText();
        if ("".equals(p2Name)) {
            p2Name = NameGenUtility.generateName();
        }
        names[1] = p2Name;
        if (numPlayers >= 3) {
            String p3Name = theView.getTextP3Name().getText();
            if ("".equals(p3Name)) {
                p3Name = NameGenUtility.generateName();
            }
            names[2] = p3Name;
        }
        if (numPlayers >= 4) {
            String p4Name = theView.getTextP4Name().getText();
            if ("".equals(p4Name)) {
                p4Name = NameGenUtility.generateName();
            }
            names[3] = p4Name;
        }
        if (numPlayers >= 5) {
            String p5Name = theView.getTextP5Name().getText();
            if ("".equals(p5Name)) {
                p5Name = NameGenUtility.generateName();
            }
            names[4] = p5Name;
        }
        if (numPlayers == 6) {
            String p6Name = theView.getTextP6Name().getText();
            if ("".equals(p6Name)) {
                p6Name = NameGenUtility.generateName();
            }
            names[5] = p6Name;
        }
        this.theModel.setPlayers(names);
        updatePlayerList();
        updateCurrentPlayer();
        playerArmyLabels = theView.getLblPlayerArmies();
        for (int i = 0; i < numPlayers; i++) {
            this.theModel.getPlayerList().get(i).setArmies(
                    this.theModel.calculateStartingArmies());
            playerArmyLabels.get(i).setText(
                    "Armies: " + this.theModel.getPlayerList().get(i).getArmies());
        }
        this.theModel.setIsSetUp(true);
        this.theView.getButtonArmies().setEnabled(true);
    }

    /**
     * Updates the view to the current number of armies the player has
     * remaining. This is called after the player places an army.
     */
    public void armyRefresh() {
        for (int i = 0; i < theModel.getPlayerList().size(); i++) {
            playerArmyLabels.get(i).setText(
                    "Armies: " + this.theModel.getPlayerList().get(i).getArmies());
        }
    }

    /**
     * Update the player list in the View from the Model.
     */
    public void updatePlayerList() {
        int numPlayers = theModel.getNumPlayers();
        if (numPlayers >= 1) {
            String name = this.theModel.getPlayerList().get(0).getName();
            this.theView.getLblPlayer1().setText(name);
        }
        if (numPlayers >= 2) {
            String name = this.theModel.getPlayerList().get(1).getName();
            this.theView.getLblPlayer2().setText(name);
        }
        if (numPlayers >= 3) {
            String name = this.theModel.getPlayerList().get(2).getName();
            this.theView.getLblPlayer3().setText(name);
        }
        if (numPlayers >= 4) {
            String name = this.theModel.getPlayerList().get(3).getName();
            this.theView.getLblPlayer4().setText(name);
        }
        if (numPlayers >= 5) {
            String name = this.theModel.getPlayerList().get(4).getName();
            this.theView.getLblPlayer5().setText(name);
        }
        if (numPlayers == 6) {
            String name = this.theModel.getPlayerList().get(5).getName();
            this.theView.getLblPlayer6().setText(name);
        }
    }

    /**
     * Change the highlighted flag in the player panel of the model to reflect
     * the current turn.
     */
    public void updateCurrentPlayer() {
        int currIndex = this.theModel.getCurrentPlayerIndex();
        this.theView.getLblPlayer1Action().setEnabled(false);
        this.theView.getLblPlayer2Action().setEnabled(false);
        this.theView.getLblPlayer3Action().setEnabled(false);
        this.theView.getLblPlayer4Action().setEnabled(false);
        this.theView.getLblPlayer5Action().setEnabled(false);
        this.theView.getLblPlayer6Action().setEnabled(false);
        switch (currIndex) {
            case 0:
                this.theView.getLblPlayer1Action().setEnabled(true);
                break;
            case 1:
                this.theView.getLblPlayer2Action().setEnabled(true);
                break;
            case 2:
                this.theView.getLblPlayer3Action().setEnabled(true);
                break;
            case 3:
                this.theView.getLblPlayer4Action().setEnabled(true);
                break;
            case 4:
                this.theView.getLblPlayer5Action().setEnabled(true);
                break;
            case 5:
                this.theView.getLblPlayer6Action().setEnabled(true);
                break;
        }
    }

    /**
     * Ask the Model to roll dice for the current attack, then update the View
     * with the Dice rolled.
     */
    public void handleDiceRoll() {
        int[] diceResult = this.theModel.handleDiceRoll();
        updateDie(this.theView.getLblAttackDie1(), diceResult[0]);
        updateDie(this.theView.getLblAttackDie2(), diceResult[1]);
        updateDie(this.theView.getLblAttackDie3(), diceResult[2]);
        updateDie(this.theView.getLblDefendDie1(), diceResult[3]);
        updateDie(this.theView.getLblDefendDie2(), diceResult[4]);
        //Update the view on outcome of battle
        updateViewAfterBattle();
        this.theView.getButtonRollDice().setEnabled(false);
        // Reset attacking and defending territories to null
        this.theModel.setAttackingTerr(null);
        this.theModel.setDefendingTerr(null);
    }

    /**
     * Updates the attacking and defending territories in the view after a
     * battle has taken place.
     */
    public void updateViewAfterBattle() {
        // Update the number of armies
        Integer attackerRemaining = this.theModel.getAttackingTerr().getNumArmies();
        Integer defenderRemaining = this.theModel.getDefendingTerr().getNumArmies();
        JLabel attackerLabel = findTerrLbl(this.theModel.getAttackingTerr());
        JLabel defenderLabel = findTerrLbl(this.theModel.getDefendingTerr());
        attackerLabel.setText(attackerRemaining.toString());
        defenderLabel.setText(defenderRemaining.toString());
        // Update ownership of the defending province
        JLabel defenderIcon = findTerrIcon(this.theModel.getDefendingTerr());
        setTerrColor(this.theModel.getDefendingTerr().getOwner(), defenderIcon);
        updateRiskCards(this.theModel.getCurrentPlayer());
    }

    /**
     * Update the sending and receiving territories in the view after an army
     * transfer has taken place.
     */
    public void updateViewAfterTransfer() {
        // Update the number of armies
        Integer senderRemaining = this.theModel.getSendingTerr().getNumArmies();
        Integer recieverRemaining = this.theModel.getRecievingTerr().getNumArmies();
        JLabel senderLabel = findTerrLbl(this.theModel.getSendingTerr());
        JLabel recieverLabel = findTerrLbl(this.theModel.getRecievingTerr());
        senderLabel.setText(senderRemaining.toString());
        recieverLabel.setText(recieverRemaining.toString());
        this.theModel.setSendingTerr(null);
        this.theModel.setRecievingTerr(null);
        this.theModel.setNumTransferArmies(0);
    }

    /**
     * Updates the icon for a die in the Dice Panel of the View
     *
     * @param die the label to update
     * @param result the integer representing the result of this die roll, 0 if
     * the die was not rolled
     */
    public void updateDie(JLabel die, int result) {
        switch (result) {
            case 0:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhiteblank.png")));
                break;
            case 1:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhite1.png")));
                break;
            case 2:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhite2.png")));
                break;
            case 3:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhite3.png")));
                break;
            case 4:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhite4.png")));
                break;
            case 5:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhite5.png")));
                break;
            case 6:
                die.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/view/dicewhite6.png")));
                break;
        }
    }

    /**
     * Invokes the action performed by the user
     *
     * @param e The action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == theView.getButtonStartGame()) {
            //If the start game button is clicked
            this.theView.getStartGameDialog().setVisible(true);

        } else if (e.getSource() == theView.getButtonArmies()) {
            // if the armies button is clicked
            if (theModel.getCurrentPlayer().getArmies() > 0) {
                enableButtons(this.territoryVisualList);
                theView.getButtonAttack().setEnabled(false);
                theView.getButtonRiskCards().setEnabled(false);
                theView.getButtonEndTurn().setEnabled(false);
            } else {
                theModel.setIsTransferFrom(true);
                enableButtons(this.territoryVisualList);
                theView.getButtonAttack().setEnabled(false);
                theView.getButtonArmies().setEnabled(false);
                theView.getButtonEndTurn().setEnabled(false);
                theView.getButtonRiskCards().setEnabled(false);
            }
        } else if (terrBtnList.contains(e.getSource())) {
            //If one of the territory buttons are clicked
            //Find source button
            JButton terrBtn = (JButton) e.getSource();
            //Use button to find TerritoryVisuals Object
            TerritoryVisuals terrVis = null;
            terrVis = findTerrVisFromButton(terrBtn, terrVis);
            JLabel armyLbl = terrVis.getTerritoryArmies();
            //Find the territory that was clicked
            Territory terr = null;
            terr = findTerr(terrVis, terr);
            if (theModel.getIsSetUp()) {
                placeArmy(terr, armyLbl);
                if (terr.getOwner() == null) {
                    terr.setOwner(this.theModel.getCurrentPlayer());
                    this.theModel.getCurrentPlayer().addTerritory(terr);
                    setTerrColor(terr.getOwner(), terrVis.getTerritoryLogo());
                }
                this.theView.getButtonArmies().setEnabled(false);
                this.theView.getButtonEndTurn().setEnabled(true);
            } else if (theModel.getIsAttackFrom()) {
                disableButtons(this.terrBtnList);
                theModel.setIsAttackFrom(false);
                theModel.setIsAttackOn(true);
                theModel.setAttackingTerr(terr);
                enableButtons(this.territoryVisualList);
            } else if (theModel.getIsAttackOn()) {
                disableButtons(this.terrBtnList);
                //set defending terr
                theModel.setDefendingTerr(terr);
                //prompt for attacking armies
                this.theView.getAttackTransferDialog().setVisible(true);
                this.theView.getButtonConfrimAttackTransfer().setText("Attack!");
                this.theView.getLblAttackTransfer().setText(
                        "How many armies would you like to attack with?");
                if (this.theModel.getAttackingTerr().getNumArmies() == 2) {
                    this.theView.getComboNumArmies().setModel(
                            new javax.swing.DefaultComboBoxModel(
                                    new String[]{"1"}));
                } else if (this.theModel.getAttackingTerr().getNumArmies() == 3) {
                    this.theView.getComboNumArmies().setModel(
                            new javax.swing.DefaultComboBoxModel(
                                    new String[]{"2", "1"}));
                } else {
                    this.theView.getComboNumArmies().setModel(
                            new javax.swing.DefaultComboBoxModel(
                                    new String[]{"3", "2", "1"}));
                }
            } else if (theModel.getCurrentPlayer().getArmies() > 0) {
                placeArmy(terr, armyLbl);
                disableButtons(this.terrBtnList);
                this.theView.getButtonAttack().setEnabled(true);
                this.theView.getButtonEndTurn().setEnabled(true);
                this.theView.getButtonRiskCards().setEnabled(true);
            } else if (theModel.getIsTransferFrom()) {
                disableButtons(this.terrBtnList);
                theModel.setIsTransferFrom(false);
                theModel.setIsTransferTo(true);
                theModel.setSendingTerr(terr);
                enableButtons(this.territoryVisualList);
            } else if (theModel.getIsTransferTo()) {
                disableButtons(this.terrBtnList);
                // set the recieving territory
                theModel.setRecievingTerr(terr);
                //prompt for num armies to transfer
                this.theView.getAttackTransferDialog().setVisible(true);
                this.theView.getButtonConfrimAttackTransfer().setText("Transfer");
                this.theView.getLblAttackTransfer().setText(
                        "How many armies would you like to transfer?");
                int availableArmies = theModel.getSendingTerr().getNumArmies() - 1;
                String[] comboList = new String[availableArmies];
                int currArmy = availableArmies;
                for (int i = 0; i < availableArmies; i++) {
                    comboList[i] = String.valueOf(currArmy);
                    currArmy -= 1;
                }
                this.theView.getComboNumArmies().setModel(
                        new javax.swing.DefaultComboBoxModel(comboList));
            }

        } else if (e.getSource() == theView.getButtonOpenNumPlayers()) {
            //If the select number of players button is clicked
            this.theView.getNumPlayersDialog().setVisible(true);
            this.theView.getStartGameDialog().setVisible(false);

        } else if (e.getSource() == theView.getButtonConfirmNumPlayers()) {
            //Confirmation button is clicked
            // getSelectedIndex returns the index of the combo box.  The numPlayers box starts at 2
            int numPlayers = theView.getComboNumPlayers().getSelectedIndex() + 2;
            int aiPlayers = theView.getComboNumAIPlayers().getSelectedIndex();
            if (aiPlayers < numPlayers) {
                this.theModel.setNumPlayers(numPlayers);
                this.theModel.setNumAI(aiPlayers);
                updateNameSelectionBoxes();
                this.theView.getNumPlayersDialog().setVisible(false);
                this.theView.getStartGameDialog().setVisible(true);
            }

        } else if (e.getSource() == theView.getButtonConfirmStartGame()) {
            //Start game confirmation button is clicked
            startGame();
            this.theView.getStartGameDialog().setVisible(false);

        } else if (e.getSource() == theView.getButtonAttack()) {
            //Attack button is clicked
            theModel.setIsAttackFrom(true);
            enableButtons(this.territoryVisualList);
            theView.getButtonAttack().setEnabled(false);
            theView.getButtonArmies().setEnabled(false);
            theView.getButtonEndTurn().setEnabled(false);
            theView.getButtonRiskCards().setEnabled(false);
        } else if (e.getSource() == theView.getButtonEndTurn()) {
            handleEndTurn();
            this.theView.getButtonArmies().setEnabled(true);
            if (theModel.getIsSetUp()) {
                this.theView.getButtonEndTurn().setEnabled(false);
            } else {
                theView.getButtonAttack().setEnabled(true);
                theView.getButtonRiskCards().setEnabled(true);
            }
        } else if (e.getSource() == theView.getButtonRollDice()) {
            //Roll dice button has been clicked
            handleDiceRoll();
            //Disable dice button
            theView.getButtonRollDice().setEnabled(false);
            //Re-enable attack/move armies/risk card buttons
            theView.getButtonAttack().setEnabled(true);
            theView.getButtonArmies().setEnabled(true);
            theView.getButtonEndTurn().setEnabled(true);
            theView.getButtonRiskCards().setEnabled(true);
            //set isAttackOn to false, reset model properties
            theModel.setIsAttackFrom(false);
            theModel.setIsAttackOn(false);
        } else if (e.getSource() == theView.getButtonConfrimAttackTransfer()) {
            if (theModel.getIsAttackOn()) {
                int numAttackingArmies = Integer.parseInt(
                        (String) this.theView.getComboNumArmies().getSelectedItem());
                this.theModel.setNumAttackingArmies(numAttackingArmies);
                this.theView.getAttackTransferDialog().setVisible(false);
                // Enable the Dice Roll button
                this.theView.getButtonRollDice().setEnabled(true);
            } else if (theModel.getIsTransferTo()) {
                int numTransferArmies = Integer.parseInt(
                        (String) this.theView.getComboNumArmies().getSelectedItem());
                this.theModel.setNumTransferArmies(numTransferArmies);
                this.theView.getAttackTransferDialog().setVisible(false);
                // Transfer the Armies
                this.theModel.handleTransfer();
                // Update the View
                updateViewAfterTransfer();
                // Re-enable attack/move armies/risk card buttons
                theView.getButtonAttack().setEnabled(true);
                theView.getButtonArmies().setEnabled(true);
                theView.getButtonEndTurn().setEnabled(true);
                theView.getButtonRiskCards().setEnabled(true);
                //set the model transferFrom/transferTo flags to false
                theModel.setIsTransferFrom(false);
                theModel.setIsTransferTo(false);
            }
        } else if (e.getSource() == theView.getButtonRiskCards()) {
            theView.getRiskCardDialog().setVisible(true);
        } else if (e.getSource() == theView.getButtonExitCards()) {
            theView.getRiskCardDialog().setVisible(false);
        } else if (e.getSource() == theView.getButtonTradeIn()) {
            handleRedeemCards();
        }

    }

    /**
     * Handles the action taken when the trade-in button of the RiskCard dialog
     * is pressed. Checks if the player can trade in cards and calls the model
     * to retrieve the cards from the player and to distribute armies to the
     * player
     */
    public void handleRedeemCards() {
        if (theModel.getCurrentPlayer().canRedeemCards() == true) {
            theModel.handleCards();
            updateRiskCards(this.theModel.getCurrentPlayer());
            armyRefresh();
        }
    }

    /**
     * Handles the movement of the game onto the next turn when the end turn
     * button is pressed. Checks for and skips players who have been eliminated.
     * Displays a game over screen when only one player is left. If the next
     * player in the turn order is an AI, calls the handleAITurn() method.
     */
    public void handleEndTurn() {
        //Turn has been ended
        //Condition for exiting game set up
        Player lastPlayer = theModel.getCurrentPlayer();
        if (theModel.getIsSetUp() == true && theModel.isEndSetUp()) {
            theModel.setIsSetUp(false);
        }
        this.theModel.setIsAttackFrom(false);
        this.theModel.setIsAttackOn(false);
        this.theModel.setIsTransferFrom(false);
        this.theModel.setIsTransferTo(false);
        this.theModel.acquireCard(); //add card if earned
        this.theModel.nextTurn();
        updateRiskCards(this.theModel.getCurrentPlayer());
        // If this player has no armies, and we're not in setup, then they have been eliminated.
        // Skip them and move to the next player
        if (!theModel.getIsSetUp()) {
            while (theModel.getCurrentPlayer().getTerritories().isEmpty()) {
                this.theModel.nextTurn();
            }
        }
        // Run a check to see if we've looped around to the same player who just ended the turn.
        // If so, all other players have been eliminated, and this player is the winner.
        if (theModel.getCurrentPlayer() == lastPlayer) {
            JOptionPane.showMessageDialog(null, "We have a winner", "Winner",
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        this.theModel.refreshArmies();
        armyRefresh();
        updateCurrentPlayer();
        if (theModel.getCurrentPlayer() instanceof AIPlayer) {
            handleAITurn();
        }
    }

    /**
     * Finds the Territory object corresponding with a specified
     * TerritoryVisuals
     *
     * @param terrVis the TerritoryVisual to look for a matching Territory for
     * @param terr
     * @return the Territory matching the TerritoryVisuals
     */
    private Territory findTerr(TerritoryVisuals terrVis, Territory terr) {
        for (Territory t : this.theModel.getTerritoryList()) {
            if (t.getName() == terrVis.getTerritoryName()) {
                terr = t;
            }
        }
        return terr;
    }

    /**
     * Finds the TerritoryVisuals object corresponding with a specified
     * Territory Button
     *
     * @param terrBtn The Territory Button to find the matching TerritoryVisuals
     * for
     * @param terrVis
     * @return The TerritoryVisuals matching the Territory Button
     */
    private TerritoryVisuals findTerrVisFromButton(JButton terrBtn,
                                                   TerritoryVisuals terrVis) {
        for (int i = 0; i < this.territoryVisualList.size(); i++) {
            if (terrBtn == this.territoryVisualList.get(i).getTerritoryBtn()) {
                terrVis = this.territoryVisualList.get(i);
                break;
            }
        }
        return terrVis;
    }

    /**
     * Finds the TerritoryVisuals object corresponding with a specified
     * Territory
     *
     * @param terr The Territory to find the matching TerritoryVisuals for
     * @return The TerritoryVisuals matching the Territory
     */
    private TerritoryVisuals findTerrVisFromTerr(Territory terr) {
        TerritoryVisuals terrVis = null;
        for (int i = 0; i < this.territoryVisualList.size(); i++) {
            if (terr.getName() == this.territoryVisualList.get(i).getTerritoryName()) {
                terrVis = this.territoryVisualList.get(i);
                break;
            }
        }
        return terrVis;
    }

    /**
     * Finds the Territory Label object corresponding with a specified Territory
     *
     * @param terr The Territory to find the matching Territory Label for
     * @return the Territory Label matching the Territory
     */
    private JLabel findTerrLbl(Territory terr) {
        JLabel armyLabel = null;
        for (TerritoryVisuals terrVis : territoryVisualList) {
            if (terr.getName() == terrVis.getTerritoryName()) {
                armyLabel = terrVis.getTerritoryArmies();
            }
        }
        return armyLabel;
    }

    /**
     * Finds the Territory Icon object corresponding with a specified Territory
     *
     * @param terr The Territory to find the matching Territory Icon for
     * @return the Territory Icon matching the Territory
     */
    private JLabel findTerrIcon(Territory terr) {
        JLabel terrIcon = null;
        for (TerritoryVisuals terrVis : territoryVisualList) {
            if (terr.getName() == terrVis.getTerritoryName()) {
                terrIcon = terrVis.getTerritoryLogo();
            }
        }
        return terrIcon;
    }

    /**
     * Handles the placement of an army on the board. Updates the Territory and
     * Player objects, as well as the territory label and player numArmies label
     * in the GUI
     *
     * @param terr The territory to add an army to
     * @param armyLbl The territory label of the specified territory
     */
    private void placeArmy(Territory terr, JLabel armyLbl) {
        disableButtons(this.terrBtnList);
        terr.increaseArmies(1);
        Integer armies = terr.getNumArmies();
        armyLbl.setText(armies.toString());
        this.theModel.getCurrentPlayer().decreaseArmies(1);
        armyRefresh();
    }

    /**
     * Handles enabling certain on-territory buttons in all possible scenarios
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableButtons(ArrayList<TerritoryVisuals> territoryVisList) {
        if (theModel.getIsSetUp() && theModel.isTerritoryListOnes() == false) {
            enableEmptyTerrBtns(territoryVisList);
        } else if (theModel.getIsSetUp()) {
            enableCurrPlayerTerrBtns(territoryVisList);
        } else if (theModel.getIsAttackFrom()) {
            enableAttackFromTerrBtns(territoryVisList);
        } else if (theModel.getIsAttackOn()) {
            enableAttackOnTerrBtns(territoryVisList);
        } else if (theModel.getIsTransferFrom()) {
            enableTransferFromTerrBtns(territoryVisList);
        } else if (theModel.getIsTransferTo()) {
            enableTransferToTerrBtns(territoryVisList);
        } else {
            enableCurrPlayerTerrBtns(territoryVisList);
        }
    }

    /**
     * Enables on-territory buttons for any territory not currently owned by any
     * player
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableEmptyTerrBtns(
            ArrayList<TerritoryVisuals> territoryVisList) {
        for (int i = 0; i < territoryVisList.size(); i++) {
            if (this.theModel.getTerritoryFromList(
                    territoryVisList.get(i).getTerritoryName()).getNumArmies() == 0) {
                territoryVisList.get(i).getTerritoryBtn().setEnabled(true);
                territoryVisList.get(i).getTerritoryBtn().setContentAreaFilled(
                        true);
                territoryVisList.get(i).getTerritoryBtn().setText("Add Army");
            }
        }
    }

    /**
     * Enables on-territory buttons for any territory it is possible for this
     * player to make an attack from
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableAttackFromTerrBtns(
            ArrayList<TerritoryVisuals> territoryVisList) {
        for (int i = 0; i < territoryVisList.size(); i++) {
            if (this.theModel.getTerritoryFromList(
                    territoryVisList.get(i).getTerritoryName()).getOwner().equals(
                            theModel.getCurrentPlayer()) && Integer.parseInt(
                            territoryVisList.get(i).getTerritoryArmies().getText()) > 1) {
                territoryVisList.get(i).getTerritoryBtn().setEnabled(true);
                territoryVisList.get(i).getTerritoryBtn().setContentAreaFilled(
                        true);
                territoryVisList.get(i).getTerritoryBtn().setText(
                        "Attack From");
            }
        }
    }

    /**
     * Enables on-territory buttons for any territory it is possible for this
     * player to transfer an army out of
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableTransferFromTerrBtns(
            ArrayList<TerritoryVisuals> territoryVisList) {
        for (int i = 0; i < territoryVisList.size(); i++) {
            if (this.theModel.getTerritoryFromList(
                    territoryVisList.get(i).getTerritoryName()).getOwner().equals(
                            theModel.getCurrentPlayer()) && Integer.parseInt(
                            territoryVisList.get(i).getTerritoryArmies().getText()) > 1) {
                territoryVisList.get(i).getTerritoryBtn().setEnabled(true);
                territoryVisList.get(i).getTerritoryBtn().setContentAreaFilled(
                        true);
                territoryVisList.get(i).getTerritoryBtn().setText(
                        "Transfer From");
            }
        }
    }

    /**
     * Enables on-territory buttons for any territory it is possible for this
     * player to attack from the selected territory
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableAttackOnTerrBtns(
            ArrayList<TerritoryVisuals> territoryVisList) {
        //Creating list of attackable territories
        ArrayList<String> adjTerrNames = new ArrayList<String>();
        for (int i = 0; i < theModel.getAttackingTerr().getAdjTerritories().length; i++) {
            adjTerrNames.add(
                    theModel.getAttackingTerr().getAdjTerritories()[i]);
        }
        //Enable the buttons of attackable territories
        for (int i = 0; i < territoryVisList.size(); i++) {
            if (adjTerrNames.contains(
                    territoryVisList.get(i).getTerritoryName()) && !theModel.getTerritoryFromList(
                            territoryVisList.get(i).getTerritoryName()).getOwner().equals(
                            theModel.getCurrentPlayer())) {
                territoryVisList.get(i).getTerritoryBtn().setEnabled(true);
                territoryVisList.get(i).getTerritoryBtn().setContentAreaFilled(
                        true);
                territoryVisList.get(i).getTerritoryBtn().setText(
                        "Attack");
                theModel.setHasAttackableTerr(true);
            }
        }
        if (!theModel.getHasAttackableTerr()) {
            this.theView.getButtonAttack().setEnabled(true);
            this.theView.getButtonArmies().setEnabled(true);
            this.theView.getButtonRiskCards().setEnabled(true);
            this.theView.getButtonEndTurn().setEnabled(true);
            this.theModel.setAttackingTerr(null);
            this.theModel.setDefendingTerr(null);
            this.theModel.setIsAttackFrom(false);
            this.theModel.setIsAttackOn(false);
        }
        this.theModel.setHasAttackableTerr(false);
    }

    /**
     * Enables on-territory buttons for any territory it is possible for this
     * player to transfer armies to from the selected territory
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableTransferToTerrBtns(
            ArrayList<TerritoryVisuals> territoryVisList) {
        //Creating list of adjacent friendly territories
        ArrayList<String> adjTerrNames = new ArrayList<String>();
        for (int i = 0; i < theModel.getSendingTerr().getAdjTerritories().length; i++) {
            adjTerrNames.add(
                    theModel.getSendingTerr().getAdjTerritories()[i]);
        }
        //Enable the buttons of territories available for transfer
        for (int i = 0; i < territoryVisList.size(); i++) {
            if (adjTerrNames.contains(
                    territoryVisList.get(i).getTerritoryName()) && theModel.getTerritoryFromList(
                            territoryVisList.get(i).getTerritoryName()).getOwner().equals(
                            theModel.getCurrentPlayer())) {
                territoryVisList.get(i).getTerritoryBtn().setEnabled(true);
                territoryVisList.get(i).getTerritoryBtn().setContentAreaFilled(
                        true);
                territoryVisList.get(i).getTerritoryBtn().setText(
                        "Send");
                theModel.setHasTransferrableTerr(true);
            }
        }
        if (!theModel.getHasTransferrableTerr()) {
            this.theView.getButtonAttack().setEnabled(true);
            this.theView.getButtonArmies().setEnabled(true);
            this.theView.getButtonRiskCards().setEnabled(true);
            this.theView.getButtonEndTurn().setEnabled(true);
            this.theModel.setSendingTerr(null);
            this.theModel.setRecievingTerr(null);
            this.theModel.setIsTransferFrom(false);
            this.theModel.setIsTransferTo(false);
        }
        this.theModel.setHasTransferrableTerr(false);
    }

    /**
     * Enables on-territory buttons for any territory this player currently owns
     *
     * @param territoryVisList The list of TerritoryVisuals for every Territory
     * on the map
     */
    private void enableCurrPlayerTerrBtns(
            ArrayList<TerritoryVisuals> territoryVisList) {
        for (int i = 0; i < territoryVisList.size(); i++) {
            if (this.theModel.getTerritoryFromList(
                    territoryVisList.get(i).getTerritoryName()).getOwner().equals(
                            theModel.getCurrentPlayer())) {
                territoryVisList.get(i).getTerritoryBtn().setEnabled(true);
                territoryVisList.get(i).getTerritoryBtn().setContentAreaFilled(
                        true);
                territoryVisList.get(i).getTerritoryBtn().setText("Add Army");
            }
        }
    }

    /**
     * Disables all on-territory buttons
     *
     * @param territoryBtnList The list of every Territory Button on the map
     */
    private void disableButtons(ArrayList<JButton> territoryBtnList) {
        for (JButton btn : territoryBtnList) {
            btn.setEnabled(false);
            btn.setContentAreaFilled(false);
            btn.setText("");
        }
    }

    /**
     * Generates the Territory Button List from the TerritoryVisuals list.
     */
    private void getButtonList() {
        for (TerritoryVisuals t : territoryVisualList) {
            terrBtnList.add(t.getTerritoryBtn());
        }
    }

    /**
     * Adds action listeners for each Territory Button.
     */
    private void setTerrActionListeners() {
        for (JButton btn : this.terrBtnList) {
            btn.addActionListener(this);
        }
    }

    /**
     * Sets the color of the Territory Logo to match the color of the player who
     * owns it
     *
     * @param owner The player whose color we are matching
     * @param territoryLogo The territory logo to change the color of
     */
    private void setTerrColor(Player owner, JLabel territoryLogo) {
        PlayerColor color = owner.getColor();
        if (color == PlayerColor.BLACK) {
            territoryLogo.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/view/armylogo.png")));
        } else if (color == PlayerColor.BLUE) {
            territoryLogo.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/view/armylogoblue.png")));
        } else if (color == PlayerColor.GREEN) {
            territoryLogo.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/view/armylogogreen.png")));
        } else if (color == PlayerColor.GREY) {
            territoryLogo.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/view/armylogogrey.png")));
        } else if (color == PlayerColor.PINK) {
            territoryLogo.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/view/armylogopink.png")));
        } else {
            territoryLogo.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/view/armylogored.png")));
        }
    }
    /*
     private void handleAIChanges() {
     for (int i = 0; i < theModel.getPlayerList().size(); i++) {
     if (theModel.getPlayerList().get(i) instanceof AIPlayer) {
     //                try {
     //                    TimeUnit.MILLISECONDS.sleep(500);
     //                } catch (Exception e) {
     //
     //                }
     LinkedList<Territory> terrs = theModel.getPlayerList().get(i).getTerritories();
     updateAITerritories(terrs, theModel.getPlayerList().get(i));
     }
     }
     }

     private void updateAITerritories(LinkedList<Territory> terrs, Player player) {
     for (int i = 0; i < this.territoryVisualList.size(); i++) {
     if (terrs.contains(theModel.getTerritoryFromList(
     this.territoryVisualList.get(i).getTerritoryName()))) {
     TerritoryVisuals terrVis = this.territoryVisualList.get(i);
     setTerrColor(player, terrVis.getTerritoryLogo());
     setAIArmies(terrVis.getTerritoryArmies(),
     theModel.getTerritoryFromList(
     this.territoryVisualList.get(i).getTerritoryName()));
     }
     }
     }

     private void setAIArmies(JLabel territoryArmies,
     Territory terr) {
     Integer numArmies = terr.getNumArmies();
     territoryArmies.setText(numArmies.toString());
     }
     */

    /**
     * Calls the Model method isTerritoryListOnes to determine if all
     * territories on the board have at least one army
     *
     * Intended for use by AI Handler
     *
     * @return if all territories on the board have been claimed by a player
     */
    public boolean allTerrFilled() {
        return theModel.isTerritoryListOnes();
    }

    /**
     * Method that allows the AI to access the list of territories in the game
     *
     * @return The territory list currently held by the Model
     */
    public ArrayList<Territory> getTerritoryList() {
        return this.theModel.getTerritoryList();
    }

    /**
     * Claims a territory for the AI in the setup phase of the game
     *
     * @param terr The territory to claim
     */
    public void handleAIClaimTerr(Territory terr) {
        JLabel terrLbl = findTerrLbl(terr);
        placeArmy(terr, terrLbl);
        terr.setOwner(this.theModel.getCurrentPlayer());
        this.theModel.getCurrentPlayer().addTerritory(terr);
        TerritoryVisuals terrVis = findTerrVisFromTerr(terr);
        setTerrColor(terr.getOwner(), terrVis.getTerritoryLogo());
    }

    /**
     * Fortifies a territory using an army an AI player holds
     *
     * @param terr the territory to fortify
     */
    public void handleAIFortifyTerr(Territory terr) {
        JLabel terrLbl = findTerrLbl(terr);
        placeArmy(terr, terrLbl);
    }

    /**
     * Transfers armies between territories owned by the AI
     *
     * @param sender the territory sending the armies
     * @param reciever the territory receiving the armies
     */
    public void handleAITransferArmies(Territory sender, Territory reciever,
                                       int numArmies) {
        theModel.setSendingTerr(sender);
        theModel.setRecievingTerr(reciever);
        theModel.setNumTransferArmies(numArmies);
        theModel.handleTransfer();
        updateViewAfterTransfer();
    }

    /**
     * Sends a method call to the appropriate AI handler based on AI difficulty
     * and phase of the game.
     */
    private void handleAITurn() {
        AIPlayer currPlayer = (AIPlayer) theModel.getCurrentPlayer();
        if (currPlayer.getDifficulty() == Difficulty.EASY) {
            RandomAITurnHandler AI = new RandomAITurnHandler(
                    (AIPlayer) theModel.getCurrentPlayer(), this);
            if (theModel.getIsSetUp()) {
                AI.setupTurn();
            } else {
                AI.standardTurn();
            }
        } else if (currPlayer.getDifficulty() == Difficulty.HARD) {
            StackerAITurnHandler AI = new StackerAITurnHandler(
                    (AIPlayer) theModel.getCurrentPlayer(), this);
            if (theModel.getIsSetUp()) {
                AI.setupTurn();
            } else {
                AI.standardTurn();
            }
        }
    }

    /**
     * Handles an attack command made by an AI player
     *
     * @param attacker the attacking territory, owned by the current AI player
     * @param defender the defending territory
     * @param numArmies the number of armies attacking
     */
    public void handleAIAttack(Territory attacker, Territory defender,
                               int numArmies) {
        theModel.setAttackingTerr(attacker);
        theModel.setDefendingTerr(defender);
        theModel.setNumAttackingArmies(numArmies);
        handleDiceRoll();
    }

    /**
     * Updates the GUI risk card panel to reflect the hand of the current
     * player.
     *
     * @param currentPlayer The current player whose risk cards we are
     * displaying in the GUI
     */
    private void updateRiskCards(Player currentPlayer) {
        LinkedList<Card> cardList = currentPlayer.getCards();
        int numCards = cardList.size();
        final String defaultCard = "/view/defaultCard.jpg";
        switch (numCards) {
            case 0:
                setCardLbls(defaultCard, defaultCard, defaultCard, defaultCard,
                            defaultCard);
                break;
            case 1:
                setCardLbls(cardList.get(0).getImageLocation(), defaultCard,
                            defaultCard, defaultCard, defaultCard);
                break;
            case 2:
                setCardLbls(cardList.get(0).getImageLocation(), cardList.get(
                            1).getImageLocation(), defaultCard, defaultCard,
                            defaultCard);
                break;
            case 3:
                setCardLbls(cardList.get(0).getImageLocation(), cardList.get(
                            1).getImageLocation(),
                            cardList.get(2).getImageLocation(), defaultCard,
                            defaultCard);
                break;
            case 4:
                setCardLbls(cardList.get(0).getImageLocation(), cardList.get(
                            1).getImageLocation(),
                            cardList.get(2).getImageLocation(),
                            cardList.get(3).getImageLocation(), defaultCard);
                break;
            case 5:
                setCardLbls(cardList.get(0).getImageLocation(), cardList.get(
                            1).getImageLocation(),
                            cardList.get(2).getImageLocation(),
                            cardList.get(3).getImageLocation(),
                            cardList.get(4).getImageLocation());
                break;
        }
    }

    /**
     * Updates the image of each individual card in the Risk Card panel of the
     * GUI
     *
     * @param image1 the path for the image of the 1st card
     * @param image2 the path for the image of the 2nd card
     * @param image3 the path for the image of the 3rd card
     * @param image4 the path for the image of the 4th card
     * @param image5 the path for the image of the 5th card
     */
    private void setCardLbls(String image1, String image2, String image3,
                             String image4, String image5) {
        theView.getLblRiskCard1().setIcon(new javax.swing.ImageIcon(
                getClass().getResource(image1)));
        theView.getLblRiskCard2().setIcon(new javax.swing.ImageIcon(
                getClass().getResource(image2)));
        theView.getLblRiskCard3().setIcon(new javax.swing.ImageIcon(
                getClass().getResource(image3)));
        theView.getLblRiskCard4().setIcon(new javax.swing.ImageIcon(
                getClass().getResource(image4)));
        theView.getLblRiskCard5().setIcon(new javax.swing.ImageIcon(
                getClass().getResource(image5)));
    }

}
