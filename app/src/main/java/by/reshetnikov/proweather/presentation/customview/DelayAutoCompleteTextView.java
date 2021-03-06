package by.reshetnikov.proweather.presentation.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import timber.log.Timber;


public class DelayAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }

    };
    private int autoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private ProgressBar loadingIndicator;

    public DelayAutoCompleteTextView(Context context) {
        super(context);
    }

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLoadingIndicator(ProgressBar progressBar) {
        loadingIndicator = progressBar;
    }

    public void setAutoCompleteDelay(int autoCompleteDelay) {
        this.autoCompleteDelay = autoCompleteDelay;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        Timber.d("performFiltering");
        if (text.length() >= getThreshold()) {
            if (loadingIndicator != null) {
                loadingIndicator.setVisibility(View.VISIBLE);
            }
            mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), autoCompleteDelay);
        }
    }

    @Override
    public void onFilterComplete(int count) {
        if (loadingIndicator != null) {
            loadingIndicator.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }

}
