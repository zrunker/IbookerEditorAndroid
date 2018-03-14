package cc.ibooker.ibookereditorandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 图片预览Activity
 * <p>
 * Created by 邹峰立 on 2018/3/13 0013.
 */
public class ImgVPagerActivity extends AppCompatActivity implements View.OnClickListener {
    private String currentPath;
    private int position;
    private ArrayList<String> imgAllPathList;

    private ViewPager mViewPager;
    private TextView indicatorTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgvpger);

        // 获取上一个界面传值
        currentPath = getIntent().getStringExtra("currentPath");
        position = getIntent().getIntExtra("position", 0);
        imgAllPathList = getIntent().getStringArrayListExtra("imgAllPathList");

        init();
    }

    // 初始化
    private void init() {
        mViewPager = findViewById(R.id.id_viewpager);
        ImageView shareImg = findViewById(R.id.img_share);
        shareImg.setOnClickListener(this);
        ImageView leftImg = findViewById(R.id.img_left);
        leftImg.setOnClickListener(this);
        ImageView rightImg = findViewById(R.id.img_right);
        rightImg.setOnClickListener(this);
        indicatorTv = findViewById(R.id.tv_indicator);

        // 初始化数据
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // 点击事件监听
    @Override
    public void onClick(View v) {

    }
}
