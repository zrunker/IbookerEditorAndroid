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
//                .setIETopViewBackImgVisibility(View.VISIBLE)
//                .setIETopViewHelpIBtnVisibility(View.VISIBLE)
                .setIEEditViewIbookerEdHint("书客编辑器")
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
                .setText("[链接描述](http://ips.ifeng.com/video19.ifeng.com/video09/2018/09/28/p2749834-102-998769-221938.mp4?ifsign=1&vid=2f24cc82-9236-4289-adcc-5e34ae569cc8&uid=1516537011039_hyemhv7718&from=weMedia_pc&pver=weMediaHTML5Player_v1.0.0&sver=&se=%E9%A3%9E%E7%A2%9F%E8%AF%B4&cat=8622&ptype=8622&platform=pc&sourceType=h5&dt=1538135880000&gid=SLTFYWYkO4jp&sign=16536514b3d291920ac6253872661e9c&tm=1538206173419)\n" +
                        "                   " +
                        "\"This come from string. You can insert inline formula:\" +\n" +
                        "            \" \\\\(ax^2 + bx + c = 0\\\\) \" +\n" +
                        "            \"or displayed formula: $$\\\\sum_{i=0}^n i^2 = \\\\frac{(n^2+n)(2n+1)}{6}$$\";" +
                        "### 书客编辑器\n***\n书客编辑器从这里开始，我们的每一次开始，都那么小心翼翼，这一次如果可以的话不妨就现在开始试试。\n\n书客编辑器，简易，高效。轻轻松松记录你的每一刻。\n>书客编辑器不仅仅是为了“还好”而设计\n\n“还好”，那就意味着产品不够好；“很好”，那就意味着还需要努力；产品目标只为“最好”。\n```\nWish you every day to be beautiful.\n```\n书客编辑器期待你的加入！！！\n\n![书客编辑器](http://editor.ibooker.cc/resources/images-logos/ic_launcher_96.png)\n![书客创作](http://www.ibookerfile.cc/upload/images/acontents/1_1519218464761acontentimage.jpg)");

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ibookerEditorView != null)
            ibookerEditorView.stopIbookerEditor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ibookerEditorView != null)
            ibookerEditorView.destoryIbookerEditor();
    }
}
