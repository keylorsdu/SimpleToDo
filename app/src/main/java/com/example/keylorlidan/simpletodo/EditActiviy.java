package com.example.keylorlidan.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class EditActiviy extends ActionBarActivity {
    @InjectView(R.id.edit_txt) EditText message;

    @InjectView(R.id.submit_area) Button submit;
    @InjectView(R.id.edit_title) TextView title;

    String Message;
    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);
        ButterKnife.inject(this);
        Position = getIntent().getExtras().getInt(Constrains.MESSAGE_POS);
        Message = getIntent().getExtras().getString(Constrains.MESSEGE_TAG);
        message.setText(Message);
        title.setText("keylor"+Integer.toString(Position));


    }

    @OnClick(R.id.submit_area)
    public void submit() {
        if(message!=null) {
            Message = message.getText().toString();
            Intent resultintent = new Intent();
            resultintent.putExtra(Constrains.MESSAGE_POS, Position);
            resultintent.putExtra(Constrains.MESSEGE_TAG, Message);
            setResult(RESULT_OK, resultintent);
            finish();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
