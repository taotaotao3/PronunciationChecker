package jp.embodiment.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import jp.embodiment.voice.R;

public class ResultActivity extends Activity implements View.OnTouchListener, TextToSpeech.OnInitListener {

    private String mResult;
    private String mAns;
    private int mAnsImg;
    private String mPict;

    private TextToSpeech mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageView imageViewMessage = (ImageView) findViewById(R.id.imageViewMessage);
        ImageView imageViewImg = (ImageView) findViewById(R.id.imageViewImg);
        TextView textView = (TextView) findViewById(R.id.textViewAns);
        ImageView imageViewListenBtn = (ImageView) findViewById(R.id.imageViewListenBtn);
        imageViewListenBtn.setOnTouchListener(this);
        ImageView imageViewRePlayBtn = (ImageView) findViewById(R.id.imageViewRePlayBtn);
        imageViewRePlayBtn.setOnTouchListener(this);

        //インテントと付加情報の取得
        Intent intent = getIntent();
        Bundle ex = intent.getExtras();
        if (ex != null) {
            mResult = ex.getString("RESULT");
            mAns = ex.getString("ANS");
            mAnsImg = ex.getInt("ANSIMG");
            mPict = ex.getString("PICT");
        }

        //正解・不正解の画像
        if ("OK".equals(mPict)) {
            imageViewMessage.setImageResource(R.drawable.messageok);
        } else if ("NG".equals(mPict)) {
            imageViewMessage.setImageResource(R.drawable.messageng);
        }

        //正解の図
        imageViewImg.setImageResource(mAnsImg);

        //正解の言葉
        String msg = "正解は" + mAns + "でした。\nあなたの言葉は" + mResult + "でした。";
        textView.setText(msg);

        // 音声合成
        mTts = new TextToSpeech(this, this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch(v.getId()){
                case R.id.imageViewRePlayBtn:
                    finish();
                    return true;
                case R.id.imageViewListenBtn:
                    if (mTts.isSpeaking()) {
                        mTts.stop();
                    }
                    mTts.setSpeechRate(1.0f);
                    mTts.speak(mAns, TextToSpeech.QUEUE_FLUSH, null, null);
                    return true;
            }
        }
        return false;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (mTts.isLanguageAvailable(Locale.ENGLISH) >= TextToSpeech.LANG_AVAILABLE) {
                mTts.setLanguage(Locale.ENGLISH);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTts != null) {
            mTts.shutdown();
        }
    }
}
