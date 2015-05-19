package com.example.ali.tutorial_gettingstarted;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.ali.tutorial_gettingstarted.MESSAGE";
    private ArrayList<String> toDo = new ArrayList<>();
    private ListView lvItems;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvItems = (ListView) findViewById(R.id.listView);



        readItems();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, toDo);
        lvItems.setAdapter(adapter);


        setupListViewListener();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openSettings() {
    }

    private void openSearch() {
    }

    public void sendMessage(View view){
//        Intent intent = new Intent(this,DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        if(message.length()==0){
            return;
        }
        toDo.add(message);
        adapter.notifyDataSetChanged();
        editText.setText("");
        writeItems();

    }
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterr,
                                                   View item, int pos, long id) {
                        toDo.remove(pos);
                        adapter.notifyDataSetChanged();


                        writeItems();

                        return true;
                    }

                }

        );

    }
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            toDo = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {

            toDo = new ArrayList<String>();
            toDo.add("couldn't read data");
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, toDo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

