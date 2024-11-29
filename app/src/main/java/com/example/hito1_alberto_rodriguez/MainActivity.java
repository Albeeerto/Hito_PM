package com.example.hito1_alberto_rodriguez;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private int score = 0;
    private int pointsPerClick = 1;
    private int upgradeCost = 10;
    private int level = 1;
    private DatabaseHelper dbHelper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e("MainActivity", "User not authenticated");
            finish();
            return;
        }

        dbHelper = new DatabaseHelper();

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Button clickButton = findViewById(R.id.clickButton);
        Button upgradeButton = findViewById(R.id.upgradeButton);
        TextView levelTextView = findViewById(R.id.levelTextView);

        getUserScore(currentUser.getUid());

        clickButton.setOnClickListener(v -> {
            score += pointsPerClick;
            scoreTextView.setText("Puntos: " + score);
            updateLevel(levelTextView);
            updateScoreInDatabase(currentUser.getUid(), score); // Update score in database
        });

        upgradeButton.setOnClickListener(v -> {
            if (score >= upgradeCost) {
                score -= upgradeCost;
                pointsPerClick++;
                upgradeCost *= 2;
                scoreTextView.setText("Puntos: " + score);
                upgradeButton.setText("Mejora (+" + pointsPerClick + " por clic): " + upgradeCost + " puntos");
                updateScoreInDatabase(currentUser.getUid(), score); // Update score in database
                updatePointsPerClickInDatabase(currentUser.getUid(), pointsPerClick); // Update points per click in database
                updateUpgradeCostInDatabase(currentUser.getUid(), upgradeCost); // Update upgrade cost in database
            }
        });
    }

    private void updateLevel(TextView levelTextView) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e("MainActivity", "User not authenticated");
            return;
        }

        if (score >= 500) {
            level = 3;
        } else if (score >= 100) {
            level = 2;
        } else {
            level = 1;
        }
        levelTextView.setText("Nivel: " + level);
        updateLevelInDatabase(currentUser.getUid(), level); // Update level in database
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            insertUser(currentUser.getUid(), score, level);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            getUserScore(currentUser.getUid());
        }
    }

    private void insertUser(String userId, int score, int level) {
        User user = new User(userId, "", "", score, level, pointsPerClick, upgradeCost);
        dbHelper.addUser(user, new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                // Not used here
            }

            @Override
            public void DataIsInserted() {
                Log.d("MainActivity", "User inserted successfully");
            }

            @Override
            public void DataIsUpdated() {
                // Not used here
            }

            @Override
            public void DataIsDeleted() {
                // Not used here
            }
        });
    }

    private void getUserScore(String userId) {
        dbHelper.getUser(userId, user -> {
            if (user != null) {
                score = user.getScore();
                level = user.getLevel();
                pointsPerClick = user.getPointsPerClick();
                upgradeCost = user.getUpgradeCost();
                updateUI();
            }
        });
    }

    private void updateUI() {
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Button upgradeButton = findViewById(R.id.upgradeButton);
        TextView levelTextView = findViewById(R.id.levelTextView);

        scoreTextView.setText("Puntos: " + score);
        levelTextView.setText("Nivel: " + level);
        upgradeButton.setText("Mejora (+" + pointsPerClick + " por clic): " + upgradeCost + " puntos");
    }

    private void updateScoreInDatabase(String userId, int score) {
        dbHelper.updateUserScore(userId, score, new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                // Not used here
            }

            @Override
            public void DataIsInserted() {
                // Not used here
            }

            @Override
            public void DataIsUpdated() {
                Log.d("MainActivity", "User score updated successfully");
            }

            @Override
            public void DataIsDeleted() {
                // Not used here
            }
        });
    }

    private void updateLevelInDatabase(String userId, int level) {
        dbHelper.updateUserLevel(userId, level, new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                // Not used here
            }

            @Override
            public void DataIsInserted() {
                // Not used here
            }

            @Override
            public void DataIsUpdated() {
                Log.d("MainActivity", "User level updated successfully");
            }

            @Override
            public void DataIsDeleted() {
                // Not used here
            }
        });
    }

    private void updatePointsPerClickInDatabase(String userId, int pointsPerClick) {
        dbHelper.updateUserPointsPerClick(userId, pointsPerClick, new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                // Not used here
            }

            @Override
            public void DataIsInserted() {
                // Not used here
            }

            @Override
            public void DataIsUpdated() {
                Log.d("MainActivity", "User points per click updated successfully");
            }

            @Override
            public void DataIsDeleted() {
                // Not used here
            }
        });
    }

    private void updateUpgradeCostInDatabase(String userId, int upgradeCost) {
        dbHelper.updateUserUpgradeCost(userId, upgradeCost, new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                // Not used here
            }

            @Override
            public void DataIsInserted() {
                // Not used here
            }

            @Override
            public void DataIsUpdated() {
                Log.d("MainActivity", "User upgrade cost updated successfully");
            }

            @Override
            public void DataIsDeleted() {
                // Not used here
            }
        });
    }
}