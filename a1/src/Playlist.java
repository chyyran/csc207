import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.*;

public class Playlist {

	private ArrayList<Genre> genres;

	public Playlist() {
		this.genres = new ArrayList<>();
	}

	public static void main(String[] args) {
		Playlist p = new Playlist();
		for (String l : Playlist.locateMusicLines()) {
			p.parseLine(l);
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a genre.");
		for (String command = scanner.nextLine();
            !command.equalsIgnoreCase("exit");
            command = scanner.nextLine()) {
		    final String genreName = command;
            Optional<Genre> genre = p.genres.stream()
                    .filter(g -> g.getName().equalsIgnoreCase(genreName)).findFirst();
            if (genre.isPresent()) {
                System.out.println(genre.get());
            } else {
                System.out.println("This is not a valid genre.");
            }
            System.out.println("Enter a genre.");
        }
	}


	private static ArrayList<String> locateMusicLines() {
        final String lowerCaseTextFile = "music.txt";
        final String titleCaseTextFile = "Music.txt";

        for (String fileNameTest : new String[] {
                titleCaseTextFile,
                lowerCaseTextFile,
                "src/" + titleCaseTextFile,
                "src/" + lowerCaseTextFile,
                "./" + lowerCaseTextFile,
                "./" + lowerCaseTextFile,
                "~/" + titleCaseTextFile,
                "~/" + lowerCaseTextFile
        }) {
            ArrayList<String> testRoot = Playlist.getMusicLines(fileNameTest);
            if (!testRoot.isEmpty()) {
                return testRoot;
            }
        }
        return new ArrayList<String>();
    }

	private static ArrayList<String> getMusicLines(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader fileReader = new BufferedReader(new FileReader(new File(fileName)))) {
		    for (String line = fileReader.readLine();
                line != null; line = fileReader.readLine()) {
                lines.add(line.trim());
            }
		} catch (Exception ignored){
		}
		return lines;
	}

	private void parseLine(String musicLine){
		// Regular Expressions assume match for single line.
		Matcher artistRegex = Pattern.compile("^((\\w|\\s)+)(?=,)").matcher(musicLine);
		Matcher countryRegex = Pattern.compile("^(?:.+)(?:,)((\\w|\\s)+)(?=|)").matcher(musicLine);
		Matcher genreRegex = Pattern.compile("(?:\\|)((\\w|\\s)+)(?=|)").matcher(musicLine);

		if(!artistRegex.find() || !countryRegex.find()) {
			// Line is invalid, we have an artist without a country or an artist
			return;
		}

		String artistName = artistRegex.group(1).trim();
		String countryName = countryRegex.group(1).trim();
		ArrayList<Genre> genresForArtist = new ArrayList<>();

		while (genreRegex.find()) {
			String genreName = genreRegex.group(1).trim().toLowerCase();
			Optional<Genre> existingGenre = this.genres.stream()
					.filter(g -> g.getName().equalsIgnoreCase(genreName)).findFirst();

			if (existingGenre.isPresent()){
				genresForArtist.add(existingGenre.get());
			} else {
				genresForArtist.add(new Genre(genreName));
			}
		}

		Artist a = new Artist(artistName, countryName);
		for (Genre g : genresForArtist) {
			a.addGenre(g);
			if (!this.genres.contains(g)) {
				this.genres.add(g);
			}
		}

	}

}