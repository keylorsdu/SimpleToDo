package com.example.keylorlidan.simpletodo;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView Todolist;
    EditText todoitem;
    ArrayList<String> mitems;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Intent intent = new Intent(this, MainActivity2.class);
//        startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_main);
        todoitem = (EditText) findViewById(R.id.todo_item);
        Todolist = (ListView) findViewById(R.id.listView);
        readitems();
        if (mitems == null)
            mitems = new ArrayList<String>();
        adapter = new MysimpleAdapter(this,mitems);
        Todolist.setAdapter(adapter);

        Todolist.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> madapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        mitems.remove(pos);
                        // Refresh the adapter
                        adapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        writeitems();
                        return true;
                    }

                });

    }
    class MysimpleAdapter extends  ArrayAdapter<String>{
        MysimpleAdapter(Context c,ArrayList<String>items) {
            super(c, 0, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final String item = getItem(position);
            final TextView message ;
            Button edit ;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_activity2, null);

            }
            message =(TextView) convertView.findViewById(R.id.message);
            edit = (Button) convertView.findViewById(R.id.edit);
            if (edit != null) {
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        intent = new Intent(getContext(),EditActiviy.class);
                        intent.putExtra(Constrains.MESSEGE_TAG, position);
                        intent.putExtra(Constrains.MESSEGE_TAG, item);
                        startActivityForResult(intent, Constrains.REQUEST_EDIT);


                    }
                });
            }
            if (message != null) {
                message.setText(item);

            }
            return  convertView;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constrains.REQUEST_EDIT) {
                Bundle bundle = data.getExtras();
                String item = bundle.getString(Constrains.MESSEGE_TAG);
                int pos = bundle.getInt(Constrains.MESSAGE_POS);
                mitems.set(pos, item);
                adapter.notifyDataSetChanged();
                writeitems();


            }
        }
    }

    public void readitems(){
        File dir = getFilesDir();
        File todofile = new File(dir,"todo.txt");
        try {
            mitems = new ArrayList<String>(FileUtils.readLines(todofile));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void writeitems(){
        File dir = getFilesDir();
        File todofile = new File(dir,"todo.txt");
        try {
            FileUtils.writeLines(todofile,mitems);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void additem(View view){
       if(todoitem != null) {
           mitems.add(todoitem.getText().toString());
           writeitems();
           todoitem.setText("");

       }
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeitems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
