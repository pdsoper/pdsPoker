package poker.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import poker.app.MainApp;
import pokerEnums.BettingOption;
import pokerEnums.GameOption;
import pokerEnums.JokerOption;
import pokerEnums.WildCardOption;


/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author paulsoper
 */
public class RootLayoutController implements Initializable {

	// Reference to the main application
	private MainApp mainApp;

	@FXML
	private MenuBar mbar;

	@FXML
	private Menu mnuGame = new Menu();
	
	@FXML
	private Menu mnuBet = new Menu();
			
	@FXML
	private Menu mnuJoker = new Menu();
					
	@FXML
	private Menu mnuWild = new Menu();


	public String getRuleName()
	{	
		String strRuleID = null;
		for (Menu m: mbar.getMenus())
		{
			if (m.getText() == "Game")
			{
				for (MenuItem mi: m.getItems())
				{
					if (mi.getClass().equals(RadioMenuItem.class))
					{
						RadioMenuItem rmi = (RadioMenuItem)mi;
						if (rmi.isSelected() == true)
						{
							strRuleID = rmi.getId();
							break;
						}
					}
				}
			}
		}
		
		return strRuleID;
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		this.buildMenus();
	}
	
	public void buildMenus() {
		this.buildGameMenu(0);
		this.buildJokerMenu(1);
		this.buildWildMenu(2);
		this.buildBetMenu(3);
	}
    	
	public void buildGameMenu(int idx) {
		
		mnuGame.setText("Game");
		mbar.getMenus().add(idx,mnuGame);
				
		ToggleGroup tglGrpGame = new ToggleGroup();
		
		for (GameOption gameOpt : GameOption.values()) {
			RadioMenuItem rmi = new RadioMenuItem(gameOpt.toString());
			rmi.setId(gameOpt.valueString());
			rmi.setToggleGroup(tglGrpGame);
			if (gameOpt.isDefault()) {
				rmi.setSelected(true);
			}
			mnuGame.getItems().add(rmi);
		}
	}
		
	public void buildBetMenu(int idx) {
		
		mnuBet.setText("Betting");
		mbar.getMenus().add(idx,mnuBet);
				
		ToggleGroup tglGrpBet = new ToggleGroup();
		
		for (BettingOption betOpt : BettingOption.values()) {
			RadioMenuItem rmi = new RadioMenuItem(betOpt.toString());
			rmi.setId(betOpt.valueString());
			rmi.setToggleGroup(tglGrpBet);
			if (betOpt.isDefault()) {
				rmi.setSelected(true);
			}
			mnuBet.getItems().add(rmi);
		}
	}
		
	public void buildJokerMenu(int idx) {
		
		mnuJoker.setText("Jokers");
		mbar.getMenus().add(idx, mnuJoker);
				
		ToggleGroup tglGrpJoker = new ToggleGroup();
		
		for (JokerOption jokerOpt : JokerOption.values()) {
			RadioMenuItem rmi = new RadioMenuItem(jokerOpt.toString());
			rmi.setId(jokerOpt.valueString());
			rmi.setToggleGroup(tglGrpJoker);
			if (jokerOpt.isDefault()) {
				rmi.setSelected(true);
			}
			mnuJoker.getItems().add(rmi);
		}
	}
		
	public void buildWildMenu(int idx) {
		
		mnuWild.setText("Wild Cards");
		mbar.getMenus().add(idx, mnuWild);
				
		ToggleGroup tglGrpWild = new ToggleGroup();
		
		for (WildCardOption wildOpt : WildCardOption.values()) {
			RadioMenuItem rmi = new RadioMenuItem(wildOpt.toString());
			rmi.setId(wildOpt.valueString());
			rmi.setToggleGroup(tglGrpWild);
			if (wildOpt.isDefault()) {
				rmi.setSelected(true);
			}
			mnuWild.getItems().add(rmi);
		}
	}
			
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Poker");
		alert.setHeaderText("About");
		alert.setContentText("Poker as implemented in CISC 181");

		alert.showAndWait();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

}