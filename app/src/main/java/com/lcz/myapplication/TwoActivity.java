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
public class TwoActivity extends Activity {

    EditText mdate= null;

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_two);

        sp = getSharedPreferences("yconfig", MODE_PRIVATE);
        mdate= (EditText)findViewById(R.id.ymeditText4);
        String saved = sp.getString("yDate","");
        mdate.setText(saved);
        ImageButton mimagebutton5 = (ImageButton)findViewById(R.id.imagebutton5);

        mimagebutton5 .setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();

            }
        });

}
    public void log(View view){
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        String text = editText3.getText().toString();
        if( text.length()!= 0&&  mdate.getText().toString().length()!=0){
            addtime();

        }else if( text.length()== 0&& mdate.getText().toString().length()!=0){

            Toast.makeText(TwoActivity.this, "请输入天数", Toast.LENGTH_SHORT).show();
        }else if(( mdate.getText().toString().length()==0 &&text.length()!= 0)){

            Toast.makeText(TwoActivity.this, "请起始输入日期", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(TwoActivity.this, "请输入起始日期和天数", Toast.LENGTH_SHORT).show();
        }

    }

    public void addtime() {


        EditText editText3 = (EditText) findViewById(R.id.editText3);
        String text = editText3.getText().toString();
        int intext=Integer.valueOf(text);



        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        if(isDateNO(mdate.getText().toString())){
        try {
            dt = sdf.parse(  mdate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c=Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, intext);

        int year1 = c.get(Calendar.YEAR);
        CharSequence y1 =String.valueOf(year1);

        int month1 = c.get(Calendar.MONTH) + 1;
        CharSequence m1 =String.valueOf(month1);

        int date1 = c.get(Calendar.DATE);
        CharSequence d1 =String.valueOf(date1);


        TextView mTextView5=(TextView) findViewById(R.id.textView5);
        mTextView5.setText(y1 + "年" + m1 + "月" + d1 + "日");

    } else{
        Toast.makeText(getApplicationContext(), "请按格式输入起始日期",
                Toast.LENGTH_SHORT).show();
    }
    }
    public void savein(View view) {

        if (TextUtils.isEmpty(mdate.getText().toString())){
            Toast.makeText(getApplicationContext(), "请输入起始日期",
                    Toast.LENGTH_SHORT).show();


        }
        else if(isDateNO(mdate.getText().toString())){

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("yDate", mdate.getText().toString());
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
        if(  sp.getString("yDate","").length()!=0){
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
                mdate = (EditText) findViewById(R.id.ymeditText4);
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

        Intent mintent = new Intent(this,TwoActivity.class);
        startActivity(mintent);

    }
    public static boolean isDateNO(String date){
        Pattern p = Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");

        Matcher m = p.matcher(date);

        return m.matches();

    }




}
