package com.example.day_08;

import com.example.day_08.entity.*;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.enums.UserRole;
import com.example.day_08.model.request.CreateUserRequest;
import com.example.day_08.model.request.MovieFile;
import com.example.day_08.repository.MovieRepository;
import com.example.day_08.service.*;
import com.example.day_08.util.file.IFileReader;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class MovieappApplicationTests {

	@Autowired
	private MovieService movieService;
	@Autowired
	private GenreService genreService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private DirectorService directorService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private EpisodeService episodeService;
	@Autowired
	private IFileReader csvFileReader;
	@Autowired
	private UserService userService;

	@Test
	void save_movies_phim_le() {
		// Đọc dữ liệu từ file
		List<MovieFile> movieFiles = csvFileReader.readFile("movies_phim_le.csv");
		// Lọc file (loại bỏ các phim có tên trùng nhau, và các phim là phim chiếu rạp)
		Set<String> uniqueNames = new HashSet<>();
		List<MovieFile> filteredMovies = movieFiles.stream()
			.filter(movie -> uniqueNames.add(movie.getName()))
			.filter(movie -> movie.getGenres().stream()
				.map(String::toLowerCase)  // Chuyển tất cả thể loại về chữ thường
				.noneMatch(genre -> genre.equals("phim chiếu rạp"))) // Kiểm tra nếu không có "phim chiếu rạp"
			.collect(Collectors.toList());
		// Kiểm tra dữ liệu trước khi lưu
		Slugify slugify = Slugify.builder().build();
		for(MovieFile movieFile : filteredMovies) {
			// Kiểm tra phim tồn tại
			String slug = slugify.slugify(movieFile.getName());
			if (movieService.findMovieExist(slug) != null) {
				continue;
			}
			// Kiểm tra thể loại
			List<Genre> genres = movieFile.getGenres().stream()
				.map(name -> {
					Genre genre = genreService.findGenreExist(slugify.slugify(name));
					return genre != null ? genre : genreService.createGenreByName(name);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			// Kiểm tra diễn viên
			List<Actor> actors = movieFile.getActors().stream()
				.map(name -> {
					Actor actor = actorService.findActorExist(slugify.slugify(name));
					return actor != null ? actor : actorService.createActorByName(name);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			// Kiểm tra đạo diễn
			List<Director> directors = new ArrayList<>();
			Optional.ofNullable(movieFile.getDirector())
				.map(d -> directorService.findDirectorExist(slugify.slugify(d)))
				.or(() -> Optional.ofNullable(directorService.createDirectorByName(movieFile.getDirector())))
				.ifPresent(directors::add);
			// Kiểm tra country
			Country country = Optional.ofNullable(movieFile.getCountry())
				.map(c -> countryService.findCountryExist(slugify.slugify(c)))
				.or(() -> Optional.ofNullable(countryService.createCountryByName(movieFile.getCountry())))
				.orElse(null);
			// Tạo Movie
			Movie movie = new Movie();
			movie.setName(movieFile.getName());
			movie.setSlug(slug);
			movie.setDescription(movieFile.getDescription());
			movie.setThumbnail(movieFile.getThumbnail());
			movie.setReleaseYear(movieFile.getReleaseYear());
			movie.setType(MovieType.PHIM_LE);
			movie.setRating(movieFile.getRating());
			movie.setStatus(true);
			movie.setTrailer(movieFile.getTrailer());
			movie.setPublishedAt(LocalDateTime.now());
			movie.setCreatedAt(LocalDateTime.now());
			movie.setUpdatedAt(LocalDateTime.now());
			movie.setCountry(country);
			movie.setGenres(genres);
			movie.setDirectors(directors);
			movie.setActors(actors);
			Movie result = movieService.createMovie(movie);
			if(result == null) {
				continue;
			}
			// Tạo tập phim
			if (movieFile.getDuration() == null || movieFile.getDuration() <= 0) {
				continue;
			}
			Episode episode = episodeService.createEpisode(movieFile.getDuration(), result);
		}
	}

	@Test
	void save_movies_phim_chieu_rap() {
		// Đọc dữ liệu từ File
		List<MovieFile> movieFiles = csvFileReader.readFile("movies_phim_chieu_rap.csv");
		// Lọc file (loại bỏ các phim có tên trùng nhau, và thể loại phim chiếu rạp trong danh sách thể loại)
		Set<String> uniqueNames = new HashSet<>();
		movieFiles.forEach(movie ->
			movie.setGenres(
				movie.getGenres().stream()
					.filter(genre -> !"Phim chiếu rạp".equalsIgnoreCase(genre)) // Loại bỏ thể loại không mong muốn
					.collect(Collectors.toList())
			)
		);
		List<MovieFile> filteredMovies = movieFiles.stream()
			.filter(movie -> uniqueNames.add(movie.getName()))
			.collect(Collectors.toList());
		// Kiểm tra dữ liệu trước khi lưu
		Slugify slugify = Slugify.builder().build();
		for (MovieFile movieFile : filteredMovies) {
			// Kiểm tra phim tồn tại
			String slug = slugify.slugify(movieFile.getName());
			if (movieService.findMovieExist(slug) != null) {
				continue;
			}
			// Kiểm tra thể loại
			List<Genre> genres = movieFile.getGenres().stream()
				.map(name -> {
					Genre genre = genreService.findGenreExist(slugify.slugify(name));
					return genre != null ? genre : genreService.createGenreByName(name);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			// Kiểm tra diễn viên
			List<Actor> actors = movieFile.getActors().stream()
				.map(name -> {
					Actor actor = actorService.findActorExist(slugify.slugify(name));
					return actor != null ? actor : actorService.createActorByName(name);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			// Kiểm tra đạo diễn
			List<Director> directors = new ArrayList<>();
			Optional.ofNullable(movieFile.getDirector())
				.map(d -> directorService.findDirectorExist(slugify.slugify(d)))
				.or(() -> Optional.ofNullable(directorService.createDirectorByName(movieFile.getDirector())))
				.ifPresent(directors::add);
			// Kiểm tra country
			Country country = Optional.ofNullable(movieFile.getCountry())
				.map(c -> countryService.findCountryExist(slugify.slugify(c)))
				.or(() -> Optional.ofNullable(countryService.createCountryByName(movieFile.getCountry())))
				.orElse(null);
			// Tạo Movie
			Movie movie = new Movie();
			movie.setName(movieFile.getName());
			movie.setSlug(slug);
			movie.setDescription(movieFile.getDescription());
			movie.setThumbnail(movieFile.getThumbnail());
			movie.setReleaseYear(movieFile.getReleaseYear());
			movie.setType(MovieType.PHIM_CHIEU_RAP);
			movie.setRating(movieFile.getRating());
			movie.setStatus(true);
			movie.setTrailer(movieFile.getTrailer());
			movie.setPublishedAt(LocalDateTime.now());
			movie.setCreatedAt(LocalDateTime.now());
			movie.setUpdatedAt(LocalDateTime.now());
			movie.setCountry(country);
			movie.setGenres(genres);
			movie.setDirectors(directors);
			movie.setActors(actors);
			Movie result = movieService.createMovie(movie);
			if (result == null) {
				continue;
			}
			// Tạo tập phim
			if (movieFile.getDuration() == null || movieFile.getDuration() <= 0) {
				continue;
			}
			Episode episode = episodeService.createEpisode(movieFile.getDuration(), result);
		}
	}

	@Test
	void save_movies_phim_bo() {
		// Đọc dữ liệu từ file
		List<MovieFile> movieFiles = csvFileReader.readFile("movies_phim_bo.csv");
		// Lọc các bộ phim có tên trùng nhau
		Set<String> uniqueNames = new HashSet<>();
		List<MovieFile> filteredMovies = movieFiles.stream()
			.filter(movie -> uniqueNames.add(movie.getName()))
			.collect(Collectors.toList());
		// Kiểm tra dữ liệu trước khi lưu
		Slugify slugify = Slugify.builder().build();
		for (MovieFile movieFile : filteredMovies) {
			// Kiểm tra phim tồn tại
			String slug = slugify.slugify(movieFile.getName());
			if (movieService.findMovieExist(slug) != null) {
				continue;
			}
			// Kiểm tra thể loại
			List<Genre> genres = movieFile.getGenres().stream()
				.map(name -> {
					Genre genre = genreService.findGenreExist(slugify.slugify(name));
					return genre != null ? genre : genreService.createGenreByName(name);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			// Kiểm tra diễn viên
			List<Actor> actors = Optional.ofNullable(movieFile.getActors())
				.orElse(Collections.emptyList()) // Nếu null, thay bằng danh sách rỗng
				.stream()
				.map(name -> {
					Actor actor = actorService.findActorExist(slugify.slugify(name));
					return actor != null ? actor : actorService.createActorByName(name);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			// Kiểm tra đạo diễn
			List<Director> directors = new ArrayList<>();
			if (movieFile.getDirector() != null) {
				String directorName = movieFile.getDirector().trim();
				if (!directorName.isEmpty()) {
					Director director = directorService.findDirectorExist(slugify.slugify(directorName));
					if (director == null) {
						director = directorService.createDirectorByName(directorName);
					}
					directors.add(director);
				}
			}
			// Kiểm tra country
			Country country = Optional.ofNullable(movieFile.getCountry())
				.map(c -> countryService.findCountryExist(slugify.slugify(c)))
				.or(() -> Optional.ofNullable(countryService.createCountryByName(movieFile.getCountry())))
				.orElse(null);
			// Tạo Movie
			Movie movie = new Movie();
			movie.setName(movieFile.getName());
			movie.setSlug(slug);
			movie.setDescription(movieFile.getDescription());
			movie.setThumbnail(movieFile.getThumbnail());
			movie.setReleaseYear(movieFile.getReleaseYear());
			movie.setType(MovieType.PHIM_BO);
			movie.setRating(movieFile.getRating());
			movie.setStatus(true);
			movie.setTrailer(movieFile.getTrailer());
			movie.setPublishedAt(LocalDateTime.now());
			movie.setCreatedAt(LocalDateTime.now());
			movie.setUpdatedAt(LocalDateTime.now());
			movie.setCountry(country);
			movie.setGenres(genres);
			movie.setDirectors(directors);
			movie.setActors(actors);
			Movie result = movieService.createMovie(movie);
			if (result == null) {
				continue;
			}
			// Tạo danh sách tập phim
			if (movieFile.getDuration() == null || movieFile.getDuration() <= 0) {
				continue;
			}
			episodeService.createEpisodes(movieFile.getDuration(), result);
		}
	}

	@Test
	void create_user() {
		CreateUserRequest request = CreateUserRequest.builder()
			.username("nhatanh123")
			.password("123456")
			.role(UserRole.USER)
			.displayName("Nguyễn Nhật Anh")
			.email("nhatanh123@gmail.com")
			.phone("0987656555")
			.build();
		User user = userService.createUser(request);
		if (user == null) {
			System.out.println("Tạo mới user không thành công !");
		} else {
			System.out.println("Tạo mới user thành công !");
		}
	}
}
