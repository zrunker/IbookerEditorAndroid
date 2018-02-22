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
                .setIbookerBackgroundColor(Color.parseColor("#DDDDDD"));

        ibookerEditorView.getIbookerEditorVpView().getEditView().getIbookerEd().setText("### 书客编辑器\n***\n书客编辑器从这里开始，我们的每一次开始，都那么小心翼翼，这一次如果可以的话不妨就现在开始试试。\n\n书客编辑器，简易，高效。轻轻松松记录你的每一刻。\n>书客编辑器不仅仅是为了“还好”而设计\n\n“还好”，那就意味着产品不够好；“很好”，那就意味着还需要努力；产品目标只为“最好”。\n```\nWish you every day to be beautiful.\n```\n书客编辑器期待你的加入！！！\n\n![书客编辑器](http://editor.ibooker.cc/resources/images-logos/ic_launcher_96.png)\n");

        // 设置书客编辑器底部布局相关属性
        ibookerEditorView.getIbookerEditorToolView()
                .setEmojiIBtnVisibility(View.GONE);
    }
}
