package poker.app.view;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import poker.app.MainApp;
import pokerBase.Action;
import pokerBase.Card;
import pokerBase.Deck;
import pokerBase.Hand;
import pokerBase.Player;
import pokerBase.Table;
import pokerEnums.ActionOption;
import pokerEnums.GameOption;

public class PokerTableController {

	// Reference to the main application.
	private MainApp mainApp;

	public PokerTableController() {
	}
	
	@FXML 
	private ImageView imgViewDealerButtonPos1;
	@FXML 
	private ImageView imgViewDealerButtonPos2;
	@FXML 
	private ImageView imgViewDealerButtonPos3;
	@FXML 
	private ImageView imgViewDealerButtonPos4;
	
	@FXML 
	private BorderPane OuterBorderPane;

	@FXML
	private Label lblNumberOfPlayers;
	@FXML
	private TextArea txtPlayerArea;

	@FXML 
	private Button btnDeal;	
	@FXML
	private Button btnStartGame;
	
	@FXML
	private ToggleButton btnPos1SitLeave;
	@FXML
	private ToggleButton btnPos2SitLeave;
	@FXML
	private ToggleButton btnPos3SitLeave;
	@FXML
	private ToggleButton btnPos4SitLeave;

	@FXML
	private Label lblPos1Name;
	@FXML
	private Label lblPos2Name;
	@FXML
	private Label lblPos3Name;
	@FXML
	private Label lblPos4Name;
	
	@FXML
	private HBox hBoxPos1;
	@FXML
	private HBox hBoxPos2;
	@FXML
	private HBox hBoxPos3;
	@FXML
	private HBox hBoxPos4;
	
	@FXML
	private FlowPane btnFlowPanePos1;
	@FXML
	private FlowPane btnFlowPanePos2;
	@FXML
	private FlowPane btnFlowPanePos3;
	@FXML
	private FlowPane btnFlowPanePos4;

	@FXML
	private Button btnPlayer1Fold;
	@FXML
	private Button btnPlayer2Fold;
	@FXML
	private Button btnPlayer3Fold;
	@FXML
	private Button btnPlayer4Fold;
	
	@FXML
	private void initialize() {
		imgViewDealerButtonPos1.setImage(new Image("/img/d-button.png"));
		imgViewDealerButtonPos2.setImage(new Image("/img/d-button.png"));
		imgViewDealerButtonPos3.setImage(new Image("/img/d-button.png"));
		imgViewDealerButtonPos4.setImage(new Image("/img/d-button.png"));
		imgViewDealerButtonPos1.setVisible(false);
		imgViewDealerButtonPos2.setVisible(false);
		imgViewDealerButtonPos3.setVisible(false);
		imgViewDealerButtonPos4.setVisible(false);
	}

	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handlePlay() {
	}

	public void btnSitLeave_Click(ActionEvent event) {
		ToggleButton btnSitLeave = (ToggleButton) event.getSource();
		int iPlayerPosition = -1;
		if (btnSitLeave.isSelected()) {
			switch (btnSitLeave.getId().toString()) {
			case "btnPos1SitLeave":
				hBoxPos1.getChildren().clear();
				iPlayerPosition = 0;
				break;
			case "btnPos2SitLeave":
				hBoxPos2.getChildren().clear();
				iPlayerPosition = 1;
				break;
			case "btnPos3SitLeave":
				hBoxPos3.getChildren().clear();
				iPlayerPosition = 2;
				break;
			case "btnPos4SitLeave":
				hBoxPos4.getChildren().clear();
				iPlayerPosition = 3;
				break;
			}
		} else {
			iPlayerPosition = -1;
		}

		mainApp.getPlayer().setPosition(iPlayerPosition);
		Action act = new Action();
		if (btnSitLeave.isSelected()) {
			act.setActionOption(ActionOption.Sit);
		} else {
			act.setActionOption(ActionOption.Leave);
		}
		act.setPosition(iPlayerPosition);
		mainApp.messageSend(act);
	}

	public void handleTable(Table aTable) {

		lblPos1Name.setText("");
		lblPos2Name.setText("");
		lblPos3Name.setText("");
		lblPos4Name.setText("");
		
		this.showInitialButtons();

		btnPos1SitLeave.setVisible(true);
		btnPos2SitLeave.setVisible(true);
		btnPos3SitLeave.setVisible(true);
		btnPos4SitLeave.setVisible(true);

		btnPos1SitLeave.setText(btnPos1SitLeave.isSelected() ? "Leave" : "Sit");
		btnPos2SitLeave.setText(btnPos2SitLeave.isSelected() ? "Leave" : "Sit");
		btnPos3SitLeave.setText(btnPos3SitLeave.isSelected() ? "Leave" : "Sit");
		btnPos4SitLeave.setText(btnPos4SitLeave.isSelected() ? "Leave" : "Sit");

		btnStartGame.setDisable(aTable.isGameInProgress());
		
		/*
		FadeButton(btnStartGame);
		Iterator it = HubPokerTable.getHashPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			switch (p.getiPlayerPosition()) {
			case 1:
				if (p.getPlayerID().equals(mainApp.getPlayer().getPlayerID())) {
					btnPos1SitLeave.setVisible(true);
					btnPos2SitLeave.setVisible(false);
					btnPos3SitLeave.setVisible(false);
					btnPos4SitLeave.setVisible(false);
				} else {
					btnPos1SitLeave.setVisible(false);
				}
				lblPos1Name.setText(p.getPlayerName().toString());
				break;
			case 2:
				if (p.getPlayerID().equals(mainApp.getPlayer().getPlayerID())) {
					btnPos1SitLeave.setVisible(false);
					btnPos2SitLeave.setVisible(true);
					btnPos3SitLeave.setVisible(false);
					btnPos4SitLeave.setVisible(false);
				} else {
					btnPos2SitLeave.setVisible(false);
				}
				lblPos2Name.setText(p.getPlayerName().toString());
				break;
			case 3:
				if (p.getPlayerID().equals(mainApp.getPlayer().getPlayerID())) {
					btnPos1SitLeave.setVisible(false);
					btnPos2SitLeave.setVisible(false);
					btnPos3SitLeave.setVisible(true);
					btnPos4SitLeave.setVisible(false);
				} else {
					btnPos3SitLeave.setVisible(false);
				}
				lblPos3Name.setText(p.getPlayerName().toString());
				break;
			case 4:
				if (p.getPlayerID().equals(mainApp.getPlayer().getPlayerID())) {
					btnPos1SitLeave.setVisible(false);
					btnPos2SitLeave.setVisible(false);
					btnPos3SitLeave.setVisible(false);
					btnPos4SitLeave.setVisible(true);
				} else {
					btnPos4SitLeave.setVisible(false);
				}
				lblPos4Name.setText(p.getPlayerName().toString());
				break;
			}
		}
		*/
	}

/*	public void Handle_GameState(GamePlay HubGamePlay) throws HandException 
	{
		imgViewDealerButtonPos1.setVisible(false);
		imgViewDealerButtonPos2.setVisible(false);
		imgViewDealerButtonPos3.setVisible(false);
		imgViewDealerButtonPos4.setVisible(false);

		// System.out.println("Running Handle_GameState in PokerTableController");

		//TODO - Lab #5: Check to see if you're the dealer..  If you are, make the imgViewDealerButtonX visible = true
		switch (HubGamePlay.dealerPosition()) {
		case 1:
			imgViewDealerButtonPos1.setVisible(true);
			break;
		case 2:
			imgViewDealerButtonPos2.setVisible(true);
			break;
		case 3:
			imgViewDealerButtonPos3.setVisible(true);
			break;
		case 4:
			imgViewDealerButtonPos4.setVisible(true);
			break;
		}
		
	    this.showButtons(HubGamePlay);

		this.txtPlayerArea.setText(HubGamePlay.scoreReport());
		if (HubGamePlay.winner() != null){
			this.txtPlayerArea.appendText("\nClick Start to play another hand");
		} 
		for (Player aPlayer : HubGamePlay.getGamePlayers().values()) {
			showCards(HubGamePlay, aPlayer);
		}

	}
	*/
	public void showInitialButtons() {
		btnStartGame.setVisible(true);
		btnDeal.setVisible(false);
		imgViewDealerButtonPos1.setVisible(false);
		imgViewDealerButtonPos2.setVisible(false);
		imgViewDealerButtonPos3.setVisible(false);
		imgViewDealerButtonPos4.setVisible(false);
		btnFlowPanePos1.setVisible(false);
		btnFlowPanePos2.setVisible(false);
		btnFlowPanePos3.setVisible(false);
		btnFlowPanePos4.setVisible(false);
	}
	
	public void showButtons(Table aTable) {
		int myPos = this.mainApp.getPlayer().getPosition();
		btnFlowPanePos1.setVisible(false);
		btnFlowPanePos2.setVisible(false);
		btnFlowPanePos3.setVisible(false);
		btnFlowPanePos4.setVisible(false);
		if (aTable.isGameInProgress()) {
			btnStartGame.setVisible(false);
			btnDeal.setVisible(true);	
			switch (myPos) {
			case 1:
				btnFlowPanePos1.setVisible(true);
				break;
			case 2:
				btnFlowPanePos2.setVisible(true);
				break;
			case 3:
				btnFlowPanePos3.setVisible(true);
				break;
			case 4:
				btnFlowPanePos4.setVisible(true);
				break;
			}	
		} else {
			btnStartGame.setVisible(true);
			btnDeal.setVisible(false);
		}
	}
	
	public void showCards(Table aTable, Player aPlayer) {
		double ivSize = 50.0;
		String imgPath = "/img/";
		String imgExt = ".png";
		String imgUrl = "";
		Image img = null;
		Image cardBack = new Image("/img/card_back.png");
		ImageView cbiv = new ImageView(cardBack);
		cbiv.setFitHeight(ivSize);
		cbiv.setPreserveRatio(true);
		HBox hb = null;
		Player myself = this.mainApp.getPlayer();
		switch (aPlayer.getPosition()) {
		case 1:
			hb = hBoxPos1;
			break;
		case 2:
			hb = hBoxPos2;
			break;
		case 3:
			hb = hBoxPos3;
			break;
		case 4:
			hb = hBoxPos4;
			break;
		}
		hb.getChildren().clear();
		for (Card c : aPlayer.getHand().getCards()) {
			if (aTable.isCardVisibleToPlayer(c, myself)) {
				imgUrl = imgPath + c.getImageNum() + imgExt;
				img = new Image(imgUrl);
			} else {
				img = cardBack;
			}
			ImageView iv = new ImageView(img); 
			iv.setFitHeight(ivSize);
			iv.setPreserveRatio(true);
			hb.getChildren().add(iv);	
		}
	}
	
	
	@FXML
	void btnStart_Click(ActionEvent event) {
		Action act = new Action(ActionOption.StartGame, mainApp.getPlayer().getPosition());;
		GameOption Game = GameOption.FiveCardStud;
		act.setGameOpt(Game);
		
		mainApp.messageSend(act);
	}

	@FXML
	void btnDeal_Click(ActionEvent event) {
		Action act = new Action(ActionOption.Deal, mainApp.getPlayer().getPosition());
		mainApp.messageSend(act);
	}
	
	@FXML
	public void btnFold_Click(ActionEvent event) {
		Button btnFold = (Button) event.getSource();
		Action act = new Action(ActionOption.Fold, mainApp.getPlayer().getPosition());
		mainApp.messageSend(act);
	}

	@FXML
	public void btnCheck_Click(ActionEvent event) {
		Button btnFold = (Button) event.getSource();
		Action act = new Action(ActionOption.Check, mainApp.getPlayer().getPosition());
		mainApp.messageSend(act);
	}

	private void scanInputControls(Pane parent, String strControlStartsWith, boolean bVisible) {
	    for (Node component : parent.getChildren()) {
	        if (component instanceof Pane) {
	            //if the component is a container, scan its children
	            scanInputControls((Pane) component, strControlStartsWith, bVisible);
	        } else if (component instanceof TextField) {
	        }
	        else if (component instanceof Button)
	        {
	        	Button b = (Button)component;	        	
	        	if ((b.getId() != null) && (b.getId().endsWith(strControlStartsWith)))
	        	{
	        		System.out.println(b.getId());
	        		b.setVisible(bVisible);
	        	}
	        }
	    }
	}
	
	private void FadeButton(Button btn) {
		FadeTransition ft = new FadeTransition(Duration.millis(3000), btn);
		ft.setFromValue(1.0);
		ft.setToValue(0.3);
		ft.setCycleCount(4);
		ft.setAutoReverse(true);

		ft.play();
	}

}