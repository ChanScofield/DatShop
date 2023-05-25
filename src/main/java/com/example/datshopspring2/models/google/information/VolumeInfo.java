package com.example.datshopspring2.models.google.information;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolumeInfo {
    private ImageLinks imageLinks;
    private String title;
    private String description;
    private String[] authors;
}
