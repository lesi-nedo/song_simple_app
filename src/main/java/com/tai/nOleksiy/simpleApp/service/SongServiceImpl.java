package com.tai.nOleksiy.simpleApp.service;

import com.tai.nOleksiy.simpleApp.entity.Song;
import com.tai.nOleksiy.simpleApp.repo.SongRepository;
import com.tai.nOleksiy.simpleApp.repo.SongsPagingRepository;
import com.tai.nOleksiy.simpleApp.repo.SongsSortingRepository;
import com.tai.nOleksiy.simpleApp.songsValidator.SongsValidator;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongFormatException;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.*;

import static com.tai.nOleksiy.simpleApp.songsValidator.SongsValidator.removeSpacesAndTrim;
import static com.tai.nOleksiy.simpleApp.songsValidator.SongsValidator.validateField;

@Service
public class SongServiceImpl implements SongService{
    private static final String ERR_MESSAGE_ID="Could not find the song by id: ";
    private static final String ERR_MESSAGE_TITLE="Could not find the song by title: ";
    private static final String ERR_MESSAGE_AUTHOR="Could not find the song by author: ";
    //all fields in Song entity
    private static final Set<String> ALL_COLUMN_NAMES = Song.getColumnNames();
    @Autowired
    SongRepository songsRep;

    @Autowired
    SongsSortingRepository songsSortedRep;

    @Autowired
    SongsPagingRepository songsPagingRep;

    @Transactional
    @NotNull
    @Override
    public void insert(Song song) throws SongFormatException {
        song.setId(0L);
        songsRep.save(SongsValidator.validateSong(song));
    }
    @Transactional
    @NotNull
    @Override
    public void updateById(Long id, Map<String, String> columnValue) throws SongNotFoundException, SongFormatException, DateTimeParseException{
        DateTimeFormatter dForm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate year_pub = null;
        String dateStr = removeSpacesAndTrim(columnValue.get("year_pub"));
        String title = removeSpacesAndTrim(columnValue.get("title"));
        String author = removeSpacesAndTrim(columnValue.get("author"));
        String language = columnValue.get("language").trim();

        if(dateStr != null)
            year_pub = LocalDate.parse(dateStr, dForm);
        if(!songsRep.existsById(id))
            throw new SongNotFoundException(ERR_MESSAGE_ID + id);
        songsRep.updateById(validateField("title", title), validateField("author", author), year_pub, validateField("language", language), id);
    }
    @Transactional
    @NotNull
    @Override
    public void updateById(Long id, Song song) throws SongNotFoundException, SongFormatException, DateTimeParseException{
        songsRep.updateById(validateField("title", removeSpacesAndTrim(song.getTitle())), validateField("author",
                removeSpacesAndTrim(song.getAuthor())), song.getYear_pub(), validateField("language", removeSpacesAndTrim(song.getLanguage())), id);
    }
    @Transactional
    @NotNull
    @Override
    public void deleteById(Long id) throws SongNotFoundException {
        if(!songsRep.existsById(id))
            throw new SongNotFoundException(ERR_MESSAGE_ID + id);
        songsRep.deleteById(id);
    }
    @Transactional
    @NotNull
    @Override
    public List<Song> deleteAllByTitle(String title) throws SongFormatException, SongNotFoundException {
        title = removeSpacesAndTrim(title);
        validateField("title", title);
        List<Song> allSongs = songsRep.findByTitle(title);
        if(allSongs == null)
            throw new SongNotFoundException(ERR_MESSAGE_TITLE + title);
        songsRep.deleteAllByTitle(title);
        return allSongs;
    }
    @Transactional(readOnly=true)
    @Override
    public Iterable<Song>  readAll() {
        return songsRep.findAll();
    }

    @Transactional(readOnly=true)
    @NotNull
    @Override
    public Song readOne(Long id) throws SongNotFoundException {
        return  songsRep.findById(id).orElseThrow(() -> new SongNotFoundException(ERR_MESSAGE_ID+id));
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public List<Song> findByTitle(String title) throws SongNotFoundException, SongFormatException{
       List<Song> song =  songsRep.findByTitle(validateField("title", removeSpacesAndTrim(title)));
        if(song == null || song.isEmpty())
            throw new SongNotFoundException(ERR_MESSAGE_TITLE + title);
        return song;
    }

    @Transactional(readOnly=true)
    @NotNull
    @Override
    public List<Song> findAllSongsByAuthorAndDate(String author, String year_pub) throws DateTimeException, SongFormatException, SongNotFoundException{
        List<Song> songs;
        LocalDate res = helperGetDate(year_pub);
        author = validateField("author", removeSpacesAndTrim(author));

        songs = songsRep.findAllSongsByAuthorAndDate(author, res);
        if(songs == null || songs.isEmpty())
            throw new SongNotFoundException(ERR_MESSAGE_AUTHOR + author);
        return songs;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Song> findAll() {
        return songsRep.findAll();
    }
    @Transactional(readOnly = true)
    @NotNull
    @Override
    public Song findById(Long id) throws SongNotFoundException{
        return songsRep.findById(id).orElseThrow(() -> new SongNotFoundException(ERR_MESSAGE_ID+id));
    }


    @Transactional(readOnly = true)
    @NotNull
    @Override
    public List<Song> getSongsByIdInRange(String from, String to) throws NullPointerException, SongNotFoundException{

        return songsSortedRep.findSongsInRangeOrdered(null, helperToLong(from), helperToLong(to), null);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public List<Song> getSongsByIdOrdered(String from, String to, Set<String> valuesOrder) throws NullPointerException, SongNotFoundException {
        validateSet(valuesOrder);
        return songsSortedRep.findSongsInRangeOrdered(null, helperToLong(from), helperToLong(to), valuesOrder);
    }
    @Transactional(readOnly = true)
    @NotNull
    @Override
    public List<Song> getSongsByTitleOrdered(String title, Set<String> valuesOrder) throws NullPointerException, SongNotFoundException, SongFormatException {
        title = validateField("title", title);
        validateSet(valuesOrder);
        return songsSortedRep.findSongsByTitleOrdered(title, valuesOrder);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    //gets all songs ordered by parameters in orderBy, if not specified defaults to order by id DESC;
    //fromPage: from which set chunk start to get the data
    //pageSize: the total number of the songs in the page
    public List<Song> getSongsOrderedInPages(Integer fromPage, Integer pageSize, String orderBy) throws SongFormatException {

        Pageable pageable =  PageRequest.of(fromPage, pageSize, helperToGetSortObj(orderBy));
        Page<Song> pageWithSongs = songsPagingRep.findAll(pageable);
        return pageWithSongs.getContent();
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    //Gets songs  with the same title and orders by values specified in orderBy parameter if null default is used which is: id DESC
    //fromPage: from which set chunk start to get the data
    //pageSize: the total number of the songs in the page
    public List<Song> getSongsByTitleOrderedInPages(String title, Integer fromPage, Integer pageSize, String orderBy) throws SongFormatException {
        title = validateField("title", title);
        Pageable pageable =  PageRequest.of(fromPage, pageSize, helperToGetSortObj(orderBy));
        return songsPagingRep.findByTitle(title, pageable);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    //Gets songs with the same author and orders by values specified in orderBy parameter if null default is used which is id DESC
    //fromPage: from which chunk start to get the data
    //pageSize: the total number of the songs in the page
    public List<Song> getSongsByAuthorOrderedInPages(String author, Integer fromPage, Integer pageSize, String orderBy) throws SongFormatException {
        author = validateField("author", author);
        Pageable pageable = PageRequest.of(fromPage, pageSize, helperToGetSortObj(orderBy));
        return songsPagingRep.findByAuthor(author, pageable);
    }
    @Transactional
    @NotNull
    @Override
    //Gets songs that has same language as specified in language parameter and is ordered by the values present in orderBy parameter (if null default is used: id DESC)
    //fromPage: same as above
    //pageSize: same as above
    //TODO: validate language format string, ex correct format: en_US
    public List<Song> getSongsByLanguageOrderInPages(String language, Integer fromPage, Integer pageSize, String orderBy ) throws SongFormatException {
        Pageable pageable = PageRequest.of(fromPage, pageSize, helperToGetSortObj(orderBy));
        return songsPagingRep.findByLanguage(language, pageable);
    }

    //Gets all songs from the author ordered by the values found in the parameter: valuesOrder if null default id DESC
    public List<Song> getSongsByAuthorOrdered(String author,  Set<String> valuesOrder) throws NullPointerException, SongNotFoundException, SongFormatException {
        author = validateField("author", author);
        validateSet(valuesOrder);
        return songsSortedRep.findSongsByAuthor(author, valuesOrder);
    }

    //takes a string in format: column_name ASC|DESC and parses it, if null return default ID DESC
    //if format is: column_name GIBBERISH defaults to DESC
    private Sort helperToGetSortObj(String orderBy) throws SongFormatException {
        if(orderBy == null)
            return Sort.by(Sort.Direction.DESC, SongsSortingRepository.ORDER_BY_DEFAULT.split(" ")[0]);
        Sort sort = Sort.unsorted();
        String[] props = orderBy.split(",");
        for(String elem : props) {
            String[] propsAndOrd = elem.split(" ");
            if(!ALL_COLUMN_NAMES.contains(propsAndOrd[0]))
                throw new SongFormatException("Order values are ill formatted");
            if(propsAndOrd.length > 1){
                if(propsAndOrd[1].equals("ASC")){
                    sort = sort.and(Sort.by(propsAndOrd[0]).ascending());
                } else {
                    sort = sort.and(Sort.by(propsAndOrd[0]).descending());
                    System.out.println(Arrays.toString(props));
                }
            } else {
                sort = sort.and(Sort.by(propsAndOrd[0]).descending());
            }
        }
        return sort;
    }
    //parses a string and converts to long
    private Long helperToLong(String num) throws SongNotFoundException{
        long numL;
        if(num == null)
            throw new NullPointerException("from or to can not be null");
        try {
            numL = Long.parseLong(num);
        } catch (NumberFormatException e){
            throw new SongNotFoundException("from and  to  are ill formatted" );
        }
        return numL;
    }
    //throws if set is empty or there is no column to order by
    private void validateSet(Set<String> values) throws SongNotFoundException {
        if(values == null)
            return;
        if(values.isEmpty())
            throw new SongNotFoundException("Order by values are ill formatted");
        values.removeIf(element -> !ALL_COLUMN_NAMES.contains(element.split(" ")[0]));
        if(values.isEmpty()){
            throw new SongNotFoundException("Order by values are ill formatted");
        }
    }
    //a helper that converts a string in format yyyy into yyyy-01-01 of type LocalDate or yyyy-MM-ddd int the same type otherwise exception
    private LocalDate helperGetDate(String year_pub) throws SongFormatException {
        if(year_pub == null)
            return null;
        DateTimeFormatter format;
        String[] yearPar = year_pub.split("[-/]");
        try {
            if (yearPar.length == 1) {
                format = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy")
                        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                        .toFormatter();
                return LocalDate.parse(yearPar[0], format);
            } else if (yearPar.length == 3) {
                format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(yearPar[0] + "-" + yearPar[1] + "-" + yearPar[2], format);

            }
        } catch (DateTimeParseException e) {
            throw new SongFormatException("Date format ill formatted");
        }
        throw new SongFormatException("Date format ill formatted");
    }
}
