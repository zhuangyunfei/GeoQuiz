package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.print.PrinterId;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    //增添常量保存
    private static final String KEY_INDEX = "index";
    private static final int QUESTION_CODE_CHEAT = 0;
    //声明变量
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    //声明数组
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };
    //初始化索引
    private int mCurrentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null){
            //取出数据
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        //动态设置问题文本
        mQuestionTextView = findViewById(R.id.question_text_view);

        //获取控件
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        //为对象设置监听器，相应用户操作(采用匿名内部类实现监听)
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast toast = Toast.makeText(QuizActivity.this,R.string.correct_toast,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();*/
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer(false);
            }
        });
        //引用Next按钮
        mNextButton = findViewById(R.id.next_button);
        //使用匿名内部类进行监听按钮
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //递增索引，并更新文本内容
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                Log.d("QuizActivity.this","索引值为：" + mCurrentIndex);
                mIsCheat = false;
                updateQuestion();
            }
        });
        //作弊
        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(intent,QUESTION_CODE_CHEAT);
                Toast toast = Toast.makeText(QuizActivity.this,"启动成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
            }
        });
    }

    

    /**
     *User: langyazyf
     *Data: 2018/4/25
     *Time:10:46
     *Description:更多的生命周期调用
     */
    //onCreate之后的onStart
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
    }
    //onStart之后的onResume

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }
    //onResume之后的onPause()


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
    }
    //onPause()之后的onStop


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }
    //onStop之后的onDestroy()

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }

    //封装公共方法
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    //判断是否正确
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageId = 0;
        if (mIsCheat){
            messageId = R.string.judgment_toast;
        }else {
            if(userPressedTrue == answerIsTrue){
                messageId = R.string.correct_toast;
            }else {
                messageId = R.string.incorrect_toact;
            }
            Toast.makeText(this,messageId,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG, "onSaveInstanceState: called");
        saveInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }
private boolean mIsCheat;
    //检查结果是否在正确
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == Activity.RESULT_CANCELED){
            if (data == null){
                return;
            }
            mIsCheat = CheatActivity.wasAnswerShown(data);
        }
    }
}
