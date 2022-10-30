package com.tai.nOleksiy.simpleApp.repo;

import com.tai.nOleksiy.simpleApp.entity.Song;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongNotFoundException;
import com.tai.nOleksiy.simpleApp.utils.StaticMethods;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Repository
public class SongsSortingRepositoryCustomImpl implements SongsSortingRepositoryCustom<Song>{
    @PersistenceContext
    EntityManager entManag;
    @Override
    @NotNull
    public List<Song> findSongsInRangeOrdered(String author, Long from, Long to, Set<String> valuesOrder){
        Query q = entManag.createNativeQuery("select  * from songs where (:author is null or author = :author) and id >= :from and id < :to order by " + StaticMethods.buildStringQuery(valuesOrder), Song.class);
        q.setParameter("from", from)
                .setParameter("to", to)
                .setParameter("author", author);
        return q.getResultList();

    }

    public  List<Song> findSongsByTitleOrdered(String title, Set<String> valuesOrder) {
        Query q = entManag.createNativeQuery("select  * from songs where title lower(like) lower(concat(:title, '%')) order by " + StaticMethods.buildStringQuery(valuesOrder), Song.class);
        q.setParameter("title", title);
        return q.getResultList();
    }

    public List<Song> findSongsByAuthor(String author,  Set<String> valuesOrder){
        Query q = entManag.createNativeQuery("select  * from songs where lower(author)=lower(:author) order by " + StaticMethods.buildStringQuery(valuesOrder), Song.class);
        q.setParameter("author", author);
        return q.getResultList();
    }


}
