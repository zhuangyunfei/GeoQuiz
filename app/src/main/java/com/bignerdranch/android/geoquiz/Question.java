package com.bignerdranch.android.geoquiz;

/**
 * 项目名称
 * 创建人
 * 创建时间
 * 说明
 */

public class Question {
    private int mTextResId;//问题引用的字符串ID
    private boolean mAnswerTrue;//问题是否正确
    //构造函数,问题由问题的内容和问题的答案组成
    public Question(int textResId,boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
