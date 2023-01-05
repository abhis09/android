package com.example.new_meditation.FontText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class TextViewFontStyleEdit extends AppCompatEditText {
    public TextViewFontStyleEdit(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TextViewFontStyleEdit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TextViewFontStyleEdit(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Pathway Gothic One Regular.ttf"));
    }
}
