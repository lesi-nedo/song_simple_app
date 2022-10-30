package com.tai.nOleksiy.simpleApp.repo;

import com.tai.nOleksiy.simpleApp.entity.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface SongsPagingRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {
    //finds all songs by the title, number of songs per page and sorting order and by which parameter is specified in pageable parameter
    List<Song> findByTitle (String title,  Pageable pageable);
    //finds all the songs by the author
    List<Song> findByAuthor (String author, Pageable pageable);
    //finds all the songs by the language
    List<Song> findByLanguage (String language, Pageable pageable);
    //finds all the songs published after or during the year in year_pub parameter
}
