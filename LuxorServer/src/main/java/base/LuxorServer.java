package base;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pokerBase.Action;

public class LuxorServer extends Application {

    private boolean isServer = true;

    private TextArea messages = new TextArea();
    private NetworkConnection connection = isServer ? createServer() : createClient();

    
    /**
     * main - Entry point for application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * init - Executed by Application framework, will execute FIRST
     */
    @Override
    public void init() throws Exception {
        connection.startConnection();
    }

    /**
     * start - executed by Application framework, will execute after init
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * stop - executed by Application framework, if application is stopped.
     */
    @Override
    public void stop() throws Exception {
        connection.closeConnection();
    }
    
    private Parent createContent() {
        messages.setFont(Font.font(72));
        messages.setPrefHeight(550);
        TextField input = new TextField();
        input.setOnAction(event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += input.getText();
            input.clear();

            messages.appendText(message + "\n");

            try {
                connection.send(message);
            }
            catch (Exception e) {
            	//messages.appendText(e.getMessage().toString());
                messages.appendText("Failed to send\n");
            }
        });

        VBox root = new VBox(20, messages, input);
        root.setPrefSize(600, 600);
        return root;
    }




    private Server createServer() {
        return new Server(55555, data -> {
            Platform.runLater(() -> {            	
            	System.out.println("Data Received By Server: " + data.toString());
                //messages.appendText(data.toString() + "\n");
                Action act = DeSerializeAction(data.toString());
                messages.appendText("Action: " + act.getActionOption() + "\n");
                try {
					connection.send("Your turn");
				} catch (Exception e) {

					e.printStackTrace();
				}
            });
        });
    }

    private Client createClient() {
        return new Client("127.0.0.1", 55555, data -> {
            Platform.runLater(() -> {
            	System.out.println("Data Received By Client: " + data.toString());
                messages.appendText(data.toString() + "\n");
                
                

            });
        });
    }

	public static Action DeSerializeAction(String xml) {

		Action act = null;
		try {
			JAXBContext ctx = JAXBContext.newInstance(Action.class);
			javax.xml.bind.Unmarshaller um = ctx.createUnmarshaller();
			act = (Action) um.unmarshal(new StringReader(xml));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return act;
	}

}
