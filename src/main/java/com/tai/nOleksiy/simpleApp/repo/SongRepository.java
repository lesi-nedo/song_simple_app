package com.tai.nOleksiy.simpleApp.repo;

import com.tai.nOleksiy.simpleApp.entity.Song;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long> {
    @Query("select s from Song s where lower(s.title) like lower(concat(?1, '%'))")
    public List<Song> findByTitle(String title);
    @Query("select s from Song s where lower(s.author) = lower(?1) and (?2 is null or s.year_pub >= ?2)")
    public List<Song> findAllSongsByAuthorAndDate(String author, LocalDate year_pub);
    @Modifying(clearAutomatically = true)
    @Query("update Song s set s.title= coalesce(?1, s.title), s.author=coalesce(?2, s.author),"
            + " s.year_pub=coalesce(?3, s.year_pub), s.language=coalesce(?4, s.language) where s.id = ?5 ")
    public void updateById(String title, String author, LocalDate year_pub, String language, Long id);
    @Modifying(clearAutomatically = true)
    @Query("delete from Song s where lower(s.title)=lower(?1)")
    public void deleteAllByTitle(String title);
}

