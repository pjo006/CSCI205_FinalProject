/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Dec 8, 2015
 * Time: 9:25:21 PM
 *
 * Project: csci205_finalproject
 * Package: territories
 * File: TerritoryTest
 * Description: Basic tests for the Territory class includes testing of functions
 * relating to the owner and armies in the territory
 *
 * ****************************************
 */
package territories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import players.HumanPlayer;
import players.Player;

/**
 *
 * @author akk009
 */
public class TerritoryTest {
    
    private Territory territory = null;
    private String territoryName = "Northwest Territory";
    private String continentName = "North America";
    private String[] adjTerritories = {"Alaska", "Alberta", "Ontario", "Greenland"};
    
    
    public TerritoryTest() {
    }
    
    @Before
    public void setUp() {
        territory = new Territory(territoryName,continentName,adjTerritories);
    }
    
    @After
    public void tearDown() {
        territory = null;
    }

    /**
     * Test of getOwner method, of class Territory. **************************
     */
    @Test
    public void testGetOwner() {
        System.out.println("getOwner");
        System.out.println("setOwner");
        Player player = new HumanPlayer(players.PlayerColor.BLACK, "Adam");
        territory.setOwner(player);
        Player resultPlayer = territory.getOwner();
        Assert.assertEquals(player, resultPlayer);
    }

    /**
     * Test of setOwner method, of class Territory. ****************************
     */
    @Test
    public void testSetOwner() {
        System.out.println("setOwner");
        Player player = new HumanPlayer(players.PlayerColor.BLACK, "Adam");
        territory.setOwner(player);
        Assert.assertEquals(player, territory.getOwner());
    }

    /**
     * Test of setArmies method, of class Territory. *********************
     */
    @Test
    public void testSetArmies() {
        System.out.println("setArmies");
        int armies = 5;
        territory.setArmies(armies);
        int actualArmies = territory.getNumArmies();
        Assert.assertEquals(actualArmies, 5);
    }

    /**
     * Test of decreaseArmies method, of class Territory. ******************
     */
    @Test
    public void testDecreaseArmies() {
        System.out.println("decreaseArmies");
        int armies = 5;
        territory.setArmies(armies);
        territory.decreaseArmies(3);
        int actualArmies = territory.getNumArmies();
        int expectedArmies = 2;
        Assert.assertEquals(expectedArmies, actualArmies);
    }

    /**
     * Test of increaseArmies method, of class Territory. *****************
     */
    @Test
    public void testIncreaseArmies() {
        System.out.println("increaseArmies");
        int armies = 5;
        territory.setArmies(armies);
        territory.increaseArmies(6);
        int actualArmies = territory.getNumArmies();
        int expectedArmies = 11;
        Assert.assertEquals(expectedArmies, actualArmies);
    }

    /**
     * Test of getNumArmies method, of class Territory. ********************
     */
    @Test
    public void testGetNumArmies() {
        System.out.println("getNumArmies");
        int expectedArmies = 0;
        int actualArmies = territory.getNumArmies();
        assertEquals(expectedArmies, actualArmies);
    }
    
}
