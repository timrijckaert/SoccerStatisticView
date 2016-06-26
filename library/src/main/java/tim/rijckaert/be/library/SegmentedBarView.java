package tim.rijckaert.be.library;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
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
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by tim on 08.06.16.
 */
public class SegmentedBarView extends View {

    //<editor-fold desc="Constants">
    private static final boolean DEFAULT_IS_ANIMATED = true;
    private static final int FINE_BAR_HEIGHT = 3;
    private static final int BAR_HEIGHT = 8;
    private static final int SPACE_BETWEEN_GUIDES = 15;
    private static final long ANIMATION_DURATION = 750l;
    private final int homeTeamColor;
    //<editor-fold desc="Attributes">

    //</editor-fold>
    private final int awayTeamColor;
    private final int fineLineColor;
    private final boolean isAnimated;
    private Stat stat;
    //</editor-fold>

    //<editor-fold desc="Paint">
    private Paint fineLinePaint;
    private Paint homeRectPaint;
    private Paint awayRectPaint;
    //</editor-fold>

    private ValueAnimator homeTeamValueAnimator;
    private ValueAnimator awayTeamValueAnimator;

    private RectF homeRectF;
    private RectF awayRectF;

    //<editor-fold desc="Chaining Constructors">
    public SegmentedBarView(final Context context) {
        this(context, null);
    }

    public SegmentedBarView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SegmentedBarView, 0, 0);
        this.homeTeamColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorHomeTeam, Color.BLACK);
        this.awayTeamColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorAwayTeam, Color.GRAY);
        this.fineLineColor = obtainStyledAttributes.getColor(R.styleable.SegmentedBarView_colorFineLine, Color.GRAY);
        this.isAnimated = obtainStyledAttributes.getBoolean(R.styleable.SegmentedBarView_animated, DEFAULT_IS_ANIMATED);

        obtainStyledAttributes.recycle();
        initViews();
    }
    //</editor-fold>

    private void initViews() {
        fineLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fineLinePaint.setColor(fineLineColor);

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

        drawRect(true, halfCompleteWidth);
        drawRect(false, halfCompleteWidth);
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
        return isHomeTeam ? homeRectPaint : awayRectPaint;
    }

    private void drawRect(final boolean isHomeTeam, final int halfCompleteWidth) {
        final int top = 0;
        final int bottom = getHeight() - getPaddingBottom() - getPaddingTop();

        final double valueBarWidth = getValueBarWidth(isHomeTeam, halfCompleteWidth);
        if (valueBarWidth != 0) {
            final double left = getLeft(isHomeTeam, halfCompleteWidth, valueBarWidth);
            final double right = getRight(isHomeTeam, halfCompleteWidth, valueBarWidth);

            setRectF(isHomeTeam, left, top, right, bottom);
        }
    }

    private void setRectF(final boolean isHomeTeam, final double left, final int top, final double right, final int bottom) {
        if (isAnimated) {
            startDrawingAnimation(isHomeTeam, left, top, right, bottom);
        } else {
            final RectF rectF = new RectF((float) left, top, (float) right, bottom);
            if (isHomeTeam) {
                homeRectF = rectF;
            } else {
                awayRectF = rectF;
            }
        }
    }

    private void startDrawingAnimation(final boolean isHomeTeam, final double left, final int top, final double right, final int bottom) {
        if (isHomeTeam && homeTeamValueAnimator == null) {
            homeTeamValueAnimator = getAnimation(true, left, right, top, bottom);
            homeTeamValueAnimator.start();
        }

        if (!isHomeTeam && awayTeamValueAnimator == null) {
            awayTeamValueAnimator = getAnimation(false, left, right, top, bottom);
            awayTeamValueAnimator.start();
        }
    }

    private ValueAnimator getAnimation(final boolean isHomeTeam, final double left, final double right, final int top, final int bottom) {
        ValueAnimator valueAnimator = isHomeTeam ?
                ValueAnimator.ofFloat((float) right, (float) left) :
                ValueAnimator.ofFloat((float) left, (float) right);
        valueAnimator.setDuration(ANIMATION_DURATION);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new SegmentedAnimatorUpdateListener(isHomeTeam, left, right, top, bottom));
        return valueAnimator;
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
        if (factor > 0) {
            valueBarWidth = halfCompleteWidth / factor;
        }
        return valueBarWidth;
    }

    private double getFactor(final boolean isHomeTeam) {
        double factor = 1.;
        final int homeTeamValue = stat.getHomeTeamValue();
        final int awayTeamValue = stat.getAwayTeamValue();

        try {
            switch (stat.getUnit()) {
                case PERCENTAGE:
                    factor = isHomeTeam ?
                            100d / homeTeamValue :
                            100d / awayTeamValue;
                    break;
                case ABSOLUTE:
                    final double sumOfValues = (double) stat.getSum();
                    factor = isHomeTeam ?
                            sumOfValues / homeTeamValue :
                            sumOfValues / awayTeamValue;
                    break;
            }
        } catch (Exception e) {
            factor = 1.;
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

//<editor-fold desc="Animator Update Listener">
private class SegmentedAnimatorUpdateListener implements AnimatorUpdateListener {
    private final boolean isHomeTeam;
    private final double left;
    private final double right;
    private final int top;
    private final int bottom;

    public SegmentedAnimatorUpdateListener(final boolean isHomeTeam, final double left, final double right, final int top, final int bottom) {
        this.isHomeTeam = isHomeTeam;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void onAnimationUpdate(final ValueAnimator animation) {
        if (isHomeTeam) {
            final float animatedLeft = (float) animation.getAnimatedValue();
            homeRectF = new RectF(animatedLeft, top, (float) right, bottom);
            invalidate();
        } else {
            final float animatedRight = (float) animation.getAnimatedValue();
            awayRectF = new RectF((float) left, top, animatedRight, bottom);
            invalidate();
        }
    }
}
//</editor-fold>
}