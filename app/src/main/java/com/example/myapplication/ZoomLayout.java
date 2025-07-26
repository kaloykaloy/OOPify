package com.example.myapplication;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class ZoomLayout extends ViewGroup {

    private ScaleGestureDetector scaleDetector;
    private Matrix matrix = new Matrix();
    private float[] matrixValues = new float[9];

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    private PointF last = new PointF();
    private PointF start = new PointF();
    private float minScale = 0.5f;
    private float maxScale = 3.0f;
    private float saveScale = 1f;
    private float origWidth, origHeight;
    private int viewWidth, viewHeight;

    private boolean isCenteredInitially = false;

    public ZoomLayout(Context context) {
        super(context);
        init(context);
    }

    public ZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        setWillNotDraw(false);
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);

        PointF current = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last.set(current);
                start.set(last);
                mode = DRAG;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    float dx = current.x - last.x;
                    float dy = current.y - last.y;

                    if (saveScale > minScale) {
                        matrix.postTranslate(dx, dy);
                        fixTranslation();
                    }
                    last.set(current.x, current.y);
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }

        setImageMatrix();
        invalidate();
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());

            if (changed) {
                viewWidth = r - l;
                viewHeight = b - t;

                origWidth = child.getMeasuredWidth();
                origHeight = child.getMeasuredHeight();

                if (!isCenteredInitially) {
                    centerContentInitially();
                    isCenteredInitially = true;
                }
            }

            setImageMatrix();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void setImageMatrix() {
        matrix.getValues(matrixValues);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setPivotX(0);
            child.setPivotY(0);
            child.setScaleX(matrixValues[Matrix.MSCALE_X]);
            child.setScaleY(matrixValues[Matrix.MSCALE_Y]);
            child.setTranslationX(matrixValues[Matrix.MTRANS_X]);
            child.setTranslationY(matrixValues[Matrix.MTRANS_Y]);
        }
    }

    private void fixTranslation() {
        matrix.getValues(matrixValues);
        float transX = matrixValues[Matrix.MTRANS_X];
        float transY = matrixValues[Matrix.MTRANS_Y];

        float fixTransX = getFixTranslation(transX, viewWidth, origWidth * saveScale);
        float fixTransY = getFixTranslation(transY, viewHeight, origHeight * saveScale);

        if (fixTransX != 0 || fixTransY != 0) {
            matrix.postTranslate(fixTransX, fixTransY);
        }
    }

    private float getFixTranslation(float trans, float viewSize, float contentSize) {
        float minTrans, maxTrans;

        if (contentSize <= viewSize) {
            minTrans = 0;
            maxTrans = viewSize - contentSize;
        } else {
            minTrans = viewSize - contentSize;
            maxTrans = 0;
        }

        if (trans < minTrans) {
            return -trans + minTrans;
        }
        if (trans > maxTrans) {
            return -trans + maxTrans;
        }
        return 0;
    }

    private void centerContentInitially() {
        float translateX = (viewWidth - origWidth * saveScale) / 2;
        float translateY = (viewHeight - origHeight * saveScale) / 2;
        matrix.postTranslate(translateX, translateY);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            float prevScale = saveScale;
            saveScale *= scaleFactor;

            saveScale = Math.max(minScale, Math.min(saveScale, maxScale));
            scaleFactor = saveScale / prevScale;

            if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight) {
                matrix.postScale(scaleFactor, scaleFactor, viewWidth / 2, viewHeight / 2);
            } else {
                matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            }

            fixTranslation();
            setImageMatrix();
            return true;
        }
    }
}
