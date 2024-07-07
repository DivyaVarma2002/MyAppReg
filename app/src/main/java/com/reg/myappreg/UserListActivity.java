package com.reg.myappreg;

import android.util.Log;
import android.widget.ListView;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

        Log.d("UserListActivity", "Fetching user data from database");

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
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] { "username", "email" },
                new int[] { android.R.id.text1, android.R.id.text2 },
                0
        );

        listView.setAdapter(cursorAdapter);
    }
}
