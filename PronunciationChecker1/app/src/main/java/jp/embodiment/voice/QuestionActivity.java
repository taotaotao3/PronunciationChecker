package jp.embodiment.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

import jp.embodiment.voice.ResultActivity;

public class QuestionActivity extends Activity implements View.OnTouchListener {

    private static final int REQUEST_CODE = 0;
    private int mNo;
    private String[] mStr = { "orange", "umbrella", "sunflower", "elephant", "pencil" };
    private int[] mDraw = { R.drawable.q_orange, R.drawable.q_umb,
            R.drawable.q_sunflower, R.drawable.q_elephant, R.drawable.q_pencil };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewAnswerBtn);
        imageView.setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNo = new Random().nextInt(mStr.length);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewImg);
        imageView.setImageResource(mDraw[mNo]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.imageViewAnswerBtn) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((REQUEST_CODE == requestCode) && (RESULT_OK == resultCode)) {
            String answer = mStr[mNo];
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String result = "英語と認識できません";
            for (String ans : results) {
                if (ans.matches("[a-zA-Z]+")) {
                    if (ans.length() == answer.length() && ans.substring(0, 3).equals(answer.substring(0, 3))) {
                        result = answer;
                        break;
                    } else {
                        result = ans;
                    }
                }
            }

            Intent nextActivity = new Intent(this, ResultActivity.class);
            nextActivity.putExtra("RESULT", result);
            nextActivity.putExtra("ANS", answer);
            nextActivity.putExtra("ANSIMG", mDraw[mNo]);
            if (answer.equals(result)) {
                nextActivity.putExtra("PICT", "OK");
            } else {
                nextActivity.putExtra("PICT", "NG");
            }
            startActivity(nextActivity);
        }
    }
}
