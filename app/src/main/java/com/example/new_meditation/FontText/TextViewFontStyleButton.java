package com.example.new_meditation.FontText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class TextViewFontStyleButton extends AppCompatButton {
    public TextViewFontStyleButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TextViewFontStyleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TextViewFontStyleButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Pathway Gothic One Regular.ttf"));
    }
}
