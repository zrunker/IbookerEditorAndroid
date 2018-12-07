package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.content.Context.MODE_MULTI_PROCESS;

/**
 * 编辑界面设置
 */
public class IbookerEditorSetPopuwindow extends PopupWindow implements View.OnClickListener {
    static final String IBOOKEREDITER_SET_NAME = "IBOOKEREDITER_SET_NAME";
    static final String IEEDITVIEW_WEBVIEW_FONTSIZE = "IEEDITVIEW_WEBVIEW_FONTSIZE";
    static final String IEEDITVIEW_IBOOKERED_TEXTSIZE = "IEEDITVIEW_IBOOKERED_TEXTSIZE";
    static final String IBOOKEREDITER_ISBRIGHTNESS = "IBOOKEREDITER_ISBRIGHTNESS";
    static final String IBOOKEREDITER_BRIGHTNESS = "IBOOKEREDITER_BRIGHTNESS";
    static final String IBOOKEREDITER_BACKGROUNDCOLOR = "IBOOKEREDITER_BACKGROUNDCOLOR";
    private Context context;
    private IbookerEditorView ibookerEditorView;
    private SeekBar seekBar;
    private TextView followSysTv, editFontSizeTv, preFontSizeTv;
    private LinearLayout backgroundLayout;
    private ImageView whiteImg, ivoryImg, lightyellowImg, yellowImg, snowImg, floralwhiteImg, lemonchiffonImg,
            cornsilkImg, seashellImg, lavenderblushImg, papayawhipImg, blanchedalmondImg, mistyroseImg, bisqueImg,
            moccasinImg, navajowhiteImg, peachpuffImg, goldImg, pinkImg, lightpinkImg, orangeImg, lightsalmonImg,
            darkorangeImg, coralImg, hotpinkImg, tomatoImg, orangeredImg, deeppinkImg, magentaImg,
            redImg, oldlaceImg, lightgoldenrodyellowImg, linenImg, antiquewhiteImg, salmonImg, ghostwhiteImg,
            mintcreamImg, whitesmokeImg, beigeImg, wheatImg, sandybrownImg, azureImg, honeydewImg, aliceblueImg,
            khakiImg, lightcoralImg, palegoldenrodImg, violetImg, darksalmonImg, lavenderImg, lightcyanImg,
            burlywoodImg, plumImg, gainsboroImg, crimsonImg, palevioletredImg, goldenrodImg, orchidImg,
            thistleImg, lightgrayImg, tanImg, chocolateImg, peruImg, indianredImg, mediumvioletredImg, silverImg,
            darkkhakiImg, rosybrownImg, mediumorchidImg, darkgoldenrodImg, firebrickImg, powderblueImg, lightsteelblueImg,
            paleturquoiseImg, greenyellowImg, lightblueImg, darkgrayImg, brownImg, siennaImg, darkorchidImg,
            palegreenImg, darkvioletImg, mediumpurpleImg, lightgreenImg, darkseagreenImg, saddlebrownImg,
            darkmagentaImg, darkredImg, bluevioletImg, lightskyblueImg, skyblueImg, grayImg, oliveImg,
            purpleImg, maroonImg, aquamarineImg, chartreuseImg, lawngreenImg, mediumslateblueImg, lightslategrayImg,
            slategrayImg, olivedrabImg, slateblueImg, dimgrayImg, mediumaquamarineImg, cornflowerblueImg, cadetblueImg,
            darkolivegreenImg, indigoImg, mediumturquoiseImg, darkslateblueImg, steelblueImg, royalblueImg, turquoiseImg,
            mediumseagreenImg, limegreenImg, darkslategrayImg, seagreenImg, forestgreenImg, lightseagreenImg,
            dodgerblueImg, midnightblueImg, aquaImg, springgreenImg, limeImg, mediumspringgreenImg, darkturquoiseImg,
            deepskyblueImg, darkcyanImg, tealImg, greenImg, darkgreenImg, blueImg, mediumblueImg, darkblueImg,
            navyImg, blackImg;

    private Handler handler;
    private int currentEditTextSize, currentPreFontSize;

    IbookerEditorSetPopuwindow(Context context, IbookerEditorView ibookerEditorView) {
        super(context);
        this.context = context;
        this.ibookerEditorView = ibookerEditorView;
        final View view = LayoutInflater.from(context).inflate(R.layout.ibooker_editor_layout_set, ibookerEditorView, false);
        initView(view);
        setContentView(view);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
//        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(getScreenW(context));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void initView(View view) {
        currentEditTextSize = (int) ibookerEditorView.getIbookerEditorVpView().getEditView().getCurrentTextSize();
        currentPreFontSize = ibookerEditorView.getIbookerEditorVpView().getPreView().getIbookerEditorWebView().getCurrentFontSize();

        seekBar = view.findViewById(R.id.seekbar_screen_brightness);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                if (!checkPermission(context, true) && progress > 0) {
                    // 延迟1s执行
                    if (handler == null) handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopAutoBrightness(context);
                            saveBrightness(context, progress);
                            followSysTv.setTextColor(Color.parseColor("#555555"));
                            followSysTv.setBackgroundResource(R.drawable.bg_ibooker_editor_555555_coner_3);
                        }
                    }, 500);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        followSysTv = view.findViewById(R.id.tv_follow_sys);
        followSysTv.setOnClickListener(this);
        TextView editFontSizeAddTv = view.findViewById(R.id.tv_edit_font_size_add);
        editFontSizeAddTv.setOnClickListener(this);
        editFontSizeTv = view.findViewById(R.id.tv_edit_font_size);
        TextView editFontSizeReduceTv = view.findViewById(R.id.tv_edit_font_size_reduce);
        editFontSizeReduceTv.setOnClickListener(this);
        TextView preFontSizeAddTv = view.findViewById(R.id.tv_pre_font_size_add);
        preFontSizeAddTv.setOnClickListener(this);
        preFontSizeTv = view.findViewById(R.id.tv_pre_font_size);
        TextView preFontSizeReduceTv = view.findViewById(R.id.tv_pre_font_size_reduce);
        preFontSizeReduceTv.setOnClickListener(this);

        backgroundLayout = view.findViewById(R.id.layout_background);
        whiteImg = view.findViewById(R.id.img_white);
        whiteImg.setOnClickListener(this);
        ivoryImg = view.findViewById(R.id.img_ivory);
        ivoryImg.setOnClickListener(this);
        lightyellowImg = view.findViewById(R.id.img_lightyellow);
        lightyellowImg.setOnClickListener(this);
        yellowImg = view.findViewById(R.id.img_yellow);
        yellowImg.setOnClickListener(this);
        snowImg = view.findViewById(R.id.img_snow);
        snowImg.setOnClickListener(this);
        floralwhiteImg = view.findViewById(R.id.img_floralwhite);
        floralwhiteImg.setOnClickListener(this);
        lemonchiffonImg = view.findViewById(R.id.img_lemonchiffon);
        lemonchiffonImg.setOnClickListener(this);
        cornsilkImg = view.findViewById(R.id.img_cornsilk);
        cornsilkImg.setOnClickListener(this);
        seashellImg = view.findViewById(R.id.img_seashell);
        seashellImg.setOnClickListener(this);
        lavenderblushImg = view.findViewById(R.id.img_lavenderblush);
        lavenderblushImg.setOnClickListener(this);
        papayawhipImg = view.findViewById(R.id.img_papayawhip);
        papayawhipImg.setOnClickListener(this);
        blanchedalmondImg = view.findViewById(R.id.img_blanchedalmond);
        blanchedalmondImg.setOnClickListener(this);
        mistyroseImg = view.findViewById(R.id.img_mistyrose);
        mistyroseImg.setOnClickListener(this);
        bisqueImg = view.findViewById(R.id.img_bisque);
        bisqueImg.setOnClickListener(this);
        moccasinImg = view.findViewById(R.id.img_moccasin);
        moccasinImg.setOnClickListener(this);
        navajowhiteImg = view.findViewById(R.id.img_navajowhite);
        navajowhiteImg.setOnClickListener(this);
        peachpuffImg = view.findViewById(R.id.img_peachpuff);
        peachpuffImg.setOnClickListener(this);
        goldImg = view.findViewById(R.id.img_gold);
        goldImg.setOnClickListener(this);
        pinkImg = view.findViewById(R.id.img_pink);
        pinkImg.setOnClickListener(this);
        lightpinkImg = view.findViewById(R.id.img_lightpink);
        lightpinkImg.setOnClickListener(this);
        orangeImg = view.findViewById(R.id.img_orange);
        orangeImg.setOnClickListener(this);
        lightsalmonImg = view.findViewById(R.id.img_lightsalmon);
        lightsalmonImg.setOnClickListener(this);
        darkorangeImg = view.findViewById(R.id.img_darkorange);
        darkorangeImg.setOnClickListener(this);
        coralImg = view.findViewById(R.id.img_coral);
        coralImg.setOnClickListener(this);
        hotpinkImg = view.findViewById(R.id.img_hotpink);
        hotpinkImg.setOnClickListener(this);
        tomatoImg = view.findViewById(R.id.img_tomato);
        tomatoImg.setOnClickListener(this);
        orangeredImg = view.findViewById(R.id.img_orangered);
        orangeredImg.setOnClickListener(this);
        deeppinkImg = view.findViewById(R.id.img_deeppink);
        deeppinkImg.setOnClickListener(this);
        magentaImg = view.findViewById(R.id.img_magenta);
        magentaImg.setOnClickListener(this);
        redImg = view.findViewById(R.id.img_red);
        redImg.setOnClickListener(this);
        oldlaceImg = view.findViewById(R.id.img_oldlace);
        oldlaceImg.setOnClickListener(this);
        lightgoldenrodyellowImg = view.findViewById(R.id.img_lightgoldenrodyellow);
        lightgoldenrodyellowImg.setOnClickListener(this);
        linenImg = view.findViewById(R.id.img_linen);
        linenImg.setOnClickListener(this);
        antiquewhiteImg = view.findViewById(R.id.img_antiquewhite);
        antiquewhiteImg.setOnClickListener(this);
        salmonImg = view.findViewById(R.id.img_salmon);
        salmonImg.setOnClickListener(this);
        ghostwhiteImg = view.findViewById(R.id.img_ghostwhite);
        ghostwhiteImg.setOnClickListener(this);
        mintcreamImg = view.findViewById(R.id.img_mintcream);
        mintcreamImg.setOnClickListener(this);
        whitesmokeImg = view.findViewById(R.id.img_whitesmoke);
        whitesmokeImg.setOnClickListener(this);
        beigeImg = view.findViewById(R.id.img_beige);
        beigeImg.setOnClickListener(this);
        wheatImg = view.findViewById(R.id.img_wheat);
        wheatImg.setOnClickListener(this);
        sandybrownImg = view.findViewById(R.id.img_sandybrown);
        sandybrownImg.setOnClickListener(this);
        azureImg = view.findViewById(R.id.img_azure);
        azureImg.setOnClickListener(this);
        honeydewImg = view.findViewById(R.id.img_honeydew);
        honeydewImg.setOnClickListener(this);
        aliceblueImg = view.findViewById(R.id.img_aliceblue);
        aliceblueImg.setOnClickListener(this);
        khakiImg = view.findViewById(R.id.img_khaki);
        khakiImg.setOnClickListener(this);
        lightcoralImg = view.findViewById(R.id.img_lightcoral);
        lightcoralImg.setOnClickListener(this);
        palegoldenrodImg = view.findViewById(R.id.img_palegoldenrod);
        palegoldenrodImg.setOnClickListener(this);
        violetImg = view.findViewById(R.id.img_violet);
        violetImg.setOnClickListener(this);
        darksalmonImg = view.findViewById(R.id.img_darksalmon);
        darksalmonImg.setOnClickListener(this);
        lavenderImg = view.findViewById(R.id.img_lavender);
        lavenderImg.setOnClickListener(this);
        lightcyanImg = view.findViewById(R.id.img_lightcyan);
        lightcyanImg.setOnClickListener(this);
        burlywoodImg = view.findViewById(R.id.img_burlywood);
        burlywoodImg.setOnClickListener(this);
        plumImg = view.findViewById(R.id.img_plum);
        plumImg.setOnClickListener(this);
        gainsboroImg = view.findViewById(R.id.img_gainsboro);
        gainsboroImg.setOnClickListener(this);
        crimsonImg = view.findViewById(R.id.img_crimson);
        crimsonImg.setOnClickListener(this);
        palevioletredImg = view.findViewById(R.id.img_palevioletred);
        palevioletredImg.setOnClickListener(this);
        goldenrodImg = view.findViewById(R.id.img_goldenrod);
        goldenrodImg.setOnClickListener(this);
        orchidImg = view.findViewById(R.id.img_orchid);
        orchidImg.setOnClickListener(this);
        thistleImg = view.findViewById(R.id.img_thistle);
        thistleImg.setOnClickListener(this);
        lightgrayImg = view.findViewById(R.id.img_lightgray);
        lightgrayImg.setOnClickListener(this);
        tanImg = view.findViewById(R.id.img_tan);
        tanImg.setOnClickListener(this);
        chocolateImg = view.findViewById(R.id.img_chocolate);
        chocolateImg.setOnClickListener(this);
        peruImg = view.findViewById(R.id.img_peru);
        peruImg.setOnClickListener(this);
        indianredImg = view.findViewById(R.id.img_indianred);
        indianredImg.setOnClickListener(this);
        mediumvioletredImg = view.findViewById(R.id.img_mediumvioletred);
        mediumvioletredImg.setOnClickListener(this);
        silverImg = view.findViewById(R.id.img_silver);
        silverImg.setOnClickListener(this);
        darkkhakiImg = view.findViewById(R.id.img_darkkhaki);
        darkkhakiImg.setOnClickListener(this);
        rosybrownImg = view.findViewById(R.id.img_rosybrown);
        rosybrownImg.setOnClickListener(this);
        mediumorchidImg = view.findViewById(R.id.img_mediumorchid);
        mediumorchidImg.setOnClickListener(this);
        darkgoldenrodImg = view.findViewById(R.id.img_darkgoldenrod);
        darkgoldenrodImg.setOnClickListener(this);
        firebrickImg = view.findViewById(R.id.img_firebrick);
        firebrickImg.setOnClickListener(this);
        powderblueImg = view.findViewById(R.id.img_powderblue);
        powderblueImg.setOnClickListener(this);
        lightsteelblueImg = view.findViewById(R.id.img_lightsteelblue);
        lightsteelblueImg.setOnClickListener(this);
        paleturquoiseImg = view.findViewById(R.id.img_paleturquoise);
        paleturquoiseImg.setOnClickListener(this);
        greenyellowImg = view.findViewById(R.id.img_greenyellow);
        greenyellowImg.setOnClickListener(this);
        lightblueImg = view.findViewById(R.id.img_lightblue);
        lightblueImg.setOnClickListener(this);
        darkgrayImg = view.findViewById(R.id.img_darkgray);
        darkgrayImg.setOnClickListener(this);
        brownImg = view.findViewById(R.id.img_brown);
        brownImg.setOnClickListener(this);
        siennaImg = view.findViewById(R.id.img_sienna);
        siennaImg.setOnClickListener(this);
        darkorchidImg = view.findViewById(R.id.img_darkorchid);
        darkorchidImg.setOnClickListener(this);
        palegreenImg = view.findViewById(R.id.img_palegreen);
        palegreenImg.setOnClickListener(this);
        darkvioletImg = view.findViewById(R.id.img_darkviolet);
        darkvioletImg.setOnClickListener(this);
        mediumpurpleImg = view.findViewById(R.id.img_mediumpurple);
        mediumpurpleImg.setOnClickListener(this);
        lightgreenImg = view.findViewById(R.id.img_lightgreen);
        lightgreenImg.setOnClickListener(this);
        darkseagreenImg = view.findViewById(R.id.img_darkseagreen);
        darkseagreenImg.setOnClickListener(this);
        saddlebrownImg = view.findViewById(R.id.img_saddlebrown);
        saddlebrownImg.setOnClickListener(this);
        darkmagentaImg = view.findViewById(R.id.img_darkmagenta);
        darkmagentaImg.setOnClickListener(this);
        darkredImg = view.findViewById(R.id.img_darkred);
        darkredImg.setOnClickListener(this);
        bluevioletImg = view.findViewById(R.id.img_blueviolet);
        bluevioletImg.setOnClickListener(this);
        lightskyblueImg = view.findViewById(R.id.img_lightskyblue);
        lightskyblueImg.setOnClickListener(this);
        skyblueImg = view.findViewById(R.id.img_skyblue);
        skyblueImg.setOnClickListener(this);
        grayImg = view.findViewById(R.id.img_gray);
        grayImg.setOnClickListener(this);
        oliveImg = view.findViewById(R.id.img_olive);
        oliveImg.setOnClickListener(this);
        purpleImg = view.findViewById(R.id.img_purple);
        purpleImg.setOnClickListener(this);
        maroonImg = view.findViewById(R.id.img_maroon);
        maroonImg.setOnClickListener(this);
        aquamarineImg = view.findViewById(R.id.img_aquamarine);
        aquamarineImg.setOnClickListener(this);
        chartreuseImg = view.findViewById(R.id.img_chartreuse);
        chartreuseImg.setOnClickListener(this);
        lawngreenImg = view.findViewById(R.id.img_lawngreen);
        lawngreenImg.setOnClickListener(this);
        mediumslateblueImg = view.findViewById(R.id.img_mediumslateblue);
        mediumslateblueImg.setOnClickListener(this);
        lightslategrayImg = view.findViewById(R.id.img_lightslategray);
        lightslategrayImg.setOnClickListener(this);
        slategrayImg = view.findViewById(R.id.img_slategray);
        slategrayImg.setOnClickListener(this);
        olivedrabImg = view.findViewById(R.id.img_olivedrab);
        olivedrabImg.setOnClickListener(this);
        slateblueImg = view.findViewById(R.id.img_slateblue);
        slateblueImg.setOnClickListener(this);
        dimgrayImg = view.findViewById(R.id.img_dimgray);
        dimgrayImg.setOnClickListener(this);
        mediumaquamarineImg = view.findViewById(R.id.img_mediumaquamarine);
        mediumaquamarineImg.setOnClickListener(this);
        cornflowerblueImg = view.findViewById(R.id.img_cornflowerblue);
        cornflowerblueImg.setOnClickListener(this);
        cadetblueImg = view.findViewById(R.id.img_cadetblue);
        cadetblueImg.setOnClickListener(this);
        darkolivegreenImg = view.findViewById(R.id.img_darkolivegreen);
        darkolivegreenImg.setOnClickListener(this);
        indigoImg = view.findViewById(R.id.img_indigo);
        indigoImg.setOnClickListener(this);
        mediumturquoiseImg = view.findViewById(R.id.img_mediumturquoise);
        mediumturquoiseImg.setOnClickListener(this);
        darkslateblueImg = view.findViewById(R.id.img_darkslateblue);
        darkslateblueImg.setOnClickListener(this);
        steelblueImg = view.findViewById(R.id.img_steelblue);
        steelblueImg.setOnClickListener(this);
        royalblueImg = view.findViewById(R.id.img_royalblue);
        royalblueImg.setOnClickListener(this);
        turquoiseImg = view.findViewById(R.id.img_turquoise);
        turquoiseImg.setOnClickListener(this);
        mediumseagreenImg = view.findViewById(R.id.img_mediumseagreen);
        mediumseagreenImg.setOnClickListener(this);
        limegreenImg = view.findViewById(R.id.img_limegreen);
        limegreenImg.setOnClickListener(this);
        darkslategrayImg = view.findViewById(R.id.img_darkslategray);
        darkslategrayImg.setOnClickListener(this);
        seagreenImg = view.findViewById(R.id.img_seagreen);
        seagreenImg.setOnClickListener(this);
        forestgreenImg = view.findViewById(R.id.img_forestgreen);
        forestgreenImg.setOnClickListener(this);
        lightseagreenImg = view.findViewById(R.id.img_lightseagreen);
        lightseagreenImg.setOnClickListener(this);
        dodgerblueImg = view.findViewById(R.id.img_dodgerblue);
        dodgerblueImg.setOnClickListener(this);
        midnightblueImg = view.findViewById(R.id.img_midnightblue);
        midnightblueImg.setOnClickListener(this);
        aquaImg = view.findViewById(R.id.img_aqua);
        aquaImg.setOnClickListener(this);
        springgreenImg = view.findViewById(R.id.img_springgreen);
        springgreenImg.setOnClickListener(this);
        limeImg = view.findViewById(R.id.img_lime);
        limeImg.setOnClickListener(this);
        mediumspringgreenImg = view.findViewById(R.id.img_mediumspringgreen);
        mediumspringgreenImg.setOnClickListener(this);
        darkturquoiseImg = view.findViewById(R.id.img_darkturquoise);
        darkturquoiseImg.setOnClickListener(this);
        deepskyblueImg = view.findViewById(R.id.img_deepskyblue);
        deepskyblueImg.setOnClickListener(this);
        darkcyanImg = view.findViewById(R.id.img_darkcyan);
        darkcyanImg.setOnClickListener(this);
        tealImg = view.findViewById(R.id.img_teal);
        tealImg.setOnClickListener(this);
        greenImg = view.findViewById(R.id.img_green);
        greenImg.setOnClickListener(this);
        darkgreenImg = view.findViewById(R.id.img_darkgreen);
        darkgreenImg.setOnClickListener(this);
        blueImg = view.findViewById(R.id.img_blue);
        blueImg.setOnClickListener(this);
        mediumblueImg = view.findViewById(R.id.img_mediumblue);
        mediumblueImg.setOnClickListener(this);
        darkblueImg = view.findViewById(R.id.img_darkblue);
        darkblueImg.setOnClickListener(this);
        navyImg = view.findViewById(R.id.img_navy);
        navyImg.setOnClickListener(this);
        blackImg = view.findViewById(R.id.img_black);
        blackImg.setOnClickListener(this);

        // 初始化数据
        SharedPreferences sharedPreferences = context.getSharedPreferences(IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE);
        boolean ibookerediter_isbrightness = sharedPreferences.getBoolean(IBOOKEREDITER_ISBRIGHTNESS, false);
        int ibookerediter_brightness = sharedPreferences.getInt(IBOOKEREDITER_BRIGHTNESS, 0);
        int ieeditview_ibookered_textsize = sharedPreferences.getInt(IEEDITVIEW_IBOOKERED_TEXTSIZE, 0);
        int ieeditview_webview_fontsize = sharedPreferences.getInt(IEEDITVIEW_WEBVIEW_FONTSIZE, 0);
        String ibookerediter_backgroundcolor = sharedPreferences.getString(IBOOKEREDITER_BACKGROUNDCOLOR, "");

        if (ieeditview_ibookered_textsize > 0) {
            setIEEditViewIbookerEdTextSize(ieeditview_ibookered_textsize);
            currentEditTextSize = ieeditview_ibookered_textsize;
        }
        if (ieeditview_webview_fontsize > 0) {
            setIEEditViewWebViewFontSize(ieeditview_webview_fontsize);
            currentPreFontSize = ieeditview_webview_fontsize;
        }
        if (!TextUtils.isEmpty(ibookerediter_backgroundcolor))
            setImageViewBackground(view.findViewWithTag(ibookerediter_backgroundcolor));

        editFontSizeTv.setText(currentEditTextSize + "");
        preFontSizeTv.setText(currentPreFontSize + "倍");

        if (!ScreenBrightnessUtil.checkPermission(context, false)) {
            if (ibookerediter_isbrightness) {
                startAutoBrightness(context);
                followSysTv.setTextColor(Color.parseColor("#FE7517"));
                followSysTv.setBackgroundResource(R.drawable.bg_ibooker_editor_fe7517_coner_3);
//                seekBar.setProgress(0);
            } else {
                seekBar.setProgress(ibookerediter_brightness);
            }
        }
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenW(Context aty) {
        DisplayMetrics dm;
        dm = aty.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    // 点击事件监听
    @Override
    public void onClick(View v) {
        if (ClickUtil.isFastClick()) return;
        int currentId = v.getId();
        if (currentId == R.id.tv_follow_sys) {// 亮度-跟随系统
            if (!checkPermission(context, true)) {
                startAutoBrightness(context);
                followSysTv.setTextColor(Color.parseColor("#FE7517"));
                followSysTv.setBackgroundResource(R.drawable.bg_ibooker_editor_fe7517_coner_3);
                seekBar.setProgress(0);
            }
        } else if (currentId == R.id.tv_edit_font_size_add) {// 编辑 - 字体 - 加
            setIEEditViewIbookerEdTextSize(currentEditTextSize + 1);
        } else if (currentId == R.id.tv_edit_font_size_reduce) {// 编辑 - 字体 - 减
            setIEEditViewIbookerEdTextSize(currentEditTextSize - 1);
        } else if (currentId == R.id.tv_pre_font_size_add) {// 预览 - 字体 - 加
            setIEEditViewWebViewFontSize(currentPreFontSize + 1);
        } else if (currentId == R.id.tv_pre_font_size_reduce) {// 预览 - 字体 - 减
            setIEEditViewWebViewFontSize(currentPreFontSize - 1);
        } else if (currentId == R.id.img_white) {
            setImageViewBackground(whiteImg);
        } else if (currentId == R.id.img_ivory) {
            setImageViewBackground(ivoryImg);
        } else if (currentId == R.id.img_lightyellow) {
            setImageViewBackground(lightyellowImg);
        } else if (currentId == R.id.img_yellow) {
            setImageViewBackground(yellowImg);
        } else if (currentId == R.id.img_snow) {
            setImageViewBackground(snowImg);
        } else if (currentId == R.id.img_floralwhite) {
            setImageViewBackground(floralwhiteImg);
        } else if (currentId == R.id.img_lemonchiffon) {
            setImageViewBackground(lemonchiffonImg);
        } else if (currentId == R.id.img_cornsilk) {
            setImageViewBackground(cornsilkImg);
        } else if (currentId == R.id.img_seashell) {
            setImageViewBackground(seashellImg);
        } else if (currentId == R.id.img_lavenderblush) {
            setImageViewBackground(lavenderblushImg);
        } else if (currentId == R.id.img_papayawhip) {
            setImageViewBackground(papayawhipImg);
        } else if (currentId == R.id.img_blanchedalmond) {
            setImageViewBackground(blanchedalmondImg);
        } else if (currentId == R.id.img_mistyrose) {
            setImageViewBackground(mistyroseImg);
        } else if (currentId == R.id.img_bisque) {
            setImageViewBackground(bisqueImg);
        } else if (currentId == R.id.img_moccasin) {
            setImageViewBackground(moccasinImg);
        } else if (currentId == R.id.img_navajowhite) {
            setImageViewBackground(navajowhiteImg);
        } else if (currentId == R.id.img_peachpuff) {
            setImageViewBackground(peachpuffImg);
        } else if (currentId == R.id.img_gold) {
            setImageViewBackground(goldImg);
        } else if (currentId == R.id.img_pink) {
            setImageViewBackground(pinkImg);
        } else if (currentId == R.id.img_lightpink) {
            setImageViewBackground(lightpinkImg);
        } else if (currentId == R.id.img_orange) {
            setImageViewBackground(orangeImg);
        } else if (currentId == R.id.img_lightsalmon) {
            setImageViewBackground(lightsalmonImg);
        } else if (currentId == R.id.img_darkorange) {
            setImageViewBackground(darkorangeImg);
        } else if (currentId == R.id.img_coral) {
            setImageViewBackground(coralImg);
        } else if (currentId == R.id.img_hotpink) {
            setImageViewBackground(hotpinkImg);
        } else if (currentId == R.id.img_tomato) {
            setImageViewBackground(tomatoImg);
        } else if (currentId == R.id.img_orangered) {
            setImageViewBackground(orangeredImg);
        } else if (currentId == R.id.img_deeppink) {
            setImageViewBackground(deeppinkImg);
        } else if (currentId == R.id.img_magenta) {
            setImageViewBackground(magentaImg);
        } else if (currentId == R.id.img_red) {
            setImageViewBackground(redImg);
        } else if (currentId == R.id.img_oldlace) {
            setImageViewBackground(oldlaceImg);
        } else if (currentId == R.id.img_lightgoldenrodyellow) {
            setImageViewBackground(lightgoldenrodyellowImg);
        } else if (currentId == R.id.img_linen) {
            setImageViewBackground(linenImg);
        } else if (currentId == R.id.img_antiquewhite) {
            setImageViewBackground(antiquewhiteImg);
        } else if (currentId == R.id.img_salmon) {
            setImageViewBackground(salmonImg);
        } else if (currentId == R.id.img_ghostwhite) {
            setImageViewBackground(ghostwhiteImg);
        } else if (currentId == R.id.img_mintcream) {
            setImageViewBackground(mintcreamImg);
        } else if (currentId == R.id.img_whitesmoke) {
            setImageViewBackground(whitesmokeImg);
        } else if (currentId == R.id.img_beige) {
            setImageViewBackground(beigeImg);
        } else if (currentId == R.id.img_wheat) {
            setImageViewBackground(wheatImg);
        } else if (currentId == R.id.img_sandybrown) {
            setImageViewBackground(sandybrownImg);
        } else if (currentId == R.id.img_azure) {
            setImageViewBackground(azureImg);
        } else if (currentId == R.id.img_honeydew) {
            setImageViewBackground(honeydewImg);
        } else if (currentId == R.id.img_aliceblue) {
            setImageViewBackground(aliceblueImg);
        } else if (currentId == R.id.img_khaki) {
            setImageViewBackground(khakiImg);
        } else if (currentId == R.id.img_lightcoral) {
            setImageViewBackground(lightcoralImg);
        } else if (currentId == R.id.img_palegoldenrod) {
            setImageViewBackground(palegoldenrodImg);
        } else if (currentId == R.id.img_violet) {
            setImageViewBackground(violetImg);
        } else if (currentId == R.id.img_darksalmon) {
            setImageViewBackground(darksalmonImg);
        } else if (currentId == R.id.img_lavender) {
            setImageViewBackground(lavenderImg);
        } else if (currentId == R.id.img_lightcyan) {
            setImageViewBackground(lightcyanImg);
        } else if (currentId == R.id.img_burlywood) {
            setImageViewBackground(burlywoodImg);
        } else if (currentId == R.id.img_plum) {
            setImageViewBackground(plumImg);
        } else if (currentId == R.id.img_gainsboro) {
            setImageViewBackground(gainsboroImg);
        } else if (currentId == R.id.img_crimson) {
            setImageViewBackground(crimsonImg);
        } else if (currentId == R.id.img_palevioletred) {
            setImageViewBackground(palevioletredImg);
        } else if (currentId == R.id.img_goldenrod) {
            setImageViewBackground(goldenrodImg);
        } else if (currentId == R.id.img_orchid) {
            setImageViewBackground(orchidImg);
        } else if (currentId == R.id.img_thistle) {
            setImageViewBackground(thistleImg);
        } else if (currentId == R.id.img_lightgray) {
            setImageViewBackground(lightgrayImg);
        } else if (currentId == R.id.img_tan) {
            setImageViewBackground(tanImg);
        } else if (currentId == R.id.img_chocolate) {
            setImageViewBackground(chocolateImg);
        } else if (currentId == R.id.img_peru) {
            setImageViewBackground(peruImg);
        } else if (currentId == R.id.img_indianred) {
            setImageViewBackground(indianredImg);
        } else if (currentId == R.id.img_mediumvioletred) {
            setImageViewBackground(mediumvioletredImg);
        } else if (currentId == R.id.img_silver) {
            setImageViewBackground(silverImg);
        } else if (currentId == R.id.img_darkkhaki) {
            setImageViewBackground(darkkhakiImg);
        } else if (currentId == R.id.img_rosybrown) {
            setImageViewBackground(rosybrownImg);
        } else if (currentId == R.id.img_mediumorchid) {
            setImageViewBackground(mediumorchidImg);
        } else if (currentId == R.id.img_darkgoldenrod) {
            setImageViewBackground(darkgoldenrodImg);
        } else if (currentId == R.id.img_firebrick) {
            setImageViewBackground(firebrickImg);
        } else if (currentId == R.id.img_powderblue) {
            setImageViewBackground(powderblueImg);
        } else if (currentId == R.id.img_lightsteelblue) {
            setImageViewBackground(lightsteelblueImg);
        } else if (currentId == R.id.img_paleturquoise) {
            setImageViewBackground(paleturquoiseImg);
        } else if (currentId == R.id.img_greenyellow) {
            setImageViewBackground(greenyellowImg);
        } else if (currentId == R.id.img_lightblue) {
            setImageViewBackground(lightblueImg);
        } else if (currentId == R.id.img_darkgray) {
            setImageViewBackground(darkgrayImg);
        } else if (currentId == R.id.img_brown) {
            setImageViewBackground(brownImg);
        } else if (currentId == R.id.img_sienna) {
            setImageViewBackground(siennaImg);
        } else if (currentId == R.id.img_darkorchid) {
            setImageViewBackground(darkorchidImg);
        } else if (currentId == R.id.img_palegreen) {
            setImageViewBackground(palegreenImg);
        } else if (currentId == R.id.img_darkviolet) {
            setImageViewBackground(darkvioletImg);
        } else if (currentId == R.id.img_mediumpurple) {
            setImageViewBackground(mediumpurpleImg);
        } else if (currentId == R.id.img_lightgreen) {
            setImageViewBackground(lightgreenImg);
        } else if (currentId == R.id.img_darkseagreen) {
            setImageViewBackground(darkseagreenImg);
        } else if (currentId == R.id.img_saddlebrown) {
            setImageViewBackground(saddlebrownImg);
        } else if (currentId == R.id.img_darkmagenta) {
            setImageViewBackground(darkmagentaImg);
        } else if (currentId == R.id.img_darkred) {
            setImageViewBackground(darkredImg);
        } else if (currentId == R.id.img_blueviolet) {
            setImageViewBackground(bluevioletImg);
        } else if (currentId == R.id.img_lightskyblue) {
            setImageViewBackground(lightskyblueImg);
        } else if (currentId == R.id.img_skyblue) {
            setImageViewBackground(skyblueImg);
        } else if (currentId == R.id.img_gray) {
            setImageViewBackground(grayImg);
        } else if (currentId == R.id.img_olive) {
            setImageViewBackground(oliveImg);
        } else if (currentId == R.id.img_purple) {
            setImageViewBackground(purpleImg);
        } else if (currentId == R.id.img_maroon) {
            setImageViewBackground(maroonImg);
        } else if (currentId == R.id.img_aquamarine) {
            setImageViewBackground(aquamarineImg);
        } else if (currentId == R.id.img_chartreuse) {
            setImageViewBackground(chartreuseImg);
        } else if (currentId == R.id.img_lawngreen) {
            setImageViewBackground(lawngreenImg);
        } else if (currentId == R.id.img_mediumslateblue) {
            setImageViewBackground(mediumslateblueImg);
        } else if (currentId == R.id.img_lightslategray) {
            setImageViewBackground(lightslategrayImg);
        } else if (currentId == R.id.img_slategray) {
            setImageViewBackground(slategrayImg);
        } else if (currentId == R.id.img_olivedrab) {
            setImageViewBackground(olivedrabImg);
        } else if (currentId == R.id.img_slateblue) {
            setImageViewBackground(slateblueImg);
        } else if (currentId == R.id.img_dimgray) {
            setImageViewBackground(dimgrayImg);
        } else if (currentId == R.id.img_mediumaquamarine) {
            setImageViewBackground(mediumaquamarineImg);
        } else if (currentId == R.id.img_cornflowerblue) {
            setImageViewBackground(cornflowerblueImg);
        } else if (currentId == R.id.img_cadetblue) {
            setImageViewBackground(cadetblueImg);
        } else if (currentId == R.id.img_darkolivegreen) {
            setImageViewBackground(darkolivegreenImg);
        } else if (currentId == R.id.img_indigo) {
            setImageViewBackground(indigoImg);
        } else if (currentId == R.id.img_mediumturquoise) {
            setImageViewBackground(mediumturquoiseImg);
        } else if (currentId == R.id.img_darkslateblue) {
            setImageViewBackground(darkslateblueImg);
        } else if (currentId == R.id.img_steelblue) {
            setImageViewBackground(steelblueImg);
        } else if (currentId == R.id.img_royalblue) {
            setImageViewBackground(royalblueImg);
        } else if (currentId == R.id.img_turquoise) {
            setImageViewBackground(turquoiseImg);
        } else if (currentId == R.id.img_mediumseagreen) {
            setImageViewBackground(mediumseagreenImg);
        } else if (currentId == R.id.img_limegreen) {
            setImageViewBackground(limegreenImg);
        } else if (currentId == R.id.img_darkslategray) {
            setImageViewBackground(darkslategrayImg);
        } else if (currentId == R.id.img_seagreen) {
            setImageViewBackground(seagreenImg);
        } else if (currentId == R.id.img_forestgreen) {
            setImageViewBackground(forestgreenImg);
        } else if (currentId == R.id.img_lightseagreen) {
            setImageViewBackground(lightseagreenImg);
        } else if (currentId == R.id.img_dodgerblue) {
            setImageViewBackground(dodgerblueImg);
        } else if (currentId == R.id.img_midnightblue) {
            setImageViewBackground(midnightblueImg);
        } else if (currentId == R.id.img_aqua) {
            setImageViewBackground(aquaImg);
        } else if (currentId == R.id.img_springgreen) {
            setImageViewBackground(springgreenImg);
        } else if (currentId == R.id.img_lime) {
            setImageViewBackground(limeImg);
        } else if (currentId == R.id.img_mediumspringgreen) {
            setImageViewBackground(mediumspringgreenImg);
        } else if (currentId == R.id.img_darkturquoise) {
            setImageViewBackground(darkturquoiseImg);
        } else if (currentId == R.id.img_deepskyblue) {
            setImageViewBackground(deepskyblueImg);
        } else if (currentId == R.id.img_darkcyan) {
            setImageViewBackground(darkcyanImg);
        } else if (currentId == R.id.img_teal) {
            setImageViewBackground(tealImg);
        } else if (currentId == R.id.img_green) {
            setImageViewBackground(greenImg);
        } else if (currentId == R.id.img_darkgreen) {
            setImageViewBackground(darkgreenImg);
        } else if (currentId == R.id.img_blue) {
            setImageViewBackground(blueImg);
        } else if (currentId == R.id.img_mediumblue) {
            setImageViewBackground(mediumblueImg);
        } else if (currentId == R.id.img_darkblue) {
            setImageViewBackground(darkblueImg);
        } else if (currentId == R.id.img_navy) {
            setImageViewBackground(navyImg);
        } else if (currentId == R.id.img_black) {
            setImageViewBackground(blackImg);
        }
    }

    /**
     * 修改图片背景
     */
    private void setImageViewBackground(View view) {
        if (view != null) {
            String colorStr = (String) view.getTag();
            if (!TextUtils.isEmpty(colorStr)) {
                int color = Color.parseColor(colorStr);
                ibookerEditorView.setIEEditViewBackgroundColor(color);
                ibookerEditorView.setIEPreViewBackgroundColor(color);
                for (int i = 0; i < backgroundLayout.getChildCount(); i++) {
                    View imageView = backgroundLayout.getChildAt(i);
                    if (imageView == view)
                        imageView.setBackgroundResource(R.drawable.icon_ibooker_editor_fe7517_corner_31);
                    else
                        imageView.setBackgroundResource(0);
                }

                saveSharedPreferences(context, IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE, IBOOKEREDITER_BACKGROUNDCOLOR, colorStr);
            }
        }
    }

    /**
     * 开启自动亮度
     */
    private void startAutoBrightness(Context context) {
        ScreenBrightnessUtil.startAutoBrightness(context);
        saveSharedPreferences(context, IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE, IBOOKEREDITER_ISBRIGHTNESS, true);
    }

    /**
     * 停止自动亮度
     */
    private void stopAutoBrightness(Context context) {
        ScreenBrightnessUtil.stopAutoBrightness(context);
        saveSharedPreferences(context, IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE, IBOOKEREDITER_ISBRIGHTNESS, false);
    }

    /**
     * 设置屏幕亮度
     *
     * @param light 亮度值 1-255
     */
    private void saveBrightness(Context context, int light) {
        ScreenBrightnessUtil.saveBrightness(context, light);
        saveSharedPreferences(context, IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE, IBOOKEREDITER_BRIGHTNESS, light);
    }

    /**
     * 检测权限
     */
    private boolean checkPermission(Context context, boolean isJump) {
        return ScreenBrightnessUtil.checkPermission(context, isJump);
    }

    /**
     * 设置编辑字体大小
     *
     * @param size 10 - 30
     */
    private void setIEEditViewIbookerEdTextSize(int size) {
        if (size >= 10 && size <= 30) {
            ibookerEditorView.setIEEditViewIbookerEdTextSize(size);
            editFontSizeTv.setText(size + "");
            currentEditTextSize = size;

            saveSharedPreferences(context, IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE, IEEDITVIEW_IBOOKERED_TEXTSIZE, size);
        }
        if (currentEditTextSize < 10)
            currentEditTextSize = 10;
        else if (currentEditTextSize > 30)
            currentEditTextSize = 30;
    }

    /**
     * 设置预览界面字体大小
     *
     * @param size 字体大小 - 1-5倍
     */
    private void setIEEditViewWebViewFontSize(int size) {
        if (size >= 1 && size <= 5) {
            ibookerEditorView.setIEEditViewWebViewFontSize(size);
            preFontSizeTv.setText(size + "倍");
            currentPreFontSize = size;

            saveSharedPreferences(context, IBOOKEREDITER_SET_NAME, Context.MODE_PRIVATE, IEEDITVIEW_WEBVIEW_FONTSIZE, size);
        }
        if (currentPreFontSize < 1)
            currentPreFontSize = 1;
        else if (currentPreFontSize > 5)
            currentPreFontSize = 5;
    }

    /**
     * 保存数据
     **/
    private boolean saveSharedPreferences(Context context, String name, int mode, String key, Object obj) {
        if (mode != Context.MODE_PRIVATE && mode != Context.MODE_APPEND && mode != MODE_MULTI_PROCESS)
            return false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, mode);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (obj instanceof String)
            editor.putString(key, obj.toString());
        if (obj instanceof Boolean)
            editor.putBoolean(key, (Boolean) obj);
        if (obj instanceof Integer)
            editor.putInt(key, (Integer) obj);
        if (obj instanceof Float)
            editor.putFloat(key, (Float) obj);
        if (obj instanceof Long)
            editor.putLong(key, (Long) obj);

        return editor.commit();
    }

}
