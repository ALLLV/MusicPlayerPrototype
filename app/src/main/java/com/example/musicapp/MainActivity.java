package com.example.musicapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_main);
    ArrayList<AudioModel> songsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission()) {
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        while (cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
            if (new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if (songsList.size() == 0){
            Toast.makeText(MainActivity.this, "NO SONGS IN HERE!", Toast.LENGTH_SHORT).show();
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyAdapter(songsList, getApplicationContext()));
        }


        Button musicButton = (Button) findViewById(R.id.button_music);
        Button playlistButton = (Button) findViewById(R.id.button_playlist);
        Button filesButton = (Button) findViewById(R.id.button_files);

        musicButton.setBackgroundResource(R.drawable.button_for_menu_active);

        //butts
        View.OnClickListener menuButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_music){
                    if (musicButton.getBackground() == getDrawable(R.drawable.button_for_menu_active)
                            && playlistButton.getBackground() == getDrawable(R.drawable.button_for_menu)
                            && filesButton.getBackground() == getDrawable(R.drawable.button_for_menu)){
                        return;
                    }
                    else {
                        musicButton.setBackgroundResource(R.drawable.button_for_menu_active);
                        filesButton.setBackgroundResource(R.drawable.button_for_menu);
                        playlistButton.setBackgroundResource(R.drawable.button_for_menu);
                    }
                }
                else if (v.getId() == R.id.button_playlist){
                    if (playlistButton.getBackground() == getDrawable(R.drawable.button_for_menu_active)
                            && musicButton.getBackground() == getDrawable(R.drawable.button_for_menu)
                            && filesButton.getBackground() == getDrawable(R.drawable.button_for_menu)){
                        return;
                    }
                    else {
                        playlistButton.setBackgroundResource(R.drawable.button_for_menu_active);
                        musicButton.setBackgroundResource(R.drawable.button_for_menu);
                        filesButton.setBackgroundResource(R.drawable.button_for_menu);
                    }
                }
                else if (v.getId() == R.id.button_files){
                    if (filesButton.getBackground() == getDrawable(R.drawable.button_for_menu_active)
                            && playlistButton.getBackground() == getDrawable(R.drawable.button_for_menu)
                            && musicButton.getBackground() == getDrawable(R.drawable.button_for_menu)){
                        return;
                    }
                    else {
                        filesButton.setBackgroundResource(R.drawable.button_for_menu_active);
                        musicButton.setBackgroundResource(R.drawable.button_for_menu);
                        playlistButton.setBackgroundResource(R.drawable.button_for_menu);
                    }
                }
            }
        };
        //end butts

        musicButton.setOnClickListener(menuButtonClick);
        playlistButton.setOnClickListener(menuButtonClick);
        filesButton.setOnClickListener(menuButtonClick);
    }

    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return  true;
        }
        else {
            return false;
        }
    }

    void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTINGS!", Toast.LENGTH_SHORT).show();
        }
        else {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }
}