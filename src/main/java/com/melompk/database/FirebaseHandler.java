package com.melompk.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class FirebaseHandler {//Model

    public static Firestore db;

    public static Storage storage;
    private FirebaseHandler() {
    }

    public static void initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(new File("").getAbsolutePath() + "/src/main/resources/Utilities/melozone-7db34-firebase-adminsdk-kuyso-b2b06fbe51.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
        storage = StorageOptions.newBuilder().setProjectId("melozone-7db34").build().getService();
    }
}
