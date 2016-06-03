package poker.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import netgame.common.Client;
import poker.app.model.PokerHub;
import poker.app.view.ClientServerStartController;
import poker.app.view.PokerTableController;
import poker.app.view.RootLayoutController;
import pokerBase.Action;
import pokerBase.Player;
import pokerBase.Table;
import pokerEnums.ActionOption;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private PokerHub pHub = null;
	private PokerClient pClient = null;

	private PokerTableController pokerController = null;
	private RootLayoutController rootController = null;

	private boolean isServer = false;

	private TextArea messages = new TextArea();
	//private NetworkConnection connection = createClient();

	private Player appPlayer;

	public int getClientID() {
		return pClient.getID();
	}
	
	public Player getPlayer() {
		return appPlayer;
	}

	public void setPlayer(Player player) {
		this.appPlayer = player;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		// INIT is executed by the Application framework FIRST
		//connection.startConnection();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// START is executed by the Application framework after INIT
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1300, 500);

		this.primaryStage = primaryStage;
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		
		this.primaryStage.setTitle("Poker");

		// Set the application icon.
		// this.primaryStage.getIcons().add(new
		// Image(getClass().getResourceAsStream("/res/img/26.png")));

		this.primaryStage.setScene(scene);
		this.primaryStage.show();

		showClientServer();
	}

	public void showPoker(boolean bStartHub, String strComputerName, int iPort, String strPlayerName) {

		if (bStartHub) {
			try {
				pHub = new PokerHub(iPort);
			} catch (Exception e) {
				System.out.println("Error: Can't listen on port " + iPort);
			}
		}
		try {
			pClient = new PokerClient(strComputerName, iPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setPlayer(new Player(strPlayerName));
		
		initRootLayout();

		showPokerTable();		
	}

	public void showClientServer() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ClientServerStart.fxml"));
			BorderPane ClientServerOverview = (BorderPane) loader.load();

			Scene scene = new Scene(ClientServerOverview);

			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			ClientServerStartController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			// RootLayoutController controller = loader.getController();
			rootController = loader.getController();

			rootController.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPokerTable() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PokerTable.fxml"));
			BorderPane pokerOverview = (BorderPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(pokerOverview);

			// Give the controller access to the main app.
			//PokerTableController controller = loader.getController();
			pokerController = loader.getController();
			pokerController.setMainApp(this);
			
			// getPlayer().setiPlayerPosition(0);
			// Action act = new Action(ActionOption.TableState, getPlayer());
			// messageSend(act);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void EndPoker() {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	@Override
	public void stop() throws Exception {
		//connection.closeConnection();
	}

	public void messageSend(final Object message)
	{
		//System.out.println("Sending message " + pClient.getID() );
		pClient.messageSend(message);	
	}
	
	public String getRuleName()
	{
		return rootController.getRuleName();
	}
	
	private class PokerClient extends Client {

		public PokerClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
		}

		/*
		 * messageSend - One single place to send messages
		 */
		protected void messageSend(Object message)
		{
			//System.out.println("PokerClient.messageSend");
			resetOutput();
			super.send(message);
		}
		
		/*
		 * messageReceived will get an Object message... it's up to you to determine
		 * what should happen to that the message.
		 * 
		 * If it's a Table, handle Table - level action
		 * If it's a GamePlay, handle GamePlay - level action
		 */
		@Override
		protected void messageReceived(final Object message) {
			//System.out.println("Receiving message " + getID() );
			Platform.runLater(() -> {		
				if (message instanceof String)
				{				
					System.out.println("Message Received " + message);
				}
				else if (message instanceof Table)
				{				
					//	The table changed...  poker table should react to new state of table
					pokerController.handleTable((Table) message);
					
					//	This will refresh the info box in the table... will go away when game
					//	is finished
					// pokerController.setlblNumberOfPlayers((Table)message);
				}
				else if (message instanceof Table)
				{
					try {
						pokerController.handleTable((Table) message);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					//	The game changed...  poker table should react to the new state of game
				}
			});
		}
		
		
		@Override
		/*
		 * serverShutdown - Call the hard exit.
		 */
	    protected void serverShutdown(String message) {
	    	
			Platform.runLater(() -> {		
				Platform.exit();
		        System.exit(0);
			});
	    }
	

	}
}
