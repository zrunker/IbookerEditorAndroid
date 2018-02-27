# IbookerEditorAndroid
书客编辑器安卓Java版

>作者：邹峰立，微博：zrunker，邮箱：zrunker@yahoo.com，微信公众号：书客创作，个人平台：www.ibooker.cc。

>本文选自[书客创作](www.ibooker.cc)平台第130篇文章。[阅读原文](http://www.ibooker.cc/article/130/detail) ，  [书客编辑器安卓Java版 - 体验版下载](https://www.pgyer.com/QUOX)

![书客创作](http://upload-images.jianshu.io/upload_images/3480018-3eaa10cda787aaed.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

书客编辑器是一款基于Markdown标记语言的开源的富文本编辑器，它以简易的操作界面和强大的功能深受广大开发者的喜爱。正如官方所说：现在的版本不一定是最好的版本，却是最好的开源版本。官方地址：[editor.ibooker.cc](editor.ibooker.cc)。

下面针对书客编辑器安卓Java版，进行详解说明。

### 效果图

在进行讲解之前，首先看一下书客编辑器安卓版的效果图：

![书客编辑器安卓版效果图](http://upload-images.jianshu.io/upload_images/3480018-f429940174464dda.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 一、引入资源

引入书客编辑器安卓Java版的方式有很多，这里主要提供两种方式：

1、在build.gradle文件中添加以下代码：
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
	compile 'com.github.zrunker:IbookerEditorAndroid:v1.0.1'
}
```
2、在maven文件中添加以下代码：
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
```
<dependency>
	<groupId>com.github.zrunker</groupId>
	<artifactId>IbookerEditorAndroid</artifactId>
	<version>v1.0.1</version>
</dependency>
```

### 二、使用

书客编辑器安卓版简易所在就是只需要简单引入资源之后，可以直接进行使用。因为书客编辑器安卓版不仅仅提供了功能实现，还提供了界面。所以使用过程中，连界面绘制都不用了。

**界面分析**

书客编辑器安卓版界面大致分为三个部分，即编辑器顶部，内容区（编辑区+预览区）和底部（工具栏）。

![书客编辑器安卓-布局轮廓图](http://upload-images.jianshu.io/upload_images/3480018-65cf2b785fde4990.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

首先在布局文件中引入书客编辑器安卓版控件，如布局文件为activity_main.xml，只需要在该文件内添加以下代码即可：

```
<?xml version="1.0" encoding="utf-8"?>
<cc.ibooker.ibookereditorlib.IbookerEditorView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/ibookereditorview"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
实际上IbookerEditorView继承LinearLayout，所以它具备LinearLayout的一切功能。

### 三、功能介绍

根据轮廓图可以看出，书客编辑器安卓版布局只有三个部分，所以关于书客编辑器安卓版功能模块也就分三个部分对外提供使用，即修改哪一个布局模块就是对于哪一个功能模块。

**顶部功能模块**

书客编辑器安卓版顶部实际上是采用IbookerEditorTopView控件进行呈现，所以要实现顶部相关控件功能首先要获取该控件。

![书客编辑器安卓版顶部](http://upload-images.jianshu.io/upload_images/3480018-b55b3f5dfde8705b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

书客编辑器安卓版顶部界面图，从左到右分别对应返回（back），撤销（undo），重做（redo），编辑模式（edit），预览模式（preview），帮助（help），关于（about）。知道每个按钮对应的功能，所以就可以去修改或完善相关实现过程。

例如修改返回按钮一些属性，可以使用一下代码：
```
// 设置书客编辑器顶部布局相关属性
ibookerEditorView.getIbookerEditorTopView()
        .setBackImgVisibility(View.VISIBLE)
        .setBackImageResource(R.mipmap.ic_launcher);
```

当然也可以通过IbookerEditorTopView获取相关控件，然后针对该控件进行逐一处理：
```
ibookerEditorView.getIbookerEditorTopView()
        .getBackImg()
        .setVisibility(View.VISIBLE);
```
这里只是使用返回按钮进行举例说，其他按钮使用规则更返回按钮一样。

**中间功能模块**

书客编辑器安卓版中间区域又分为两个部分，分别是编辑部分和预览部分，所以要修改相关功能就要获取到相关部分的控件。其中编辑部分由IbookerEditorEditView控件进行呈现，预览部分由IbookerEditorPreView控件进行呈现。

例如修改编辑部分相关属性，可以使用如下代码：
```
// 设置书客编辑器中间布局相关属性
ibookerEditorView.getIbookerEditorVpView().getEditView()
        .setIbookerEdHint("书客编辑器")
        .setIbookerBackgroundColor(Color.parseColor("#DDDDDD"));
```
编辑部分并不是只有一个控件，所以也可以获取相关控件，然后针对特定控件进行逐一操作：
```
ibookerEditorView.getIbookerEditorVpView()
        .getEditView()
        .getIbookerEd()
        .setText("书客编辑器");
```
```
// 执行预览功能
ibookerEditorView.getIbookerEditorVpView()
        .getPreView()
        .ibookerHtmlCompile("预览内容");
```
**底部功能模块**

书客编辑器安卓版，底部为工具栏，由IbookerEditorToolView进行呈现。

工具栏一共提供了30多种功能，每一个按钮对应一个功能。各个控件分别为：
```
boldIBtn, italicIBtn, strikeoutIBtn, underlineIBtn, capitalsIBtn, 
uppercaseIBtn, lowercaseIBtn, h1IBtn, h2IBtn, 
h3IBtn, h4IBtn, h5IBtn, h6IBtn, linkIBtn, quoteIBtn, 
codeIBtn, imguIBtn, olIBtn, ulIBtn, unselectedIBtn, 
selectedIBtn, tableIBtn, htmlIBtn, hrIBtn, emojiIBtn;
```
所以要修改底部相关属性，首先要获取到IbookerEditorToolView控件，然后对该控件进行操作。
```
// 设置书客编辑器底部布局相关属性
ibookerEditorView.getIbookerEditorToolView()
        .setEmojiIBtnVisibility(View.GONE);
```
当然底部一共有30多个控件，也可以直接获取到相关控件，然后该控件进行操作，如：
```
ibookerEditorView.getIbookerEditorToolView().getEmojiIBtn().setVisibility(View.GONE);
```

**补充功能：按钮点击事件监听**

这里的按钮点击事件监听主要是针对顶部布局按钮和底部布局按钮。

顶部部分按钮点击事件监听，需要实现IbookerEditorTopView.OnTopClickListener接口，而每个按钮点击通过对应Tag来判断，具体代码如下：

```
// 顶部按钮点击事件监听
@Override
public void onTopClick(Object tag) {
    if (tag.equals(IMG_BACK)) {// 返回
    } else if (tag.equals(IBTN_UNDO)) {// 撤销
    } else if (tag.equals(IBTN_REDO)) {// 重做
    } else if (tag.equals(IBTN_EDIT)) {// 编辑
    } else if (tag.equals(IBTN_PREVIEW)) {// 预览
    } else if (tag.equals(IBTN_HELP)) {// 帮助
    } else if (tag.equals(IBTN_ABOUT)) {// 关于
    }
}
```
其中IMG_BACK、IBTN_UNDO等变量是由IbookerEditorEnum枚举类提供。

底部部分按钮点击事件监听，需要实现IbookerEditorToolView.OnToolClickListener接口，而每个按钮点击通过对应Tag来判断，具体代码如下：

```
// 工具栏按钮点击事件监听
@Override
public void onToolClick(Object tag) {
    if (tag.equals(IBTN_BOLD)) {// 加粗
    } else if (tag.equals(IBTN_ITALIC)) {// 斜体
    } else if (tag.equals(IBTN_STRIKEOUT)) {// 删除线
    } else if (tag.equals(IBTN_UNDERLINE)) {// 下划线
    } else if (tag.equals(IBTN_CAPITALS)) {// 单词首字母大写
    } else if (tag.equals(IBTN_UPPERCASE)) {// 字母转大写
    } else if (tag.equals(IBTN_LOWERCASE)) {// 字母转小写
    } else if (tag.equals(IBTN_H1)) {// 一级标题
    } else if (tag.equals(IBTN_H2)) {// 二级标题
    } else if (tag.equals(IBTN_H3)) {// 三级标题
    } else if (tag.equals(IBTN_H4)) {// 四级标题
    } else if (tag.equals(IBTN_H5)) {// 五级标题
    } else if (tag.equals(IBTN_H6)) {// 六级标题
    } else if (tag.equals(IBTN_LINK)) {// 超链接
    } else if (tag.equals(IBTN_QUOTE)) {// 引用
    } else if (tag.equals(IBTN_CODE)) {// 代码
    } else if (tag.equals(IBTN_IMG_U)) {// 图片
    } else if (tag.equals(IBTN_OL)) {// 数字列表
    } else if (tag.equals(IBTN_UL)) {// 普通列表
    } else if (tag.equals(IBTN_UNSELECTED)) {// 复选框未选中
    } else if (tag.equals(IBTN_SELECTED)) {// 复选框选中
    } else if (tag.equals(IBTN_TABLE)) {// 表格
    } else if (tag.equals(IBTN_HTML)) {// HTML
    } else if (tag.equals(IBTN_HR)) {// 分割线
    }
}
```
其中IBTN_BOLD、IBTN_ITALIC等变量是由IbookerEditorEnum枚举类提供。

[Github地址](https://github.com/zrunker/IbookerEditorAndroid/)
[阅读原文](http://www.ibooker.cc/article/130/detail)

----------
![微信公众号：书客创作](http://upload-images.jianshu.io/upload_images/3480018-aaaca8b35890252b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
