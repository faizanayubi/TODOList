package org.swiftdeal.todolist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Faizan Ayubi on 15-07-2015.
 */
public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String todoText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TODO_TEXT));
        int pos = todoText.indexOf(10);
        if (pos != -1){
            todoText = todoText.substring(0, pos) + " ...";
        }

        TextView tv = (TextView) view.findViewById(R.id.tvTodo);
        tv.setText(todoText);
    }
}
