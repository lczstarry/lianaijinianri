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
import android.widget.CheckBox;
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
 * Created by 洲洲 on 2016/3/28.
 */
public class ThreeActivity extends Activity {

    EditText amdate= null;
    EditText aamdate= null;

    private SharedPreferences sp;
    private SharedPreferences sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_three);

        sp = getSharedPreferences("ayconfig", MODE_PRIVATE);
        amdate= (EditText)findViewById(R.id.aymeditText4);
        String saved = sp.getString("ayDate","");
        amdate.setText(saved);
        showtime(saved);
        sp1 = getSharedPreferences("aayconfig", MODE_PRIVATE);
        aamdate= (EditText)findViewById(R.id.aaymeditText4);
        String asaved = sp1.getString("aname","");
        aamdate.setText(asaved);
        ImageButton amimagebutton6 = (ImageButton)findViewById(R.id.aimagebutton6);

        amimagebutton6 .setOnClickListener(new View.OnClickListener() {

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
    public void showtime(String str){
        TextView amTextView4=(TextView) findViewById(R.id.atextView4);
        Date date_start = null;
        Date date_end = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(isDateNO(str)){
            try {

                date_end = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date_start = new Date();
            int com=getGapCount(date_start, date_end);
            CharSequence cs =String.valueOf(com);

            amTextView4.setText(cs);
        }else{
            amTextView4.setText("0");


        }

    }
    public void savein(View view) {

        if (TextUtils.isEmpty(amdate.getText().toString())){
            Toast.makeText(getApplicationContext(), "请输入纪念日日期",
                    Toast.LENGTH_SHORT).show();


        }
        else if(isDateNO(amdate.getText().toString())){

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("ayDate", amdate.getText().toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), "纪念日日期已保存",
                    Toast.LENGTH_SHORT).show();
            finish();
            startThisActivity();

        }
        else{

            Toast.makeText(getApplicationContext(), "请按格式输入纪念日日期",
                    Toast.LENGTH_SHORT).show();

        }


    }
    public void saveout(View view){
        if(  sp.getString("ayDate","").length()!=0){
            showDialog2();

        }else{

            Toast.makeText(getApplicationContext(), "纪念日日期为空，无需清除",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void showDialog2(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("提示");
        mBuilder.setMessage("确定要清除纪念日日期吗?");
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                amdate = (EditText) findViewById(R.id.aymeditText4);
                sp.edit().clear().commit();
                Toast.makeText(getApplicationContext(), "纪念日日期已清除",
                        Toast.LENGTH_SHORT).show();
                amdate.setText("");
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

        Intent mintent = new Intent(this,ThreeActivity.class);
        startActivity(mintent);

    }
    public static boolean isDateNO(String date){
        Pattern p = Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");

        Matcher m = p.matcher(date);

        return m.matches();

    }

    public void asavein(View view) {

        if (TextUtils.isEmpty(aamdate.getText().toString())){
            Toast.makeText(getApplicationContext(), "请输入纪念日名称",
                    Toast.LENGTH_SHORT).show();


        }
        else {

            SharedPreferences.Editor editor = sp1.edit();
            editor.putString("aname", aamdate.getText().toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), "纪念日名称已保存",
                    Toast.LENGTH_SHORT).show();
            finish();
            startThisActivity();

        }



    }
    public void asaveout(View view){
        if(  sp1.getString("aname","").length()!=0){
            ashowDialog2();

        }else{

            Toast.makeText(getApplicationContext(), "纪念日名称为空，无需清除",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void ashowDialog2(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("提示");
        mBuilder.setMessage("确定要清除纪念日名称吗?");
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                aamdate = (EditText) findViewById(R.id.aaymeditText4);
                sp1.edit().clear().commit();
                Toast.makeText(getApplicationContext(), "纪念日名称已清除",
                        Toast.LENGTH_SHORT).show();
                aamdate.setText("");
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


}
