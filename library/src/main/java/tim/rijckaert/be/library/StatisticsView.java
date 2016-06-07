package tim.rijckaert.be.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by trijckaert on 08.06.16.
 */
public class StatisticsView extends View {

    public StatisticsView(final Context context) {
        this(context, null);
    }

    public StatisticsView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StatisticsView,
                0, 0);
    }
}
