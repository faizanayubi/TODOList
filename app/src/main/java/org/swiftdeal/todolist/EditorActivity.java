package org.swiftdeal.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class EditorActivity extends ActionBarActivity {

    private String action;
    private EditText editor;
    private String todoFilter;
    private String oldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(TodoProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.add_task));
        } else {
            action = Intent.ACTION_EDIT;
            todoFilter = DBOpenHelper.TODO_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri, DBOpenHelper.ALL_COLUMNS, todoFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TODO_TEXT));
            editor.setText(oldText);
            editor.requestFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)){
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                finishedEditing();
                break;
            case R.id.action_delete:
                deleteTodo();
                break;
        }

        return true;
    }

    private void deleteTodo() {
        getContentResolver().delete(TodoProvider.CONTENT_URI, todoFilter, null);
        Toast.makeText(this, getString(R.string.todo_deleted), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void finishedEditing() {
        String newText = editor.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if (newText.length() == 0) {
                    setResult(RESULT_CANCELED);
                } else {
                    insertTodo(newText);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0) {
                    deleteTodo();
                } else if (oldText.equals(newText)){
                    setResult(RESULT_CANCELED);
                } else {
                    updateTodo(newText);
                }
        }

        finish();
    }

    private void updateTodo(String todoText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.TODO_TEXT, todoText);
        getContentResolver().update(TodoProvider.CONTENT_URI, values, todoFilter, null);
        Toast.makeText(this, getString(R.string.todo_updated), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void insertTodo(String todoText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.TODO_TEXT, todoText);
        getContentResolver().insert(TodoProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishedEditing();
    }
}
