package com.example.datshopspring2.repositories.googleBooksAPI;

import com.example.datshopspring2.models.google.Volume;
import com.example.datshopspring2.models.google.Volumes;
import com.nimbusds.jose.shaded.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class CustomGoogleApi {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1";

    private final OkHttpClient client;

    private final long maxResult = 20;

    public CustomGoogleApi() {
        this.client = new OkHttpClient();
    }

    public Volumes searchBook(String query) {
        System.out.println(query);
        if (query.isBlank() || query.isEmpty()) {
            return null;
        }
        String url = BASE_URL + "/volumes?q=" + query + "&maxResults=" + maxResult;
        return getVolumes(url);
    }

    public Volumes searchBookWithStartIndex(String query, long start) {
        String url = BASE_URL + "/volumes?q=" + query + "&startIndex=" + start + "&maxResults=" + maxResult;
        return getVolumes(url);
    }

    public Volume searchBookById(String id) {
        String url = BASE_URL + "/volumes/" + id;
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), Volume.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Volumes getVolumes(String url) {
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), Volumes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CustomGoogleApi apiClient = new CustomGoogleApi();

        String query = "doraemon";
        Volume volumes = apiClient.searchBookById("JdphDwAAQBAJ");

        System.out.println(volumes);
//        List<Volume> book = volumes.getItems();
//
//        for (Volume volume : book) {
//            try {
//                System.out.println("Authors: " + volume.getVolumeInfo().getAuthors()[0]);
//            } catch (NullPointerException ignore) {
//            }
//            System.out.println("Id: " + volume.getId());
//            System.out.println("Title: " + volume.getVolumeInfo().getTitle());
//
//            System.out.println("++++++++==========+++++++");
//        }
    }
}
