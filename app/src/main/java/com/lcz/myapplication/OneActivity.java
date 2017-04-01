package com.lcz.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lcz.myapplication.R.id.imagebutton5;

/**
 * Created by 洲洲 on 2016/3/22.
 */
public class OneActivity extends Activity {

    EditText mdate= null;

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_one);

        sp = getSharedPreferences("zconfig", MODE_PRIVATE);
        mdate= (EditText)findViewById(R.id.zmeditText4);
        String saved = sp.getString("zDate","");
        mdate.setText(saved);


        ImageButton mbutton6 = (ImageButton)findViewById(R.id.imagebutton6);
        mbutton6 .setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();

            }
        });


    }
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static boolean isDateNO(String date){
        Pattern p = Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");

        Matcher m = p.matcher(date);

        return m.matches();

        }
    public void showtime(){

        EditText editText4 = (EditText) findViewById(R.id.editText4);
        String text = editText4.getText().toString();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


        Date date_start = null;
        Date date_end = null;
        if (isDateNO(mdate.getText().toString())&&isDateNO(text)) {

        try {

            date_start = sdf.parse( mdate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

            try {
                date_end = sdf.parse(text);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int com=getGapCount(date_start, date_end);
            CharSequence cs =String.valueOf(com);
            TextView mTextView=(TextView) findViewById(R.id.textView);
            mTextView.setText(cs);
        }else if(!isDateNO(mdate.getText().toString())&&isDateNO(text)){
            Toast.makeText(OneActivity.this, "请按正确格式输入起始日期", Toast.LENGTH_SHORT).show();
        }else if(isDateNO(mdate.getText().toString())&&!isDateNO(text)){
            Toast.makeText(OneActivity.this, "请按正确格式输入终止日期", Toast.LENGTH_SHORT).show();
        }else if(!isDateNO(mdate.getText().toString())&&!isDateNO(text)){
            Toast.makeText(OneActivity.this, "请按正确格式输入起始和终止日期", Toast.LENGTH_SHORT).show();
        }


    }




    public void log2(View view){
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        String text = editText4.getText().toString();
        if( text.length()!= 0&& mdate.getText().toString().length()!=0){
            showtime();

        }else if( text.length()== 0&& mdate.getText().toString().length()!=0){

            Toast.makeText(OneActivity.this, "请输入终止日期", Toast.LENGTH_SHORT).show();
        }else if(( mdate.getText().toString().length()==0 &&text.length()!= 0)){

            Toast.makeText(OneActivity.this, "请起始输入日期", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(OneActivity.this, "请输入起始和终止日期", Toast.LENGTH_SHORT).show();
        }

    }

    public void savein(View view) {

        if (TextUtils.isEmpty(mdate.getText().toString())){
            Toast.makeText(getApplicationContext(), "请输入起始日期",
                    Toast.LENGTH_SHORT).show();


        }
        else if(isDateNO(mdate.getText().toString())){

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("zDate", mdate.getText().toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), "起始日期已保存",
                    Toast.LENGTH_SHORT).show();
            finish();
            startThisActivity();

        }
        else{

            Toast.makeText(getApplicationContext(), "请按格式输入起始日期",
                    Toast.LENGTH_SHORT).show();

        }


    }
    public void saveout(View view){
        if(  sp.getString("zDate","").length()!=0){
            showDialog2();

        }else{

            Toast.makeText(getApplicationContext(), "起始日期为空，无需清除",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void showDialog2(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("提示");
        mBuilder.setMessage("确定要清除起始日期吗?");
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mdate= (EditText)findViewById(R.id.zmeditText4);
                sp.edit().clear().commit();
                Toast.makeText(getApplicationContext(), "起始日期已清除",
                        Toast.LENGTH_SHORT).show();
                mdate.setText("");
                finish();
                startThisActivity();


            }

        });
        mBuilder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        mBuilder.create().show();
    }
    public void startThisActivity(){

        Intent mintent = new Intent(this,OneActivity.class);
        startActivity(mintent);

    }

}