package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private final List<Video> videos;
  private Video currentlyPlayingVideo;
  private HashMap<String, Video> videosPerId;
  private boolean videoIsPaused = true;
  private HashMap<String, VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videos = videoLibrary.getVideos();
    associateVideosWithId();
    playlists = new HashMap<>();
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
    Video chosenVideo = videosPerId.get(videoId);
    if (chosenVideo == null) {
      //The video does not exist, we print a warning message and end the method
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    //We stop the video that is currently playing
    if (currentlyPlayingVideo != null) {
      System.out.println("Stopping video: " + currentlyPlayingVideo.getTitle());
    }

    //We play the chosen video (if the given videoId is valid)
    currentlyPlayingVideo = chosenVideo;
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
      playlists.put(playlistName, new VideoPlaylist());
      System.out.println("Successfully created new playlist: " + playlistName);
    } else { //There is already a playlist with the same name
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }

  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
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