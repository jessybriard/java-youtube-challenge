package com.google;

import java.util.*;

/** A class used to represent a Playlist.
 *  We consider a Playlist as an extended List.
 */
class VideoPlaylist extends ArrayList<Video> {

    private final String name;

    public VideoPlaylist(String playlistName) {
        super();
        this.name = playlistName;
    }


    /**
     * Get the name (case-sensitive) of the playlist.
     * @return The name of the playlist
     */
    public String getName() {
        return name;
    }

}
