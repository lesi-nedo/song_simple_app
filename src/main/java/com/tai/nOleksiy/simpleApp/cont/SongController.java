package com.tai.nOleksiy.simpleApp.cont;

import com.tai.nOleksiy.simpleApp.entity.Song;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongFormatException;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongNotFoundException;
import com.tai.nOleksiy.simpleApp.service.SongService;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongSuccessObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import java.util.*;

@RestController
public class SongController {
    private final SongService songsServ;
    @Value("${spring.application.name}")
    String nameApp;
    @Autowired
    public SongController(SongService songsServ) {
        this.songsServ = songsServ;
    }



    @GetMapping(value="/song")
    public Iterable<Song> findALl(Model model) {
        return songsServ.findAll();
    }

    @GetMapping("/song/{id}")
    public Song findOne(Model model,
                          @Pattern(regexp="[\\d+]", message="ID should be an positive number")
                          @PathVariable Long id) throws SongNotFoundException {
        return songsServ.findById(id);
    }
    @GetMapping(value="/song", params="title")
    public List<Song> findByTitle(@RequestParam String title)
            throws SongFormatException, SongNotFoundException
    {
        return songsServ.findByTitle(title);
    }

    @GetMapping(value="/song", params="author")
    public List<Song> findByAuthorPubDate(@RequestParam(name="author") String author, @RequestParam(name="year_pub", required = false) String year_pub) throws SongFormatException, SongNotFoundException {
        return songsServ.findAllSongsByAuthorAndDate(author, year_pub);
    }

    @PostMapping(value="/song")
    public SongSuccessObj createSong(@RequestBody Song song) throws SongFormatException {
        songsServ.insert(song);
        SongSuccessObj objRet = new SongSuccessObj();
        objRet.setSong(song);
        objRet.setStatus(HttpStatus.ACCEPTED);
        objRet.setMessage("Song by id got created");
        return objRet;
    }

    @PutMapping(value="/song/{id}")
    public SongSuccessObj updateSongById(
            @Pattern(regexp="[\\d+]", message="ID should be an positive number")
            @PathVariable Long id, @MatrixVariable Map<String, String> values) throws SongFormatException, SongNotFoundException {
        songsServ.updateById(id, values);
        SongSuccessObj objRet = new SongSuccessObj();
        objRet.setAuthor(values.get("author"));
        objRet.setTitle(values.get("title"));
        objRet.setDate(values.get("year_pub"));
        objRet.setLanguage(values.get("language"));
        objRet.setStatus(HttpStatus.ACCEPTED);
        objRet.setMessage("Song by id got updated");
        return objRet;
    }

    @PutMapping(value="/update/{id}")
    public SongSuccessObj updateSongById(
            @Pattern(regexp="[\\d+]", message="ID should be an positive number")
            @PathVariable Long id, @RequestBody Song song
    ) throws SongFormatException, SongNotFoundException {
        songsServ.updateById(id, song);
        SongSuccessObj objRet = new SongSuccessObj();
        objRet.setSong(song);
        return objRet;

    }

    @DeleteMapping(value="/song/{id}")
    public SongSuccessObj deleteSongById(@PathVariable Long id) throws SongNotFoundException {
        Song song = songsServ.findById(id);
        songsServ.deleteById(id);
        SongSuccessObj objRet = new SongSuccessObj();

        objRet.setSong(song);
        objRet.setStatus(HttpStatus.ACCEPTED);
        objRet.setMessage("Song by id got deleted");
        return objRet;
    }

    @DeleteMapping(value="/song", params="title")
    public Map<String, SongSuccessObj> deleteAllByTitle (@RequestParam String title) throws SongFormatException,
            SongNotFoundException {
        List<Song> songs = songsServ.deleteAllByTitle(title);
        return helperFromListToMap(songs);
    }

    //gets songs in the range and orders by id in dsc
    @GetMapping(value="/song/ordered", params={"from", "to"})
    public List<Song> findSongByIdInRange(@RequestParam Map<String, String> ids) throws NumberFormatException, SongNotFoundException{
        List<Song> songs = songsServ.getSongsByIdInRange(ids.get("from"), ids.get("to"));
        return songs;
    }

    //gets song by id in the range and orders by column specified by the caller (correct form: column_name ASC | DSC)
    @GetMapping(value="/song/ordered", params={"from", "to", "order_by"})
    public List<Song> findSongByIdInRangeOrdered(@RequestParam Map<String, String> idsAndOrdBy) throws NumberFormatException, SongNotFoundException{
        String[] ordByVal = idsAndOrdBy.get("order_by").split(",");
        List<Song> songs = songsServ.getSongsByIdOrdered(idsAndOrdBy.get("from"), idsAndOrdBy.get("to"), new HashSet<>(List.of(ordByVal)));
        return songs;
    }

    @GetMapping(value="/song/ordered", params={"title", "order_by"})
    public List<Song> findSongByTitleOrdered(@RequestParam Map<String, String> idsAndOrdBy)
            throws NumberFormatException, SongNotFoundException, SongFormatException
    {
        String[] ordByVal = idsAndOrdBy.get("order_by").split(",");
        List<Song> songs = songsServ.getSongsByTitleOrdered(idsAndOrdBy.get("title"), new HashSet<>(List.of(ordByVal)));
        return songs;
    }

    @GetMapping(value="/song/ordered", params={"title"})
    public List<Song> findSongByTitleOrderedById(@RequestParam Map<String, String> idsAndOrdBy) throws NumberFormatException, SongNotFoundException, SongFormatException {
        List<Song> songs = songsServ.getSongsByTitleOrdered(idsAndOrdBy.get("title"), null);
        return songs;
    }

    @GetMapping(value="/song/ordered", params={"author", "order_by"})
    public List<Song> findSongByAuthorOrdered(@RequestParam Map<String, String> idsAndOrdBy) throws NumberFormatException, SongNotFoundException, SongFormatException {
        String[] ordByVal = idsAndOrdBy.get("order_by").split(",");
        List<Song> songs = songsServ.getSongsByAuthorOrdered(idsAndOrdBy.get("author"), new HashSet<>(List.of(ordByVal)));
        return songs;
    }

    @GetMapping(value="/song/ordered", params={"author"})
    public List<Song> findSongByAuthorOrderedById(@RequestParam Map<String, String> idsAndOrdBy) throws NumberFormatException, SongNotFoundException, SongFormatException {
        List<Song> songs = songsServ.getSongsByAuthorOrdered(idsAndOrdBy.get("Author"), null);
        return songs;
    }

    @GetMapping(value="/song/pages", params={"fromPage", "pageSize"})
    //Gets songs starting from the set chunk specified in fromPage and the size of page specified in pageSize
    //and if present can be order by
    public List<Song> findSongsOrderedInPages(
            @RequestParam(name="fromPage") Integer fromPage,
            @RequestParam(name="pageSize") Integer pageSize,
            @RequestParam(name="order_by", required = false) String order_by
    ) throws SongFormatException {
        List<Song> songs = songsServ.getSongsOrderedInPages(fromPage, pageSize, order_by);
        return songs;
    }

    @GetMapping(value="/song/pages", params={"title", "fromPage", "pageSize"})
    //Gets songs with title equal to the title parameter starting from the set chunk
    // specified in fromPage and the size of page specified in pageSize
    //and if present can be order by
    public List<Song> findSongsByTitleOrderedInPages(
            @RequestParam(name="title") String title,
            @RequestParam(name="fromPage") Integer fromPage,
            @RequestParam(name="pageSize") Integer pageSize,
            @RequestParam(name="order_by", required = false) String order_by
    ) throws SongFormatException {
        List<Song> songs = songsServ.getSongsByTitleOrderedInPages(title, fromPage, pageSize, order_by);
        return songs;
    }

    @GetMapping(value="/song/pages", params={"author", "fromPage", "pageSize"})
    //Gets songs with author equal to the author parameter starting from the set chunk specified in fromPage and the size of page specified in pageSize
    //and if present can be order by
    public List<Song> findSongsByAuthorOrderedInPages(
            @RequestParam(name="author") String author,
            @RequestParam(name="fromPage") Integer fromPage,
            @RequestParam(name="pageSize") Integer pageSize,
            @RequestParam(name="order_by", required = false) String order_by
    ) throws SongFormatException {
        List<Song> songs = songsServ.getSongsByAuthorOrderedInPages(author, fromPage, pageSize, order_by);
        return songs;
    }
    @GetMapping(value="/song/pages", params={"language", "fromPage", "pageSize"})
    //Gets songs ordered with the same language in pages sized by the parameter, if necessary can be order by other column_name than id
    //A helper that converts a List of song to Map of songs
    public List<Song> findSongsByLanguageOrderedInPages(
            @RequestParam(name="language") String language,
            @RequestParam(name="fromPage") Integer fromPage,
            @RequestParam(name="pageSize") Integer pageSize,
            @RequestParam(name="order_by", required = false) String order_by
    ) throws SongFormatException{
        List<Song> songs = songsServ.getSongsByLanguageOrderInPages(language, fromPage, pageSize, order_by);
        return songs;
    }
    private Map<String, SongSuccessObj> helperFromListToMap(List<Song> songs){
        Map<String, SongSuccessObj> objRet = new HashMap<>();
        int i = 1;
        for(Song song : songs){
            SongSuccessObj songObj =  new SongSuccessObj();
            songObj.setSong(song);
            songObj.setStatus(HttpStatus.ACCEPTED);
            songObj.setMessage("Song by title got deleted");
            objRet.put("song" + i +": ", songObj);
            i++;
        }
        return objRet;
    }

}
