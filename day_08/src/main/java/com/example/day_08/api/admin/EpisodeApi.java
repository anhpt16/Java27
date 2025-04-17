package com.example.day_08.api.admin;

import com.example.day_08.entity.Episode;
import com.example.day_08.model.request.CreateEpisodeRequest;
import com.example.day_08.model.request.UpdateEpisodeRequest;
import com.example.day_08.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/episodes")
@RequiredArgsConstructor
public class EpisodeApi {
    private final EpisodeService episodeService;

    @GetMapping("/{movieId}")
    public Page<Episode> getEpisodeByMovieId(
        @PathVariable Integer movieId,
        @RequestParam (name = "page", defaultValue = "1") int page,
        @RequestParam (name = "size", defaultValue = "10") int size
    ) {
        return episodeService.getEpisodesByMovieId(movieId, page - 1, size);
    }

    @PostMapping
    public ResponseEntity<?> createNewEpisode(
        @RequestBody @Valid CreateEpisodeRequest request
    ) {
        Episode episode = episodeService.createEpisode(request);
        if (episode == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(episode);
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<?> updateEpisode(
        @PathVariable Integer episodeId,
        @RequestBody @Valid UpdateEpisodeRequest request
    ) {
        Episode episode = episodeService.updateEpisode(episodeId, request);
        if (episode == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(episode);
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<?> deleteEpisode(
        @PathVariable Integer episodeId
    ) {
        Boolean result = episodeService.deleteEpisode(episodeId);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
