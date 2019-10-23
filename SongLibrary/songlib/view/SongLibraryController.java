//Connor Magee
//Daniel Harding

package view;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import app.Song;

public class SongLibraryController {

	@FXML
	ListView<Song> listview;
	@FXML
	private TextField nameText;
	@FXML
	private TextField artistText;
	@FXML
	private TextField albumText;
	@FXML
	private TextField yearText;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnSave;

	
	private ObservableList<Song> songlist;
	Gson gson = new Gson();

	public void start() {
		try {
			readSavedList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		listview.setItems(songlist);

		if (!listview.getItems().isEmpty()) {
			listview.getSelectionModel().select(0);
			Song s = listview.getSelectionModel().getSelectedItem();
			nameText.setText(s.name);
			artistText.setText(s.artist);
			albumText.setText(s.album);
			yearText.setText(s.date);
		}
		
		
		//add button
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Adding Song");
				alert.setHeaderText("You are about to add a song to your Song Library.");
				alert.setContentText("Are you sure you want to add this song?" + "\n" + "\n" +
									  "Song Name: " + nameText.getText() + "\n" +
									  "Song Artist: " + artistText.getText() + "\n" +
									  "Song Album: " + albumText.getText() + "\n" +
									  "Song Year: " + yearText.getText());
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.get() == ButtonType.OK) {
					Song song = new Song(nameText.getText(), artistText.getText(), albumText.getText(), yearText.getText());
					addSong(song);
					listview.getSelectionModel().select(song);
				}
			}
		});
		
		
		//delete button
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Deleting Song");
				alert.setHeaderText("You are about to delete a song from your Song Library.");
				alert.setContentText("Are you sure you want to delete this song?" + "\n" + "\n" +
									  "Song Name: " + nameText.getText() + "\n" +
									  "Song Artist: " + artistText.getText() + "\n" +
									  "Song Album: " + albumText.getText() + "\n" +
									  "Song Year: " + yearText.getText());
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					Song s = listview.getSelectionModel().getSelectedItem();
					deleteSong(s);
				}
			}
		});
		
		//save button
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Saving Edits");
				alert.setHeaderText("You are about to make changes to a song from your Song Library.");
				alert.setContentText("Are you sure you want to make these changes?" + "\n" + "\n" + 
									  "Song Name: " + nameText.getText() + "\n" +
									  "Song Artist: " + artistText.getText() + "\n" +
									  "Song Album: " + albumText.getText() + "\n" +
									  "Song Year: " + yearText.getText());
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					updateSong(listview.getSelectionModel().getSelectedItem());
				}
			}	
		});

		
	}
	
	private void addSong(Song s) {
		for(Song song : songlist) {
			if (song.compareTo(s) == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Duplicate Song Found!");
				alert.setContentText("This song already exists in your Song List.");
				alert.showAndWait();
				return;
			}
		}
		songlist.add(s);
		Collections.sort(songlist);
	}
	
	private void updateSong(Song s) {
		s.name = nameText.getText();
		s.artist = artistText.getText();
		s.album = albumText.getText();
		s.date = yearText.getText();
		Collections.sort(songlist);
	}
	
	private void deleteSong(Song s) {
		int targetIndex = songlist.indexOf(s) == songlist.size() - 1 ? songlist.size() - 2 : songlist.indexOf(s);
		songlist.remove(s);
		Collections.sort(songlist);
		listview.getSelectionModel().select(targetIndex);
		Song selected = listview.getSelectionModel().getSelectedItem();
		nameText.setText(selected.name);
		artistText.setText(selected.artist);
		albumText.setText(selected.album);
		yearText.setText(selected.date);
	}

	public void saveList() {
		File file = new File("songlib/data/list.json");
		if(file.exists()) {
			file.delete();
		}
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(gson.toJson(songlist.toArray(), Song[].class));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private void readSavedList() throws IOException {
		File file = new File("songlib/data/list.json");
		if(file.exists()) {
			JsonReader reader = new JsonReader(new FileReader(file));
			ArrayList<Song> songArrayList = new ArrayList<Song>(Arrays.asList(gson.fromJson(reader, Song[].class)));
			songlist = FXCollections.observableArrayList(songArrayList);
		}else {
			throw new IOException("Saved library does not exist");
		}
	}
		
	@FXML
	private void displaySelected(MouseEvent event) {
		Song s = listview.getSelectionModel().getSelectedItem();
		if (s == null) {
			nameText.setText("Unknown");
			artistText.setText("Unknown");
			albumText.setText("Unknown");
			yearText.setText("Unknown");
		} else {
			nameText.setText(s.name);
			artistText.setText(s.artist);
			albumText.setText(s.album);
			yearText.setText(s.date);
		}
	}
}
