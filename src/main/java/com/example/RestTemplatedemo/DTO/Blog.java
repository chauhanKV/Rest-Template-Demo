package com.example.RestTemplatedemo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Blog {

    private Long id;
    private String title;
    private String body;
    private Date date;
}
