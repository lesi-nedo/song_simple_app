package com.tai.nOleksiy.simpleApp.songsValidator;

import com.tai.nOleksiy.simpleApp.entity.Song;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongFormatException;

import javax.validation.constraints.NotNull;

public final class SongsValidator {

    @NotNull
    public static Song validateSong(Song song) throws SongFormatException {

        if(song.getAuthor().isEmpty())
            throw new SongFormatException("author is empty");
        if(song.getTitle().isEmpty())
            throw new SongFormatException("title is empty");
        if(song.getLanguage().isEmpty())
            throw new SongFormatException("language is empty");
        return song;
    }

    public static String validateField(String name, String value) throws SongFormatException{
        if(value != null && value.isEmpty())
            throw new SongFormatException(name + " is ill formatted");
        return value;
    }

    public static String removeSpacesAndTrim(String str) {
        if(str != null) {
            str = str.trim();
            str = str.replaceAll("((?<=[\\S])\\s{2,}(?=\\S))", " ");
        }
        return str;
    }

}
