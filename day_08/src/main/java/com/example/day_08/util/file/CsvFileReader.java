package com.example.day_08.util.file;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.request.MovieFile;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvFileReader implements IFileReader{
    @Override
    public List<MovieFile> readFile(String path) {
        List<MovieFile> movies = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                if (line.length < 10) {
                    continue;
                }
                String name = (line[0] != null && !line[0].trim().isEmpty() ? line[0].trim() : null);
                String thumbnail = (line[1] != null && !line[1].trim().isEmpty() ? line[1].trim() : null);
                String trailer = (line[2] != null && !line[2].trim().isEmpty() ? line[2].trim() : null);
                Integer duration = (line[3] != null && !line[3].trim().isEmpty() ? Integer.parseInt(line[3].trim()) : null);
                Integer rating;
                try {
                    rating = (line[4] != null && !line[4].trim().isEmpty())
                        ? Integer.parseInt(line[4].trim().replace(",", ""))
                        : null;
                } catch (NumberFormatException e) {
                    rating = 0;
                }
                List<String> genres = null;
                if (line[5] != null && !line[5].trim().isEmpty()) {
                    genres = Arrays.stream(line[5].split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                }
                List<String> actors = null;
                if (line[6] != null && !line[6].trim().isEmpty()) {
                    actors = Arrays.stream(line[6].split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                }
                Integer releaseYear = (line[7] != null && !line[7].trim().isEmpty() ? Integer.parseInt(line[7].trim()) : null);
                String director = (line[8] != null & !line[8].trim().isEmpty() ? line[8] : null);
                String country = (line[9] != null & !line[9].trim().isEmpty() ? line[9] : null);
                String description = (line[10] != null & !line[10].trim().isEmpty() ? line[10] : null);
                //
                MovieFile movieFile = MovieFile.builder()
                    .name(name)
                    .thumbnail(thumbnail)
                    .trailer(trailer)
                    .duration(duration)
                    .rating(rating)
                    .genres(genres)
                    .actors(actors)
                    .releaseYear(releaseYear)
                    .director(director)
                    .country(country)
                    .description(description)
                    .build();
                movies.add(movieFile);
                System.out.println(movieFile.toString());
            }
        } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
        }
        return movies;
    }
}
