package com.android.utils.androidmemtest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import com.android.utils.androidmem.AndroidMem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart ()
    {
        super.onStart();
        Log.d("onStart", AndroidMem.getVMHeapInfo());
        Log.d("onStart", AndroidMem.getNativeHeapInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
