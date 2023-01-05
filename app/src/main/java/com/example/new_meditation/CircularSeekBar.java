package com.example.new_meditation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ResourceType")
public class CircularSeekBar extends View {
    public static final int[] CircularSeekBarss = {R.attr.circle_color, R.attr.circle_fill,
            R.attr.circle_progress_color, R.attr.circle_stroke_width, R.attr.circle_x_radius,
            R.attr.circle_y_radius, R.attr.end_angle, R.attr.lock_enabled,
            R.attr.maintain_equal_circle, R.attr.max, R.attr.move_outside_circle,
            R.attr.pointer_alpha_ontouch, R.attr.pointer_color, R.attr.pointer_halo_border_width,
            R.attr.pointer_halo_color, R.attr.pointer_halo_color_ontouch,
            R.attr.pointer_halo_width, R.attr.pointer_radius, R.attr.progress, R.attr.start_angle
            , R.attr.use_custom_radii};
    protected static final int DEFAULT_CIRCLE_COLOR = -12303292;
    protected static final int DEFAULT_CIRCLE_FILL_COLOR = 0;
    protected static final int DEFAULT_CIRCLE_PROGRESS_COLOR = Color.argb(235, 74, 138, 255);
    protected static final float DEFAULT_CIRCLE_STROKE_WIDTH = 5.0f;
    protected static final float DEFAULT_CIRCLE_X_RADIUS = 30.0f;
    protected static final float DEFAULT_CIRCLE_Y_RADIUS = 30.0f;
    protected static final float DEFAULT_END_ANGLE = 270.0f;
    protected static final boolean DEFAULT_LOCK_ENABLED = true;
    protected static final boolean DEFAULT_MAINTAIN_EQUAL_CIRCLE = true;
    protected static final int DEFAULT_MAX = 100;
    protected static final boolean DEFAULT_MOVE_OUTSIDE_CIRCLE = false;
    protected static final int DEFAULT_POINTER_ALPHA = 135;
    protected static final int DEFAULT_POINTER_ALPHA_ONTOUCH = 100;
    protected static final int DEFAULT_POINTER_COLOR = Color.argb(235, 74, 138, 255);
    protected static final float DEFAULT_POINTER_HALO_BORDER_WIDTH = 2.0f;
    protected static final int DEFAULT_POINTER_HALO_COLOR = Color.argb(DEFAULT_POINTER_ALPHA, 74,
            138, 255);
    protected static final int DEFAULT_POINTER_HALO_COLOR_ONTOUCH =
            Color.argb(DEFAULT_POINTER_ALPHA, 74, 138, 255);
    protected static final float DEFAULT_POINTER_HALO_WIDTH = 6.0f;
    protected static final float DEFAULT_POINTER_RADIUS = 7.0f;
    protected static final int DEFAULT_PROGRESS = 0;
    protected static final float DEFAULT_START_ANGLE = 270.0f;
    protected static final boolean DEFAULT_USE_CUSTOM_RADII = false;
    protected final float DPTOPX_SCALE = getResources().getDisplayMetrics().density;
    protected final float MIN_TOUCH_TARGET_DP = 48.0f;
    protected float ccwDistanceFromEnd;
    protected float ccwDistanceFromPointer;
    protected float ccwDistanceFromStart;
    protected float cwDistanceFromEnd;
    protected float cwDistanceFromPointer;
    protected float cwDistanceFromStart;
    protected boolean isTouchEnabled = true;
    protected float lastCWDistanceFromStart;
    protected boolean lockAtEnd = false;
    protected boolean lockAtStart = true;
    protected boolean lockEnabled = true;
    protected int mCircleColor = DEFAULT_CIRCLE_COLOR;
    protected int mCircleFillColor = 0;
    protected Paint mCircleFillPaint;
    protected float mCircleHeight;
    protected Paint mCirclePaint;
    protected Path mCirclePath;
    protected int mCircleProgressColor = DEFAULT_CIRCLE_PROGRESS_COLOR;
    protected Paint mCircleProgressGlowPaint;
    protected Paint mCircleProgressPaint;
    protected Path mCircleProgressPath;
    protected RectF mCircleRectF = new RectF();
    protected float mCircleStrokeWidth;
    protected float mCircleWidth;
    protected float mCircleXRadius;
    protected float mCircleYRadius;
    protected boolean mCustomRadii;
    protected float mEndAngle;
    protected boolean mIsMovingCW;
    protected boolean mMaintainEqualCircle;
    protected int mMax;
    protected boolean mMoveOutsideCircle;
    protected OnCircularSeekBarChangeListener mOnCircularSeekBarChangeListener;
    protected int mPointerAlpha = DEFAULT_POINTER_ALPHA;
    protected int mPointerAlphaOnTouch = 100;
    protected int mPointerColor = DEFAULT_POINTER_COLOR;
    protected Paint mPointerHaloBorderPaint;
    protected float mPointerHaloBorderWidth;
    protected int mPointerHaloColor = DEFAULT_POINTER_HALO_COLOR;
    protected int mPointerHaloColorOnTouch = DEFAULT_POINTER_HALO_COLOR_ONTOUCH;
    protected Paint mPointerHaloPaint;
    protected float mPointerHaloWidth;
    protected Paint mPointerPaint;
    protected float mPointerPosition;
    protected float[] mPointerPositionXY = new float[2];
    protected float mPointerRadius;
    protected int mProgress;
    protected float mProgressDegrees;
    protected float mStartAngle;
    protected float mTotalCircleDegrees;
    protected boolean mUserIsMovingPointer = false;

    public CircularSeekBar(Context context) {
        super(context);
        init((AttributeSet) null, 0);
    }


    public CircularSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public CircularSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    public void initAttributes(TypedArray typedArray) {
        this.mCircleXRadius = typedArray.getDimension(4, this.DPTOPX_SCALE * 30.0f);
        this.mCircleYRadius = typedArray.getDimension(5, this.DPTOPX_SCALE * 30.0f);
        this.mPointerRadius = typedArray.getDimension(17,
                this.DPTOPX_SCALE * DEFAULT_POINTER_RADIUS);
        this.mPointerHaloWidth = typedArray.getDimension(16,
                this.DPTOPX_SCALE * DEFAULT_POINTER_HALO_WIDTH);
        this.mPointerHaloBorderWidth = typedArray.getDimension(13,
                this.DPTOPX_SCALE * DEFAULT_POINTER_HALO_BORDER_WIDTH);
        this.mCircleStrokeWidth = typedArray.getDimension(3,
                this.DPTOPX_SCALE * DEFAULT_CIRCLE_STROKE_WIDTH);
        this.mPointerColor = typedArray.getColor(12, DEFAULT_POINTER_COLOR);
        this.mPointerHaloColor = typedArray.getColor(14, DEFAULT_POINTER_HALO_COLOR);
        this.mPointerHaloColorOnTouch = typedArray.getColor(15, DEFAULT_POINTER_HALO_COLOR_ONTOUCH);
        this.mCircleColor = typedArray.getColor(0, DEFAULT_CIRCLE_COLOR);
        this.mCircleProgressColor = typedArray.getColor(2, DEFAULT_CIRCLE_PROGRESS_COLOR);
        this.mCircleFillColor = typedArray.getColor(1, 0);
        this.mPointerAlpha = Color.alpha(this.mPointerHaloColor);
        this.mPointerAlphaOnTouch = typedArray.getInt(11, 100);
        if (this.mPointerAlphaOnTouch > 255 || this.mPointerAlphaOnTouch < 0) {
            this.mPointerAlphaOnTouch = 100;
        }
        this.mMax = typedArray.getInt(9, 100);
        this.mProgress = typedArray.getInt(18, 0);
        this.mCustomRadii = typedArray.getBoolean(20, false);
        this.mMaintainEqualCircle = typedArray.getBoolean(8, true);
        this.mMoveOutsideCircle = typedArray.getBoolean(10, false);
        this.lockEnabled = typedArray.getBoolean(7, true);
        this.mStartAngle = ((typedArray.getFloat(19, 270.0f) % 360.0f) + 360.0f) % 360.0f;
        this.mEndAngle = ((typedArray.getFloat(6, 270.0f) % 360.0f) + 360.0f) % 360.0f;
        if (this.mStartAngle == this.mEndAngle) {
            this.mEndAngle -= 0.1f;
        }
    }


    public void initPaints() {
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setDither(true);
        this.mCirclePaint.setColor(this.mCircleColor);
        this.mCirclePaint.setStrokeWidth(this.mCircleStrokeWidth);
        this.mCirclePaint.setStyle(Paint.Style.STROKE);
        this.mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        this.mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mCircleFillPaint = new Paint();
        this.mCircleFillPaint.setAntiAlias(true);
        this.mCircleFillPaint.setDither(true);
        this.mCircleFillPaint.setColor(this.mCircleFillColor);
        this.mCircleFillPaint.setStyle(Paint.Style.FILL);
        this.mCircleProgressPaint = new Paint();
        this.mCircleProgressPaint.setAntiAlias(true);
        this.mCircleProgressPaint.setDither(true);
        this.mCircleProgressPaint.setColor(this.mCircleProgressColor);
        this.mCircleProgressPaint.setStrokeWidth(this.mCircleStrokeWidth);
        this.mCircleProgressPaint.setStyle(Paint.Style.STROKE);
        this.mCircleProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mCircleProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mCircleProgressGlowPaint = new Paint();
        this.mCircleProgressGlowPaint.set(this.mCircleProgressPaint);
        this.mCircleProgressGlowPaint.setMaskFilter(new BlurMaskFilter(this.DPTOPX_SCALE * DEFAULT_CIRCLE_STROKE_WIDTH, BlurMaskFilter.Blur.NORMAL));
        this.mPointerPaint = new Paint();
        this.mPointerPaint.setAntiAlias(true);
        this.mPointerPaint.setDither(true);
        this.mPointerPaint.setStyle(Paint.Style.FILL);
        this.mPointerPaint.setColor(this.mPointerColor);
        this.mPointerPaint.setStrokeWidth(this.mPointerRadius);
        this.mPointerHaloPaint = new Paint();
        this.mPointerHaloPaint.set(this.mPointerPaint);
        this.mPointerHaloPaint.setColor(this.mPointerHaloColor);
        this.mPointerHaloPaint.setAlpha(this.mPointerAlpha);
        this.mPointerHaloPaint.setStrokeWidth(this.mPointerRadius + this.mPointerHaloWidth);
        this.mPointerHaloBorderPaint = new Paint();
        this.mPointerHaloBorderPaint.set(this.mPointerPaint);
        this.mPointerHaloBorderPaint.setStrokeWidth(this.mPointerHaloBorderWidth);
        this.mPointerHaloBorderPaint.setStyle(Paint.Style.STROKE);
    }


    public void calculateTotalDegrees() {
        this.mTotalCircleDegrees = (360.0f - (this.mStartAngle - this.mEndAngle)) % 360.0f;
        if (this.mTotalCircleDegrees <= 0.0f) {
            this.mTotalCircleDegrees = 360.0f;
        }
    }


    public void calculateProgressDegrees() {
        this.mProgressDegrees = this.mPointerPosition - this.mStartAngle;
        this.mProgressDegrees = this.mProgressDegrees < 0.0f ? this.mProgressDegrees + 360.0f :
                this.mProgressDegrees;
    }


    public void calculatePointerAngle() {
        this.mPointerPosition =
                ((((float) this.mProgress) / ((float) this.mMax)) * this.mTotalCircleDegrees) + this.mStartAngle;
        this.mPointerPosition %= 360.0f;
    }


    public void calculatePointerXYPosition() {
        PathMeasure pathMeasure = new PathMeasure(this.mCircleProgressPath, false);
        if (!pathMeasure.getPosTan(pathMeasure.getLength(), this.mPointerPositionXY,
                (float[]) null)) {
            new PathMeasure(this.mCirclePath, false).getPosTan(0.0f, this.mPointerPositionXY,
                    (float[]) null);
        }
    }


    public void initPaths() {
        this.mCirclePath = new Path();
        this.mCirclePath.addArc(this.mCircleRectF, this.mStartAngle, this.mTotalCircleDegrees);
        this.mCircleProgressPath = new Path();
        this.mCircleProgressPath.addArc(this.mCircleRectF, this.mStartAngle, this.mProgressDegrees);
    }


    public void initRects() {
        this.mCircleRectF.set(-this.mCircleWidth, -this.mCircleHeight, this.mCircleWidth,
                this.mCircleHeight);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate((float) (getWidth() / 2), (float) (getHeight() / 2));
        canvas.drawPath(this.mCirclePath, this.mCirclePaint);
        canvas.drawPath(this.mCircleProgressPath, this.mCircleProgressGlowPaint);
        canvas.drawPath(this.mCircleProgressPath, this.mCircleProgressPaint);
        canvas.drawPath(this.mCirclePath, this.mCircleFillPaint);
        canvas.drawCircle(this.mPointerPositionXY[0], this.mPointerPositionXY[1],
                this.mPointerRadius + this.mPointerHaloWidth, this.mPointerHaloPaint);
        canvas.drawCircle(this.mPointerPositionXY[0], this.mPointerPositionXY[1],
                this.mPointerRadius, this.mPointerPaint);
        if (this.mUserIsMovingPointer) {
            canvas.drawCircle(this.mPointerPositionXY[0], this.mPointerPositionXY[1],
                    this.mPointerRadius + this.mPointerHaloWidth + (this.mPointerHaloBorderWidth / DEFAULT_POINTER_HALO_BORDER_WIDTH), this.mPointerHaloBorderPaint);
        }
    }

    public int getProgress() {
        return Math.round((((float) this.mMax) * this.mProgressDegrees) / this.mTotalCircleDegrees);
    }

    public void setProgress(int i) {
        if (this.mProgress != i) {
            this.mProgress = i;
            if (this.mOnCircularSeekBarChangeListener != null) {
                this.mOnCircularSeekBarChangeListener.onProgressChanged(this, i, false);
            }
            recalculateAll();
            invalidate();
        }
    }


    public void setProgressBasedOnAngle(float f) {
        this.mPointerPosition = f;
        calculateProgressDegrees();
        this.mProgress =
                Math.round((((float) this.mMax) * this.mProgressDegrees) / this.mTotalCircleDegrees);
    }


    public void recalculateAll() {
        calculateTotalDegrees();
        calculatePointerAngle();
        calculateProgressDegrees();
        initRects();
        initPaths();
        calculatePointerXYPosition();
    }


    public void onMeasure(int i, int i2) {
        int defaultSize = getDefaultSize(getSuggestedMinimumHeight(), i2);
        int defaultSize2 = getDefaultSize(getSuggestedMinimumWidth(), i);
        if (this.mMaintainEqualCircle) {
            int min = Math.min(defaultSize2, defaultSize);
            setMeasuredDimension(min, min);
        } else {
            setMeasuredDimension(defaultSize2, defaultSize);
        }
        this.mCircleHeight =
                (((((float) defaultSize) / DEFAULT_POINTER_HALO_BORDER_WIDTH) - this.mCircleStrokeWidth) - this.mPointerRadius) - (this.mPointerHaloBorderWidth * 1.5f);
        this.mCircleWidth =
                (((((float) defaultSize2) / DEFAULT_POINTER_HALO_BORDER_WIDTH) - this.mCircleStrokeWidth) - this.mPointerRadius) - (this.mPointerHaloBorderWidth * 1.5f);
        if (this.mCustomRadii) {
            if (((this.mCircleYRadius - this.mCircleStrokeWidth) - this.mPointerRadius) - this.mPointerHaloBorderWidth < this.mCircleHeight) {
                this.mCircleHeight =
                        ((this.mCircleYRadius - this.mCircleStrokeWidth) - this.mPointerRadius) - (this.mPointerHaloBorderWidth * 1.5f);
            }
            if (((this.mCircleXRadius - this.mCircleStrokeWidth) - this.mPointerRadius) - this.mPointerHaloBorderWidth < this.mCircleWidth) {
                this.mCircleWidth =
                        ((this.mCircleXRadius - this.mCircleStrokeWidth) - this.mPointerRadius) - (this.mPointerHaloBorderWidth * 1.5f);
            }
        }
        if (this.mMaintainEqualCircle) {
            float min2 = Math.min(this.mCircleHeight, this.mCircleWidth);
            this.mCircleHeight = min2;
            this.mCircleWidth = min2;
        }
        recalculateAll();
    }

    public boolean isLockEnabled() {
        return this.lockEnabled;
    }

    public void setLockEnabled(boolean z) {
        this.lockEnabled = z;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        if (!this.isTouchEnabled) {
            return false;
        }
        float x = motionEvent.getX() - ((float) (getWidth() / 2));
        float y = motionEvent.getY() - ((float) (getHeight() / 2));
        float sqrt =
                (float) Math.sqrt(Math.pow((double) (this.mCircleRectF.centerX() - x), 2.0d) + Math.pow((double) (this.mCircleRectF.centerY() - y), 2.0d));
        float f2 = this.DPTOPX_SCALE * 48.0f;
        if (this.mCircleStrokeWidth < f2) {
            f = f2 / DEFAULT_POINTER_HALO_BORDER_WIDTH;
        } else {
            f = this.mCircleStrokeWidth / DEFAULT_POINTER_HALO_BORDER_WIDTH;
        }
        float max = Math.max(this.mCircleHeight, this.mCircleWidth) + f;
        float min = Math.min(this.mCircleHeight, this.mCircleWidth) - f;
        if (this.mPointerRadius >= f2 / DEFAULT_POINTER_HALO_BORDER_WIDTH) {
            float f3 = this.mPointerRadius;
        }
        float atan2 =
                (float) (((Math.atan2((double) y, (double) x) / 3.141592653589793d) * 180.0d) % 360.0d);
        if (atan2 < 0.0f) {
            atan2 += 360.0f;
        }
        this.cwDistanceFromStart = atan2 - this.mStartAngle;
        this.cwDistanceFromStart = this.cwDistanceFromStart < 0.0f ?
                this.cwDistanceFromStart + 360.0f : this.cwDistanceFromStart;
        this.ccwDistanceFromStart = 360.0f - this.cwDistanceFromStart;
        this.cwDistanceFromEnd = atan2 - this.mEndAngle;
        this.cwDistanceFromEnd = this.cwDistanceFromEnd < 0.0f ? this.cwDistanceFromEnd + 360.0f
                : this.cwDistanceFromEnd;
        this.ccwDistanceFromEnd = 360.0f - this.cwDistanceFromEnd;
        switch (motionEvent.getAction()) {
            case 0:
                double d = (double) (this.mPointerRadius * 180.0f);
                float f4 = sqrt;
                double max2 = (double) Math.max(this.mCircleHeight, this.mCircleWidth);
                Double.isNaN(max2);
                Double.isNaN(d);
                float f5 = (float) (d / (max2 * 3.141592653589793d));
                this.cwDistanceFromPointer = atan2 - this.mPointerPosition;
                this.cwDistanceFromPointer = this.cwDistanceFromPointer < 0.0f ?
                        this.cwDistanceFromPointer + 360.0f : this.cwDistanceFromPointer;
                this.ccwDistanceFromPointer = 360.0f - this.cwDistanceFromPointer;
                if (f4 < min || f4 > max || (this.cwDistanceFromPointer > f5 && this.ccwDistanceFromPointer > f5)) {
                    if (this.cwDistanceFromStart <= this.mTotalCircleDegrees) {
                        if (f4 >= min && f4 <= max) {
                            setProgressBasedOnAngle(atan2);
                            this.lastCWDistanceFromStart = this.cwDistanceFromStart;
                            this.mIsMovingCW = true;
                            this.mPointerHaloPaint.setAlpha(this.mPointerAlphaOnTouch);
                            this.mPointerHaloPaint.setColor(this.mPointerHaloColorOnTouch);
                            recalculateAll();
                            invalidate();
                            if (this.mOnCircularSeekBarChangeListener != null) {
                                this.mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                                this.mOnCircularSeekBarChangeListener.onProgressChanged(this,
                                        this.mProgress, true);
                            }
                            this.mUserIsMovingPointer = true;
                            this.lockAtEnd = false;
                            this.lockAtStart = false;
                            break;
                        } else {
                            this.mUserIsMovingPointer = false;
                            return false;
                        }
                    } else {
                        this.mUserIsMovingPointer = false;
                        return false;
                    }
                } else {
                    setProgressBasedOnAngle(this.mPointerPosition);
                    this.lastCWDistanceFromStart = this.cwDistanceFromStart;
                    this.mIsMovingCW = true;
                    this.mPointerHaloPaint.setAlpha(this.mPointerAlphaOnTouch);
                    this.mPointerHaloPaint.setColor(this.mPointerHaloColorOnTouch);
                    recalculateAll();
                    invalidate();
                    if (this.mOnCircularSeekBarChangeListener != null) {
                        this.mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                    }
                    this.mUserIsMovingPointer = true;
                    this.lockAtEnd = false;
                    this.lockAtStart = false;
                    break;
                }
            case 1:
                this.mPointerHaloPaint.setAlpha(this.mPointerAlpha);
                this.mPointerHaloPaint.setColor(this.mPointerHaloColor);
                if (this.mUserIsMovingPointer) {
                    this.mUserIsMovingPointer = false;
                    invalidate();
                    if (this.mOnCircularSeekBarChangeListener != null) {
                        this.mOnCircularSeekBarChangeListener.onStopTrackingTouch(this);
                        break;
                    }
                } else {
                    return false;
                }
                break;
            case 2:
                if (this.mUserIsMovingPointer) {
                    if (this.lastCWDistanceFromStart < this.cwDistanceFromStart) {
                        if (this.cwDistanceFromStart - this.lastCWDistanceFromStart <= 180.0f || this.mIsMovingCW) {
                            this.mIsMovingCW = true;
                        } else {
                            this.lockAtStart = true;
                            this.lockAtEnd = false;
                        }
                    } else if (this.lastCWDistanceFromStart - this.cwDistanceFromStart <= 180.0f || !this.mIsMovingCW) {
                        this.mIsMovingCW = false;
                    } else {
                        this.lockAtEnd = true;
                        this.lockAtStart = false;
                    }
                    if (this.lockAtStart && this.mIsMovingCW) {
                        this.lockAtStart = false;
                    }
                    if (this.lockAtEnd && !this.mIsMovingCW) {
                        this.lockAtEnd = false;
                    }
                    if (this.lockAtStart && !this.mIsMovingCW && this.ccwDistanceFromStart > 90.0f) {
                        this.lockAtStart = false;
                    }
                    if (this.lockAtEnd && this.mIsMovingCW && this.cwDistanceFromEnd > 90.0f) {
                        this.lockAtEnd = false;
                    }
                    if (!this.lockAtEnd && this.cwDistanceFromStart > this.mTotalCircleDegrees && this.mIsMovingCW && this.lastCWDistanceFromStart < this.mTotalCircleDegrees) {
                        this.lockAtEnd = true;
                    }
                    if (this.lockAtStart && this.lockEnabled) {
                        this.mProgress = 0;
                        recalculateAll();
                        invalidate();
                        if (this.mOnCircularSeekBarChangeListener != null) {
                            this.mOnCircularSeekBarChangeListener.onProgressChanged(this,
                                    this.mProgress, true);
                        }
                    } else if (this.lockAtEnd && this.lockEnabled) {
                        this.mProgress = this.mMax;
                        recalculateAll();
                        invalidate();
                        if (this.mOnCircularSeekBarChangeListener != null) {
                            this.mOnCircularSeekBarChangeListener.onProgressChanged(this,
                                    this.mProgress, true);
                        }
                    } else if (this.mMoveOutsideCircle || sqrt <= max) {
                        if (this.cwDistanceFromStart <= this.mTotalCircleDegrees) {
                            setProgressBasedOnAngle(atan2);
                        }
                        recalculateAll();
                        invalidate();
                        if (this.mOnCircularSeekBarChangeListener != null) {
                            this.mOnCircularSeekBarChangeListener.onProgressChanged(this,
                                    this.mProgress, true);
                        }
                    }
                    this.lastCWDistanceFromStart = this.cwDistanceFromStart;
                    break;
                } else {
                    return false;
                }
            case 3:
                this.mPointerHaloPaint.setAlpha(this.mPointerAlpha);
                this.mPointerHaloPaint.setColor(this.mPointerHaloColor);
                this.mUserIsMovingPointer = false;
                invalidate();
                break;
        }
        if (motionEvent.getAction() == 2 && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return true;
    }


    public void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, CircularSeekBarss, i, 0);
        initAttributes(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        initPaints();
    }


    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("PARENT", onSaveInstanceState);
        bundle.putInt("MAX", this.mMax);
        bundle.putInt("PROGRESS", this.mProgress);
        bundle.putInt("mCircleColor", this.mCircleColor);
        bundle.putInt("mCircleProgressColor", this.mCircleProgressColor);
        bundle.putInt("mPointerColor", this.mPointerColor);
        bundle.putInt("mPointerHaloColor", this.mPointerHaloColor);
        bundle.putInt("mPointerHaloColorOnTouch", this.mPointerHaloColorOnTouch);
        bundle.putInt("mPointerAlpha", this.mPointerAlpha);
        bundle.putInt("mPointerAlphaOnTouch", this.mPointerAlphaOnTouch);
        bundle.putBoolean("lockEnabled", this.lockEnabled);
        bundle.putBoolean("isTouchEnabled", this.isTouchEnabled);
        return bundle;
    }


    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("PARENT"));
        this.mMax = bundle.getInt("MAX");
        this.mProgress = bundle.getInt("PROGRESS");
        this.mCircleColor = bundle.getInt("mCircleColor");
        this.mCircleProgressColor = bundle.getInt("mCircleProgressColor");
        this.mPointerColor = bundle.getInt("mPointerColor");
        this.mPointerHaloColor = bundle.getInt("mPointerHaloColor");
        this.mPointerHaloColorOnTouch = bundle.getInt("mPointerHaloColorOnTouch");
        this.mPointerAlpha = bundle.getInt("mPointerAlpha");
        this.mPointerAlphaOnTouch = bundle.getInt("mPointerAlphaOnTouch");
        this.lockEnabled = bundle.getBoolean("lockEnabled");
        this.isTouchEnabled = bundle.getBoolean("isTouchEnabled");
        initPaints();
        recalculateAll();
    }

    public void setOnSeekBarChangeListener(OnCircularSeekBarChangeListener onCircularSeekBarChangeListener) {
        this.mOnCircularSeekBarChangeListener = onCircularSeekBarChangeListener;
    }

    public int getCircleColor() {
        return this.mCircleColor;
    }

    public void setCircleColor(int i) {
        this.mCircleColor = i;
        this.mCirclePaint.setColor(this.mCircleColor);
        invalidate();
    }

    public int getCircleProgressColor() {
        return this.mCircleProgressColor;
    }

    public void setCircleProgressColor(int i) {
        this.mCircleProgressColor = i;
        this.mCircleProgressPaint.setColor(this.mCircleProgressColor);
        invalidate();
    }

    public int getPointerColor() {
        return this.mPointerColor;
    }

    public void setPointerColor(int i) {
        this.mPointerColor = i;
        this.mPointerPaint.setColor(this.mPointerColor);
        invalidate();
    }

    public int getPointerHaloColor() {
        return this.mPointerHaloColor;
    }

    public void setPointerHaloColor(int i) {
        this.mPointerHaloColor = i;
        this.mPointerHaloPaint.setColor(this.mPointerHaloColor);
        invalidate();
    }

    public int getPointerAlpha() {
        return this.mPointerAlpha;
    }

    public void setPointerAlpha(int i) {
        if (i >= 0 && i <= 255) {
            this.mPointerAlpha = i;
            this.mPointerHaloPaint.setAlpha(this.mPointerAlpha);
            invalidate();
        }
    }

    public int getPointerAlphaOnTouch() {
        return this.mPointerAlphaOnTouch;
    }

    public void setPointerAlphaOnTouch(int i) {
        if (i >= 0 && i <= 255) {
            this.mPointerAlphaOnTouch = i;
        }
    }

    public int getCircleFillColor() {
        return this.mCircleFillColor;
    }

    public void setCircleFillColor(int i) {
        this.mCircleFillColor = i;
        this.mCircleFillPaint.setColor(this.mCircleFillColor);
        invalidate();
    }

    public synchronized int getMax() {
        return this.mMax;
    }

    public void setMax(int i) {
        if (i > 0) {
            if (i <= this.mProgress) {
                this.mProgress = 0;
                if (this.mOnCircularSeekBarChangeListener != null) {
                    this.mOnCircularSeekBarChangeListener.onProgressChanged(this, this.mProgress,
                            false);
                }
            }
            this.mMax = i;
            recalculateAll();
            invalidate();
        }
    }

    public boolean getIsTouchEnabled() {
        return this.isTouchEnabled;
    }

    public void setIsTouchEnabled(boolean z) {
        this.isTouchEnabled = z;
    }

    public interface OnCircularSeekBarChangeListener {
        void onProgressChanged(CircularSeekBar circularSeekBar, int i, boolean z);

        void onStartTrackingTouch(CircularSeekBar circularSeekBar);

        void onStopTrackingTouch(CircularSeekBar circularSeekBar);
    }
}
