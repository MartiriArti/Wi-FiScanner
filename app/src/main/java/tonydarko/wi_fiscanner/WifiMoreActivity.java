package tonydarko.wi_fiscanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class WifiMoreActivity extends AppCompatActivity {

    String[] net = new String[14];

    TextView tvSSID, tvBSSID, tvHESSID, tvSecurity, tvLevel, tvFrequency, tvTimeStamp, tvDistance,
        tvDistanceSd, tvPassPoint, tvChannelBandWidth, tvCenterFreq0, tvCenterFreq1, tv80211Responder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  setTitle(R.string.title_activity_wifi_more);
        net = getIntent().getExtras().getStringArray("net");

      //  tvSSID = (TextView) findViewById(R.id.tvSSID);
        tvBSSID = (TextView) findViewById(R.id.tvBSSID);
        tvHESSID = (TextView) findViewById(R.id.tvHESSID);
        tvSecurity = (TextView) findViewById(R.id.tvSecurity);
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvFrequency = (TextView) findViewById(R.id.tvFrequency);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDistanceSd = (TextView) findViewById(R.id.tvDistanceSd);
        tvPassPoint = (TextView) findViewById(R.id.tvPassPoint);
        tvChannelBandWidth = (TextView) findViewById(R.id.tvChannelBandWidth);
        tvCenterFreq0 = (TextView) findViewById(R.id.tvCenterFreq0);
        tvCenterFreq1 = (TextView) findViewById(R.id.tvCenterFreq1);
        tv80211Responder = (TextView) findViewById(R.id.tv80211Responder);

//        tvSSID.setText(net[0]);
        tvBSSID.setText(net[1]);
        tvHESSID.setText(net[2]);
        tvSecurity.setText(net[3]);
        tvLevel.setText(net[4] + " dB");
        tvFrequency.setText(net[5] + " Hz");
        tvTimeStamp.setText(net[6]);
        tvDistance.setText(net[7]);
        tvDistanceSd.setText(net[8]);
        tvPassPoint.setText(net[9]);
        tvChannelBandWidth.setText(net[10]);
        tvCenterFreq0.setText(net[11] + " Hz");
        tvCenterFreq1.setText(net[12] + " Hz");
        tv80211Responder.setText(net[13]);

        setTitle(net[0]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        net = null;
    }
}
