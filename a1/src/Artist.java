
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Artist {
	private String name;
	private ArrayList<Genre> genres;
	private String country;

	public Artist(String name, String country) {
		this.name = name;
		this.country = country;
		this.genres = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void addGenre(Genre genre) {
		genre.addArtist(this);
		if (!this.genres.contains(genre)) {
			this.genres.add(genre);
		}
	}

	public String getCountry() {
		return country;
	}

	public boolean equals(Artist a) {
		return this.getName().equals(a.getName())
				&& this.getCountry().equals(a.getCountry());
	}

	@Override
	public boolean equals(Object a) {
		return a instanceof Artist && this.equals((Artist) a);
	}

	@Override
	public String toString() {
		String artistDesc = this.getName() + ", " + this.getCountry();
		String genreList = System.lineSeparator() + this.genres.stream()
				.map(Genre::getName)
				.collect(Collectors.joining(System.lineSeparator()));
		return (artistDesc + genreList).trim();
	}
}
