package ics466.oahuhikes;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

public class MainSearch extends AppCompatActivity {
    private static ListView searchResultList;
    private static ArrayList<String> hikes = new ArrayList<String>();
    SQLiteHelper myDB;
    EditText editSearch;
    Button searchButton;
    RadioButton radioButtonName, radioButtonLen, radioButtonDiff;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new SQLiteHelper(this);
        editSearch = (EditText) findViewById(R.id.editText_Search);
        searchButton = (Button) findViewById(R.id.button_search);
        radioButtonName = (RadioButton) findViewById(R.id.searchName);
        radioButtonLen = (RadioButton) findViewById(R.id.searchLength);
        radioButtonDiff = (RadioButton) findViewById(R.id.searchDiff);
        searchResultList = (ListView) findViewById(R.id.resultsListView);
        adapter = new ArrayAdapter<String>(this, R.layout.hikes, hikes);
        search();
        listView();
    }

    public void listView()
    {
        searchResultList.setAdapter(adapter);
    }

    public void search()
    {
        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res;

                        if(!hikes.isEmpty())
                        {
                            hikes.clear();
                        }

                        if(radioButtonName.isChecked())
                        {
                            res = myDB.search(editSearch.getText().toString(), "NAME");
                        }
                        else if(radioButtonLen.isChecked())
                        {
                            res = myDB.search(editSearch.getText().toString(), "LENGTH");
                        }
                        else
                        {
                            res = myDB.search(editSearch.getText().toString(), "DIFFICULTY");
                        }
                        if(res.getCount() == 0)
                        {
                            showMessage("Error", "No data found.");
                            return;
                        }
                        else {
                            while (res.moveToNext()) {
                                hikes.add("Name:" + res.getString(0) + ", Length:" + res.getString(1)
                                        + ", Difficulty:" + res.getString(2) + "\n\n");
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_search, menu);
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
