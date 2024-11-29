package com.example.hito1_alberto_rodriguez;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHelper {
    private DatabaseReference databaseReference;

    public DatabaseHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hito1-a530d-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("users");
    }

    public void addUser(User user, final DataStatus dataStatus) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            databaseReference.child(userId).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("DatabaseHelper", "User added successfully");
                        dataStatus.DataIsInserted();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("DatabaseHelper", "Failed to add user", e);
                    });
        } else {
            Log.e("DatabaseHelper", "No authenticated user found");
        }
    }

    public void getUser(String userId, final UserCallback callback) {
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onCallback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DatabaseHelper", "Failed to read user", databaseError.toException());
            }
        });
    }

    public void updateUserScore(String userId, int score, final DataStatus dataStatus) {
        databaseReference.child(userId).child("score").setValue(score)
                .addOnSuccessListener(aVoid -> {
                    Log.d("DatabaseHelper", "User score updated successfully");
                    dataStatus.DataIsUpdated();
                })
                .addOnFailureListener(e -> {
                    Log.e("DatabaseHelper", "Failed to update user score", e);
                });
    }

    public void updateUserLevel(String userId, int level, final DataStatus dataStatus) {
        databaseReference.child(userId).child("level").setValue(level)
                .addOnSuccessListener(aVoid -> {
                    Log.d("DatabaseHelper", "User level updated successfully");
                    dataStatus.DataIsUpdated();
                })
                .addOnFailureListener(e -> {
                    Log.e("DatabaseHelper", "Failed to update user level", e);
                });
    }

    public void updateUserPointsPerClick(String userId, int pointsPerClick, final DataStatus dataStatus) {
        databaseReference.child(userId).child("pointsPerClick").setValue(pointsPerClick)
                .addOnSuccessListener(aVoid -> {
                    Log.d("DatabaseHelper", "User points per click updated successfully");
                    dataStatus.DataIsUpdated();
                })
                .addOnFailureListener(e -> {
                    Log.e("DatabaseHelper", "Failed to update user points per click", e);
                });
    }

    public void updateUserUpgradeCost(String userId, int upgradeCost, final DataStatus dataStatus) {
        databaseReference.child(userId).child("upgradeCost").setValue(upgradeCost)
                .addOnSuccessListener(aVoid -> {
                    Log.d("DatabaseHelper", "User upgrade cost updated successfully");
                    dataStatus.DataIsUpdated();
                })
                .addOnFailureListener(e -> {
                    Log.e("DatabaseHelper", "Failed to update user upgrade cost", e);
                });
    }

    public interface UserCallback {
        void onCallback(User user);
    }

    public interface DataStatus {
        void DataIsLoaded(User user);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
}