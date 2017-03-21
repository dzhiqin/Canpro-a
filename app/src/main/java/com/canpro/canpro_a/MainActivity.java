package com.canpro.canpro_a;

import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView tabFunction;
    private ImageView tabSetting;
    private RelativeLayout bottomFunction;
    private RelativeLayout bottomSetting;
    private ViewPager mViewPager;
    private Button btnBack;
    private int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        //隐藏底部软键盘
        Window mWindow=getWindow();
        WindowManager.LayoutParams params=mWindow.getAttributes();
        params.systemUiVisibility= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        mWindow.setAttributes(params);

        //要分页显示的View
        View viewFunction= LayoutInflater.from(this).inflate(R.layout.layout_function,null);
        View viewSetting=LayoutInflater.from(this).inflate(R.layout.layout_setting,null);
        final ArrayList<View> views=new ArrayList<View>();
        views.add(viewFunction);
        views.add(viewSetting);
        //填充viewpager的适配器
        PagerAdapter mPagerAdapter=new PagerAdapter(){
            @Override
            public int getCount(){
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_back:
                if(currentIndex==0){
                    new AlertDialog.Builder(MainActivity.this).setMessage("确认退出吗？")
                            .setTitle("提示")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    MainActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();
                }else{
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
        @Override
        public void onPageSelected(int arg0){

            switch (arg0){
                case 0:
                    bottomFunction.setBackground(getResources().getDrawable(R.mipmap.maintabbg_sel));
                    tabFunction.setBackground(getResources().getDrawable(R.mipmap.btcontrl_icon_sel));
                    bottomSetting.setBackground(getResources().getDrawable(R.mipmap.maintabbg));
                    tabSetting.setBackground(getResources().getDrawable(R.mipmap.btset_icon));
                    break;
                case 1:
                    bottomFunction.setBackground(getResources().getDrawable(R.mipmap.maintabbg));
                    tabFunction.setBackground(getResources().getDrawable(R.mipmap.btcontrl_icon));
                    bottomSetting.setBackground(getResources().getDrawable(R.mipmap.maintabbg_sel));
                    tabSetting.setBackground(getResources().getDrawable(R.mipmap.btset_icon));
                    break;
            }
        }
    }

    /**
     * 页卡头点击监听
     */
    public class MyOnClickListener implements View.OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
            this.index=i;
        }

        @Override
        public void onClick(View view) {
            mViewPager.setCurrentItem(index);
        }
    }
    private void initial(){
        currentIndex=0;
        tabFunction=(ImageView)findViewById(R.id.img_function);
        tabSetting=(ImageView)findViewById(R.id.img_setting);
        bottomFunction=(RelativeLayout)findViewById(R.id.bottom_function_layout);
        bottomSetting=(RelativeLayout)findViewById(R.id.bottom_setting_layout);
        tabFunction.setOnClickListener(new MyOnClickListener(0));
        tabSetting.setOnClickListener(new MyOnClickListener(1));
        mViewPager=(ViewPager)findViewById(R.id.tabpager);
        btnBack=(Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
    }
}
