package com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tai.nOleksiy.simpleApp.entity.Song;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongSuccessObj implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Song song;
    private String author;
    private String title;
    private String date;
    private String language;
    private String message;
    private HttpStatus status;
}
