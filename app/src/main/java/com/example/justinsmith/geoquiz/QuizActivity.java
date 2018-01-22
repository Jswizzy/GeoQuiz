package com.example.justinsmith.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private boolean mIsCheater;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private int mAnswered = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(
                next());

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(
                v -> {
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }
        );

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                v -> checkAnswer(true)
        );
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(
                v -> checkAnswer(false)
        );

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(prev());

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(next());

        updateQuestion();
    }

    @NonNull
    private View.OnClickListener next() {
        return v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        };
    }

    @NonNull
    private View.OnClickListener prev() {
        return v -> {
            mCurrentIndex =
                    mCurrentIndex - 1 < 0
                            ? mCurrentIndex = mQuestionBank.length - 1
                            : mCurrentIndex - 1;
            updateQuestion();
        };
    }

    private void updateQuestion() {
        //Log.d(TAG, "Updating question text", new Exception());
        Question question = mQuestionBank[mCurrentIndex];
        int id = question.getTextResId();
        mQuestionTextView.setText(id);
        mTrueButton.setEnabled(!question.isAnswered());
        mFalseButton.setEnabled(!question.isAnswered());
        mCheatButton.setEnabled(!question.isAnswered());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkAnswer(boolean userAnswer) {
        Question question = mQuestionBank[mCurrentIndex];
        question.setUserAnswer(userAnswer);
        question.setAnswered();
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        mCheatButton.setEnabled(false);


        int messageResId;

        if (mIsCheater) messageResId = R.string.judgment_toast;
        else if (question.isCorrect()) messageResId = R.string.correct_toast;
        else messageResId = R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

        if (++mAnswered == mQuestionBank.length)
            Toast.makeText(this,
                    String.format(Locale.getDefault(),
                            "Congrats you got %d out of %d correct",
                            Arrays.stream(mQuestionBank)
                                    .filter(Question::isCorrect)
                                    .count(),
                            mQuestionBank.length),
                    Toast.LENGTH_LONG)
                    .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}
