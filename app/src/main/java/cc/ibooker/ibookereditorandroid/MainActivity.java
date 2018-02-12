package cc.ibooker.ibookereditorandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cc.ibooker.ibookereditorlib.IbookerEditorView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IbookerEditorView ibookerEditorView = findViewById(R.id.ibookereditorview);

        // 设置书客编辑器顶部布局相关属性
        ibookerEditorView.getIbookerEditorTopView()
                .setBackImgVisibility(View.VISIBLE)
                .setHelpIBtnVisibility(View.VISIBLE);

        // 设置书客编辑器中间布局相关属性
        ibookerEditorView.getIbookerEditorVpView().getEditView()
                .setIbookerEdHint("书客编辑器")
                .setIbookerEdBackgroundColor(Color.parseColor("#DDDDDD"));

        // 设置书客编辑器底部布局相关属性
        ibookerEditorView.getIbookerEditorToolView()
                .setEmojiIBtnVisibility(View.GONE);
    }
}
