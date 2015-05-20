package com.example.ali.tutorial_gettingstarted;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//TODO: change name from "send" to "add"
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.ali.tutorial_gettingstarted.MESSAGE";
    private ArrayList<String> toDo = new ArrayList<>();
    private ListView listViewItems;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewItems = (ListView) findViewById(R.id.listView);


        readItems();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDo);
        listViewItems.setAdapter(adapter);


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

    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        if (message.length() == 0) {
            return;
        }
        toDo.add(0, message);
        adapter.notifyDataSetChanged();
        editText.setText("");
        writeItems();

    }

    //TODO:  put in a reminder / create paperbin
    // TODO: undo
    private void setupListViewListener() {
        listViewItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterList,
                                                   View item, int pos, long id) {

                    showNoticeDialog(pos);
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



    public void showNoticeDialog(int pos) {
        final int position = pos;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.dialog_dialog_list);

        alertBuilder.setPositiveButton(R.string.delete,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //save
                        toDo.remove(position);
                        adapter.notifyDataSetChanged();
                        writeItems();

                        //toast popup
                        Context context = getApplicationContext();
                        CharSequence text =  getResources().getString(R.string.deleted);;
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
        alertBuilder.setNeutralButton(R.string.set_time, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //toast
                Context context = getApplicationContext();
                CharSequence text =  getResources().getString(R.string.not_implemented);;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
        alertBuilder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}

