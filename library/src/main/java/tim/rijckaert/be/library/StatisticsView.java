package tim.rijckaert.be.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tim on 08.06.16.
 */
public class StatisticsView extends LinearLayout {

    //<editor-fold desc="Views">
    private TextView homeTeamTextView;
    private TextView awayTeamTextView;
    private TextView labelTextView;
    private SegmentedBarView segmentedBarView;
    //</editor-fold>

    //<editor-fold desc="Chaining Constructors">
    public StatisticsView(final Context context) {
        this(context, null);
    }

    public StatisticsView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.compound_statistics_labels, this, true);
    }
    //</editor-fold>

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        homeTeamTextView = (TextView) findViewById(R.id.statisticsView_homeTeam_tv);
        awayTeamTextView = (TextView) findViewById(R.id.statisticsView_awayTeam_tv);
        labelTextView = (TextView) findViewById(R.id.statisticsView_label_tv);
        segmentedBarView = (SegmentedBarView) findViewById(R.id.statisticsView_segmentedBarView);
    }

    public void setStat(final Stat stat) {
        checkPreconditionsAreSet(stat);
        setupViews(stat);
    }

    private void checkPreconditionsAreSet(final Stat stat) {
        if (stat == null) {
            throw new RuntimeException("Error! Stat object could not be instantiated.");
        }

        if (stat.getLabel() == null) {
            throw new RuntimeException("Error! No label was set!");
        }

        if (stat.getHomeTeamValue() < 0 || stat.getAwayTeamValue() < 0) {
            throw new RuntimeException("Error! the value should be bigger than 0");
        }

        if (stat.getUnit().equals(Stat.Unit.PERCENTAGE)) {
            if (stat.getSum() != 100) {
                throw new RuntimeException("Error! the home team value and away team value should add up to 100% if you are using the percentage unit");
            }
        }
    }

    public void setupViews(@NonNull final Stat stat) {
        homeTeamTextView.setText(stat.getHomeTeamValue() + stat.getUnit().getExtension());
        awayTeamTextView.setText(stat.getAwayTeamValue() + stat.getUnit().getExtension());
        labelTextView.setText(stat.getLabel());

        segmentedBarView.updateView(stat);
    }
}