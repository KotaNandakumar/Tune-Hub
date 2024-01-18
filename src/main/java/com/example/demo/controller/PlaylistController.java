package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.PlayList;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController
{
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService palylistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model)
	{
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute PlayList playlist)
	{
		//Updating play list table
		palylistService.addPlaylist(playlist);
		//Update song table
		List<Song> songList = playlist.getSongs();
		for(Song s : songList)
		{
			s.getPlaylists().add(playlist);
			
			//Update song object in db
			songService.updateSong(s);
		}
		return "adminHome";
	}

       @GetMapping("/viewPlaylists")
       public String viewPlaylists(Model model)
       {
    	   List<PlayList> allPlaylists = palylistService.fetchAllPlaylists();
    	   model.addAttribute("allPlaylists", allPlaylists);
    	   return "displayPlaylists";
       }
}
     





























