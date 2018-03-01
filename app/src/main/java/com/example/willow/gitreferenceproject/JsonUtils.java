package com.example.willow.gitreferenceproject;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Edited and Created by Willow on 2/22/2018.
 */


/**
 * Created by kbriggs on 2/19/18.
 */

public class JsonUtils {

    private static InputStream is;

    public static String parseJsonToString(InputStream is) {
        JsonUtils.is = is;
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<GitReference> populateGitReferences(String jsonString) {
        ArrayList<GitReference> data=new ArrayList<>();

        JSONArray jArray = null;
        try {
            // Load json String into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Extract all the "command" JSON objects into a JsonArray
            jArray = jsonObject.getJSONArray("commands");

            // Extract json objects from JsonArray and store into ArrayList as class objects
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                GitReference gitReference = new GitReference();
                gitReference.setCommand(json_data.getString("command"));
                gitReference.setExample(json_data.getString("example"));
                gitReference.setExplanation(json_data.getString("explanation"));

                Log.i("JSON", "Adding: " + gitReference.getCommand());
                Log.i("JSON", "Adding: " + gitReference.getExample());
                Log.i("JSON", "Adding: " + gitReference.getExplanation());
                data.add(gitReference);
            }

        } catch (Exception ex) {

        }

        return data;
    }

    public static String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    public static boolean create(Context context, String fileName, String jsonString){
        try {
            FileOutputStream fos =  context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    public static boolean append(Context context, String fileName, String jsonString) throws IOException {
        InputStream is = context.getApplicationContext().getAssets().open("gitReference.json");
        Log.i("JSON", jsonString);
        return false;
    }

    public static boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }



    public static class GitReference {
        private String command;
        private String example;
        private String explanation;
        private String section;

        public GitReference() {}

        public GitReference(String command, String example, String explanation, String section) {
            this.command = command;
            this.example = example;
            this.explanation = explanation;
            this.section = section;
        }

        public void setCommand(String command) {

            this.command = command;
        }

        public String getCommand() {
            return command;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }
    }
}
