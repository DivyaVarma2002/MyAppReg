package com.reg.myappreg;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_item);
        TextView userDetailsHeading = findViewById(R.id.user_details_heading);

        Log.d("UserListActivity", "Fetching user data from database");

        userDetailsHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the registration page
                Intent intent = new Intent(UserListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        displayUserList();
    }

    private void displayUserList() {
        // Modify the query to alias 'id' as '_id'
        String query = "SELECT id AS _id, username, email FROM " + DatabaseHelper.TABLE_NAME;
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            // Log cursor data
            Log.d("UserListActivity", "User data fetched successfully");
        } else {
            // Log empty cursor
            Log.d("UserListActivity", "No user data found");
        }

        cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item, // Custom layout for list items
                cursor,
                new String[] { "username", "email" },
                new int[] { R.id.text1, R.id.text2 },
                0
        ) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);

                // Find the delete button in the view
                Button deleteButton = view.findViewById(R.id.delete_button);
                // Set a click listener for the delete button
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the ID of the item to delete
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                        deleteUser(id);
                    }
                });
            }
        };

        listView.setAdapter(cursorAdapter);
    }

    private void deleteUser(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        // Refresh the list after deletion
        displayUserList();
    }
}