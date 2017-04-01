package com.lcz.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    EditText mdate= null;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 =(Button)findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startOneActivity();

            }

        });
        Button mmbutton3 = (Button)findViewById(R.id.mbutton3);
        mmbutton3 .setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                showDialog();
            }
        });
        sp = getSharedPreferences("config", MODE_PRIVATE);
        mdate= (EditText)findViewById(R.id.meditText4);
        String saved = sp.getString("Date","");
        mdate.setText(saved);

        showtime(saved);
        Button mbutton1 = (Button)findViewById(R.id.mbutton1);
        mbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTwoActivity();

            }

        });
    }
    public void savein(View view) {

        if (TextUtils.isEmpty(mdate.getText().toString())){
            Toast.makeText(getApplicationContext(), "请输入日期",
                    Toast.LENGTH_SHORT).show();


        }
        else if(isDateNO(mdate.getText().toString())){

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Date", mdate.getText().toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), "日期已保存",
                    Toast.LENGTH_SHORT).show();
            finish();
            startThisActivity();

        }
        else{

            Toast.makeText(getApplicationContext(), "请按格式输入日期~",
                    Toast.LENGTH_SHORT).show();

        }


    }
    public void saveout(View view){
        if(  sp.getString("Date","").length()!=0){
            showDialog2();

        }else{

            Toast.makeText(getApplicationContext(), "起始日期为空，无需清除",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public  void log3(View view ){

        startThreeActivity();

    }


    private void showDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("提示");
        mBuilder.setMessage("真的要离开吗?");
        mBuilder.setPositiveButton("转身离开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                MainActivity.this.finish();

            }

        });
        mBuilder.setNegativeButton("不忍离去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        mBuilder.create().show();
    }
    private void showDialog2(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("提示");
        mBuilder.setMessage("确定要清除起始日期吗?");
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mdate= (EditText)findViewById(R.id.meditText4);
                sp.edit().clear().commit();
                Toast.makeText(getApplicationContext(), "日期已清除",
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
    @Override
    public void onBackPressed() {
        showDialog();

    }
    public void startThreeActivity(){

        Intent mintent = new Intent(this,ThreeActivity.class);
        startActivity(mintent);

    }
    public void startThisActivity(){

        Intent mintent = new Intent(this,MainActivity.class);
        startActivity(mintent);

    }
    public void startOneActivity(){

        Intent mintent = new Intent(this,OneActivity.class);
        startActivity(mintent);

    }
    public void startTwoActivity(){

        Intent mintent = new Intent(this,TwoActivity.class);
        startActivity(mintent);

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
        TextView mTextView4=(TextView) findViewById(R.id.textView4);
        Date date_start = null;
        Date date_end = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    if(isDateNO(str)){
        try {

            date_start = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date_end = new Date();
        int com=getGapCount(date_start, date_end);
        CharSequence cs =String.valueOf(com);

        mTextView4.setText(cs);
    }else{
        mTextView4.setText("0");


    }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static boolean isDateNO(String date) {
        Pattern p = Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");

        Matcher m = p.matcher(date);

        return m.matches();

    }

}
