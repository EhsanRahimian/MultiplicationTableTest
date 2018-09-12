package com.nicootech.multiplicationtabletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TestActivity extends AppCompatActivity {

     /**
      * TODO
      * let user pick the level of questions.
      * add an array of math functions + - / * jazr
      * keep user record, number of true answers out of number of questions.
      * Add time limit in higher levels, like 5 sec.
      * For timer add a seekBar that shows time spent.
      *
      * */


    private static final String TAG = "TestActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private boolean mCorectAnswer;

    private void updateQuestion(){
        // int question = mQuestionBank[mCurrentIndex].getTextResId();
        mCorectAnswer = System.currentTimeMillis() % 2 == 0;
        Log.i("zzzzz", "time: " + System.currentTimeMillis() + "  " + mCorectAnswer);
        mQuestionTextView.setText(getQuestion() + " = " + getAnswer(mCorectAnswer));
    }
    private void checkAnswer(boolean userPressedTrue){
        //boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if(mIsCheater){
            messageResId = R.string.incorrect_toast;
        }else {
            if (userPressedTrue == /*answerIsTrue*/mCorectAnswer) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_test);

        mQuestionTextView =(TextView)findViewById(R.id.question_text_view);


        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        mNextButton =(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //mCurrentIndex = (mCurrentIndex+1)% mQuestionBank.length;
                mIsCheater=false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(TestActivity.this, /*answerIsTrue*/mCorectAnswer);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        if (savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX , 0);
        }

        updateQuestion();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX , mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");
    }

    private int[] aLevel1 = new int[]{0,1,2,3,4,5,6,7,8,9};
    private int[] bLevel1 = new int[]{0,1,2,3,4,5,6,7,8,9};
    private int[] wrongAnswersFlavors = new int[]{0,1,2,3,4};

    private int m1stNum,m2ndNum;
    private String getQuestion(){
        m1stNum = getRandom(aLevel1);
        m2ndNum = getRandom(bLevel1);

        StringBuilder builder = new StringBuilder();
        return builder.append(m1stNum).append(" x ").append(m2ndNum).toString();
    }

    private int getAnswer(boolean correctAnswer) {
        if (correctAnswer){
            return m1stNum*m2ndNum;
        } else {
            return getWrongAnswer(getRandom(wrongAnswersFlavors));
        }
    }

    private int getWrongAnswer(int flavor) {
        switch (flavor) {
            case 1:
                return (m1stNum*m2ndNum + 2) ;
            case 2:
                return (m1stNum*m2ndNum)/2 +1 ;
            case 3:
                return (m1stNum*m2ndNum + 1) ;
            case 4:
                return (m1stNum*m2ndNum /2+3) ;
            default:
                return (m1stNum*m2ndNum + 5/2) ;
        }
    }

    private int getRandom(int[] array) {
        if (array!=null && array.length > 1) {
            int rnd = new Random().nextInt(array.length);
            return array[rnd];
        } else {
            return 0;
        }
    }

/*private Question[] mQuestionBank = new Question[]{

                new Question(R.string.question_102,true),
                new Question(R.string.question_78,false),
                new Question(R.string.question_54,true),
                new Question(R.string.question_01,true),
                new Question(R.string.question_41,true),
                new Question(R.string.question_31,true),
                new Question(R.string.question_53,true),
                new Question(R.string.question_34,false),
                new Question(R.string.question_56,false),
                new Question(R.string.question_00,false),
                new Question(R.string.question_79,false),
                new Question(R.string.question_39,false),
                new Question(R.string.question_61,true),
                new Question(R.string.question_32,true),
                new Question(R.string.question_43,true),
                new Question(R.string.question_63,false),
                new Question(R.string.question_58,true),
                new Question(R.string.question_45,true),
                new Question(R.string.question_47,true),
                new Question(R.string.question_33,false),
                new Question(R.string.question_52,true),
                new Question(R.string.question_38,true),
                new Question(R.string.question_80,true),
                new Question(R.string.question_59,false),
                new Question(R.string.question_57,true),
                new Question(R.string.question_35,false),
                new Question(R.string.question_60,true),
                new Question(R.string.question_37,false),
                new Question(R.string.question_46,false),
                new Question(R.string.question_08,false),
                new Question(R.string.question_67,true),
                new Question(R.string.question_73,true),
                new Question(R.string.question_07,false),
                new Question(R.string.question_107,true),
                new Question(R.string.question_22,true),
                new Question(R.string.question_112,false),
                new Question(R.string.question_70,true),
                new Question(R.string.question_44,false),
                new Question(R.string.question_72,true),
                new Question(R.string.question_113,true),
                new Question(R.string.question_05,false),
                new Question(R.string.question_14,false),
                new Question(R.string.question_71,true),
                new Question(R.string.question_10,false),
                new Question(R.string.question_106,true),
                new Question(R.string.question_108,false),
                new Question(R.string.question_77,true),
                new Question(R.string.question_110,false),
                new Question(R.string.question_68,true),
                new Question(R.string.question_111,false),
                new Question(R.string.question_24,true),
                new Question(R.string.question_03,true),
                new Question(R.string.question_75,true),
                new Question(R.string.question_69,true),
                new Question(R.string.question_76,true),
                new Question(R.string.question_28,false),
                new Question(R.string.question_18,true),
                new Question(R.string.question_109,false),
                new Question(R.string.question_19,true),
                new Question(R.string.question_23,false),
                new Question(R.string.question_90,true),
                new Question(R.string.question_36,true),
                new Question(R.string.question_74,false),
                new Question(R.string.question_02,true),
                new Question(R.string.question_21,true),
                new Question(R.string.question_04,true),
                new Question(R.string.question_25,true),
                new Question(R.string.question_93,true),
                new Question(R.string.question_15,false),
                new Question(R.string.question_16,true),
                new Question(R.string.question_17,true),
                new Question(R.string.question_88,false),
                new Question(R.string.question_65,false),
                new Question(R.string.question_55,true),
                new Question(R.string.question_40,true),
                new Question(R.string.question_91,false),
                new Question(R.string.question_100,true),
                new Question(R.string.question_92,true),
                new Question(R.string.question_27,false),
                new Question(R.string.question_103,false),
                new Question(R.string.question_30,true),
                new Question(R.string.question_101,true),
                new Question(R.string.question_49,false),
                new Question(R.string.question_50,false),
                new Question(R.string.question_51,false),
                new Question(R.string.question_104,false),
                new Question(R.string.question_20,true),
                new Question(R.string.question_99,true),
                new Question(R.string.question_82,false),
                new Question(R.string.question_84,true),
                new Question(R.string.question_48,false),
                new Question(R.string.question_105,false),
                new Question(R.string.question_81,false),
                new Question(R.string.question_29,true),
                new Question(R.string.question_96,true),
                new Question(R.string.question_86,true),
                new Question(R.string.question_13,true),
                new Question(R.string.question_87,true),
                new Question(R.string.question_11,true),
                new Question(R.string.question_06,true),
                new Question(R.string.question_62,true),
                new Question(R.string.question_42,false),
                new Question(R.string.question_64,true),
                new Question(R.string.question_09,true),
                new Question(R.string.question_66,false),
                new Question(R.string.question_12,true),
                new Question(R.string.question_26,true),
                new Question(R.string.question_85,true),
                new Question(R.string.question_94,false),
                new Question(R.string.question_83,true),
                new Question(R.string.question_95,true),
                new Question(R.string.question_97,true),
                new Question(R.string.question_89,false),
                new Question(R.string.question_98,true),
    };*/

}
