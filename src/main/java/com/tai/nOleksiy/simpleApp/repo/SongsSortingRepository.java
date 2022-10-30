package com.tai.nOleksiy.simpleApp.repo;

import com.tai.nOleksiy.simpleApp.entity.Song;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tai.nOleksiy.simpleApp.utils.StaticMethods;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SongsSortingRepository extends JpaRepository<Song, Long>,  SongsSortingRepositoryCustom<Song> {
    String ORDER_BY_DEFAULT="id DESC";


}
