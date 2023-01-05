package com.example.new_meditation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.new_meditation.Ad_Global;
import com.example.new_meditation.R;
import com.example.new_meditation.TwoButtonDialogListener;


public class SettingActivity extends AppCompatActivity {
    Context context;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle((CharSequence) "");
        setSupportActionBar(toolbar);
        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((TextView) findViewById(R.id.adsPrivacyPolicy)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.showDialog();
            }
        });
    }


    public void showDialog() {
        Ad_Global.showPersonalizeDialog(false, this, getString(R.string.app_name), getString(R.string.app_description1), getString(R.string.app_description2), getString(R.string.app_description3), new TwoButtonDialogListener() {
            public void onCancel() {
                SettingActivity.this.finish();
            }

            public void onOk(boolean z) {
//                Ad_Global.setnpa(SettingActivity.this.context);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
