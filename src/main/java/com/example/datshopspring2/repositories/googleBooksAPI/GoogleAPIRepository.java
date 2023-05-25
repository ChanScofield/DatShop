package com.example.datshopspring2.repositories.googleBooksAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GoogleAPIRepository {

    @Value("${google.api.key}")
    private String apiKey;

    private final long maxResult = 20;

    public Volumes searchWithGoogle(String query) throws Exception {
        Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("My Books App")
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                .build();

        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setMaxResults(maxResult);

        Volumes volumes = volumesList.execute();
        return volumes;
    }

    public Volume searchByVolumeId(String bid) throws Exception {
        Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("My Books App")
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                .build();

        Books.Volumes.Get volumesGet = books.volumes().get(bid);

        Volume volume = volumesGet.execute();
        return volume;
    }

    public List<Volume> searchWithStartIndex(String query, long start) throws Exception {
        Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("My Books App")
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                .build();

        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setMaxResults(maxResult);
        volumesList.setStartIndex(start);

        Volumes volumes = volumesList.execute();

        return new ArrayList<>(volumes.getItems());
    }

    public static void main(String[] args) throws Exception {
        GoogleAPIRepository googleAPIRepository = new GoogleAPIRepository();
        System.out.println(googleAPIRepository.searchByVolumeId("QnG6zgEACAAJ"));
    }
}
