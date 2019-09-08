
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Genre {
	private String name;
	private ArrayList<Artist> artists;

	public Genre(String name) {
		this.name = name;
		this.artists = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addArtist(Artist artist) {
		if (!this.artists.contains(artist)) {
			this.artists.add(artist);
			artist.addGenre(this);
		}
	}

	public boolean isPlayedBy(Artist a) {
		return this.artists.contains(a);
	}

	public boolean hasSameArtist(Genre g2) {
		return this.artists.stream().anyMatch(g2::isPlayedBy);
	}


	public boolean equals(Genre g) {
		return this.getName().equals(g.getName())
				&& this.artists.stream().allMatch(this::isPlayedBy);
	}

	@Override
	public boolean equals(Object g) {
		return g instanceof Genre && this.equals((Genre) g);
	}

	@Override
	public String toString() {
		String artistList = this.artists.stream().map(Artist::getName)
				.collect(Collectors.joining(", "));
		return this.getName() + " (" + artistList + ")";
	}
}
