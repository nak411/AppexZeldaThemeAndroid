package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zeldaiconpack.zeldatheme.R;

/**
 * Created by Naveed on 11/30/13.
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLaunchDefault:
                launchDefault();
                break;
            case R.id.btnIconPicker:
                launchIconPicker();
                break;
        }
    }

    private void launchDefault(){
        Intent intent = new Intent(this, DefaultSelectionActivity.class);
        startActivity(intent);

    }

    private void launchIconPicker(){
        Intent intent = new Intent(this, IconPickerActivity.class);
        startActivity(intent);
    }
}
