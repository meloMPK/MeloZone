package com.melompk.melo;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


public class FirebaseHandler {
    public static Firestore db;
    public static Storage storage;
    public static void initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("/Users/andrzej/Documents/TCS/PO2023/MeloZone/MeloZone/src/main/resources/com/melompk/melo/Database/melozone-12081-firebase-adminsdk-yqrh2-724202e019.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
        storage = StorageOptions.newBuilder().setProjectId("melozone-12081").build().getService();
    }
}
