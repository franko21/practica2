package com.example.practica2.controller;

import com.example.practica2.model.Playlist;
import com.example.practica2.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/crear")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String listName) {
        Optional<Playlist> playlist = playlistService.getPlaylistByName(listName);
        return playlist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{listName}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable String listName, @RequestBody Playlist updatedPlaylist) {
        if (updatedPlaylist.getName() != null && !updatedPlaylist.getName().equals(listName)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Playlist playlist = playlistService.updatePlaylist(listName, updatedPlaylist);
        if (playlist != null) {
            return new ResponseEntity<>(playlist, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String listName) {
        Optional<Playlist> playlist = playlistService.getPlaylistByName(listName);
        if (playlist.isPresent()) {
            playlistService.deletePlaylist(listName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
