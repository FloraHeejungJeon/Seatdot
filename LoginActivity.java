package com.superoid.test.seatdot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by 전희정 on 2016-06-02.
 */
public class LoginActivity extends Activity {

    public static String Username;
    public static String Userid;
    public static String Userpassword;
    public static String Userbirth;
    public static String Usersex;
    public static String Userphone;
    public static String Userfollower;

    EditText ID;
    EditText PW;
    Button Login;
    Button Join;
    Button Skip;
    String id;
    String password;

    public static int loginornot;

    CheckBox Check_id;
    CheckBox Check_idpw;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ID = (EditText)findViewById(R.id.login_id);
        PW = (EditText)findViewById(R.id.login_pw);

        Check_id = (CheckBox)findViewById(R.id.Check_id);
        Check_idpw = (CheckBox)findViewById(R.id.Check_idpw);
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        id = pref.getString("editText", "");
        Boolean chk_id = pref.getBoolean("chk_id", false);
        Boolean chk_idpw = pref.getBoolean("chk_idpw", false);

        Check_id.setChecked(chk_id);
        Check_idpw.setChecked(chk_idpw);

        if(Check_id.isChecked()) {
            ID.setText(id);
        }

        Login = (Button)findViewById(R.id.loginbtn);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = ID.getText().toString();
                password = PW.getText().toString();
                insertToDatabase(id, password);

            }
        });
        Join = (Button)findViewById(R.id.joinbtn);
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(join);
            }
        });
        Skip = (Button)findViewById(R.id.skipbtn);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(skip);
            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    private void insertToDatabase(String id, String password){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];
                    String password = (String)params[1];

                    String link="http://florajeon.vps.phps.kr/seatdotmemberlogin.php";
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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

                if(s.equalsIgnoreCase("failure")){
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                }else {
                    String str = s;
                    String[] info = str.split(":@/#@:");
                        String idx = info[0];
                        Username = info[1];
                        Userid = info[2];
                        Userpassword = info[3];
                        Userbirth = info[4];
                        Usersex = info[5];
                        Userphone = info[6];
                        Userfollower = info[7];

                    Toast.makeText(getApplicationContext(), Userid+"님 "+"Welcome!", Toast.LENGTH_LONG).show();
                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                    loginornot = 1;
                    login.putExtra("id", Userid);
                    login.putExtra("name", Username);
                    login.putExtra("pass", Userpassword);
                    login.putExtra("birth", Userbirth);
                    login.putExtra("sex", Usersex);
                    login.putExtra("phone", Userphone);
                    login.putExtra("follower", Userfollower);
                    startActivity(login);
                }
            }
        }

        InsertData task = new InsertData();
        task.execute(id, password);
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
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ID = (EditText)findViewById(R.id.login_id);

        Check_id = (CheckBox)findViewById(R.id.Check_id);
        Check_idpw = (CheckBox)findViewById(R.id.Check_idpw);

        editor.putString("editText", ID.getText().toString());
        editor.putBoolean("chk_id", Check_id.isChecked());
        editor.putBoolean("chk_idpw", Check_idpw.isChecked());

        editor.commit();

        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
