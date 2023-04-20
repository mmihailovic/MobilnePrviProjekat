package com.example.myapplication.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

public class MyApplication extends Application {
    public static String password = "123";
    public static File file;
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        File file = new File(getExternalFilesDir(null), "password.txt");
        if (!file.exists()) {
            try {
                // Stvaranje nove datoteke
                file.createNewFile();

                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    outputStream.write("A1aaa".getBytes());
                    outputStream.close();
                    password = "A1aaa";
                    Log.d("password",password);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                StringBuilder stringBuilder = new StringBuilder();
                int character;
                while ((character = inputStream.read()) != -1) {
                    stringBuilder.append((char) character);
                }
                password = stringBuilder.toString();
                Log.d("password application",password);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        this.file = file;
    }
}
