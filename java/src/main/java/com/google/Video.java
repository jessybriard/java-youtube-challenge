package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /**
   * Returns the title of the video.
   * @return The title of the video
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the video id of the video.
   * @return The video id of the video
   * */
  public String getVideoId() {
    return videoId;
  }

  /**
   * Returns a readonly collection of the tags of the video.
   * @return A collection of the tags of the video
   */
  public List<String> getTags() {
    return tags;
  }

  /**
   * Returns the full display String of the video, under the "title (video_id) [tags]" format.
   * @return The full display String of the video
   */
  public String getFullDisplayString() {
    String displayString = title + " (" + videoId + ") [";
    for (String tag: tags) {
      displayString = displayString + tag + " ";
    }
    displayString = displayString.trim() + "]";
    return displayString;
  }

}
