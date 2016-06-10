package com.superoid.test.seatdot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class StoreActivity_View extends Activity {

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


    public String st_idx;
    public String[] data;
    public static String storeowner;
    public static String storename;
    public static String branchname;
    public static String storetype;
    public static String opentime;
    public static String closetime;
    public static String licensenumber;
    public static String storephone;
    public static String storeaddress;
    public static String storeaddressDetails;
    public static String contents;
    public static String storephoto1;
    public static String storephoto2;
    public static String storephoto3;
    public static String storephoto4;
    public static String storephoto5;

    TextView Storeview_name;
    TextView Storeview_time;
    ImageButton Storeview_phone;
    ImageButton Storeview_map;
    TextView Storeview_txt;
    ImageView Storeview_seatdot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeview);

        ///**드로어에 나오는 유저네임과 아이디
        UserName = (TextView) findViewById(R.id.userName);
        UserId = (TextView) findViewById(R.id.userId);
            UserName.setText(MainActivity.name);
            UserId.setText(MainActivity.id);
        ////////////

        Intent getidx = getIntent();
        st_idx = getidx.getExtras().getString("st_idx");

        insertToDatabase(st_idx);
        Toast.makeText(StoreActivity_View.this, st_idx, Toast.LENGTH_SHORT).show();



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
                            Intent goHelp = new Intent(StoreActivity_View.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 3: //환경설정
                            Intent goSetting = new Intent(StoreActivity_View.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 4: //로그인
                            Intent gologin = new Intent(StoreActivity_View.this, LoginActivity.class);
                            startActivity(gologin);
                            break;
                    }
                }else { //로그인 되어있으면
                    switch (position) {
                        case 0: //내정보관리
                            break;
                        case 1: //관심스토어
                            Intent goMylist = new Intent(StoreActivity_View.this, MylistActivity.class);
                            startActivity(goMylist);
                            break;
                        case 2: //스토어관리
                            Intent goManageStore = new Intent(StoreActivity_View.this, StoreActivity_Manage.class);
                            startActivity(goManageStore);
                            break;
                        case 3: //스토어등록
                            Intent goNewStore = new Intent(StoreActivity_View.this, StoreActivity_New.class);
                            startActivity(goNewStore);
                            break;
                        case 4: //고객센터
                            Intent goHelp = new Intent(StoreActivity_View.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 5: //환경설정
                            Intent goSetting = new Intent(StoreActivity_View.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 6: //로그아웃
                            LoginActivity.loginornot = 0;
                            removeAllPreferences();
                            Intent gologout = new Intent(StoreActivity_View.this, LoginActivity.class);
                            startActivity(gologout);
                            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    mDrawerLayout.closeDrawer(mDrawerPane);
                }
            }
        });

///**드로어관련2끝


    }///end of Create


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



    ///////////////////아직 이 액티비티에서 바로 서버통신하는게 아니라, Search에서 인텐트로 데이터를 보냄.
    /////////맵을 실시간으로 새로고침하려면 이 페이지에서 해야할 것 같은데, 왜 여기서 서버통신한 stirng 값들이 안뜨지???
    private void insertToDatabase(String st_idx){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(StoreActivity_View.this, "Please Wait", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String st_idx= (String)params[0];

                    String link="http://florajeon.vps.phps.kr/seatdotstoresview.php";
                    String data  = URLEncoder.encode("st_idx", "UTF-8") + "=" + URLEncoder.encode(st_idx, "UTF-8");


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

                if (s.equalsIgnoreCase("empty")) {
                    Toast.makeText(getApplicationContext(), "해당하는 데이터가 없습니다.", Toast.LENGTH_LONG).show();
                } else {

                    String str = s;
                    data = str.split(":@/#@:");
                        storeowner = data[1];
                        storename = data[2];
                        branchname = data[3];
                        storetype = data[4];
                        opentime = data[5];
                        closetime = data[6];
                        licensenumber = data[7];
                        storephone = data[8];
                        storeaddress = data[9];
                        storeaddressDetails = data[10];
                        contents = data[11];
                        storephoto1 = data[12];
                   //     storephoto2 = data[13];
                   //     storephoto3 = data[14];
                   //     storephoto4 = data[15];
                   //     storephoto5 = data[16];
                   //     Toast.makeText(getApplicationContext(), storename+opentime+contents, Toast.LENGTH_LONG).show();

                    Storeview_name = (TextView)findViewById(R.id.storeview_name);
                    Storeview_name.setText(storename+" "+branchname);
                    Storeview_time = (TextView)findViewById(R.id.storeview_time);
                    if(opentime.equals("0")){
                        Storeview_time.setText("24hours");
                    }else {
                        Storeview_time.setText(opentime + "부터 " + closetime + "까지");
                    }
                    Storeview_phone = (ImageButton)findViewById(R.id.storeview_phone);
                    Storeview_phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + storephone));
                            startActivity(call);
                        }
                    });
                    Storeview_map = (ImageButton)findViewById(R.id.storeview_map);
                    Storeview_txt = (TextView)findViewById(R.id.storeview_txt);
                    Storeview_txt.setText(contents);
                    Storeview_seatdot = (ImageView)findViewById(R.id.storeview_seatdot);


                }
            }
        }

        InsertData task = new InsertData();
        task.execute(st_idx);
    }

}
