package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * 自定义可以缩放的ImageView
 *
 * @author 邹峰立
 */
public class IbookerEditorScaleImageView extends AppCompatImageView implements OnGlobalLayoutListener, OnScaleGestureListener, OnTouchListener {
    private boolean mOnce = false; // 是否为第一次加载
    /**
     * 初始化时缩放值，也是最小缩放值
     */
    private float mInitScale;
    /**
     * 双击时，放大达到的值
     */
    private float mMidScale;
    /**
     * 放大的最大值
     */
    private float mMaxScale;

    private Matrix mScaleMatrix; // 缩放或平移图片
    private ScaleGestureDetector mScaleGestureDetector; // 捕获多指触碰比例
    // 自由移动
    /**
     * 记录上一次多点触碰的数量
     */
    private int mLastPointerCount;
    private float mLastX;
    private float mLastY;
    private boolean isCanDrag; // 判断是否移动
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;
    // 双击放大和缩小
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;

    /**
     * 三种构造方法
     */
    public IbookerEditorScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public void onLongPress(MotionEvent e) {// 长按事件监听
                        super.onLongPress(e);
                        if (onMyLongClickListener != null)
                            onMyLongClickListener.onMyLongClick(IbookerEditorScaleImageView.this);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {// 单击事件监听
                        if (onMyClickListener != null)
                            onMyClickListener.onMyClick(IbookerEditorScaleImageView.this);
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {// 双击事件监听
                        if (isAutoScale) {
                            return true;
                        }

                        float x = e.getX();
                        float y = e.getY();

                        if (getScale() < mMidScale) {
                            // mScaleMatrix.postScale(mMidScale / getScale(),
                            // mMidScale / getScale(), x, y);
                            // setImageMatrix(mScaleMatrix);
                            postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                            isAutoScale = true;
                        } else {
                            // mScaleMatrix.postScale(mInitScale / getScale(),
                            // mInitScale / getScale(), x, y);
                            // setImageMatrix(mScaleMatrix);
                            postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                            isAutoScale = true;
                        }
                        return true;
                    }
                });
    }

    public IbookerEditorScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorScaleImageView(Context context) {
        this(context, null);
    }

    /**
     * 自动放大与缩小
     *
     * @author 邹峰立
     */
    private class AutoScaleRunnable implements Runnable {
        private float mTargetScale; // 缩放的目标值
        /**
         * 缩放的中心点
         */
        private float x;
        private float y;
        // 梯度值
        private final float BIGGER = 1.04f;
        private final float SMALL = 0.93f;
        private float tempScale;

        AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tempScale = BIGGER;
            }
            if (getScale() > mTargetScale) {
                tempScale = SMALL;
            }
        }

        @Override
        public void run() {
            // 进行过缩放
            mScaleMatrix.postScale(tempScale, tempScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();
            if ((tempScale > 1.0f && currentScale < mTargetScale) || (tempScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else { // 设置成目标值
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }

    }

    /**
     * 初始化图片，获取imageView加载完成之后的图片，OnGlobalLayoutListener
     */
    @Override
    public void onGlobalLayout() {
        // 只有当全局布局完成之后进行调用
        if (!mOnce) {
            // 得到控件的宽和高
            float width = getWidth();
            float height = getHeight();

            // 得到我们的图片，以及图片的宽和高
            Drawable d = getDrawable();
            if (d == null)
                return;
            float dw = d.getIntrinsicWidth();
            float dh = d.getIntrinsicHeight();

            // 对比图片的高度和宽度和控件的宽度和高度
            float scale = 1.0f; // 缩放比例
            if (dw > width && dh < height) { // 图片宽度大于控件的宽度，高度小于控件的高度，缩小
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw < width) { // 图片高度大于控件的宽度，宽度小于控件的高度，缩小
                scale = height * 1.0f / dh;
            }
            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            /**
             * 得到一些縮放比例
             */
            mInitScale = scale;
            mMidScale = 2.0f * scale;
            mMaxScale = 4.0f * scale;
            /**
             * 将图片移动到当前控件中心位置
             */
            float dx = getWidth() / 2 - dw / 2; // x轴上偏移量
            float dy = getHeight() / 2 - dh / 2; // y轴上偏移量
            // 之后进行缩放和移动
            if (mScaleMatrix != null) {
                mScaleMatrix.postTranslate(dx, dy); // 平移
                mScaleMatrix.postScale(mInitScale, mInitScale, getWidth() / 2, getHeight() / 2); // 缩放
                setImageMatrix(mScaleMatrix);
            }

            mOnce = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        // 当View从window上显示的时候调用
        super.onAttachedToWindow();
        // 注册onGlobalLayout
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        // 当View从window上消费的时候调用
        super.onDetachedFromWindow();
        // 消费onGlobalLayout
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取当前缩放值
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 下面三个方法实现多点触碰 缩放区间 mInitScale maxScale
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        // 缩放进行中
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor(); // 想要缩放的比例，<1.0f代表想要缩小，>1.0f代表想要放大

        if (getDrawable() == null)
            return true;
        // 缩放范围控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
        }
        // 实现缩放
        mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

        checkBorderAndCenterWhenScale();

        setImageMatrix(mScaleMatrix);
        return true;
    }

    /**
     * 获得图片放大或缩小以后的宽和高，以及l，t,r,b
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 在缩放的时候进行边界控制和位置控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        // X和Y的偏移量，水平和竖直
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();
        // 缩放时，进行边界检测，防止出现白边
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        // 如果图片的宽度和高度小于控件的宽和高，居中
        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;
        }
        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector arg0) {
        // 缩放开始，一定要return true
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector arg0) {
        // 缩放结束

    }

    /**
     * 触摸事件监听
     */
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event); // 将event事件交给mScaleGestureDetector处理
        /**
         * 实现图片移动
         */
        // 获取中心点位置
        float x = 0;
        float y = 0;
        // 多触点数量
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            // 只有一个触点时才能移动
            isCanDrag = pointerCount == 1;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF(); // 获取rectF
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 按下
                // 处理按下事件
                if (rectF.height() > getHeight() + 0.01 || rectF.width() > getWidth() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true); // 取消父控件拦截
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.height() > getHeight() + 0.01 || rectF.width() > getWidth() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true); // 取消父控件拦截
                }
                if (isCanDrag) {
                    // 如果宽度小于控件宽度，不允许横向移动，如果高度小于控件高度，不允许竖向移动
                    if (getDrawable() != null) {
                        // 记录移动距离
                        float dx = x - mLastX;
                        float dy = y - mLastY;

                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当移动时，进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        float width = getWidth();
        float height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * ImageView单击事件，对外接口
     */
    private OnMyClickListener onMyClickListener;

    public interface OnMyClickListener {
        void onMyClick(View v);
    }

    public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
        this.onMyClickListener = onMyClickListener;
    }

    /**
     * ImageView长按事件监听
     */
    private OnMyLongClickListener onMyLongClickListener;

    public interface OnMyLongClickListener {
        void onMyLongClick(View v);
    }

    public void setOnMyLongClickListener(OnMyLongClickListener onMyLongClickListener) {
        this.onMyLongClickListener = onMyLongClickListener;
    }
}
