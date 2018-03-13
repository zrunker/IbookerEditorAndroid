package cc.ibooker.ibookereditorandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cc.ibooker.ibookereditorlib.IbookerEditorScaleImageView;
import cc.ibooker.zviewpagerlib.DecoratorLayout;
import cc.ibooker.zviewpagerlib.Holder;
import cc.ibooker.zviewpagerlib.HolderCreator;
import cc.ibooker.zviewpagerlib.OnItemClickListener;

/**
 * 图片预览Activity
 * <p>
 * Created by 邹峰立 on 2018/3/13 0013.
 */
public class ImgVPagerActivity extends AppCompatActivity {
    private String currentPath;
    private int position;
    private ArrayList<String> imgAllPathList;

    private DecoratorLayout<String> decoratorLayout;

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
        decoratorLayout = findViewById(R.id.decoratorLayout);

        // 初始化decoratorLayout
        decoratorLayout.init(new HolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, imgAllPathList)
                // 设置指示器，第一个代表选中，第二个代表未选中
                .setPageIndicator(R.mipmap.icon_focusdot, R.mipmap.icon_defaultdot)
                // 设置轮播停顿时间
                .setDuration(3000)
                // 指示器位置，居左、居中、居右
                .setPageIndicatorAlign(DecoratorLayout.PageIndicatorAlign.CENTER_HORIZONTAL)
                // 设置指示器是否可见
                .setPointViewVisible(true)
                // 点击事件监听
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        Toast.makeText(ImgVPagerActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                // 解决与父控件listView/ScrollView..滑动冲突
                // .setViewPagerParent(parent)
                // 开启轮播
                .start();
        // ViewPager改变监听
        decoratorLayout.setOnViewPagerChangeListener(new DecoratorLayout.OnViewPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    // 自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
    private class ImageViewHolder implements Holder<String> {
        private IbookerEditorScaleImageView imageView;

        @Override
        public View createView(Context context) {
            // 创建数据
            imageView = new IbookerEditorScaleImageView(context);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 加载数据
            Picasso.get().load(data).into(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        decoratorLayout.stop();
    }
}
