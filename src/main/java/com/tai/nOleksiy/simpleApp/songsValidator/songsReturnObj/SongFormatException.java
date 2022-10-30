package com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj;

public class SongFormatException extends Exception{

    public SongFormatException(){
        super();
    }

    public SongFormatException(final String message){
        super(message);
    }

    public SongFormatException(final String message, final Throwable cause){
        super(message, cause);
    }

    public SongFormatException(final Throwable cause) {
        super(cause);
    }


}
