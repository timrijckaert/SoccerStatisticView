package tim.rijckaert.be.library;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tim on 08.06.16.
 */
public class StatisticView extends LinearLayout {

    //<editor-fold desc="Views">
    private TextView homeTeamTextView;
    private TextView awayTeamTextView;
    private TextView labelTextView;
    private SegmentedBarView segmentedBarView;
    //</editor-fold>

    //<editor-fold desc="Chaining Constructors">
    public StatisticView(final Context context) {
        this(context, null);
    }

    public StatisticView(final Context context, final AttributeSet attrs) {
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
        setupViews(stat);
    }

    private void setupViews(final Stat stat) {
        if (stat == null) {
            return;
        }

        if (stat.getHomeTeamValue() > stat.getAwayTeamValue()) {
            homeTeamTextView.setTypeface(Typeface.DEFAULT_BOLD);
            awayTeamTextView.setTypeface(Typeface.DEFAULT);
        } else if (stat.getHomeTeamValue() < stat.getAwayTeamValue()) {
            homeTeamTextView.setTypeface(Typeface.DEFAULT);
            awayTeamTextView.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (stat.getHomeTeamValue() == stat.getHomeTeamValue()) {
            homeTeamTextView.setTypeface(Typeface.DEFAULT);
            awayTeamTextView.setTypeface(Typeface.DEFAULT);
        }

        homeTeamTextView.setText(stat.getHomeTeamValue() + stat.getUnit().getExtension());
        awayTeamTextView.setText(stat.getAwayTeamValue() + stat.getUnit().getExtension());
        labelTextView.setText(stat.getLabel() != null ? stat.getLabel() : "");

        segmentedBarView.updateView(stat);
    }
}