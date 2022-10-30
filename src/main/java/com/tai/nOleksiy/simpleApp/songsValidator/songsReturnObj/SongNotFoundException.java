package com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj;

public class SongNotFoundException extends Exception{
    public SongNotFoundException() {
        super();
    }

    public SongNotFoundException (final String message) {
        super(message);
    }

    public SongNotFoundException (final String message, final Throwable cause) {
        super(message, cause);
    }

    public SongNotFoundException (final Throwable cause) {
        super(cause);
    }
}
