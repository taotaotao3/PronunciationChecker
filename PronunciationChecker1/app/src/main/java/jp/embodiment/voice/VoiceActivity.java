package jp.embodiment.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class VoiceActivity extends Activity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewStartBtn);
        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (v.getId() == R.id.imageViewStartBtn){
                Intent intent = new Intent(this, QuestionActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }
}
