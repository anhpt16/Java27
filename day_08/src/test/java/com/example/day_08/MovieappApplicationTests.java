package com.example.day_08;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.repository.MovieRepository;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootTest
class MovieappApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	void save_movies() {
		Faker faker = new Faker();
		Slugify slugify = Slugify.builder().build();
		Random rd = new Random();



		for (int i = 0; i < 150; i++) {
			String name = faker.book().title();
			String thumbnail = "https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase();
			Boolean status = faker.bool().bool();
			int rdIndexMovieType = rd.nextInt(MovieType.values().length);
			MovieType type = MovieType.values()[rdIndexMovieType];

			Movie movie = Movie.builder()
				.name(name)
				.slug(slugify.slugify(name))
				.description(faker.lorem().paragraph())
				.thumbnail(thumbnail)
				.releaseYear(faker.number().numberBetween(1990, 2024))
				.status(status)
				.rating(faker.number().randomDouble(1, 5, 10))
				.type(type)
				.trailer("https://www.youtube.com/embed/tEs08eC0GJE?si=GBmKSmKO3Y6aCPFn")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.publishedAt(status ? LocalDateTime.now() : null)
				.build();

			movieRepository.save(movie);
		}
	}

	@Test
	void repo_movies () {
//		List<Movie> movies = movieRepository.findByNameContainingIgnoreCase("The");
//		movies.forEach(movie -> System.out.println(movie.toString()));
		List<Movie> movies1 = movieRepository.findByReleaseYear(2002);
		movies1.forEach(movie -> System.out.println(movie.toString()));
	}

}
