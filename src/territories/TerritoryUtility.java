/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dale Hartman, PJ Onusconich, and Adam Karl
 * Date: Nov 8, 2015
 * Time: 5:42:46 PM
 *
 * Project: csci205_finalproject
 * Package: risk
 * File: TerritoryUtility
 * Description: Instantiates the hardcoded web of territories. Each territory
 * knows its name, its continent, and the names of each adjacent territory
 *
 * ****************************************
 */
package territories;

import java.util.ArrayList;
import view.MainView;

/**
 *
 * @author akk009
 */
public class TerritoryUtility {

    /**
     * Generates the web of territories and outputs an ArrayList with all the
     * territories in the game
     *
     * @return ArrayList<Territory> territoryList
     */
    public ArrayList<Territory> instantiateTerritories() {

        ArrayList<Territory> territoryList = new ArrayList<Territory>();

        //North America
        String[] adjAlaska = {"Alberta", "Kamchatka", "Northwest Territory"};
        Territory Alaska = new Territory("Alaska", "North America", adjAlaska);
        territoryList.add(Alaska);

        String[] adjNWTerritory = {"Alaska", "Alberta", "Ontario", "Greenland"};
        Territory NWTerritory = new Territory("Northwest Territory",
                                              "North America", adjNWTerritory);
        territoryList.add(NWTerritory);

        String[] adjGreenland = {"Northwest Territory", "Ontario", "Quebec", "Iceland"};
        Territory Greenland = new Territory("Greenland", "North America",
                                            adjGreenland);
        territoryList.add(Greenland);

        String[] adjAlberta = {"Alaska", "Western United States", "Northwest Territory",
                               "Ontario"};
        Territory Alberta = new Territory("Alberta", "North America", adjAlberta);
        territoryList.add(Alberta);

        String[] adjOntario = {"Alberta", "Northwest Territory", "Western United States",
                               "Eastern United States", "Greenland", "Quebec"};
        Territory Ontario = new Territory("Ontario", "North America", adjOntario);
        territoryList.add(Ontario);

        String[] adjQuebec = {"Ontario", "Eastern United States", "Greenland"};
        Territory Quebec = new Territory("Quebec", "North America", adjQuebec);
        territoryList.add(Quebec);

        String[] adjWUnitedStates = {"Alberta", "Ontario", "Eastern United States",
                                     "Central America"};
        Territory WUnitedStates = new Territory("Western United States",
                                                "North America",
                                                adjWUnitedStates);
        territoryList.add(WUnitedStates);

        String[] adjEUnitedStates = {"Central America", "Western United States", "Ontario",
                                     "Quebec"};
        Territory EUnitedStates = new Territory("Eastern United States",
                                                "North America",
                                                adjEUnitedStates);
        territoryList.add(EUnitedStates);

        String[] adjCentralAmerica = {"Western United States", "Eastern United States",
                                      "Venezuela"};
        Territory CentralAmerica = new Territory("Central America",
                                                 "North America",
                                                 adjCentralAmerica);
        territoryList.add(CentralAmerica);

        //South America
        String[] adjVenezuela = {"Central America", "Peru", "Brazil"};
        Territory Venezuela = new Territory("Venezuela", "South America",
                                            adjVenezuela);
        territoryList.add(Venezuela);

        String[] adjPeru = {"Venezuela", "Brazil", "Argentina"};
        Territory Peru = new Territory("Peru", "South America", adjPeru);
        territoryList.add(Peru);

        String[] adjBrazil = {"Venezuela", "Peru", "Argentina", "North Africa"};
        Territory Brazil = new Territory("Brazil", "South America", adjBrazil);
        territoryList.add(Brazil);

        String[] adjArgentina = {"Peru", "Brazil"};
        Territory Argentina = new Territory("Argentina", "South America",
                                            adjArgentina);
        territoryList.add(Argentina);

        //Africa
        String[] adjNAfrica = {"Brazil", "Western Europe", "East Africa", "Congo",
                               "Southern Europe", "Egypt"};
        Territory NAfrica = new Territory("North Africa", "Africa", adjNAfrica);
        territoryList.add(NAfrica);

        String[] adjEgypt = {"North Africa", "Southern Europe", "Middle East",
                             "East Africa"};
        Territory Egypt = new Territory("Egypt", "Africa", adjEgypt);
        territoryList.add(Egypt);

        String[] adjEAfrica = {"North Africa", "Egypt", "Middle East", "Madagascar",
                               "South Africa", "Congo"};
        Territory EAfrica = new Territory("East Africa", "Africa", adjEAfrica);
        territoryList.add(EAfrica);

        String[] adjCongo = {"North Africa", "East Africa", "South Africa"};
        Territory Congo = new Territory("Congo", "Africa", adjCongo);
        territoryList.add(Congo);

        String[] adjSAfrica = {"Congo", "East Africa", "Madagascar"};
        Territory SAfrica = new Territory("South Africa", "Africa", adjSAfrica);
        territoryList.add(SAfrica);

        String[] adjMadagascar = {"South Africa", "East Africa"};
        Territory Madagascar = new Territory("Madagascar", "Africa",
                                             adjMadagascar);
        territoryList.add(Madagascar);

        //Europe
        String[] adjIceland = {"Greenland", "Great Britain", "Scandinavia"};
        Territory Iceland = new Territory("Iceland", "Europe", adjIceland);
        territoryList.add(Iceland);

        String[] adjGBritain = {"Iceland", "Scandinavia", "Western Europe", "Northern Europe"};
        Territory GBritain = new Territory("Great Britain", "Europe",
                                           adjGBritain);
        territoryList.add(GBritain);

        String[] adjScandinavia = {"Iceland", "Great Britain", "Northern Europe", "Ukraine"};
        Territory Scandinavia = new Territory("Scandinavia", "Europe",
                                              adjScandinavia);
        territoryList.add(Scandinavia);

        String[] adjUkraine = {"Scandinavia", "Ural", "Afghanistan", "Middle East",
                               "Southern Europe", "Northern Europe"};
        Territory Ukraine = new Territory("Ukraine", "Europe", adjUkraine);
        territoryList.add(Ukraine);

        String[] adjNEurope = {"Great Britain", "Scandinavia", "Ukraine", "Southern Europe",
                               "Western Europe"};
        Territory NEurope = new Territory("Northern Europe", "Europe",
                                          adjNEurope);
        territoryList.add(NEurope);

        String[] adjWEurope = {"Great Britain", "Northern Europe", "Southern Europe",
                               "North Africa"};
        Territory WEurope = new Territory("Western Europe", "Europe", adjWEurope);
        territoryList.add(WEurope);

        String[] adjSEurope = {"Western Europe", "Northern Europe", "Ukraine", "Middle East",
                               "Egypt", "North Africa"};
        Territory SEurope = new Territory("Southern Europe", "Europe",
                                          adjSEurope);
        territoryList.add(SEurope);

        //Asia
        String[] adjUral = {"Ukraine", "Siberia", "China", "Afghanistan"};
        Territory Ural = new Territory("Ural", "Asia", adjUral);
        territoryList.add(Ural);

        String[] adjSiberia = {"Ural", "Yakutsk", "Irkutsk", "Mongolia", "China"};
        Territory Siberia = new Territory("Siberia", "Asia", adjSiberia);
        territoryList.add(Siberia);

        String[] adjYakutsk = {"Siberia", "Kamchatka", "Irkutsk"};
        Territory Yakutsk = new Territory("Yakutsk", "Asia", adjYakutsk);
        territoryList.add(Yakutsk);

        String[] adjKamchatka = {"Yakutsk", "Alaska", "Japan", "Mongolia", "Irkutsk"};
        Territory Kamchatka = new Territory("Kamchatka", "Asia", adjKamchatka);
        territoryList.add(Kamchatka);

        String[] adjJapan = {"Kamchatka", "Mongolia"};
        Territory Japan = new Territory("Japan", "Asia", adjJapan);
        territoryList.add(Japan);

        String[] adjIrkutsk = {"Siberia", "Yakutsk", "Kamchatka", "Mongolia"};
        Territory Irkutsk = new Territory("Irkutsk", "Asia", adjIrkutsk);
        territoryList.add(Irkutsk);

        String[] adjMongolia = {"Irkutsk", "Kamchatka", "Japan", "China", "Siberia"};
        Territory Mongolia = new Territory("Mongolia", "Asia", adjMongolia);
        territoryList.add(Mongolia);

        String[] adjAfghanistan = {"Ukraine", "Ural", "China", "India", "Middle East"};
        Territory Afghanistan = new Territory("Afghanistan", "Asia",
                                              adjAfghanistan);
        territoryList.add(Afghanistan);

        String[] adjChina = {"Afghanistan", "Ural", "Siberia", "Mongolia", "Siam", "India"};
        Territory China = new Territory("China", "Asia", adjChina);
        territoryList.add(China);

        String[] adjMEast = {"Ukraine", "Afghanistan", "India", "East Africa", "Egypt",
                             "Southern Europe"};
        Territory MEast = new Territory("Middle East", "Asia", adjMEast);
        territoryList.add(MEast);

        String[] adjIndia = {"Afghanistan", "China", "Siam", "Middle East"};
        Territory India = new Territory("India", "Asia", adjIndia);
        territoryList.add(India);

        String[] adjSiam = {"India", "China", "Indonesia"};
        Territory Siam = new Territory("Siam", "Asia", adjSiam);
        territoryList.add(Siam);

        //Australia
        String[] adjIndonesia = {"Siam", "New Guinea", "Western Australia"};
        Territory Indonesia = new Territory("Indonesia", "Australia",
                                            adjIndonesia);
        territoryList.add(Indonesia);

        String[] adjNGuinea = {"Indonesia", "Western Australia", "Eastern Australia"};
        Territory NGuinea = new Territory("New Guinea", "Australia", adjNGuinea);
        territoryList.add(NGuinea);

        String[] adjWAustralia = {"Indonesia", "New Guinea", "Eastern Australia"};
        Territory WAustralia = new Territory("Western Australia", "Australia",
                                             adjWAustralia);
        territoryList.add(WAustralia);

        String[] adjEAustralia = {"Western Australia", "New Guinea"};
        Territory EAustralia = new Territory("Eastern Australia", "Australia",
                                             adjEAustralia);
        territoryList.add(EAustralia);

        return territoryList;
    }

    /**
     * Generates a list of TerritoryVisuals objects containing Territory Names
     * and the Logo, Label, and Button they are linked to
     *
     * @param theView The GUI containing the buttons, labels, and logos
     * @return a list of TerritoryVisuals objects
     */
    public ArrayList<TerritoryVisuals> getTerritoryInfo(MainView theView) {
        ArrayList<TerritoryVisuals> territoryVisualList = new ArrayList<TerritoryVisuals>();
        territoryVisualList.add(
                new TerritoryVisuals(theView.getButtonAfghanistan(),
                                     theView.getLblAfghanistanLogo(),
                                     theView.getLblAfghanistanArmies(),
                                     "Afghanistan"));

        territoryVisualList.add(new TerritoryVisuals(theView.getButtonAlaska(),
                                                     theView.getLblAlaskaLogo(),
                                                     theView.getLblAlaskaArmies(),
                                                     "Alaska"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonAlberta(),
                                                     theView.getLblAlbertaLogo(),
                                                     theView.getLblAlbertaArmies(),
                                                     "Alberta"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonArgentina(),
                theView.getLblArgentinaLogo(),
                theView.getLblArgentinaArmies(), "Argentina"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonBrazil(),
                                                     theView.getLblBrazilLogo(),
                                                     theView.getLblBrazilArmies(),
                                                     "Brazil"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonCentAm(),
                                                     theView.getLblCentAmLogo(),
                                                     theView.getLblCentAmArmies(),
                                                     "Central America"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonChina(),
                                                     theView.getLblChinaLogo(),
                                                     theView.getLblChinaArmies(),
                                                     "China"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonCongo(),
                                                     theView.getLblCongoLogo(),
                                                     theView.getLbCongoArmies(),
                                                     "Congo"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonEAfrica(),
                                                     theView.getLblEAfricaLogo(),
                                                     theView.getLblEAfricaArmies(),
                                                     "East Africa"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonEAustralia(),
                theView.getLblEAustraliaLogo(),
                theView.getLblEAustraliaArmies(), "Eastern Australia"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonEUS(),
                                                     theView.getLblEUSLogo(),
                                                     theView.getLblEUSArmies(),
                                                     "Eastern United States"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonEgypt(),
                                                     theView.getLblEgyptLogo(),
                                                     theView.getLblEgyptArmies(),
                                                     "Egypt"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonGreatBritain(),
                theView.getLblGreatBritainLogo(),
                theView.getLblGreatBritainArmies(), "Great Britain"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonGreenland(),
                theView.getLblGreenlandLogo(),
                theView.getLblGreenlandArmies(), "Greenland"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonIceland(),
                                                     theView.getLblIcelandLogo(),
                                                     theView.getLblIcelandArmies(),
                                                     "Iceland"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonIndia(),
                                                     theView.getLblIndiaLogo(),
                                                     theView.getLblIndiaArmies(),
                                                     "India"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonIndonesia(),
                theView.getLblIndonesiaLogo(),
                theView.getLblIndonesiaArmies(), "Indonesia"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonIrkutsk(),
                                                     theView.getLblIrkutskLogo(),
                                                     theView.getLblIrkutskArmies(),
                                                     "Irkutsk"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonJapan(),
                                                     theView.getLblJapanLogo(),
                                                     theView.getLblJapanArmies(),
                                                     "Japan"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonKamchatka(),
                theView.getLblKamchatkaLogo(),
                theView.getLblKamChatkaArmies(), "Kamchatka"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonMadagascar(),
                theView.getLblMadagascarLogo(),
                theView.getLblMadagascarArmies(), "Madagascar"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonMiddleEast(),
                theView.getLblMiddleEastLogo(),
                theView.getLblMiddleEastArmies(), "Middle East"));
        territoryVisualList.add(
                new TerritoryVisuals(theView.getButtonMongolia(),
                                     theView.getLblMongoliaLogo(),
                                     theView.getLblMongoliaArmies(), "Mongolia"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonNAfrica(),
                                                     theView.getLblNAfricaLogo(),
                                                     theView.getLblNAfricaArmies(),
                                                     "North Africa"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonNEurope(),
                                                     theView.getLbNEuropeLogo(),
                                                     theView.getLblNEuropeArmies(),
                                                     "Northern Europe"));
        territoryVisualList.add(
                new TerritoryVisuals(theView.getButtonNWTerritory(),
                                     theView.getLblNWTerritoryLogo(),
                                     theView.getLblNWTerritoryArmies(),
                                     "Northwest Territory"
                ));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonNewGuinea(),
                theView.getLblNewGuineaLogo(),
                theView.getLblNewGuineaArmies(), "New Guinea"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonOntario(),
                                                     theView.getLblOntarioLogo(),
                                                     theView.getLblOntarioArmies(),
                                                     "Ontario"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonPeru(),
                                                     theView.getLblPeruLogo(),
                                                     theView.getLblPeruArmies(),
                                                     "Peru"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonQuebec(),
                                                     theView.getLblQuebecLogo(),
                                                     theView.getLblQuebecArmies(),
                                                     "Quebec"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonSAfrica(),
                                                     theView.getLblSAfricaLogo(),
                                                     theView.getLblSAfricaArmies(),
                                                     "South Africa"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonSEurope(),
                                                     theView.getLblSEuropeLogo(),
                                                     theView.getLblSEuropeArmies(),
                                                     "Southern Europe"));
        territoryVisualList.add(
                new TerritoryVisuals(theView.getButtonScandanavia(),
                                     theView.getLblScandanaviaLogo(),
                                     theView.getLblScandanaviaArmies(),
                                     "Scandinavia"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonSiam(),
                                                     theView.getLblSiamLogo(),
                                                     theView.getLblSiamArmies(),
                                                     "Siam"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonSiberia(),
                                                     theView.getLblSiberiaLogo(),
                                                     theView.getLblSiberiaArmies(),
                                                     "Siberia"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonUkraine(),
                                                     theView.getLblUkraineLogo(),
                                                     theView.getLblUkraineArmies(),
                                                     "Ukraine"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonUral(),
                                                     theView.getLblUralLogo(),
                                                     theView.getLblUralArmies(),
                                                     "Ural"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonVenezuela(),
                theView.getLblVenezuelaLogo(),
                theView.getLblVenezuelaArmies(), "Venezuela"));
        territoryVisualList.add(new TerritoryVisuals(
                theView.getButtonWAustralia(),
                theView.getLblWAustraliaLogo(),
                theView.getLblWAustraliaArmies(), "Western Australia"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonWEurope(),
                                                     theView.getLblWEuropeLogo(),
                                                     theView.getLblWEuropeArmies(),
                                                     "Western Europe"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonWUS(),
                                                     theView.getLblWUSLogo(),
                                                     theView.getLblWUSArmies(),
                                                     "Western United States"));
        territoryVisualList.add(new TerritoryVisuals(theView.getButtonYakutsk(),
                                                     theView.getLblYakutskLogo(),
                                                     theView.getLblYakutskArmies(),
                                                     "Yakutsk"));
        return territoryVisualList;
    }
}
