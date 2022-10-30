package com.tai.nOleksiy.simpleApp.repo;

import com.tai.nOleksiy.simpleApp.entity.Song;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongNotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SongsSortingRepositoryCustom<T> {
    //return a list of song in a range of the id and ordered by the values specified in the parameter valuesOrder
    //if author null gets all search by id if not search also by author
    //if valuesOrder is null, orders all song by id in desc order

    List<T> findSongsInRangeOrdered(String author, Long from, Long to, Set<String> valuesOrder);
    List<Song> findSongsByTitleOrdered(String title, Set<String> valuesOrder);
    List<Song> findSongsByAuthor(String author,  Set<String> valuesOrder);

}
