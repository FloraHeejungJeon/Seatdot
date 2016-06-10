package com.superoid.test.seatdot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by 전희정 on 2016-06-08.
 */
public class SearchActivity extends Activity {

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
    LinearLayout mMainContent;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    TextView UserName;
    TextView UserId;

    private Inflater menuInflater;
    ///**드로어관련1끝


    EditText SearchAddress;
    EditText SearchName;
    Button Search;

    String[] parentdata;
    String[] data;
    String st_idx;
    String storeowner;
    String storename;
    String branchname;
    String storetype;
    String licensenumber;
    String opentime;
    String closetime;
    String storephone;
    String storeaddress;
    String storeaddressDetails;
    String contents;
    String storephoto1;
    String storephoto2;
    String storephoto3;
    String storephoto4;
    String storephoto5;

    ListView list;
    Search_ListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchName = (EditText) findViewById(R.id.search_name);
        SearchAddress = (EditText) findViewById(R.id.search_address);

        ///**드로어에 나오는 유저네임과 아이디
        UserName = (TextView) findViewById(R.id.userName);
        UserId = (TextView) findViewById(R.id.userId);
        UserName.setText(MainActivity.name);
        UserId.setText(MainActivity.id);
        ////////////

        // 리스트뷰 참조 및 Adapter달기
        list = (ListView) findViewById(R.id.search_result);

        // ListView의 이벤트 설정
        list.setOnItemClickListener(new ListViewItemClickListener());
        ///롱클릭      listview.setOnItemLongClickListener( new ListViewItemLongClickListener() );


        Search = (Button) findViewById(R.id.searchbtn);
        Search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(SearchName.getText().toString().equals("")&&SearchAddress.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "스토어이름 또는 주소를 입력하세요.", Toast.LENGTH_LONG).show();
                }else {
                    insert(view);
                }
            }
        });


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
        mMainContent = (LinearLayout) findViewById(R.id.mainContent);
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
                            Intent goHelp = new Intent(SearchActivity.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 3: //환경설정
                            Intent goSetting = new Intent(SearchActivity.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 4: //로그인
                            Intent gologin = new Intent(SearchActivity.this, LoginActivity.class);
                            startActivity(gologin);
                            break;
                    }
                }else { //로그인 되어있으면
                    switch (position) {
                        case 0: //내정보관리
                            break;
                        case 1: //관심스토어
                            Intent goMylist = new Intent(SearchActivity.this, MylistActivity.class);
                            startActivity(goMylist);
                            break;
                        case 2: //스토어관리
                            Intent goManageStore = new Intent(SearchActivity.this, StoreActivity_Manage.class);
                            startActivity(goManageStore);
                            break;
                        case 3: //스토어등록
                            Intent goNewStore = new Intent(SearchActivity.this, StoreActivity_New.class);
                            startActivity(goNewStore);
                            break;
                        case 4: //고객센터
                            Intent goHelp = new Intent(SearchActivity.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 5: //환경설정
                            Intent goSetting = new Intent(SearchActivity.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 6: //로그아웃
                            LoginActivity.loginornot = 0;
                            removeAllPreferences();
                            Intent gologout = new Intent(SearchActivity.this, LoginActivity.class);
                            startActivity(gologout);
                            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    mDrawerLayout.closeDrawer(mDrawerPane);
                }
            }
        });

///**드로어관련2끝

    }//end of Create


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



    ///ListView 아이템 클릭했을 때 이벤트
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String[] dts = parentdata[position].split(":@/#@:");
            String stidx=dts[0];
            Intent details = new Intent(SearchActivity.this, StoreActivity_View.class);
            details.putExtra("st_idx", stidx);
            startActivity(details);
            //각 item들의 row값은 String.valueOf(parent.getAdapter().getItemId(position))

            //         Toast.makeText(SearchActivity.this, String.valueOf(parent.getAdapter().getItemId(position)), Toast.LENGTH_SHORT).show();

        }
    }
////////////////////////////////

    public void insert(View view){
        String ser_name = SearchName.getText().toString();
        String ser_address = SearchAddress.getText().toString();

        insertToDatabase(ser_name,ser_address);


    }

    private void insertToDatabase(String ser_name, String ser_address){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String storename = (String)params[0];
                    String storeaddress = (String)params[1];


                    String link="http://florajeon.vps.phps.kr/seatdotsearchstores.php";
                    String data  = URLEncoder.encode("storename", "UTF-8") + "=" + URLEncoder.encode(storename, "UTF-8");
                    data += "&" + URLEncoder.encode("storeaddress", "UTF-8") + "=" + URLEncoder.encode(storeaddress, "UTF-8");


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
                    adapter.notifyDataSetChanged();
                } else {

                    // Adapter 생성
                    adapter = new Search_ListViewAdapter();
                    list.setAdapter(adapter);

                    String str = s;
                    parentdata = str.split("/@@##@@/");
                    for (int i = 0; i < parentdata.length; i++) {
                        data = parentdata[i].split(":@/#@:");

                        st_idx = data[0];
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
                        adapter.addItem(ContextCompat.getDrawable(SearchActivity.this, R.drawable.main01), storename + " " + branchname, storeaddress, contents, st_idx);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

        InsertData task = new InsertData();
        task.execute(ser_name, ser_address);
    }
//////////////////////////

    ////ListView 어댑터를 이너클래스로 옮겼음
    public class Search_ListViewAdapter extends BaseAdapter {
        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<Search_ListViewItem> listViewItemList = new ArrayList<Search_ListViewItem>();

        // ListViewAdapter의 생성자
        public Search_ListViewAdapter() {

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
                convertView = inflater.inflate(R.layout.search_result_list_item, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.storeimage);
            TextView NameTextView = (TextView) convertView.findViewById(R.id.storenbranch);
            TextView AddressTextView = (TextView) convertView.findViewById(R.id.storeaddress);
            TextView ConTentsView = (TextView) convertView.findViewById(R.id.contents);
            TextView stidx = (TextView) convertView.findViewById(R.id.stidx);
            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            Search_ListViewItem listViewItem = listViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            iconImageView.setImageDrawable(listViewItem.getIcon());
            NameTextView.setText(listViewItem.getName());
            AddressTextView.setText(listViewItem.getAddress());
            ConTentsView.setText(listViewItem.getCon());
            convertView.setTag(stidx.getText().toString());

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

        public String getItemIdx(View convertView) {
            return convertView.getTag().toString();
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem(Drawable icon, String storename, String storeaddress, String contents, String st_idx) {
            Search_ListViewItem item = new Search_ListViewItem();

            item.setIcon(icon);
            item.setName(storename);
            item.setAddress(storeaddress);
            item.setCon(contents);
            item.setIdx(st_idx);

            listViewItemList.add(item);
        }
    }
    ///////////////////////////


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
