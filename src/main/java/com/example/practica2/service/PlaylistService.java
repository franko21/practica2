package com.example.practica2.service;

import com.example.practica2.model.Playlist;
import com.example.practica2.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistByName(String name) {
        return Optional.ofNullable(playlistRepository.findByName(name));
    }

    public Playlist updatePlaylist(String name, Playlist updatedPlaylist) {
        Playlist existingPlaylist = playlistRepository.findByName(name);
        if (existingPlaylist != null) {
            existingPlaylist.setDescription(updatedPlaylist.getDescription());
            existingPlaylist.setSongs(updatedPlaylist.getSongs());
            return playlistRepository.save(existingPlaylist);
        }
        return null;
    }

    public void deletePlaylist(String name) {
        Playlist existingPlaylist = playlistRepository.findByName(name);
        if (existingPlaylist != null) {
            playlistRepository.delete(existingPlaylist);
        }
    }
}

