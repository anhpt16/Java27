package com.example.day_08.repository;

import com.example.day_08.entity.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    Page<Episode> findByMovieId(Integer movieId, Pageable pageable);
}
