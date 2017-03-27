package com.androfly.texttospeech;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Locale;

import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextToSpeech tts;
    EditText ed1;
    Button b1;

    float speed = 2.0f ;

    SeekBar sbSpeed ;

    String toSpeak ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);

        sbSpeed = (SeekBar) findViewById(R.id.seekbar);

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSpeak = ed1.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
                    tts.setSpeechRate(speed);
                } else {
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    tts.setSpeechRate(speed);
                }

            }
        });


        sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = ((float)progress)/50f ;
                Toast.makeText(MainActivity.this, ""+speed, Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
                    tts.setSpeechRate(speed);
                } else {
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    tts.setSpeechRate(speed);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

}