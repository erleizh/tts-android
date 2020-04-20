package com.lll.tts;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.lll.tts.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech mSpeech;
    private AppCompatSpinner mSpinner;
    private EditText mInputText;
    private TextView mTvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mSpeech = new TextToSpeech(this, this);
    }

    private void findViews() {
        mInputText = ((EditText) findViewById(R.id.et_input_text));
        mSpinner = ((AppCompatSpinner) findViewById(R.id.spinner));
        mTvInfo = ((TextView) findViewById(R.id.tv_info));
    }

    public void play(View view) {
        String string = mInputText.getText().toString();
        mSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null);
        L.i("play : %s", string);
    }

    @Override
    public void onInit(int status) {
        L.i("onInit status : %d", status);
        if (status != TextToSpeech.ERROR) {
            mSpeech.setLanguage(Locale.CHINA);
            mSpeech.setEngineByPackageName(mSpeech.getDefaultEngine());
            mTvInfo.append("DefaultEngine:");
            mTvInfo.append(mSpeech.getDefaultEngine());
            mTvInfo.append("\n");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTvInfo.append("AvailableLanguages:");
                mTvInfo.append(mSpeech.getAvailableLanguages().toString());
                mTvInfo.append("\n");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTvInfo.append("DefaultVoice:");
                mTvInfo.append(mSpeech.getDefaultVoice().toString());
                mTvInfo.append("\n");
            }
            initSpinner(mSpeech.getEngines());
        }
    }

    private void initSpinner(List<TextToSpeech.EngineInfo> engines) {
        final ArrayList<String> arrayList = new ArrayList<>(engines.size());
        for (TextToSpeech.EngineInfo engine : engines) {
            arrayList.add(engine.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(arrayList.indexOf(mSpeech.getDefaultEngine()));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = arrayList.get(i);
                mSpinner.setPrompt(name);
                if (mSpeech.isSpeaking()) {
                    mSpeech.stop();
                }
                mSpeech.setEngineByPackageName(name);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
