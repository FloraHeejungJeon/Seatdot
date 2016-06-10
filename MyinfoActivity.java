package com.superoid.test.seatdot;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

/**
 * Created by 전희정 on 2016-06-06.
 */
public class MyinfoActivity extends Activity {

    public static String name;
    public static String id;


    static final int DATE_DIALOG_ID = 0;


    //**드로어와 액션바1
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            } else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText(mNavItems.get(position).mTitle);
            subtitleView.setText(mNavItems.get(position).mSubtitle);
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }

    private static String TAG = MainActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    RelativeLayout mMainContent;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    TextView UserName;
    TextView UserId;

    private Inflater menuInflater;
    ///**드로어관련1끝


    EditText Name;
    TextView ID;
    EditText PW;
    EditText NewPW;
    EditText NewPW_re;
    //    EditText Birth;
//    EditText Sex;
    EditText Phone;
    TextView Follower;
    Button Edit;

    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    RadioButton male;
    RadioButton female;
    RadioGroup rg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        rg = (RadioGroup) findViewById(R.id.rdgroup);

        Name = (EditText) findViewById(R.id.myinfo_name);
        ID = (TextView) findViewById(R.id.myinfo_email);
        PW = (EditText) findViewById(R.id.myinfo_pw);
        NewPW = (EditText) findViewById(R.id.myinfo_newpw);
        NewPW_re = (EditText) findViewById(R.id.myinfo_newpwre);
        Phone = (EditText) findViewById(R.id.myinfo_phone);
        Follower = (TextView) findViewById(R.id.myinfo_follower);


        ///**드로어에 나오는 유저네임과 아이디
        UserName = (TextView) findViewById(R.id.userName);
        UserId = (TextView) findViewById(R.id.userId);
        UserName.setText(MainActivity.name);
        UserId.setText(MainActivity.id);
        ////////////

        Name.setText(MainActivity.name);
        ID.setText(MainActivity.id);
        Phone.setText(MainActivity.phone);
        Follower.setText(MainActivity.follower);

        Edit = (Button) findViewById(R.id.edit_myinfo);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PW.getText().toString().equals(MainActivity.password)) {

                        if (NewPW.getText().toString().equals(NewPW_re.getText().toString())) {
                            //        insert(v);
                        } else {
                            Toast.makeText(MyinfoActivity.this, "새로운 비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                } else {
                    Toast.makeText(MyinfoActivity.this, "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            }
            }
        });
        mPickDate = (Button) findViewById(R.id.birthbtn);
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


        //**드로어관련2
        if(MainActivity.id.equals("Please login")) {
            mNavItems.add(new NavItem("관심스토어", "Check your favorite stores", R.drawable.ic_action_mylist));
            mNavItems.add(new NavItem("스토어등록", "Register your store", R.drawable.ic_action_newstore));
            mNavItems.add(new NavItem("고객센터", "Seatdot is always open", R.drawable.ic_action_contact));
            mNavItems.add(new NavItem("환경설정", "Set your environment", R.drawable.ic_action_setting2));
            mNavItems.add(new NavItem("로그인", "Enjoy more!", R.drawable.ic_action_login));
        }else{
            mNavItems.add(new NavItem("내정보관리", "Manage your information", R.drawable.ic_action_myinfo));
            mNavItems.add(new NavItem("관심스토어", "Check your favorite stores", R.drawable.ic_action_mylist));
            mNavItems.add(new NavItem("스토어관리", "Manage your store", R.drawable.ic_action_store));
            mNavItems.add(new NavItem("스토어등록", "Register your store", R.drawable.ic_action_newstore));
            mNavItems.add(new NavItem("고객센터", "Seatdot is always open", R.drawable.ic_action_contact));
            mNavItems.add(new NavItem("환경설정", "Set your environment", R.drawable.ic_action_setting2));
            mNavItems.add(new NavItem("로그아웃", "Are you sure?", R.drawable.ic_action_logout));
        }
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mMainContent = (RelativeLayout) findViewById(R.id.mainContent);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter Dadapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(Dadapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
                if(MainActivity.id.equals("Please login")) { //로그인안되어있으면
                    switch (position) {
                        case 0: //관심스토어
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case 1: //스토어등록
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case 2: //고객센터
                            Intent goHelp = new Intent(MyinfoActivity.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 3: //환경설정
                            Intent goSetting = new Intent(MyinfoActivity.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 4: //로그인
                            Intent gologin = new Intent(MyinfoActivity.this, LoginActivity.class);
                            startActivity(gologin);
                            break;
                    }
                }else { //로그인 되어있으면
                    switch (position) {
                        case 0: //내정보관리
                            break;
                        case 1: //관심스토어
                            Intent goMylist = new Intent(MyinfoActivity.this, MylistActivity.class);
                            startActivity(goMylist);
                            break;
                        case 2: //스토어관리
                            Intent goManageStore = new Intent(MyinfoActivity.this, StoreActivity_Manage.class);
                            startActivity(goManageStore);
                            break;
                        case 3: //스토어등록
                            Intent goNewStore = new Intent(MyinfoActivity.this, StoreActivity_New.class);
                            startActivity(goNewStore);
                            break;
                        case 4: //고객센터
                            Intent goHelp = new Intent(MyinfoActivity.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 5: //환경설정
                            Intent goSetting = new Intent(MyinfoActivity.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 6: //로그아웃
                            LoginActivity.loginornot = 0;
                            removeAllPreferences();
                            Intent gologout = new Intent(MyinfoActivity.this, LoginActivity.class);
                            startActivity(gologout);
                            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    mDrawerLayout.closeDrawer(mDrawerPane);
                }
            }
        });

///**드로어관련2끝


    } //end of Create

    ///////////////////////////////Shared 메소드들 시작
    // 값 불러오기
    private void getPreferences(String Uid, String Uname, String Upassword, String Ubirth, String Usex, String Uphone, String Ufollower){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        MainActivity.id= pref.getString(Uid, "Please login");
        MainActivity.name= pref.getString(Uname, "Please login");
        MainActivity.password = pref.getString(Upassword, "Please login");
        MainActivity.birth = pref.getString(Ubirth, "Please login");
        MainActivity.sex = pref.getString(Usex, "Please login");
        MainActivity.phone = pref.getString(Uphone, "Please login");
        MainActivity.follower = pref.getString(Ufollower, "Please login");
    }

    // 값 저장하기
    private void savePreferences(String key, String value){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences(String key){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    ///////////////////////////////////////////////////////////Shared 메소드들 끝




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




    //**드로어와 액션바3
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String text = null;

        switch (item.getItemId()) {
            case android.R.id.home:
                break;

            case R.id.item1:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
                break;

            case R.id.item3:
                if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                else
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;

            default:
                return false;
        }
        return super.onOptionsItemSelected(item);

    }

    ///메뉴내용 가져오기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
////**드로어와액션바3끝


@Override
protected void onStart() {
    super.onStart();
}
    @Override
    protected void onResume() {
        super.onResume();
        getPreferences("id", "name", "password", "birth", "sex", "phone", "follower");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        savePreferences("id", MainActivity.id);
        savePreferences("name", MainActivity.name);
        savePreferences("password", MainActivity.password);
        savePreferences("birth", MainActivity.birth);
        savePreferences("sex", MainActivity.sex);
        savePreferences("phone", MainActivity.phone);
        savePreferences("follower", MainActivity.follower);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
