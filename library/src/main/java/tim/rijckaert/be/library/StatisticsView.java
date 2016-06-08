package tim.rijckaert.be.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tim on 08.06.16.
 */
public class StatisticsView extends ViewGroup {

    public static final String TAG = "StatisticsView";
    private static final int DEFAULT_TEXT_SIZE = 12;

    //<editor-fold desc="Attributes">
    private final int homeTeamColor;
    private final int awayTeamColor;
    private final int indifferentColor;
    private final int fineLineColor;
    private final boolean isAnimated;
    //</editor-fold>

    //<editor-fold desc="Paint">
    private Paint valuePaint;
    private Paint fineLinePaint;
    private Paint homeValuePaint;
    private Paint awayValuePaint;
    private Paint labelPaint;
    //</editor-fold>

    //<editor-fold desc="Chaining Constructors">
    public StatisticsView(final Context context) {
        this(context, null);
    }

    public StatisticsView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(final Context context,
                          @ColorInt final int colorHomeTeam,
                          @ColorInt final int awayTeamColor,
                          @ColorInt final int indifferentColor,
                          @ColorInt final int fineLineColor,
                          final boolean isAnimated) {
        super(context);
        this.homeTeamColor = colorHomeTeam;
        this.awayTeamColor = awayTeamColor;
        this.indifferentColor = indifferentColor;
        this.fineLineColor = fineLineColor;
        this.isAnimated = isAnimated;

        initViews();
    }

    public StatisticsView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(R.styleable.StatisticsView);
        this.homeTeamColor = obtainStyledAttributes.getColor(R.styleable.StatisticsView_colorHomeTeam, Color.BLACK);
        this.awayTeamColor = obtainStyledAttributes.getColor(R.styleable.StatisticsView_colorAwayTeam, Color.GRAY);
        this.indifferentColor = obtainStyledAttributes.getColor(R.styleable.StatisticsView_colorIndifferent, Color.GRAY);
        this.fineLineColor = obtainStyledAttributes.getColor(R.styleable.StatisticsView_colorFineLine, Color.GRAY);
        this.isAnimated = obtainStyledAttributes.getBoolean(R.styleable.StatisticsView_animated, true);
        obtainStyledAttributes.recycle();

        initViews();
    }
    //</editor-fold>

    private void initViews() {
        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setTextSize(getSizeFromPx(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE));
        valuePaint.setColor(Color.BLACK);
        valuePaint.setTextAlign(Paint.Align.LEFT);

        labelPaint = valuePaint;
        labelPaint.setTextAlign(Align.CENTER);

        fineLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fineLinePaint.setColor(fineLineColor);

        homeValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        homeValuePaint.setColor(homeTeamColor);

        awayValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        awayValuePaint.setColor(awayTeamColor);
    }

    private float getSizeFromPx(final int typedValue, final int pixelSize) {
        return TypedValue.applyDimension(typedValue, pixelSize, getResources().getDisplayMetrics());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // TODO: 08.06.16 start animation
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {

        final TextView child = new TextView(getContext());
        child.setText("This is jut a test!");
        addViewInLayout(child, 0, new ViewGroup.LayoutParams(100, 100));

        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // TODO: 08.06.16 stop animations
    }

    //<editor-fold desc="LayoutParams">
    public static class LayoutParams extends ViewGroup.LayoutParams {

        private final int width;
        private final int height;

        public LayoutParams(final int width, final int height) {
            super(width, height);
            this.width = width;
            this.height = height;
        }

        private int getWidth() {
            return width;
        }

        private int getHeight() {
            return height;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Saved Instance">
//    static class SavedInstanceState extends BaseSavedState {
//
//        public SavedInstanceState(final Parcel source) {
//            super(source);
//        }
//
//        public SavedInstanceState(final Parcelable superState) {
//            super(superState);
//        }
//    }
    //</editor-fold>
}
