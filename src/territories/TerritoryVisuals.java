/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 16, 2015
 * Time: 5:28:18 PM
 *
 * Project: csci205_finalproject
 * Package: territories
 * File: TerritoryVisuals
 * Description:
 * TerritoryVisuals links a territory by name to the Button, Label, and Logo in
 * the GUI that it corresponds to
 * ****************************************
 */
package territories;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author pjo006
 */
public class TerritoryVisuals {
    private JButton territoryBtn;
    private JLabel territoryLogo;
    private JLabel territoryArmies;
    private String territoryName;

    public TerritoryVisuals(JButton btn, JLabel logo, JLabel army, String name) {
        this.territoryArmies = army;
        this.territoryBtn = btn;
        this.territoryLogo = logo;
        this.territoryName = name;
    }

    public JButton getTerritoryBtn() {
        return territoryBtn;
    }

    public void setTerritoryBtn(JButton territoryBtn) {
        this.territoryBtn = territoryBtn;
    }

    public JLabel getTerritoryLogo() {
        return territoryLogo;
    }

    public void setTerritoryLogo(JLabel territoryLogo) {
        this.territoryLogo = territoryLogo;
    }

    public JLabel getTerritoryArmies() {
        return territoryArmies;
    }

    public void setTerritoryArmies(JLabel territoryArmies) {
        this.territoryArmies = territoryArmies;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

}
