package com.superoid.test.seatdot;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

/**
 * Created by 전희정 on 2016-06-06.
 */
public class StoreActivity_New extends Activity{
    final int REQ_CODE_SELECT_IMAGE=100;

    EditText Storename;
    EditText Branchname;
    Spinner Storetype;
    Button Opentime;
    Button Closetime;
    int hour, minute;
    CheckBox Check24h;
    EditText LicenseNum;
    EditText Storephone;
    EditText Storeaddress;
    EditText StoreaddressDetails;
    EditText Contents;
    ImageView Photo1;
    ImageView Photo2;
    ImageView Photo3;
    ImageView Photo4;
    ImageView Photo5;
    Button StoreInsert;

    public static String storephoto1;
    public static String storephoto2;
    public static String storephoto3;
    public static String storephoto4;
    public static String storephoto5;

    private String urlPath;
    private FragmentManager supportFragmentManager;
    private final String UrlPath = "http://florajeon.vps.phps.kr/seatdotstoresinsert.php";



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
    LinearLayout mMainContent;
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
        setContentView(R.layout.activity_newstore);
        ///**드로어에 나오는 유저네임과 아이디 셋팅
        UserName = (TextView) findViewById(R.id.userName);
        UserId = (TextView) findViewById(R.id.userId);
        /////
        ///**드로어에 나오는 유저네임과 아이디
        UserName.setText(MainActivity.name);
        UserId.setText(MainActivity.id);
        ////////////

        Storename = (EditText)findViewById(R.id.new_storename);
        Branchname = (EditText)findViewById(R.id.new_branchname);
        Storetype = (Spinner)findViewById(R.id.storetype);
        ArrayAdapter STadapter = ArrayAdapter.createFromResource(this, R.array.storetype, android.R.layout.simple_spinner_dropdown_item);
        Storetype.setAdapter(STadapter);
        Opentime = (Button)findViewById(R.id.opentimebtn);
        Closetime = (Button)findViewById(R.id.closetimebtn);
        Check24h = (CheckBox)findViewById(R.id.check_24h);
        LicenseNum = (EditText)findViewById(R.id.new_license);
        Storephone = (EditText)findViewById(R.id.new_storephone);
        Storeaddress = (EditText)findViewById(R.id.new_address);
        StoreaddressDetails = (EditText)findViewById(R.id.new_address_detail);
        Contents = (EditText)findViewById(R.id.new_contents);


        StoreInsert = (Button)findViewById(R.id.store_insertbtn);
        StoreInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(v);
            }
        });
        Photo1 = (ImageView)findViewById(R.id.photo1);
        Photo1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    Intent photo1 = new Intent(Intent.ACTION_PICK);
                    photo1.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    photo1.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photo1, REQ_CODE_SELECT_IMAGE);
                    }
              });
        Photo2=(ImageView)findViewById(R.id.photo2);
        Photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo1 = new Intent(Intent.ACTION_PICK);
                photo1.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                photo1.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photo1, REQ_CODE_SELECT_IMAGE);
            }
        });
        Photo3=(ImageView)findViewById(R.id.photo3);
        Photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo1 = new Intent(Intent.ACTION_PICK);
                photo1.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                photo1.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photo1, REQ_CODE_SELECT_IMAGE);
            }
        });
        Photo4=(ImageView)findViewById(R.id.photo4);
        Photo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo1 = new Intent(Intent.ACTION_PICK);
                photo1.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                photo1.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photo1, REQ_CODE_SELECT_IMAGE);
            }
        });
        Photo5=(ImageView)findViewById(R.id.photo5);
        Photo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo1 = new Intent(Intent.ACTION_PICK);
                photo1.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                photo1.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photo1, REQ_CODE_SELECT_IMAGE);
            }
        });



            GregorianCalendar calendar = new GregorianCalendar();
            hour=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);

            findViewById(R.id.opentimebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
            new TimePickerDialog(StoreActivity_New.this, timeSetListener, hour, minute, false).show();
                    }
               }
            );

            findViewById(R.id.closetimebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
            new TimePickerDialog(StoreActivity_New.this, timeSetListeners, hour, minute, false).show();
                 }
            }

            );


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
                            Intent goHelp = new Intent(StoreActivity_New.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 3: //환경설정
                            Intent goSetting = new Intent(StoreActivity_New.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 4: //로그인
                            Intent gologin = new Intent(StoreActivity_New.this, LoginActivity.class);
                            startActivity(gologin);
                            break;
                    }
                }else { //로그인 되어있으면
                    switch (position) {
                        case 0: //내정보관리
                            Intent goMyinfo = new Intent(StoreActivity_New.this, MyinfoActivity.class);
                            startActivity(goMyinfo);
                            break;
                        case 1: //관심스토어
                            Intent goMylist = new Intent(StoreActivity_New.this, MylistActivity.class);
                            startActivity(goMylist);
                            break;
                        case 2: //스토어관리
                            Intent goManageStore = new Intent(StoreActivity_New.this, StoreActivity_Manage.class);
                            startActivity(goManageStore);
                            break;
                        case 3: //스토어등록
                            Intent goNewStore = new Intent(StoreActivity_New.this, StoreActivity_New.class);
                            startActivity(goNewStore);
                            break;
                        case 4: //고객센터
                            Intent goHelp = new Intent(StoreActivity_New.this, HelpActivity.class);
                            startActivity(goHelp);
                            break;
                        case 5: //환경설정
                            Intent goSetting = new Intent(StoreActivity_New.this, SettingActivity.class);
                            startActivity(goSetting);
                            break;
                        case 6: //로그아웃
                            LoginActivity.loginornot = 0;
                            removeAllPreferences();
                            Intent gologout = new Intent(StoreActivity_New.this, LoginActivity.class);
                            startActivity(gologout);
                            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    mDrawerLayout.closeDrawer(mDrawerPane);
                }
            }
        });

///**드로어관련2끝


        }/////end of Create


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

        private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            Opentime.setText(hourOfDay+"시 "+minute+"분");
        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListeners = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            Closetime.setText(hourOfDay+"시 "+minute+"분");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    ////////////////수정해야함! 알고리즘 문제있음
                    //배치해놓은 ImageView에 set
                    if(Photo1.getTag()==null) {
                        Photo1.setImageBitmap(image_bitmap);
                        Photo1.setTag("exist");
                    }else if(Photo1.getTag().toString().equals("exist")){
                        Photo2.setImageBitmap(image_bitmap);
                        Photo2.setTag("exist");
                    }else if(Photo2.getTag().toString().equals("exist")){
                        Photo3.setImageBitmap(image_bitmap);
                        Photo3.setTag("exist");
                    }else if(Photo3.getTag().toString().equals("exist")) {
                        Photo4.setImageBitmap(image_bitmap);
                        Photo4.setTag("exist");
                    }else if(Photo4.getTag().toString().equals("exist")){
                        Photo5.setImageBitmap(image_bitmap);
                        Photo5.setTag("exist");
                    }


                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
 /////   Uri 에서 파일명을 추출하는 로직
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        storephoto1 = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }


    ///php 통해 database에 insert하는 부분시작

    public void insert(View view) {
        String storeowner = MainActivity.id;
        String storename = Storename.getText().toString();
        String branchname = Branchname.getText().toString();
        String storetype = Storetype.getSelectedItem().toString();
        String opentime;
        String closetime;
        if(Check24h.isChecked()) {
            opentime = "0";
            closetime = "0";
        }else{
            opentime = Opentime.getText().toString();
            closetime = Closetime.getText().toString();
        }
        String storephone = Storephone.getText().toString();
        String storeaddress = Storeaddress.getText().toString();
        String storeaddressDetails = StoreaddressDetails.getText().toString();
        String contents = Contents.getText().toString();

        insertToDatabase(storeowner, storename, branchname, storetype, opentime, closetime, storephone, storeaddress, storeaddressDetails, contents, storephoto1, storephoto2, storephoto3, storephoto4, storephoto5);
    }


    private void insertToDatabase(String storeowner, String storename, String branchname, String storetype, String opentime, String closetime, String storephone, String storeaddress, String storeaddressDetails, String contents, String storephoto1, String storephoto2, String storephoto3, String storephoto4, String storephoto5) {

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(StoreActivity_New.this, "Please Wait", null, true, true);
        }

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method
                try {

                    String storeowner = (String)params[0];
                    String storename = (String)params[1];
                    String branchname = (String)params[2];
                    String storetype = (String)params[3];
                    String opentime = (String)params[4];
                    String closetime = (String)params[5];
                    String storephone = (String)params[6];
                    String storeaddress = (String)params[7];
                    String storeaddressDetails = (String)params[8];
                    String contents = (String)params[9];

                    String link="http://florajeon.vps.phps.kr/seatdotstoresinsert.php";
                    String data  = URLEncoder.encode("storeowner", "UTF-8") + "=" + URLEncoder.encode(storeowner, "UTF-8");
                    data += "&" + URLEncoder.encode("storename", "UTF-8") + "=" + URLEncoder.encode(storename, "UTF-8");
                    data += "&" + URLEncoder.encode("branchname", "UTF-8") + "=" + URLEncoder.encode(branchname, "UTF-8");
                    data += "&" + URLEncoder.encode("storetype", "UTF-8") + "=" + URLEncoder.encode(storetype, "UTF-8");
                    data += "&" + URLEncoder.encode("opentime", "UTF-8") + "=" + URLEncoder.encode(opentime, "UTF-8");
                    data += "&" + URLEncoder.encode("closetime", "UTF-8") + "=" + URLEncoder.encode(closetime, "UTF-8");
                    data += "&" + URLEncoder.encode("storephone", "UTF-8") + "=" + URLEncoder.encode(storephone, "UTF-8");
                    data += "&" + URLEncoder.encode("storeaddress", "UTF-8") + "=" + URLEncoder.encode(storeaddress, "UTF-8");
                    data += "&" + URLEncoder.encode("storeaddressDetails", "UTF-8") + "=" + URLEncoder.encode(storeaddressDetails, "UTF-8");
                    data += "&" + URLEncoder.encode("contents", "UTF-8") + "=" + URLEncoder.encode(contents, "UTF-8");

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
                    Toast.makeText(getApplicationContext(), "등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    Intent goManage = new Intent(StoreActivity_New.this, StoreActivity_Manage.class);
                    startActivity(goManage);
                }else {
                    Toast.makeText(getApplicationContext(), "등록에 실패하였습니다."+s, Toast.LENGTH_LONG).show();
                }
            }
        }

        InsertData task = new InsertData();
        task.execute(storeowner, storename, branchname, storetype, opentime, closetime, storephone, storeaddress, storeaddressDetails, contents,  storephoto1, storephoto2, storephoto3, storephoto4, storephoto5);
    }



    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    ///////////////끝

    @Override
    public void onStart() {
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
    public void onStop() {
        super.onStop();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





}
