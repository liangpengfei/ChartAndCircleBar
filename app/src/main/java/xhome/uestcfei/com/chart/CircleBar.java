package xhome.uestcfei.com.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Email : luckyliangfei@gmail.com
 * Created by fei on 16/2/25.
 */
public class CircleBar extends View {
    public CircleBar(Context context) {
        super(context);
        init();
    }

    public CircleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int percent;
    private int mProgessColor;
    private String mCustomText;//名称

    public void setPercent(int percent) {
        this.percent = percent;
        if (isShown()) {
            curTime=0;
            this.invalidate();
        }
    }

    public void setProgessColor(int mProgessColor) {
        this.mProgessColor = mProgessColor;
        if (isShown()) {
            this.invalidate();
        }
    }

    /**
     * 设置名称
     * @param mCustomText
     */
    public void setCustomText(String mCustomText) {
        this.mCustomText = mCustomText;
    }

    DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    private int duration = 10;
    private int curTime = 0;

    private Handler mHandler = new Handler();
    private Runnable mAnimation = new Runnable() {
        @Override
        public void run() {
            if (curTime < duration) {
                curTime++;
                CircleBar.this.invalidate();
            }
        }
    };

    private void init() {
        percent = 0;
        mProgessColor= Color.rgb(95, 112, 72);
        mCustomText="HOME";
    }

    //各种画笔
    private Paint paintBar = new Paint();
    private Paint paintText = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mWidth = getWidth();
        float mHeight = getHeight();

        //进度条画笔
        paintBar.setStrokeWidth(4);
        paintBar.setStyle(Paint.Style.STROKE);
        paintBar.setAntiAlias(true);
        paintBar.setColor(Color.WHITE);
        paintBar.setAlpha(80);

        //字体画笔
        paintText.setTextSize(20);
        paintText.setColor(Color.WHITE);
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        //圆的半径
        float radius = Math.min(mWidth, mHeight) / 2 - 10;

        //画进度条
        canvas.save();
        canvas.clipRect(0, 0, mWidth, mHeight / 2 + radius - textHeight * 3 / 4);

        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, paintBar);

        float theta_offset = (float) Math.acos((radius - textHeight / 2) / radius) + 90;
        float theta_full = 360 - 2 * (theta_offset - 90);
        float thetaProcess = mDecelerateInterpolator.getInterpolation(1.0f * curTime / duration) * percent * theta_full / 100;

        paintBar.setColor(mProgessColor);
        canvas.drawArc(new RectF(mWidth / 2 - radius, mHeight / 2 - radius, mWidth / 2 + radius, mHeight / 2 + radius), theta_offset, thetaProcess, false, paintBar);

        canvas.restore();

        //写名称
        paintText.setTextSize(20);
        fontMetrics = paintText.getFontMetrics();
        float textBaseLineOffset = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(mCustomText, mWidth / 2, mHeight / 2 + radius - textHeight / 2 + textBaseLineOffset, paintText);

        //写百分号
        paintText.setTextSize(mHeight * 1 / 8);
        fontMetrics = paintText.getFontMetrics();
        textBaseLineOffset = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText("%", mWidth / 2, mHeight / 2 + radius / 3 + textBaseLineOffset, paintText);

        //写百分比
        paintText.setTextSize(mHeight * 3 / 8);
        canvas.drawText("" + (int)(percent*mDecelerateInterpolator.getInterpolation(1.0f * curTime / duration)), mWidth / 2, mHeight / 2, paintText);

        mHandler.postDelayed(mAnimation, 20);
    }
}
