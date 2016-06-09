package tim.rijckaert.be.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by tim on 08.06.16.
 */
public class SegmentedBarView extends View {

    private Stat stat;

    //<editor-fold desc="Constants">
    private static final boolean DEFAULT_ABSOLUTE = false;
    private static final int DEFAULT_VALUE = 0;
    private static final boolean DEFAULT_IS_ANIMATED = false;

    private static final int FINE_BAR_HEIGHT = 3;
    private static final int BAR_HEIGHT = 8;
    private final int SPACE_BETWEEN_GUIDES = 15;
    //</editor-fold>

    //<editor-fold desc="Attributes">
    private final int homeTeamColor;
    private final int awayTeamColor;
    private final int indifferentColor;
    private final int fineLineColor;
    private final boolean isAnimated;
    //</editor-fold>

    //<editor-fold desc="Paint">
    private Paint fineLinePaint;
    private Paint homeRectPaint;
    private Paint awayRectPaint;
    private Paint indifferentPaint;
    //</editor-fold>

    //<editor-fold desc="Chaining Constructors">
    public SegmentedBarView(final Context context) {
        this(context, null);
    }

    public SegmentedBarView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SegmentedBarView, 0, 0);
        this.homeTeamColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorHomeTeam, Color.BLACK);
        this.awayTeamColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorAwayTeam, Color.GRAY);
        this.indifferentColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorIndifferent, Color.GRAY);
        this.fineLineColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorFineLine, Color.GRAY);
        this.isAnimated = obtainStyledAttributes.getBoolean(R.styleable.SegmentedBarView_animated, DEFAULT_IS_ANIMATED);

        obtainStyledAttributes.recycle();
        initViews();
    }
    //</editor-fold>

    private void initViews() {
        fineLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fineLinePaint.setColor(fineLineColor);

        indifferentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indifferentPaint.setColor(indifferentColor);

        homeRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        homeRectPaint.setColor(homeTeamColor);

        awayRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        awayRectPaint.setColor(awayTeamColor);
    }

    public void updateView(@NonNull final Stat stat) {
        this.stat = stat;
        invalidate();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int completeWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        final int halfCompleteWidth = completeWidth / 2 - SPACE_BETWEEN_GUIDES;

        drawGuideLines(canvas, halfCompleteWidth);

        final RectF homeRectF = getRectF(true, halfCompleteWidth);
        final RectF awayRectF = getRectF(false, halfCompleteWidth);
        drawSegmentedBarsValues(canvas, homeRectF, awayRectF);
    }

    private void drawSegmentedBarsValues(final Canvas canvas, final RectF homeRectF, final RectF awayRectF) {
        if (homeRectF != null) {
            canvas.drawRect(homeRectF, getAppropriatePaint(true));
        }

        if (awayRectF != null) {
            canvas.drawRect(awayRectF, getAppropriatePaint(false));
        }
    }

    private void drawGuideLines(final Canvas canvas, final int halfCompleteWidth) {
        final float barCenter = getBarCenter();
        final float halfBarHeight = getSizeInPixels(TypedValue.COMPLEX_UNIT_DIP, FINE_BAR_HEIGHT) / 2;

        final RectF leftRect = new RectF(
                getPaddingLeft(),
                barCenter - halfBarHeight,
                halfCompleteWidth - (SPACE_BETWEEN_GUIDES / 2),
                barCenter + halfBarHeight
        );

        final RectF rightRect = new RectF(
                getPaddingLeft() + halfCompleteWidth + (SPACE_BETWEEN_GUIDES / 2),
                barCenter - halfBarHeight,
                getWidth() - getPaddingRight(),
                barCenter + halfBarHeight
        );

        canvas.drawRect(leftRect, fineLinePaint);
        canvas.drawRect(rightRect, fineLinePaint);
    }

    //<editor-fold desc="Helper Methods">
    private float getBarCenter() {
        return getHeight() - getPaddingTop() - getPaddingBottom() - (getSizeInPixels(TypedValue.COMPLEX_UNIT_DIP, BAR_HEIGHT)) / 2;
    }

    private Paint getAppropriatePaint(final boolean isHomeTeam) {
        Paint appropriatePaint = isHomeTeam ? homeRectPaint : awayRectPaint;
        if (stat.getHomeTeamValue() == stat.getAwayTeamValue()) {
            appropriatePaint = indifferentPaint;
        }
        return appropriatePaint;
    }

    private RectF getRectF(final boolean isHomeTeam, final int halfCompleteWidth) {
        RectF rect = null;

        final int top = 0;
        final int bottom = getHeight() - getPaddingBottom() - getPaddingTop();

        final double valueBarWidth = getValueBarWidth(isHomeTeam, halfCompleteWidth);
        if (valueBarWidth != 0) {
            final double left = getLeft(isHomeTeam, halfCompleteWidth, valueBarWidth);
            final double right = getRight(isHomeTeam, halfCompleteWidth, valueBarWidth);

            rect = new RectF((float) left, top, (float) right, bottom);
        }
        return rect;
    }

    private double getLeft(final boolean isHomeTeam, final int halfCompleteWidth, final double valueBarWidth) {
        double left;
        if (isHomeTeam) {
            left = halfCompleteWidth - (SPACE_BETWEEN_GUIDES / 2) - valueBarWidth;
        } else {
            left = halfCompleteWidth + (SPACE_BETWEEN_GUIDES / 2);
        }
        return left;
    }

    private double getRight(final boolean isHomeTeam, final int halfCompleteWidth, final double valueBarWidth) {
        double right;
        if (isHomeTeam) {
            right = halfCompleteWidth - (SPACE_BETWEEN_GUIDES / 2);
        } else {
            right = halfCompleteWidth + (SPACE_BETWEEN_GUIDES / 2) + valueBarWidth;
        }
        return right;
    }

    private double getValueBarWidth(final boolean isHomeTeam, final int halfCompleteWidth) {
        double valueBarWidth = 0;
        final double factor = getFactor(isHomeTeam);
        if (factor != 0) {
            valueBarWidth = halfCompleteWidth / factor;
        }
        return valueBarWidth;
    }

    private double getFactor(final boolean isHomeTeam) {
        double factor = 1.;
        switch (stat.getUnit()) {
            case PERCENTAGE:
                factor = isHomeTeam ?
                        100d / stat.getHomeTeamValue() :
                        100d / stat.getAwayTeamValue();
                break;
            case ABSOLUTE:
                final double sumOfValues = (double) stat.getSum();
                factor = isHomeTeam ?
                        sumOfValues / stat.getHomeTeamValue() :
                        sumOfValues / stat.getAwayTeamValue();
                break;
        }
        return factor;
    }

    private int measureHeight(int measureSpec) {
        int size = getPaddingTop() + getPaddingBottom();
        size += getSizeInPixels(TypedValue.COMPLEX_UNIT_DIP, BAR_HEIGHT);
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private float getSizeInPixels(final int typedValueType, final int typedValue) {
        return TypedValue.applyDimension(typedValueType, typedValue, getResources().getDisplayMetrics());
    }
    //</editor-fold>
}