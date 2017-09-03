/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 19, 2015
 * Time: 6:28:58 PM
 *
 * Project: csci205_finalproject
 * Package: controller
 * File: NameGenUtility
 * Description:
 * Utility class designed to generate names for unnamed players
 * ****************************************
 */
package controller;

import java.util.Random;

/**
 * Utility class designed to generate names for unnamed players
 *
 * @author dah054
 */
public class NameGenUtility {

    /**
     * Utility method designed to generate a name for a player who did not
     * select one
     *
     * @return the new name of the Player
     */
    public static String generateName() {
        String[] nameList = {"Bashful", "Doc", "Dopey", "Grumpy", "Happy", "Sleepy", "Sneezey"};
        return nameList[new Random().nextInt(nameList.length)];
    }
}
