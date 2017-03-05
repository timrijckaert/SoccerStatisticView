package tim.rijckaert.be.statisticsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tim.rijckaert.be.library.Stat;
import tim.rijckaert.be.library.StatisticView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatisticView statisticsView = (StatisticView) findViewById(R.id.statisticsView);
        statisticsView.setStat(new Stat("Balbezit", "AA Gent", 95, "RSCA Anderlecht", 5, true));

        StatisticView statisticsView2 = (StatisticView) findViewById(R.id.statisticsView2);
        statisticsView2.setStat(new Stat("Ze zijn gelijk", "AA Gent", 50, "RSCA Anderlecht", 50, true));

        StatisticView statisticsView3 = (StatisticView) findViewById(R.id.statisticsView3);
        statisticsView3.setStat(new Stat("Geen Percentage", "AA Gent", 95, "RSCA Anderlecht", 5, false));

        StatisticView statisticsView4 = (StatisticView) findViewById(R.id.statisticsView4);
        statisticsView4.setStat(new Stat("Iets Anders", "AA Gent", 17, "RSCA Anderlecht", 50, false));
    }
}