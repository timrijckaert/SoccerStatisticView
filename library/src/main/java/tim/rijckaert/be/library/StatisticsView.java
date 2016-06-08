package tim.rijckaert.be.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tim on 08.06.16.
 */
public class StatisticsView extends LinearLayout {

    private TextView homeTeamTextView;
    private TextView awayTeamTextView;
    private TextView labelTextView;
    private SegmentedBarView segmentedBarView;

    public StatisticsView(final Context context) {
        this(context, null);
    }

    public StatisticsView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.compound_statistics_labels, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        homeTeamTextView = (TextView) findViewById(R.id.statisticsView_homeTeam_tv);
        awayTeamTextView = (TextView) findViewById(R.id.statisticsView_awayTeam_tv);
        labelTextView = (TextView) findViewById(R.id.statisticsView_label_tv);
        segmentedBarView = (SegmentedBarView) findViewById(R.id.statisticsView_segmentedBarView);

        initViews();
    }

    private void initViews() {
        homeTeamTextView.setText("95%");
        awayTeamTextView.setText("5%");
        labelTextView.setText("Balbezit");
    }
}