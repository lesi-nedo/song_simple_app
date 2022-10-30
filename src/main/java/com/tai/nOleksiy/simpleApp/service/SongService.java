package com.tai.nOleksiy.simpleApp.service;

import com.tai.nOleksiy.simpleApp.entity.Song;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongFormatException;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongNotFoundException;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SongService {
     List<Song> findByTitle(String title) throws SongNotFoundException, SongFormatException;
     Iterable<Song> findAll();
     Song findById(Long id) throws SongNotFoundException;
     List<Song> findAllSongsByAuthorAndDate(String author, String year_pub)  throws SongNotFoundException, SongFormatException;
     void insert(Song song) throws SongFormatException;
     void updateById(Long id, Map<String, String> columnValue)  throws SongNotFoundException, SongFormatException, DateTimeParseException;
     void updateById(Long id, Song song) throws SongNotFoundException, SongFormatException, DateTimeParseException;
     void deleteById(Long id)  throws SongNotFoundException;
     List<Song> deleteAllByTitle(String title)  throws SongNotFoundException, SongFormatException;
     Iterable<Song> readAll();
     Song readOne(Long id) throws SongNotFoundException;
     //Gets all songs by id from id to id (excluded) and ordered by id
     List <Song> getSongsByIdInRange(String from, String to) throws NullPointerException, SongNotFoundException;
     List<Song> getSongsByIdOrdered(String from, String to, Set<String> valuesOrder) throws NullPointerException, SongNotFoundException;
     List<Song> getSongsByTitleOrdered(String title, Set<String> valuesOrder) throws NullPointerException, SongNotFoundException, SongFormatException;
     List<Song> getSongsByAuthorOrdered(String author,  Set<String> valuesOrder) throws NullPointerException, SongNotFoundException, SongFormatException;

     List<Song> getSongsOrderedInPages(Integer fromPage, Integer pageSize, String orderBy) throws SongFormatException;
     List<Song> getSongsByTitleOrderedInPages(String title, Integer fromPage, Integer pageSize,  String orderBy) throws SongFormatException;
     List<Song> getSongsByAuthorOrderedInPages(String author, Integer fromPage, Integer pageSize,  String orderBy) throws SongFormatException;
     List<Song> getSongsByLanguageOrderInPages(String language, Integer fromPage, Integer pageSize, String orderBy ) throws SongFormatException;
}
