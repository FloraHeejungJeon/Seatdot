package com.superoid.test.seatdot;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends Activity {

    public static String name;
    public static String id;
    public static String password;
    public static String birth;
    public static String sex;
    public static String phone;
    public static String follower;

    public static int loginornot;

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
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///**드로어에 나오는 유저네임과 아이디 셋팅
        UserName = (TextView) findViewById(R.id.userName);
        UserId = (TextView) findViewById(R.id.userId);
        /////

        if(LoginActivity.loginornot==1) {
            Intent logininfo = getIntent();
            id = logininfo.getExtras().getString("id");
            name = logininfo.getExtras().getString("name");
            password = logininfo.getExtras().getString("pass");
            birth = logininfo.getExtras().getString("birth");
            sex = logininfo.getExtras().getString("sex");
            phone = logininfo.getExtras().getString("phone");
            follower = logininfo.getExtras().getString("follower");
            LoginActivity.loginornot=0;
        }

        savePreferences("id", id);
        savePreferences("name", name);
        savePreferences("password", password);
        savePreferences("birth", birth);
        savePreferences("sex", sex);
        savePreferences("phone", phone);
        savePreferences("follower", follower);

        getPreferences("id","name","password","birth","sex","phone","follower");

            ///**드로어에 나오는 유저네임과 아이디
            UserName.setText(name);
            UserId.setText(id);
            ////////////


        ListView listview;
        Main_ListViewAdapter adapter;

        // Adapter 생성
        adapter = new Main_ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.main_listview);
        listview.setAdapter(adapter);

        // ListView의 이벤트 설정
        listview.setOnItemClickListener(new ListViewItemClickListener());
        ///롱클릭      listview.setOnItemLongClickListener( new ListViewItemLongClickListener() );

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.main01),
                "갤러리가 있는 카페");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.main02),
                "청춘에너지가 느껴지는 곳!");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.main03),
                "커피와 플리마켓이 만나면?");
/////

//**드로어관련2
        if(id.equals("Please login")) {
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
                if(id.equals("Please login")) { //로그인안되어있으면
                    switch (position) {
                        case 0: //관심스토어
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case 1: //스토어등록
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case 2: //고객센터
                            Intent goHelp = new Intent(MainActivity.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 3: //환경설정
                            Intent goSetting = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 4: //로그인
                            Intent gologin = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(gologin);
                            break;
                    }
                }else { //로그인 되어있으면
                    switch (position) {
                        case 0: //내정보관리
                            Intent goMyinfo = new Intent(MainActivity.this, MyinfoActivity.class);
                            startActivity(goMyinfo);
                            break;
                        case 1: //관심스토어
                            Intent goMylist = new Intent(MainActivity.this, MylistActivity.class);
                            startActivity(goMylist);
                            break;
                        case 2: //스토어관리
                            Intent goManageStore = new Intent(MainActivity.this, StoreActivity_Manage.class);
                            startActivity(goManageStore);
                            break;
                        case 3: //스토어등록
                            Intent goNewStore = new Intent(MainActivity.this, StoreActivity_New.class);
                            startActivity(goNewStore);
                            break;
                        case 4: //고객센터
                            Intent goHelp = new Intent(MainActivity.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 5: //환경설정
                            Intent goSetting = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 6: //로그아웃
                            LoginActivity.loginornot = 0;
                            removeAllPreferences();
                            Intent gologout = new Intent(MainActivity.this, LoginActivity.class);
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
        id= pref.getString(Uid, "Please login");
        name= pref.getString(Uname, "Please login");
        password = pref.getString(Upassword, "Please login");
        birth = pref.getString(Ubirth, "Please login");
        sex = pref.getString(Usex, "Please login");
        phone = pref.getString(Uphone, "Please login");
        follower = pref.getString(Ufollower, "Please login");
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


    ///ListView 아이템 클릭했을 때 이벤트
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent details = new Intent(MainActivity.this, StoreActivity_View.class);
            startActivity(details);
            //각 item들의 row값은 String.valueOf(parent.getAdapter().getItemId(position))

            Toast.makeText(MainActivity.this, String.valueOf(parent.getAdapter().getItemId(position)), Toast.LENGTH_SHORT).show();

        }
    }

//**드로어와 액션바3
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String text = null;

        switch (item.getItemId()) {
            case android.R.id.home:
                break;

            case R.id.item1: //search btn
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

////ListView 어댑터를 이너클래스로 옮겼음
    public class Main_ListViewAdapter extends BaseAdapter {
        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<Main_ListViewItem> listViewItemList = new ArrayList<Main_ListViewItem>();

        // ListViewAdapter의 생성자
        public Main_ListViewAdapter() {

        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return listViewItemList.size();
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.main_listview_item, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.main_imgview);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.main_txtview);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            Main_ListViewItem listViewItem = listViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            iconImageView.setImageDrawable(listViewItem.getIcon());
            titleTextView.setText(listViewItem.getTitle());

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return listViewItemList.get(position);
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem(Drawable icon, String title) {
            Main_ListViewItem item = new Main_ListViewItem();

            item.setIcon(icon);
            item.setTitle(title);

            listViewItemList.add(item);
        }
    }

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
        savePreferences("id", id);
        savePreferences("name", name);
        savePreferences("password", password);
        savePreferences("birth", birth);
        savePreferences("sex", sex);
        savePreferences("phone", phone);
        savePreferences("follower", follower);

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
