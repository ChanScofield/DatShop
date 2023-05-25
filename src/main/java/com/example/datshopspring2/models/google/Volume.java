package com.example.datshopspring2.models.google;


import com.example.datshopspring2.models.google.information.AccessInfo;
import com.example.datshopspring2.models.google.information.VolumeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Volume {
    private String id;
    private VolumeInfo volumeInfo;
    private AccessInfo accessInfo;

}
