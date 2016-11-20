package com.inrotation.andrew.inrotation.model;

/**
 * Created by andrewcofano on 11/20/16.
 */

public class PlaylistSongNode {

    private Song current;
    private Song previous;
    private Song next;

    public PlaylistSongNode(Song current) {
        this.current = current;
        this.previous = null;
        this.next = null;
    }


    public Song getCurrent() {
        return current;
    }

    public void setCurrent(Song current) {
        this.current = current;
    }

    public Song getPrevious() {
        return previous;
    }

    public void setPrevious(Song previous) {
        this.previous = previous;
    }

    public Song getNext() {
        return next;
    }

    public void setNext(Song next) {
        this.next = next;
    }
}
