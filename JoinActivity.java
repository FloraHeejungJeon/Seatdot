package com.superoid.test.seatdot;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.zip.Inflater;

/**
 * Created by 전희정 on 2016-06-02.
 */
public class JoinActivity extends Activity {

    //**드로어와 액션바

    private Inflater menuInflater;
    ///**

    EditText Name;
    EditText ID;
    EditText PW;
    EditText PW_re;
//    EditText Birth;
//    EditText Sex;
    EditText Phone;
    EditText Follower;
    Button Join;

    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    RadioButton male;
    RadioButton female;
    RadioGroup rg;

    static final int DATE_DIALOG_ID = 0;

    private final String urlPath = "http://florajeon.vps.phps.kr/seatdotmemberinsert.php";
    private FragmentManager supportFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        rg = (RadioGroup) findViewById(R.id.rdgroup);

        Name = (EditText)findViewById(R.id.edit_name);
        ID = (EditText)findViewById(R.id.edit_email);
        PW = (EditText)findViewById(R.id.edit_pw);
        PW_re = (EditText)findViewById(R.id.edit_pwre);
        Phone = (EditText)findViewById(R.id.edit_phone);
        Follower = (EditText)findViewById(R.id.edit_follower);
        Join = (Button)findViewById(R.id.submit_join);
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PW.getText().toString().equals(PW_re.getText().toString())){
                    insert(v);
                }else {
                    Toast.makeText(JoinActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPickDate = (Button)findViewById(R.id.birthbtn);
        mPickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();

    }
    ////end of onCreate


    private void updateDisplay()
    {
        mPickDate.setText(new StringBuilder()
                .append(mYear).append("-")
                .append(mMonth+1).append("-")
                .append(mDay).append(""));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch(id)
        {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }


    public void insert(View view){
        String name = Name.getText().toString();
        String id = ID.getText().toString();
        String pw = PW.getText().toString();
        String pw_re = PW_re.getText().toString();
        String birth = mPickDate.getText().toString();
        RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        String sex = rd.getText().toString();
        String phone = Phone.getText().toString();
        String follower = Follower.getText().toString();

        insertToDatabase(name, id, pw, birth, sex, phone, follower);


    }

    private void insertToDatabase(String name, String id, String pw, String birth, String sex, String phone, String follower){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(JoinActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];
                    String id = (String)params[1];
                    String pw = (String)params[2];
                    String birth = (String)params[3];
                    String sex = (String)params[4];
                    String phone = (String)params[5];
                    String follower = (String)params[6];

            String link="http://florajeon.vps.phps.kr/seatdotmemberinsert.php";
            String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("pw", "UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");
            data += "&" + URLEncoder.encode("birth", "UTF-8") + "=" + URLEncoder.encode(birth, "UTF-8");
            data += "&" + URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(sex, "UTF-8");
            data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
            data += "&" + URLEncoder.encode("follower", "UTF-8") + "=" + URLEncoder.encode(follower, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            return sb.toString();

        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intro = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intro);
                }else {
                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_LONG).show();
                }
            }
        }

        InsertData task = new InsertData();
        task.execute(name, id, pw, birth, sex, phone, follower);
    }

    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    @Override
    protected void onStart() {
    super.onStart();
}
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

