package com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj;


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
public class SongErrorObj implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String exception;
    private String message;
    private HttpStatus status;
    private String path;


}
