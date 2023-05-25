package com.example.datshopspring2.models.google;

import lombok.Data;

import java.util.List;

@Data
public class Volumes {
    private String kind;
    private Integer totalItems;
    private List<Volume> items;
}
