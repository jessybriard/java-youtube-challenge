package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean flagged = false;
  private String flagReason;

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

    if (flagged) {
      displayString = displayString + " - FLAGGED (reason: " + flagReason + ")";
    }

    return displayString;
  }

  /**
   * Flag the video.
   * @param reason The reason supplied by the user to flag the video
   */
  public void flag(String reason) {

    flagged = true;
    flagReason = reason;

  }

  /**
   * Whether the video is currently flagged or not.
   * @return True if the video is flagged, otherwise false
   */
  public boolean isFlagged() {
    return flagged;
  }

  /**
   * Get the reason for flagging the video.
   * @return The flag reason
   */
  public String getFlagReason() {
    return flagReason;
  }

}
