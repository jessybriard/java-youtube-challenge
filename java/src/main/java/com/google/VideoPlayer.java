package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private final List<Video> videos;
  private Video currentlyPlayingVideo;
  private HashMap<String, Video> videosPerId;
  private boolean videoIsPaused = true;
  private TreeMap<String, VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videos = videoLibrary.getVideos();
    associateVideosWithId();
    playlists = new TreeMap<>();
  }

  /**
   * This method instantiates the videosPerId HashMap, which allows a Video object to be retrieved from its videoId.
   */
  public void associateVideosWithId() {
    videosPerId = new HashMap<>();
    for (Video video: videos) {
      videosPerId.put(video.getVideoId(), video);
    }
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

    int numberOfVideos = videos.size();

    if (numberOfVideos == 0) {
      //No videos available, we print a warning message and end the method
      System.out.println("No videos available");
      return;
    }

    Random random = new Random();
    Video randomVideo = videos.get(random.nextInt(numberOfVideos));
    playVideo(randomVideo.getVideoId());

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
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}