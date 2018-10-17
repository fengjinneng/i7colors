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
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {


        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button3:
                Calendar c= Calendar.getInstance();
                //直接创建一个DatePickerDialog对话框实例，并显示出来
                new DatePickerDialog(this,
                        //绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String text="您选择了:"+year+"年"+(month+1)
                                        +"月"+dayOfMonth+"日";
                            }
                        }
                        //设置初始日期
                        ,c.get(Calendar.YEAR)
                        ,c.get(Calendar.MONTH)
                        ,c.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }
}
