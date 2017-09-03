/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Dec 7, 2015
 * Time: 11:43:10 PM
 *
 * Project: csci205_finalproject
 * Package: model
 * File: RiskModelTest
 * Description:
 *
 * ****************************************
 */
package model;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import players.HumanPlayer;
import players.Player;
import players.PlayerColor;
import territories.Territory;

/**
 *
 * @author pjo006
 */
public class RiskModelTest {

    private RiskModel model = null;
    private String[] playerNames = {"Adam", "PJ", "Dale", "Prof King", "Prof Peck", "Bravman"};

    public RiskModelTest() {
    }

    @Before
    public void setUp() {
        model = new RiskModel();
        model.setPlayers(playerNames);
    }

    @After
    public void tearDown() {
        model = null;
    }

    /**
     * Test of nextTurn method, of class RiskModel.
     */
    @Test
    public void testNextTurn() {
        System.out.println("nextTurn");
        Player expected = new HumanPlayer(PlayerColor.RED, "PJ");
        String expectedName = expected.getName();
        model.nextTurn();
        String result = model.getCurrentPlayer().getName();
        Assert.assertEquals(expectedName, result);

    }

    /**
     * Test of calculateStartingArmies method, of class RiskModel.
     */
    @Test
    public void testCalculateStartingArmies() {
        System.out.println("calculateStartingArmies");
        int expected = 20;
        int result = model.calculateStartingArmies();
        Assert.assertEquals(expected, result);

    }

    /**
     * Test of handleLosses method, of class RiskModel.
     */
    @Test
    public void testHandleLosses() {
        System.out.println("handleLosses");
        String[] adjAlaska = {"Alberta", "Kamchatka", "Northwest Territory"};
        Territory Alaska = new Territory("Alaska", "North America", adjAlaska);

        String[] adjNWTerritory = {"Alaska", "Alberta", "Ontario", "Greenland"};
        Territory NWTerritory = new Territory("Northwest Territory",
                                              "North America", adjNWTerritory);
        model.setAttackingTerr(NWTerritory);
        model.setDefendingTerr(Alaska);
        model.getAttackingTerr().setArmies(5);
        model.getDefendingTerr().setArmies(7);
        int attackerLosses = 2;
        int defenderLosses = 3;
        int expectedAttackerArmies = 3;
        int expectedDefenderArmies = 4;
        model.handleLosses(attackerLosses, defenderLosses);
        int resultAttackerArmies = model.getAttackingTerr().getNumArmies();
        int resultDefenderArmies = model.getDefendingTerr().getNumArmies();
        Assert.assertEquals(expectedAttackerArmies, resultAttackerArmies);
        Assert.assertEquals(expectedDefenderArmies, resultDefenderArmies);

    }

    /**
     * Test of handleTransfer method, of class RiskModel.
     */
    @Test
    public void testHandleTransfer() {
        System.out.println("handleTransfer");
        String[] adjAlaska = {"Alberta", "Kamchatka", "Northwest Territory"};
        Territory Alaska = new Territory("Alaska", "North America", adjAlaska);

        String[] adjNWTerritory = {"Alaska", "Alberta", "Ontario", "Greenland"};
        Territory NWTerritory = new Territory("Northwest Territory",
                                              "North America", adjNWTerritory);

        NWTerritory.increaseArmies(10);
        Alaska.increaseArmies(3);

        model.setSendingTerr(NWTerritory);
        model.setRecievingTerr(Alaska);
        model.setNumTransferArmies(5);
        int nwExpected = 5;
        int alaskaExpected = 8;
        model.handleTransfer();
        int nwResult = model.getSendingTerr().getNumArmies();
        int alaskaResult = model.getRecievingTerr().getNumArmies();
        Assert.assertEquals(nwExpected, nwResult);
        Assert.assertEquals(alaskaExpected, alaskaResult);
    }

    /**
     * Test of isEndSetUp method, of class RiskModel.
     */
    @Test
    public void testIsEndSetUp() {
        System.out.println("isEndSetUp");
        Assert.assertTrue(model.isEndSetUp());
    }

    /**
     * Test of hasAI method, of class RiskModel.
     */
    @Test
    public void testHasAI() {
        System.out.println("hasAI");
        model.setNumAI(1);
        model.setNumPlayers(6);
        model.setPlayers(playerNames);
        Assert.assertTrue(model.hasAI());

    }

}
