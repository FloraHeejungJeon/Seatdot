package com.superoid.test.seatdot;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.Inflater;

/**
 * Created by 전희정 on 2016-05-23.
 */
public class StoreActivity_Manage_SD extends Activity implements View.OnClickListener {

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

    public static int numTable = 0;
    public static int numChair = 0;
    public static int codeTnC = 0;
    private final int DYNAMIC_VIEW_ID = 0x8000;
    private RelativeLayout dynamicLayout;

    private int moveX = 0;
    private int moveY = 0;

    TextView WhatStore;
    TextView tablenum;
    TextView chairnum;
    Button SaveBtn;
    ImageButton CustomTable;

    final HashMap<Integer, String> map = new HashMap<Integer, String>();

    private ActionBar supportActionBar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_board);

        ///**드로어에 나오는 유저네임과 아이디 셋팅
        UserName = (TextView) findViewById(R.id.userName);
        UserId = (TextView) findViewById(R.id.userId);
        /////
        ///**드로어에 나오는 유저네임과 아이디
        UserName.setText(MainActivity.name);
        UserId.setText(MainActivity.id);
        ////////////

        findViewById(R.id.dynamicArea).setOnDragListener(
                new DragListener());
        findViewById(R.id.bottomlinear).setOnDragListener(
                new DragListener());

        Intent getIdx = getIntent();
        String st_idx = getIdx.getExtras().getString("st_idx");
        String stname = getIdx.getExtras().getString("stname");
        String stbranch = getIdx.getExtras().getString("stbranch");
        WhatStore = (TextView)findViewById(R.id.mb_storename);
        WhatStore.setText(stname+" "+stbranch+" Seatdot");

        tablenum = (TextView) findViewById(R.id.status_table);
        chairnum = (TextView) findViewById(R.id.status_chair);
        tablenum.setText(String.valueOf(numTable));
        chairnum.setText(String.valueOf(numChair));
        SaveBtn = (Button)findViewById(R.id.savebtn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CustomTable = (ImageButton)findViewById(R.id.customtablebtn);
        CustomTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCustom = new Intent(StoreActivity_Manage_SD.this, CustomTablePopup.class);
                startActivityForResult(goCustom, 1);
            }
        });
        //밑에 ActivityForResult관련 메소드 있음. create 끝나고.

        dynamicLayout = (RelativeLayout) findViewById(R.id.dynamicArea);

        //**table1x1부분 시작
        ImageView Table1x1 = (ImageView) findViewById(R.id.table1x1);
        Table1x1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView t1x1 = new ImageView(StoreActivity_Manage_SD.this);
                t1x1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                t1x1.setBackgroundResource(R.drawable.table_squre1x1);
                t1x1.setTag("t1x1");
                dynamicLayout.addView(t1x1);
                numTable++;
                codeTnC++;

                t1x1.setId(DYNAMIC_VIEW_ID + codeTnC);

                t1x1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_MOVE:

                        //        Toast.makeText(StoreActivity_Manage_SD.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();

                                // 태그 생성
                                ClipData.Item item = new ClipData.Item(
                                        (CharSequence) String.valueOf(v.getId()));

                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                ClipData data = new ClipData(String.valueOf(v.getId()),
                                        mimeTypes, item);
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                        t1x1);

                                t1x1.startDrag(data, // data to be dragged
                                        shadowBuilder, // drag shadow
                                        t1x1, // 드래그 드랍할  Vew
                                        0 // 필요없은 플래그
                                );
                                t1x1.setVisibility(View.INVISIBLE);
                                break;
                        }//end switch

                        return true;
                    }
                });
            }
        });
////table1x1부분 끝

        //**table2x1부분 시작
        ImageView Table2x1 = (ImageView) findViewById(R.id.table2x1);
        Table2x1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView t2x1 = new ImageView(StoreActivity_Manage_SD.this);
                t2x1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                t2x1.setBackgroundResource(R.drawable.table_squre2x1);
                t2x1.setTag("t2x1");
                dynamicLayout.addView(t2x1);
                numTable++;
                codeTnC++;

                t2x1.setId(DYNAMIC_VIEW_ID + codeTnC);

                t2x1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_MOVE:

                           //     Toast.makeText(StoreActivity_Manage_SD.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();

                                // 태그 생성
                                ClipData.Item item = new ClipData.Item(
                                        (CharSequence) String.valueOf(v.getId()));

                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                ClipData data = new ClipData(String.valueOf(v.getId()),
                                        mimeTypes, item);
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                        t2x1);

                                t2x1.startDrag(data, // data to be dragged
                                        shadowBuilder, // drag shadow
                                        t2x1, // 드래그 드랍할  Vew
                                        0 // 필요없은 플래그
                                );
                                t2x1.setVisibility(View.INVISIBLE);
                                break;
                        }//end switch

                        return true;

                    }
                });
            }
        });
////table2x1부분 끝

        //**table2x2부분 시작
        ImageView Table2x2 = (ImageView) findViewById(R.id.table2x2);
        Table2x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView t2x2 = new ImageView(StoreActivity_Manage_SD.this);
                t2x2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                t2x2.setBackgroundResource(R.drawable.table_squre2x2);
                t2x2.setTag("t2x2");
                dynamicLayout.addView(t2x2);
                numTable++;
                codeTnC++;

                t2x2.setId(DYNAMIC_VIEW_ID + codeTnC);

                t2x2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_MOVE:

                                //     Toast.makeText(StoreActivity_Manage_SD.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();

                                // 태그 생성
                                ClipData.Item item = new ClipData.Item(
                                        (CharSequence) String.valueOf(v.getId()));

                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                ClipData data = new ClipData(String.valueOf(v.getId()),
                                        mimeTypes, item);
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                        t2x2);

                                t2x2.startDrag(data, // data to be dragged
                                        shadowBuilder, // drag shadow
                                        t2x2, // 드래그 드랍할  Vew
                                        0 // 필요없은 플래그
                                );
                                t2x2.setVisibility(View.INVISIBLE);
                                break;
                        }//end switch

                        return true;

                    }
                });
            }
        });
////table2x2부분 끝

        //**table3x1부분 시작
        ImageView Table3x1 = (ImageView) findViewById(R.id.table3x1);
        Table3x1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView t3x1 = new ImageView(StoreActivity_Manage_SD.this);
                t3x1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                t3x1.setBackgroundResource(R.drawable.table_squre3x1);
                t3x1.setTag("t3x1");
                dynamicLayout.addView(t3x1);
                numTable++;
                codeTnC++;

                t3x1.setId(DYNAMIC_VIEW_ID + codeTnC);

                t3x1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_MOVE:

                                //     Toast.makeText(StoreActivity_Manage_SD.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();

                                // 태그 생성
                                ClipData.Item item = new ClipData.Item(
                                        (CharSequence) String.valueOf(v.getId()));

                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                ClipData data = new ClipData(String.valueOf(v.getId()),
                                        mimeTypes, item);
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                        t3x1);

                                t3x1.startDrag(data, // data to be dragged
                                        shadowBuilder, // drag shadow
                                        t3x1, // 드래그 드랍할  Vew
                                        0 // 필요없은 플래그
                                );
                                t3x1.setVisibility(View.INVISIBLE);
                                break;
                        }//end switch

                        return true;

                    }
                });
            }
        });
////table2x2부분 끝

        //**chair1부분 시작
        ImageView Chair1 = (ImageView) findViewById(R.id.chair1);
        Chair1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView c1 = new ImageView(StoreActivity_Manage_SD.this);
                c1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                c1.setBackgroundResource(R.drawable.chair_empty);
                c1.setTag("c1");
                dynamicLayout.addView(c1);
                numChair++;
                codeTnC++;

                c1.setId(DYNAMIC_VIEW_ID + codeTnC);

                c1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_MOVE:

                                //     Toast.makeText(StoreActivity_Manage_SD.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();

                                // 태그 생성
                                ClipData.Item item = new ClipData.Item(
                                        (CharSequence) String.valueOf(v.getId()));

                                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                                ClipData data = new ClipData(String.valueOf(v.getId()),
                                        mimeTypes, item);
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                        c1);

                                c1.startDrag(data, // data to be dragged
                                        shadowBuilder, // drag shadow
                                        c1, // 드래그 드랍할  Vew
                                        0 // 필요없은 플래그
                                );
                                c1.setVisibility(View.INVISIBLE);
                                break;
                        }//end switch

                        return true;

                    }
                });
            }
        });
////chair1부분 끝

        //테이블, 의자 수 0.1초 단위로 업데이트
        ForthThread forth = new ForthThread();
        forth.setDaemon(true);
        forth.start(); //


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



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
                            Intent goHelp = new Intent(StoreActivity_Manage_SD.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 3: //환경설정
                            Intent goSetting = new Intent(StoreActivity_Manage_SD.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 4: //로그인
                            Intent gologin = new Intent(StoreActivity_Manage_SD.this, LoginActivity.class);
                            startActivity(gologin);
                            break;
                    }
                }else { //로그인 되어있으면
                    switch (position) {
                        case 0: //내정보관리
                            Intent goMyinfo = new Intent(StoreActivity_Manage_SD.this, MyinfoActivity.class);
                            startActivity(goMyinfo);
                            break;
                        case 1: //관심스토어
                            Intent goMylist = new Intent(StoreActivity_Manage_SD.this, MylistActivity.class);
                            startActivity(goMylist);
                            break;
                        case 2: //스토어관리
                            Intent goManageStore = new Intent(StoreActivity_Manage_SD.this, StoreActivity_Manage.class);
                            startActivity(goManageStore);
                            break;
                        case 3: //스토어등록
                            Intent goNewStore = new Intent(StoreActivity_Manage_SD.this, StoreActivity_New.class);
                            startActivity(goNewStore);
                            break;
                        case 4: //고객센터
                            Intent goHelp = new Intent(StoreActivity_Manage_SD.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 5: //환경설정
                            Intent goSetting = new Intent(StoreActivity_Manage_SD.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 6: //로그아웃
                            LoginActivity.loginornot = 0;
                            removeAllPreferences();
                            Intent gologout = new Intent(StoreActivity_Manage_SD.this, LoginActivity.class);
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





///Custom table 비트맵 이미지 받아와서 뷰 추가하는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
// 액티비티가 정상적으로 종료되었을 경우
        {
            if (requestCode == 1 && !data.equals(null))
// InformationInput에서 호출한 경우에만 처리합니다.
            {
// 받아온 데이터를 InformationInput 액티비티에 표시합니다.
                try {
                    Bitmap CustomBitmap = (Bitmap) data.getExtras().get("data");
                    String Tag = data.getStringExtra("data_tag");
                    final ImageView custom = new ImageView(StoreActivity_Manage_SD.this);
                    custom.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    custom.setImageBitmap(CustomBitmap);
                    custom.setScaleType(ImageView.ScaleType.FIT_XY);
                    custom.setTag(Tag);
                    dynamicLayout.addView(custom);
                    numTable++;
                    codeTnC++;

                    custom.setId(DYNAMIC_VIEW_ID + codeTnC);

                    custom.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_MOVE:

                                    // 태그 생성
                                    ClipData.Item item = new ClipData.Item(
                                            (CharSequence) String.valueOf(v.getId()));

                                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                                    ClipData data = new ClipData(String.valueOf(v.getId()),
                                            mimeTypes, item);
                                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                            custom);

                                    custom.startDrag(data, // data to be dragged
                                            shadowBuilder, // drag shadow
                                            custom, // 드래그 드랍할  Vew
                                            0 // 필요없은 플래그
                                    );
                                    custom.setVisibility(View.INVISIBLE);
                                    break;
                            }//end switch

                            return true;

                        }
                    });
                } catch (Exception e) {
                    return;
                }

            }
        }
    }




    private class DragListener implements View.OnDragListener {
        Drawable normalShape = getResources().getDrawable(
                R.drawable.normal_shape);
        Drawable targetShape = getResources().getDrawable(
                R.drawable.target_shape);

        public boolean onDrag(View v, DragEvent event) {

            // 이벤트 시작
            switch (event.getAction()) {

                // 이미지를 드래그 시작될때
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    break;

                // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");
                    // 이미지가 들어왔다는 것을 알려주기 위해 배경이미지 변경
                    if (v == findViewById(R.id.bottomlinear)) {
                        v.setBackground(targetShape);
                    }
                    break;

                // 드래그한 이미지가 영역을 빠져 나갈때
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");
                    if (v == findViewById(R.id.bottomlinear)) {
                        v.setBackground(normalShape);
                    }
                    break;

                // 이미지를 드래그해서 드랍시켰을때
                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DROP");

                    if (v == findViewById(R.id.bottomlinear)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view
                                .getParent();
                        viewgroup.removeView(view);

                        //Layout에서 항목 삭제
                        dynamicLayout.removeView(v);
                        //Hashmap에서 항목 삭제
                        map.remove(String.valueOf(v.getId()));
                    //    Toast.makeText(StoreActivity_Manage_SD.this, String.valueOf(view.getTag()), Toast.LENGTH_SHORT).show();
                        if(String.valueOf(view.getTag()).equals("t1x1")) { //table1x1
                            numTable--;
                        }else if(String.valueOf(view.getTag()).equals("t2x1")) { //table 2x1
                            numTable--;
                        }else if(String.valueOf(view.getTag()).equals("t2x2")) { //table 2x2
                            numTable--;
                        }else if(String.valueOf(view.getTag()).equals("t3x1")) { //table 2x2
                            numTable--;
                        }else if(String.valueOf(view.getTag()).substring(0, 7).equals("customt")) {
                            numTable--;
                        }else if(String.valueOf(view.getTag()).equals("c1")) { //chair1
                            numChair--;
                        }

                        RelativeLayout containView = (RelativeLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.INVISIBLE);

                    }else if (v == findViewById(R.id.dynamicArea)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view
                                .getParent();
                        viewgroup.removeView(view);

                        RelativeLayout containView = (RelativeLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);

                  //     Toast.makeText(StoreActivity_Manage_SD.this, view.getTag().toString(), Toast.LENGTH_SHORT).show();
                        //table2x1의 tag번호 (위치조정 위해 조건문)
                        if(view.getTag().toString().equals("t1x1")) { //table1x1의 tag
                            moveX = (-50) + (int) event.getX();
                            moveY = (-50) + (int) event.getY();
                        }else if(view.getTag().toString().equals("t2x1")) { //table2x1의 tag
                            moveX = (-100) + (int) event.getX();
                            moveY = (-50) + (int) event.getY();
                        }else if(view.getTag().toString().equals("t2x2")) { //table2x2의 tag
                            moveX = (-100) + (int) event.getX();
                            moveY = (-100) + (int) event.getY();
                        }else if(view.getTag().toString().equals("t3x1")) { //table2x2의 tag
                            moveX = (-150) + (int) event.getX();
                            moveY = (-150) + (int) event.getY();
                        }else if(view.getTag().toString().equals("c1")) { //chair1의 tag
                            moveX = (-35) + (int) event.getX();
                            moveY = (-35) + (int) event.getY();
                        }else if(view.getTag().toString().substring(0, 10).equals("customt1x1")) {
                            moveX = (-70) + (int) event.getX();
                            moveY = (-70) + (int) event.getY();
                        }else if(view.getTag().toString().substring(0, 10).equals("customt1x2")) {
                            moveX = (-150) + (int) event.getX();
                            moveY = (-150) + (int) event.getY();
                        }else if(view.getTag().toString().substring(0, 10).equals("customt2x1")) {
                            moveX = (-115) + (int) event.getX();
                            moveY = (-100) + (int) event.getY();
                        }else if(view.getTag().toString().substring(0, 10).equals("customt2x2")) {
                            moveX = (-140) + (int) event.getX();
                            moveY = (-140) + (int) event.getY();
                        }else {
                            moveX = (-50) + (int) event.getX();
                            moveY = (-50) + (int) event.getY();
                        }

                        //Hashmap에 추가
                            if(view.getTag().toString().equals("t1x1")) {
                                map.put(view.getId(), "table1x1"+"&&&&"+String.valueOf(moveX) + "&&" + String.valueOf(moveY));
                            }else if(view.getTag().toString().equals("t2x1")) {
                                map.put(view.getId(), "table2x1"+"&&&&"+String.valueOf(moveX) + "&&" + String.valueOf(moveY));
                            }else if(view.getTag().toString().equals("t2x2")) {
                                map.put(view.getId(), "table2x2"+"&&&&"+String.valueOf(moveX) + "&&" + String.valueOf(moveY));
                            }else if(view.getTag().toString().equals("c1")) {
                                map.put(view.getId(), "chair1"+"&&&&"+String.valueOf(moveX) + "&&" + String.valueOf(moveY));
                            }else if(view.getTag().toString().substring(0, 7).equals("customt")) {
                                map.put(view.getId(), view.getTag().toString().substring(0, 10)+"&&"+view.getTag().toString().substring(10)+"&&"+String.valueOf(moveX) + "&&" + String.valueOf(moveY));
                            } //custom table 종류와, 각도를 &&로 구분하여 추가.

                        TextView Imageid = (TextView)findViewById(R.id.imageid);
                        Iterator<Integer> iter = map.keySet().iterator();
                        while(iter.hasNext()) {
                            int key = iter.next();
                            String value = map.get(key);

                          Imageid.setText(key + "와" + value);

                        }

                        RelativeLayout.LayoutParams lay = null;
                        lay = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        lay.leftMargin = moveX;
                        lay.topMargin = moveY;
                        view.setLayoutParams(lay);

                    }else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context,
                                "이미지를 다른 지역에 드랍할수 없습니다.",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");
                    if (v == findViewById(R.id.bottomlinear)) {
                        v.setBackground(normalShape); // go back to normal shape
                    }
                default:
                    break;
            }
            return true;
        }
    }

    //테이블이랑 의자수 바로바로 올라가도록
    class ForthThread extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(100);
                    messageHandler.sendMessage(Message.obtain(messageHandler, 0x10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // end while
        } // end run()
    } // end class ForthThread

    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 0x10:
                    tablenum.setText(String.valueOf(numTable));
                    chairnum.setText(String.valueOf(numChair));

                case 0x20:

                    break;
            }
        }
    };
    //


    public ActionBar getSupportActionBar() {
        return supportActionBar;
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Manager Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.superoid.test.seatdot/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
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
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Manager Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.superoid.test.seatdot/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}
