package tim.rijckaert.be.statisticsview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tim.rijckaert.be.library.Stat;
import tim.rijckaert.be.library.StatisticsView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatisticsView statisticsView = (StatisticsView) findViewById(R.id.statisticsView);
        statisticsView.setStat(new Stat("Balbezit", 95, 5, true));

        StatisticsView statisticsView2 = (StatisticsView) findViewById(R.id.statisticsView2);
        statisticsView2.setStat(new Stat("Ze zijn gelijk", 50, 50, true));

        StatisticsView statisticsView3 = (StatisticsView) findViewById(R.id.statisticsView3);
        statisticsView3.setStat(new Stat("Geen Percentage", 95, 5, false));

        StatisticsView statisticsView4 = (StatisticsView) findViewById(R.id.statisticsView4);
        statisticsView4.setStat(new Stat("Iets Anders", 17, 50, false));
    }
}