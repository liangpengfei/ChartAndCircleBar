package xhome.uestcfei.com.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Arrays;

/**
 * Email : luckyliangfei@gmail.com
 * Created by fei on 16/2/25.
 */
public class Chart extends View {

    public Chart(Context context) {
        super(context);
        init();
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public enum Performance {
        WINS(0),
        DRAW(1),
        LOSE(2);

        public int type;

        Performance(int type) {
            this.type = type;
        }
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
                Chart.this.invalidate();
            }
        }
    };

    private Performance[] mPerformance_1, mPerformance_2;
    private int mLineColor1, mLineColor2;//两条折线的颜色

    public void setPerformances(Performance[] performance1, Performance[] performance2) {
        if (performance1 == null) {
            performance1 = new Performance[0];
        }
        if (performance2 == null) {
            performance2 = new Performance[0];
        }

        mPerformance_1 = Arrays.copyOf(performance1, performance1.length > 8 ? 8 : performance1.length);
        mPerformance_2 = Arrays.copyOf(performance2, performance2.length > 8 ? 8 : performance1.length);
        if (isShown()) {
            curTime = 0;
            this.invalidate();
        }
    }

    /**
     * 设置折线1的颜色
     *
     * @param mLineColor1
     */
    public void setLineColor1(int mLineColor1) {
        this.mLineColor1 = mLineColor1;
    }

    /**
     * 设置折线2的颜色
     *
     * @param mLineColor2
     */
    public void setLineColor2(int mLineColor2) {
        this.mLineColor2 = mLineColor2;
    }

    private void init() {
        mPerformance_1 = new Performance[8];
        mPerformance_1[0] = Performance.WINS;
        mPerformance_1[1] = Performance.DRAW;
        mPerformance_1[2] = Performance.WINS;
        mPerformance_1[3] = Performance.LOSE;
        mPerformance_1[4] = Performance.DRAW;
        mPerformance_1[5] = Performance.DRAW;
        mPerformance_1[6] = Performance.WINS;
        mPerformance_1[7] = Performance.LOSE;

        mPerformance_2 = new Performance[8];
        mPerformance_2[0] = Performance.WINS;
        mPerformance_2[1] = Performance.LOSE;
        mPerformance_2[2] = Performance.WINS;
        mPerformance_2[3] = Performance.WINS;
        mPerformance_2[4] = Performance.DRAW;
        mPerformance_2[5] = Performance.WINS;
        mPerformance_2[6] = Performance.WINS;
        mPerformance_2[7] = Performance.DRAW;

        mLineColor1 = Color.rgb(95, 112, 72);
        mLineColor2 = Color.rgb(69, 91, 136);
    }

    //各种画笔
    private Paint paintText = new Paint();
    private Paint paintLine = new Paint();
    private Paint paintPoint = new Paint(); //圆点画笔
    private Paint paintArea = new Paint();//画面积
    private float circleRadius = 4;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mWidth = getWidth();
        float mHeigh = getHeight();

        float textWide = mWidth / 8;
        float left_offset = 10;
        float chartWide = mWidth - textWide - left_offset;

        float[] mLineYs = new float[]{mHeigh / 8, mHeigh / 2, mHeigh * 7 / 8};
        float[] mLineXs = new float[]{
                textWide + left_offset + chartWide * 0 / 8,
                textWide + left_offset + chartWide * 1 / 8,
                textWide + left_offset + chartWide * 2 / 8,
                textWide + left_offset + chartWide * 3 / 8,
                textWide + left_offset + chartWide * 4 / 8,
                textWide + left_offset + chartWide * 5 / 8,
                textWide + left_offset + chartWide * 6 / 8,
                textWide + left_offset + chartWide * 7 / 8,
        };

        //写入分类名称
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.WHITE);
        paintText.setAlpha(126);
        paintText.setTextSize(18);
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
        float textBaseLineOffset = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText("WINS", textWide / 2, mLineYs[0] + textBaseLineOffset, paintText);
        canvas.drawText("DRAW", textWide / 2, mLineYs[1] + textBaseLineOffset, paintText);
        canvas.drawText("LOSE", textWide / 2, mLineYs[2] + textBaseLineOffset, paintText);

        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.WHITE);
        paintLine.setAlpha(80);

        //画网格
        paintLine.setStrokeWidth(1);
        canvas.drawLine(textWide, mHeigh / 8, mWidth, mLineYs[0], paintLine);
        canvas.drawLine(textWide, mHeigh / 2, mWidth, mLineYs[1], paintLine);
        canvas.drawLine(textWide, mHeigh * 7 / 8, mWidth, mLineYs[2], paintLine);
        canvas.drawLine(mLineXs[0], 0, mLineXs[0], mHeigh, paintLine);
        canvas.drawLine(mLineXs[1], 0, mLineXs[1], mHeigh, paintLine);
        canvas.drawLine(mLineXs[2], 0, mLineXs[2], mHeigh, paintLine);
        canvas.drawLine(mLineXs[3], 0, mLineXs[3], mHeigh, paintLine);
        canvas.drawLine(mLineXs[4], 0, mLineXs[4], mHeigh, paintLine);
        canvas.drawLine(mLineXs[5], 0, mLineXs[5], mHeigh, paintLine);
        canvas.drawLine(mLineXs[6], 0, mLineXs[6], mHeigh, paintLine);
        canvas.drawLine(mLineXs[7], 0, mLineXs[7], mHeigh, paintLine);

        //画折线图
        paintPoint.setStyle(Paint.Style.STROKE);
        paintPoint.setAntiAlias(true);
        paintPoint.setColor(Color.WHITE);

        paintArea.setStyle(Paint.Style.FILL);
        paintArea.setAntiAlias(true);
        paintArea.setColor(Color.WHITE);
        paintArea.setAlpha(20);

        Path path = new Path();

        float AnimCoaf = mDecelerateInterpolator.getInterpolation(1.0f * curTime / duration);
        paintLine.setColor(mLineColor1);
        for (int i = 0; i < mPerformance_1.length - 1; i++) {
            if (curTime == duration) {
                path.reset();
                path.moveTo(mLineXs[i], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_1[i].type]) * AnimCoaf);
                path.lineTo(mLineXs[i + 1], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_1[i + 1].type]) * AnimCoaf);
                path.lineTo(mLineXs[i + 1], mLineYs[2]);
                path.lineTo(mLineXs[i], mLineYs[2]);
                path.close();
                canvas.drawPath(path, paintArea);
            }

            paintLine.setStrokeWidth(5);
            canvas.drawLine(mLineXs[i], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_1[i].type]) * AnimCoaf,
                    mLineXs[i + 1], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_1[i + 1].type]) * AnimCoaf, paintLine);
            canvas.drawCircle(mLineXs[i], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_1[i].type]) * AnimCoaf, circleRadius, paintPoint);
        }
        canvas.drawCircle(mLineXs[mPerformance_1.length - 1], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_1[mPerformance_1.length - 1].type]) * AnimCoaf, circleRadius, paintPoint);
        paintLine.setColor(mLineColor2);
        for (int i = 0; i < mPerformance_2.length - 1; i++) {
            if (curTime == duration) {
                path.reset();
                path.moveTo(mLineXs[i], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_2[i].type]) * AnimCoaf);
                path.lineTo(mLineXs[i + 1], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_2[i + 1].type]) * AnimCoaf);
                path.lineTo(mLineXs[i + 1], mLineYs[2]);
                path.lineTo(mLineXs[i], mLineYs[2]);
                path.close();
                canvas.drawPath(path, paintArea);
            }

            paintLine.setStrokeWidth(5);
            canvas.drawLine(mLineXs[i], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_2[i].type]) * AnimCoaf,
                    mLineXs[i + 1], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_2[i + 1].type]) * AnimCoaf, paintLine);
            canvas.drawCircle(mLineXs[i], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_2[i].type]) * AnimCoaf, circleRadius, paintPoint);
        }
        canvas.drawCircle(mLineXs[mPerformance_2.length - 1], mLineYs[2] - (mLineYs[2] - mLineYs[mPerformance_2[mPerformance_2.length - 1].type]) * AnimCoaf, circleRadius, paintPoint);
        mHandler.postDelayed(mAnimation, 20);
    }
}
