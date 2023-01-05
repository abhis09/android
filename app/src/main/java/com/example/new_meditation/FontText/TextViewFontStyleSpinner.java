package com.example.new_meditation.FontText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSpinner;

public class TextViewFontStyleSpinner extends AppCompatSpinner {
    private void setTypeface(Typeface typeface) {
    }

    public TextViewFontStyleSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TextViewFontStyleSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TextViewFontStyleSpinner(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Pathway Gothic One Regular.ttf"));
    }
}
