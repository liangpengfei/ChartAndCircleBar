package xhome.uestcfei.com.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Email : luckyliangfei@gmail.com
 * Created by fei on 16/2/25.
 */
public class MainActivity extends AppCompatActivity {

    private CircleBar win;
    private CircleBar draw;
    private CircleBar lose;

    private Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (Chart) findViewById(R.id.chart);
        win = (CircleBar) findViewById(R.id.circle_win);
        draw = (CircleBar) findViewById(R.id.circle_draw);
        lose = (CircleBar) findViewById(R.id.circle_lose);

        char[] s1 = {'L', 'W', 'W', 'W', 'D', 'W', 'L', 'W'};
        char[] s2 = {'W', 'L', 'L', 'L', 'D', 'L', 'W', 'L'};

        Chart.Performance[] performances1=new Chart.Performance[8];
        for (int i=0;i<performances1.length;i++) {
            String item = String.valueOf(s1[i]);
            if (item.equals("L")) {
                performances1[performances1.length - 1 - i] = Chart.Performance.LOSE;
            } else if (item.equals("D")) {
                performances1[performances1.length - 1 - i] = Chart.Performance.DRAW;
            } else if (item.equals("W")) {
                performances1[performances1.length - 1 - i] = Chart.Performance.WINS;
            }
        }

        Chart.Performance[] performances2=new Chart.Performance[8];
        for (int i=0;i<performances2.length;i++) {
            String item = String.valueOf(s2[i]);
            if (item.equals("L")) {
                performances2[performances2.length - 1 - i] = Chart.Performance.LOSE;
            } else if (item.equals("D")) {
                performances2[performances2.length - 1 - i] = Chart.Performance.DRAW;
            } else if (item.equals("W")) {
                performances2[performances2.length - 1 - i] = Chart.Performance.WINS;
            }
        }

        chart.setPerformances(performances1, performances2);
        win.setPercent(30);
        win.setCustomText("win");
        win.setProgessColor(Color.RED);
        draw.setPercent(40);
        draw.setCustomText("draw");
        draw.setProgessColor(Color.BLUE);
        lose.setPercent(55);
        lose.setCustomText("lose");
        lose.setProgessColor(Color.YELLOW);

    }

    
}
