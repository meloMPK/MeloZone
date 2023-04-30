package com.melompk.melo;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.api.core.ApiFuture;

import java.util.concurrent.ExecutionException;

public class GetData {
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
