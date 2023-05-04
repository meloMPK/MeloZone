package com.melompk.melo;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.api.core.ApiFuture;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetData {
    public static String FindSongId(String artist, String album, String title) throws ExecutionException, InterruptedException {
        Map<String, Object> song = FirebaseHandler.db.collection("Songs")
                .whereEqualTo("Artist", artist)
                .whereEqualTo("Album", album)
                .whereEqualTo("Name", title).get().get().getDocuments().get(0).getData();
        return song.get("dbId")+".mp3";
    }
    public static void FetchSample() throws ExecutionException, InterruptedException {
        DocumentReference docRef = FirebaseHandler.db.collection("Songs").document("1");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            System.out.println("Document data: " + document.getData());
        } else {
            System.out.println("No such document!");
        }
    }
}