package edu.cuc.stephen.systemservicedemo;

import android.app.ActivityManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public SeekBar seekBar;
    public TextView textView;
    public AudioManager audioManager;
    public CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_view_info);
        seekBar = (SeekBar) findViewById(R.id.volume_bar);
        checkBox = (CheckBox) findViewById(R.id.checkbox_vibrate);
        audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MainActivity.this.audioManager.setStreamVolume(AudioManager.STREAM_RING, i, 0);
                MainActivity.this.textView.append("更改音量：" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });
    }


    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button_check_network:
                ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null) {
                    if (networkInfo.isAvailable()) {
                        textView.append("网络已经连接\n");
                    } else {
                        textView.append("网络尚未连接\n");
                    }
                } else
                    textView.append("无法判断网络状况\n");
                break;
            case R.id.button_change_wifi:
                WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
                Button wifiButton = (Button) findViewById(R.id.button_change_wifi);
                if (wifiManager != null) {
                    if (wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(false);
                        textView.append("WIFI已经关闭\n");
                        wifiButton.setText("打开WIFI");
                    } else {
                        wifiManager.setWifiEnabled(true);
                        textView.append("WIFI已经打开\n");
                        wifiButton.setText("关闭WIFI");
                    }
                } else
                    textView.append("无法判断WIFI状况\n");
                break;
            case R.id.button_get_volume:
                int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
                seekBar.setMax(max);
                textView.append("系统最大音量为："+max+"\n");
                int current = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                textView.append("当前音量为：" + current + "\n");
                seekBar.setProgress(current);
                if(AudioManager.RINGER_MODE_VIBRATE == audioManager.getRingerMode()) {
                    ((CheckBox) findViewById(R.id.checkbox_vibrate)).setChecked(true);
                    textView.append("震动\n");
                }else {
                    ((CheckBox) findViewById(R.id.checkbox_vibrate)).setChecked(false);
                    textView.append("无震动\n");
                }
                break;
            case R.id.button_get_package:
                ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
                //activityManager.getAppTasks().get(0).getTaskInfo();
                break;
            case R.id.checkbox_vibrate:
                textView.append("ao\n");
                break;
            default:
                textView.append("有鬼啊！\n");
                break;
        }
    }
}
