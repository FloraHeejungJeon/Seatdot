package com.superoid.test.seatdot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by 전희정 on 2016-06-03.
 */
public class CustomTablePopup extends Activity {

    EditText Garo;
    EditText Sero;
    ImageView CustomTablePrv;
    ImageButton RotateBtn_re;
    ImageButton RotateBtn;
    Button CreateBtn;
    Button CancelBtn;
    static String garo;
    static String sero;
    int mDegree = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.customtablepopup);

        Garo = (EditText)findViewById(R.id.garo);
        Sero = (EditText)findViewById(R.id.sero);
        CustomTablePrv = (ImageView)findViewById(R.id.customtable_view);
        RotateBtn_re = (ImageButton)findViewById(R.id.customtable_rotate_re);
        RotateBtn = (ImageButton)findViewById(R.id.customtable_rotate);
        CreateBtn = (Button)findViewById(R.id.create_table);
        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCustom = getIntent();
                switch (String.valueOf(CustomTablePrv.getTag())) {
                    case "customt1x1":
                            goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                            goCustom.putExtra("data", (rotateImage(
                            BitmapFactory.decodeResource(getResources(),
                                    R.drawable.table_squre1x1), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;

                    case "customt1x2":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre2x1_90), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt1x3":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre3x1_90), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt1x4":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x1_90), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt2x1":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre2x1), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt2x2":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre2x2), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt2x3":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre3x2_90), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt2x4":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x2_90), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt3x1":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre3x1), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt3x2":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre3x2), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt3x3":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre3x3), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt3x4":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x3_90), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt4x1":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x1), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt4x2":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x2), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt4x3":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x3), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                    case "customt4x4":
                        goCustom.putExtra("data_tag", String.valueOf(CustomTablePrv.getTag()));
                        goCustom.putExtra("data", (rotateImage(
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.table_squre4x4), mDegree))) ;
                        setResult(RESULT_OK, goCustom);
                        finish();
                        break;
                }
            }
        });
        CancelBtn = (Button)findViewById(R.id.cancel_table);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        DrawThread draw = new DrawThread();
        draw.setDaemon(true);
        draw.start(); //
    }

    //가로세로 입력하면 바로 이미지 뜨도록
    class DrawThread extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(500);
                    messageHandler.sendMessage(Message.obtain(messageHandler, 0x10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // end while
        } // end run()
    } // end class DrawThread

    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            garo = Garo.getText().toString();
            sero = Sero.getText().toString();
            switch (msg.what) {
                case 0x10:
                    if(garo.equals("1")&&sero.equals("1")){
                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre1x1);
                        }
                            RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mDegree = mDegree - 15;
                                    CustomTablePrv.setImageBitmap(rotateImage(
                                            BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.table_squre1x1), mDegree));
                                }
                            });
                            RotateBtn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mDegree = mDegree + 15;
                                    CustomTablePrv.setImageBitmap(rotateImage(
                                            BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.table_squre1x1), mDegree));
                                }
                            });
                        CustomTablePrv.setTag("customt1x1" + mDegree);

                    }else if(garo.equals("1")&&sero.equals("2")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre2x1_90);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre2x1_90), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre2x1_90), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt1x2" + mDegree);
                    }else if(garo.equals("1")&&sero.equals("3")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre3x1_90);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x1_90), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x1_90), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt1x3" + mDegree);
                    }else if(garo.equals("1")&&sero.equals("4")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x1_90);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x1_90), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x1_90), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt1x4" + mDegree);
                    }else if(garo.equals("2")&&sero.equals("1")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre2x1);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre2x1), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre2x1), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt2x1" + mDegree);
                    }else if(garo.equals("2")&&sero.equals("2")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre2x2);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre2x2), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre2x2), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt2x2" + mDegree);
                    }else if(garo.equals("2")&&sero.equals("3")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre3x2_90);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x2_90), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x2_90), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt2x3" + mDegree);
                    }else if(garo.equals("2")&&sero.equals("4")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x2_90);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x2_90), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x2_90), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt2x4" + mDegree);
                    }else if(garo.equals("3")&&sero.equals("1")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre3x1);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x1), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x1), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt3x1" + mDegree);
                    }else if(garo.equals("3")&&sero.equals("2")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre3x2);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x2), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x2), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt3x2" + mDegree);
                    }else if(garo.equals("3")&&sero.equals("3")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre3x1);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x3), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre3x3), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt3x3" + mDegree);
                    }else if(garo.equals("3")&&sero.equals("4")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x3_90);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x3_90), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x3_90), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt3x4" + mDegree);
                    }else if(garo.equals("4")&&sero.equals("1")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x1);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x1), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x1), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt4x1" + mDegree);
                    }else if(garo.equals("4")&&sero.equals("2")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x2);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x2), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x2), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt4x2" + mDegree);
                    }else if(garo.equals("4")&&sero.equals("3")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x3);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x3), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x3), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt4x3" + mDegree);
                    }else if(garo.equals("4")&&sero.equals("4")){

                        if(mDegree==0) {
                            CustomTablePrv.setImageResource(R.drawable.table_squre4x4);
                        }
                        RotateBtn_re.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree - 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x4), mDegree));
                            }
                        });
                        RotateBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mDegree = mDegree + 15;
                                CustomTablePrv.setImageBitmap(rotateImage(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.table_squre4x4), mDegree));
                            }
                        });
                        CustomTablePrv.setTag("customt4x4"+mDegree);
                    }else {

                    }

                case 0x20:
                    break;
            }
        }
    };
    /////

    // 이미지 회전 함수
    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

}
