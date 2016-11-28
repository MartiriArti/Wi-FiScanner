package tonydarko.wi_fiscanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*Массив от сканирования сети имеет следующий вид
    [0]  SSID: Experience,
    [1]  BSSID: 30:b5:c2:98:fe:ae,
    [2]  HESSID: <none>,
    [3]  capabilities: [WPA-PSK-CCMP][WPA2-PSK-CCMP][WPS][ESS],
    [4]  level: -53,
    [5]  frequency: 2427,
    [6]  timestamp: 359536324299,
    [7]  distance: ?(cm),
    [8]  distanceSd: ?(cm),
    [9]  passpoint: no,
    [10]  ChannelBandwidth: 1,
    [11] centerFreq0: 2447,
    [12] centerFreq1: 0,
    [13] 802.11mcResponder: is not supported, */

    public Element[] nets;
    private WifiManager wifiManager;
    private List<ScanResult> wifiList;

    String[] vector_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectWifi();
                Snackbar.make(view, "Сканирование...", Snackbar
                        .LENGTH_SHORT)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    public void detectWifi() {
        this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        this.wifiManager.startScan();
        this.wifiList = this.wifiManager.getScanResults();

        this.nets = new Element[wifiList.size()];
        final String[][] list = new String[wifiList.size()][];
        for (int i = 0; i < wifiList.size(); i++) {
            String item = wifiList.get(i).toString();
            vector_item = item.split(",");
            list[i] = vector_item;
            String item_essid = vector_item[0];
            String item_capabilities = vector_item[3];
            String item_level = vector_item[4];
                String ssid = item_essid.split(": ")[1];
                String security = item_capabilities.split(": ")[1];
                String level = item_level.split(": ")[1];

            nets[i] = new Element(ssid, security, level);
        }

        AdapterElements adapterElements = new AdapterElements(this);
        ListView netList = (ListView) findViewById(R.id.listView);
        netList.setAdapter(adapterElements);

       netList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               Intent intent = new Intent(getBaseContext(), WifiMoreActivity.class);
               intent.putExtra("net", list[position]);
               startActivity(intent);
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    //////Adapter||||||||||

    class AdapterElements extends ArrayAdapter<Object> {
        Activity context;

        AdapterElements(Activity context) {
            super(context, R.layout.item, nets);
            this.context = context;
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            @SuppressLint({"ViewHolder", "InflateParams"})
            View item = inflater.inflate(R.layout.item, null);

            TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
            tvSsid.setText(nets[position].getName());

            TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);
            tvSecurity.setText(nets[position].getSecurity());

            TextView tvLevel = (TextView) item.findViewById(R.id.tvLevel);
            String level = nets[position].getLevel();
            try {
                int i = Integer.parseInt(level);
                if (i > -50) {
                    tvLevel.setText("Высокий");
                } else if (i <= -50 && i > -80) {
                    tvLevel.setText("Средний");
                } else if (i <= -80) {
                    tvLevel.setText("Низкий");
                }
            } catch (NumberFormatException e) {
                Log.d("TAG", "Неверный формат строки");
            }
            return item;
        }
    }
}