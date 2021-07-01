package com.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private final List<Video> videos;
  private Video currentlyPlayingVideo;
  private final HashMap<String, Video> videosPerId;
  private boolean videoIsPaused = true;
  private TreeMap<String, VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videosPerId = videoLibrary.getVideos();
    this.videos = new ArrayList<>(this.videosPerId.values());
    playlists = new TreeMap<>();
  }


  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videos.size());
  }

  public void showAllVideos() {

    //We sort the videos
    TreeMap<String, Video> sortedVideos = new TreeMap<>();
    for (Video video: videos) {
      sortedVideos.put(video.getTitle(), video);
    }

    //We print the list of available videos
    System.out.println("Here's a list of all available videos:");
    for (Video video: sortedVideos.values()) {
      System.out.println(video.getFullDisplayString());
    }
  }

  public void playVideo(String videoId) {

    //We check if there is a video associated with videoId
    Video selectedVideo = videosPerId.get(videoId);
    if (selectedVideo == null) {
      //The video does not exist, we print a warning message and end the method
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    //If the video is flagged, we print a warning message and end the method
    if (selectedVideo.isFlagged()) {
      System.out.println("Cannot play video: Video is currently flagged (reason: " + selectedVideo.getFlagReason() + ")");
      return;
    }

    //We stop the video that is currently playing
    if (currentlyPlayingVideo != null) {
      System.out.println("Stopping video: " + currentlyPlayingVideo.getTitle());
    }

    //We play the selected video (if the given videoId is valid)
    currentlyPlayingVideo = selectedVideo;
    System.out.println("Playing video: " + currentlyPlayingVideo.getTitle());
    videoIsPaused = false;

  }

  public void stopVideo() {

    if (currentlyPlayingVideo != null) {
      //There is a video currently playing, we stop it
      System.out.println("Stopping video: " + currentlyPlayingVideo.getTitle());
      currentlyPlayingVideo = null;
    } else {
      //There is no video currently playing, we display a warning message
      System.out.println("Cannot stop video: No video is currently playing");
    }

  }

  public void playRandomVideo() {

    List<Video> notFlaggedVideos = getNotFlaggedVideos();

    int numberOfVideos = notFlaggedVideos.size();

    if (numberOfVideos == 0) {
      //No videos available, we print a warning message and end the method
      System.out.println("No videos available");
      return;
    }

    Random random = new Random();
    Video randomVideo = notFlaggedVideos.get(random.nextInt(numberOfVideos));
    playVideo(randomVideo.getVideoId());

  }

  /**
   * Get all available videos that are not flagged, as a List.
   * @return The List of all available not flagged videos
   */
  private List<Video> getNotFlaggedVideos() {
    List<Video> notFlaggedVideos = new ArrayList<>();
    for (Video video: videos) {
      if (! video.isFlagged()) {
        notFlaggedVideos.add(video);
      }
    }
    return notFlaggedVideos;
  }

  public void pauseVideo() {

    if (currentlyPlayingVideo == null) { //There is no video playing
      System.out.println("Cannot pause video: No video is currently playing");
    }
    else if (videoIsPaused) { //The video is already paused
      System.out.println("Video already paused: " + currentlyPlayingVideo.getTitle());
    }
    else { //The video is not paused, we pause the video
      System.out.println("Pausing video: " + currentlyPlayingVideo.getTitle());
      videoIsPaused = true;
    }


  }

  public void continueVideo() {

    if (currentlyPlayingVideo == null) { //There is no video playing
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else if (videoIsPaused) { //The video is already paused, we continue the video
      System.out.println("Continuing video: " + currentlyPlayingVideo.getTitle());
      videoIsPaused = false;
    }
    else { //The video is not paused
      System.out.println("Cannot continue video: Video is not paused");
    }

  }

  public void showPlaying() {

    if (currentlyPlayingVideo == null) { //There is no video playing
      System.out.println("No video is currently playing");
    } else { //There is a video playing
      System.out.print("Currently playing: " + currentlyPlayingVideo.getFullDisplayString());
      if (videoIsPaused) {
        System.out.print(" - PAUSED");
      }
      System.out.println();
    }

  }

  public void createPlaylist(String playlistName) {

    //There is no need to handle playlist names with whitespaces as the CommandParser uses whitespaces to separate command words,
    //therefore the passed playlistName parameter does not contain a whitespace

    if (playlists.get(playlistName.toLowerCase()) == null) { //There is no playlist with the same name
      playlists.put(playlistName.toLowerCase(), new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
    } else { //There is already a playlist with the same name
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }

  }

  public void addVideoToPlaylist(String playlistName, String videoId) {

    VideoPlaylist selectedPlaylist = playlists.get(playlistName.toLowerCase());
    if (selectedPlaylist != null) {

      Video selectedVideo = videosPerId.get(videoId);
      if (selectedVideo != null) {

        //Both the playlist and the video exist

        //We check if the video is flagged
        if (selectedVideo.isFlagged()) {
          //We print a warning message and end the method
          System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " + selectedVideo.getFlagReason() + ")");
          return;
        }

        if (selectedPlaylist.contains(selectedVideo)) {
          //The video is already in the playlist
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
        } else {
          //We add the video to the playlist
          selectedPlaylist.add(selectedVideo);
          System.out.println("Added video to " + playlistName + ": " + selectedVideo.getTitle());
        }

      } else { //The video does not exist
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }

    } else { //The playlist does not exist
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }

  }

  public void showAllPlaylists() {

    if (playlists.isEmpty()) { //No playlist created, we print a message
      System.out.println("No playlists exist yet");
    }
    else {
      System.out.println("Showing all playlists:");
      for (VideoPlaylist playlist: playlists.values()) {
        System.out.println("  " + playlist.getName());
      }
    }

  }

  public void showPlaylist(String playlistName) {

    VideoPlaylist selectedPlaylist = playlists.get(playlistName.toLowerCase());
    if (selectedPlaylist != null) { //The playlist exists

      System.out.println("Showing playlist: " + playlistName);
      if (selectedPlaylist.isEmpty()) { //There is no video in the playlist
        System.out.println("  No videos here yet");
      }
      else { //There are videos in the playlist
        for (Video video: selectedPlaylist) {
          System.out.println("  " + video.getFullDisplayString());
        }
      }

    }
    else { //The playlist does not exist
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }

  }

  public void removeFromPlaylist(String playlistName, String videoId) {

    VideoPlaylist selectedPlaylist = playlists.get(playlistName.toLowerCase());
    if (selectedPlaylist != null) { //The playlist exists

      Video selectedVideo = videosPerId.get(videoId);
      if (selectedVideo != null) { //The video exists

        if (selectedPlaylist.contains(selectedVideo)) {
          //The video is in the playlist, we remove it
          selectedPlaylist.remove(selectedVideo);
          System.out.println("Removed video from " + playlistName + ": " + selectedVideo.getTitle());
        } else {
          //The video is not in the playlist
          System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
        }

      } else { //The video does not exist
        System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      }

    } else { //The playlist does not exist
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }

  }

  public void clearPlaylist(String playlistName) {

    VideoPlaylist selectedPlaylist = playlists.get(playlistName.toLowerCase());
    if (selectedPlaylist != null) { //The playlist exists
      selectedPlaylist.clear();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
    else { //The playlist does not exist
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    }

  }

  public void deletePlaylist(String playlistName) {

    VideoPlaylist selectedPlaylist = playlists.get(playlistName.toLowerCase());
    if (selectedPlaylist != null) { //The playlist exists
      playlists.remove(playlistName.toLowerCase());
      System.out.println("Deleted playlist: " + playlistName);
    }
    else { //The playlist does not exist
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    }

  }

  public void searchVideos(String searchTerm) {

    //This list will contain the videos corresponding to the search
    TreeMap<String, Video> searchResults = new TreeMap<>(); //Stores the search results and their title as key
    //The current implementation does not support videos with duplicate titles (duplicate keys)

    //Remove flagged videos from available videos
    List<Video> notFlaggedVideos = getNotFlaggedVideos();

    for (Video video: notFlaggedVideos) {
      if (video.getTitle().toLowerCase().contains(searchTerm)) {
        searchResults.put(video.getTitle(), video);
      }
    }

    showSearchResults(searchResults, searchTerm);

  }

  /**
   * This method determines if a given String can be cast to an Integer object.
   * @param input The String object to check
   * @return True if the object can be cast to Integer
   */
  private boolean isAnInteger(String input) {
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }


  public void searchVideosWithTag(String videoTag) {

    //This list will contain the videos corresponding to the search
    TreeMap<String, Video> searchResults = new TreeMap<>(); //Stores the search results and their title as key
    //The current implementation does not support videos with duplicate titles (duplicate keys)

    //Remove flagged videos from available videos
    List<Video> notFlaggedVideos = getNotFlaggedVideos();

    for (Video video: notFlaggedVideos) {
      if (video.getTags().contains(videoTag.toLowerCase())) {
        searchResults.put(video.getTitle(), video);
      }
    }

    showSearchResults(searchResults, videoTag);

  }

  public void showSearchResults(TreeMap<String, Video> searchResults, String searchTerm) {

    if (searchResults.isEmpty()) { //No corresponding video
      System.out.println("No search results for " + searchTerm);
    } else {
      System.out.println("Here are the results for " + searchTerm + ":");
      int index = 1;
      ArrayList<Video> searchResultsVideos = new ArrayList<>(searchResults.values());
      for (Video video: searchResultsVideos) {
        System.out.println("  " + index++ + ") " + video.getFullDisplayString());
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      //Catch user's input
      try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
        String input = reader.readLine();
        if (isAnInteger(input) && Integer.parseInt(input) > 0 && Integer.parseInt(input) <= searchResults.size()) {
          int selectedIndex = Integer.parseInt(input);
          Video selectedVideo = searchResultsVideos.get(selectedIndex-1);
          playVideo(selectedVideo.getVideoId());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }



  public void flagVideo(String videoId) {

    flagVideo(videoId, "Not supplied");

  }

  public void flagVideo(String videoId, String reason) {

    Video selectedVideo = videosPerId.get(videoId);
    if (selectedVideo != null) { //The video exists

      if (selectedVideo.isFlagged()) { //Video already flagged
        System.out.println("Cannot flag video: Video is already flagged");
      }
      else {
        selectedVideo.flag(reason);
        if (currentlyPlayingVideo == selectedVideo) {
          stopVideo();
        }
        System.out.println("Successfully flagged video: " + selectedVideo.getTitle() + " (reason: " + reason + ")");
      }

    }
    else { //The video does not exist
      System.out.println("Cannot flag video: Video does not exist");
    }

  }

  public void allowVideo(String videoId) {

    Video selectedVideo = videosPerId.get(videoId);

    if (selectedVideo != null) { //The video exists

      if (selectedVideo.isFlagged()) {
        selectedVideo.allow();
        System.out.println("Successfully removed flag from video: " + selectedVideo.getTitle());
      }
      else { //The video is not flagged
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }

    } else { //The video does not exist
      System.out.println("Cannot remove flag from video: Video does not exist");
    }

  }
}