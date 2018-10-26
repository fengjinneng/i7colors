package com.company.qcy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Button
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_wode_qiugouliebiao);
        initView();
    }

    private void initView() {





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button3:

                break;
        }
    }
}
