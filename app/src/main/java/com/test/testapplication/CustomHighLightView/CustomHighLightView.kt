package com.test.testapplication.CustomHighLightView

import android.R.color.holo_blue_dark
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

import com.test.testapplication.R

/**
 * TODO: document your custom view class.
 */
class CustomHighLightView : View {
    private var mExampleString: String? = null // TODO: use a default from R.string...
    private var mExampleColor = Color.RED // TODO: use a default from R.color...
    private var mExampleDimension = 0f // TODO: use a default from R.dimen...
    private var mCenterValX = 0f // TODO: use a default from R.dimen...
    private var mCenterValY = 0f // TODO: use a default from R.dimen...

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    var exampleDrawable: Drawable? = null

    private var mTextPaint: TextPaint? = null
    private var mTextWidth: Float = 0.toFloat()
    private var mTextHeight: Float = 0.toFloat()

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    var exampleString: String?
        get() = mExampleString
        set(exampleString) {
            mExampleString = exampleString
            invalidateTextPaintAndMeasurements()
        }
    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    var exampleColor: Int
        get() = mExampleColor
        set(exampleColor) {
            mExampleColor = exampleColor
            invalidateTextPaintAndMeasurements()
        }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    var exampleDimension: Float
        get() = mExampleDimension
        set(exampleDimension) {
            mExampleDimension = exampleDimension
            invalidateTextPaintAndMeasurements()
        }
    var centerValX: Float
        get() = mCenterValX
        set(centerValX) {
            mCenterValX = centerValX
            this.invalidate()
        }
    var centerValY: Float
        get() = mCenterValY
        set(centerValY) {
            mCenterValY = centerValY
            this.invalidate()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.CustomHighLightView, defStyle, 0)

        mExampleString = a.getString(
                R.styleable.CustomHighLightView_exampleString)
        mExampleColor = a.getColor(
                R.styleable.CustomHighLightView_exampleColor,
                mExampleColor)
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.CustomHighLightView_exampleDimension,
                mExampleDimension)

        if (a.hasValue(R.styleable.CustomHighLightView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                    R.styleable.CustomHighLightView_exampleDrawable)
            exampleDrawable!!.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        mTextPaint = TextPaint()
        mTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        mTextPaint!!.textAlign = Paint.Align.LEFT

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
//        mTextPaint!!.textSize = mExampleDimension
//        mTextPaint!!.color = mExampleColor
////        mTextWidth = mTextPaint!!.measureText(mExampleString)
//
//        val fontMetrics = mTextPaint!!.fontMetrics
//        mTextHeight = fontMetrics.bottom
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
//
//        // Draw the text.
//        canvas.drawText(mExampleString!!,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint!!)
        var paint = Paint()
        paint.color = Color.TRANSPARENT
        paint.style = Paint.Style.FILL

        var paint2 = Paint()
        paint.strokeWidth = 40f
        paint2.color = Color.parseColor("#ff0099cc")
        paint2.style = Paint.Style.FILL_AND_STROKE


        if (centerValX > 0f || centerValY > 0f) {
            canvas.drawCircle(centerValX, centerValY, 40f, paint)
        }


        // Draw the example drawable on top of the text.
        if (exampleDrawable != null) {
            exampleDrawable!!.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight)
            exampleDrawable!!.draw(canvas)
        }
//        invalidate()
    }


}
