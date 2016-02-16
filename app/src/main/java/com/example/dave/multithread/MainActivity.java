package com.example.dave.multithread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private File file;
    private String filename;
    private ArrayAdapter<String> myAdapter;
    private Creatable create;
    private Loadable load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filename = "numbers.txt";
        file = new File(this.getFilesDir(), filename);
        create = new Creatable(file);

        ListView myListView = (ListView)(findViewById(R.id.listView));
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myListView.setAdapter(myAdapter);
        Loadable load = new Loadable(myAdapter, file);
    }

    public void create(View v) {
        Thread create_thread = new Thread(create);
        create_thread.start();
    }

    public void load(View v) {
        Thread load_thread = new Thread(load);
        load_thread.start();
    }

    public void clear(View v) {
        myAdapter.clear();
    }
}

class Creatable implements Runnable {

    private File file;

    public void run() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            for (int i = 1; i < 11; ++i) {
                String output = (String.valueOf(i) + "\n");
                out.write(output);
//                Thread.sleep(250);
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println("Threw: " + e);
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    Creatable(File myFile) {
        file = myFile;
    }

}

class Loadable implements Runnable {

    private File file;
    private ArrayAdapter<String> myAdapter;

    public void run() {
        String line;

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            while ((line = in.readLine()) != null) {
                myAdapter.add(line);
//                Thread.sleep(250);
            }
            in.close();
        }
        catch (IOException e) {
            System.out.println("Threw: " + e);
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    Loadable(ArrayAdapter<String> adapter, File myFile) {
        myAdapter = adapter;
        file = myFile;
    }


}