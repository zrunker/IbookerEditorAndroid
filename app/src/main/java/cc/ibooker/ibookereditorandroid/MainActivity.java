package cc.ibooker.ibookereditorandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cc.ibooker.ibookereditorlib.IbookerEditorView;
import cc.ibooker.ibookereditorlib.IbookerEditorWebView;

public class MainActivity extends AppCompatActivity {
    private IbookerEditorView ibookerEditorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibookerEditorView = findViewById(R.id.ibookereditorview);

        ibookerEditorView.setIEEditViewBackgroundColor(Color.parseColor("#DDDDDD"))
                .setIETopViewBackImgVisibility(View.VISIBLE)
                .setIETopViewHelpIBtnVisibility(View.VISIBLE)
                .setIEEditViewIbookerEdHint("书客编辑器")
                .setIEToolViewEmojiIBtnVisibility(View.GONE)
                .setIbookerEditorImgPreviewListener(new IbookerEditorWebView.IbookerEditorImgPreviewListener() {
                    @Override
                    public void onIbookerEditorImgPreview(String currentPath, int position, ArrayList<String> imgAllPathList) {
                        Toast.makeText(MainActivity.this, currentPath + "===" + position + "===" + imgAllPathList.toString(), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, ImgVPagerActivity.class);
                        intent.putExtra("currentPath", currentPath);
                        intent.putExtra("position", position);
                        intent.putStringArrayListExtra("imgAllPathList", imgAllPathList);
                        startActivity(intent);
                    }
                });

        ibookerEditorView.getIbookerEditorVpView()
                .getEditView()
                .getIbookerEd()
                .setText("\"This come from string. You can insert inline formula:\" +\n" +
                        "            \" \\\\(ax^2 + bx + c = 0\\\\) \" +\n" +
                        "            \"or displayed formula: $$\\\\sum_{i=0}^n i^2 = \\\\frac{(n^2+n)(2n+1)}{6}$$\";" +
                        "### 书客编辑器\n***\n书客编辑器从这里开始，我们的每一次开始，都那么小心翼翼，这一次如果可以的话不妨就现在开始试试。\n\n书客编辑器，简易，高效。轻轻松松记录你的每一刻。\n>书客编辑器不仅仅是为了“还好”而设计\n\n“还好”，那就意味着产品不够好；“很好”，那就意味着还需要努力；产品目标只为“最好”。\n```\nWish you every day to be beautiful.\n```\n书客编辑器期待你的加入！！！\n\n![书客编辑器](http://editor.ibooker.cc/resources/images-logos/ic_launcher_96.png)\n![书客创作](http://www.ibookerfile.cc/upload/images/acontents/1_1519218464761acontentimage.jpg)");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ibookerEditorView != null)
            ibookerEditorView.destoryIbookerEditor();
    }
}
