package com.example.willow.gitreferenceproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    InputStream inputStream;
    String jsonStr;
    ArrayList<JsonUtils.GitReference> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.GitReference);


        try {
            InputStream inputStream = getApplicationContext().getAssets().open("gitReference.json");
            jsonStr = JsonUtils.parseJsonToString(inputStream);
            listItems = JsonUtils.populateGitReferences(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }



        GitReferenceAdapter adapter = new GitReferenceAdapter(this, listItems);
        listView.setAdapter(adapter);

    }

    private ArrayList<String> populateData(String jsonFileName) {
        ArrayList<String> returnList = new ArrayList<>();

        String jsonString = processData(jsonFileName);
        Log.i("JSON", jsonString);

        ArrayList<JsonUtils.GitReference> references = JsonUtils.populateGitReferences(jsonString);

        for(JsonUtils.GitReference g:references) {
            returnList.add(g.getCommand());
        }

        return returnList;
    }

    private String processData(String filename) {
        String jsonString = "";
        boolean isFilePresent = JsonUtils.isFilePresent(this, filename);

        if(isFilePresent) {
            jsonString = JsonUtils.read(this, filename);

            Log.i("JSON", "Json was present");
        }

        else {
            Log.i("JSON", "Json file is not present. Creating......");
            InputStream is = null;
            try {
                is = getApplicationContext().getAssets().open("gitReference.json");
            }
            catch (Exception e) {

            }

            jsonString = JsonUtils.parseJsonToString(is);
            boolean isFileCreated = JsonUtils.create(this, filename, jsonString);

            if(isFileCreated) {
                Log.i("JSON", "Created the file JSON");
            }

            else {

            }
        }

        return jsonString;
    }
}
