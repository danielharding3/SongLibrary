//Connor Magee
//Daniel Harding


package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.SongLibraryController;

public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/songlibrary.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		SongLibraryController controller = loader.getController();
		controller.start();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				final Stage confirmationDialog = new Stage();
				confirmationDialog.initModality(Modality.APPLICATION_MODAL);
				confirmationDialog.initOwner(primaryStage);
				confirmationDialog.setTitle("Alert");
				VBox dialogVbox = new VBox(20);
				dialogVbox.setAlignment(Pos.BASELINE_CENTER);
				Button yesButton = new Button("Yes");
				Button noButton = new Button("No");
				dialogVbox.getChildren().add(new Text("Are you sure you want to exit the Song Library?"));
				dialogVbox.getChildren().add(yesButton);
				dialogVbox.getChildren().add(noButton);
				
				yesButton.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						controller.saveList();
						primaryStage.close();
					}
				});
				
				noButton.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						we.consume();
						confirmationDialog.close();
					}
				});
				
				Scene dialogScene = new Scene(dialogVbox, 300, 200);
				confirmationDialog.setScene(dialogScene);
				confirmationDialog.show();
				we.consume();
			}
		});
		
		
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}
