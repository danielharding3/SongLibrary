//Connor Magee
//Daniel Harding

package app;

public class Song implements Comparable<Song>{

	public String name;
	public String artist;
	public String album;
	public String date;
	
	public Song(String name, String artist, String album, String date) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.date = date;
	}
	
	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
	}
	
	public String toString() {
		return this.name + "--" + this.artist;
	}
	
	public int compareTo(Song s) {
		return this.name.compareToIgnoreCase(s.name) == 0 ? this.artist.compareToIgnoreCase(s.artist) : this.name.compareToIgnoreCase(s.name);
	}
}
