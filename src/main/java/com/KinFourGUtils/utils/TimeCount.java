package com.KinFourGUtils.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 作者：KingGGG on 15/12/8 15:01
 * 描述：倒计时
 */
public class TimeCount {
    private boolean isCanReSend = true;
    private CountDownTimer countDownTimer;

    public TimeCount(final TextView hintView, long millisInFuture, long countDownInterval, final String countFinishHint, final String countingHint, final String canntReSend, final String canReSend) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                hintView.setClickable(false);
                isCanReSend = false;
                hintView.setText(millisUntilFinished / 1000 + countingHint);
                if (TextUtils.isEmpty(canntReSend)) {
                    hintView.setTextColor(Color.parseColor("#666666"));
                } else {
                    hintView.setTextColor(Color.parseColor(canntReSend));
                }
            }

            @Override
            public void onFinish() {
                hintView.setText(countFinishHint);
                hintView.setClickable(true);
                isCanReSend = true;
                if (TextUtils.isEmpty(canReSend)) {
                    hintView.setTextColor(Color.parseColor("#0870ff"));
                } else {
                    hintView.setTextColor(Color.parseColor(canReSend));
                }
            }
        };
    }

    public void start() {
        countDownTimer.start();
    }

    public boolean getIsCanReSend() {
        return isCanReSend;
    }
}
