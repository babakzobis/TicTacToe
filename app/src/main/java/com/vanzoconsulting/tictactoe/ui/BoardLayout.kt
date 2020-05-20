package com.vanzoconsulting.tictactoe.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style.STROKE
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.view.children
import androidx.gridlayout.widget.GridLayout

@SuppressLint("ResourceType")
class BoardLayout: GridLayout {

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        style = STROKE
        strokeWidth = DEFAULT_DIVIDER_WIDTH
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        context.obtainStyledAttributes(TypedValue().data, intArrayOf(
            android.R.attr.listDivider,
            android.R.attr.dividerHeight
        )).apply {
            paint.color = getColor(0, 0)
            paint.strokeWidth = getDimension(1, DEFAULT_DIVIDER_WIDTH)
        } .recycle()
    }

    override fun dispatchDraw(c: Canvas?) {
        c?.let { canvas ->
            children.forEachIndexed { index, view ->
                val left = view.left.toFloat()
                val top = view.top.toFloat()
                val right = view.right.toFloat()
                val bottom = view.bottom.toFloat()

                if (index % ROW_COLUMN_COUNT == 0) {
                    canvas.drawLine(right - 1, top, right - 1, bottom, paint)
                } else {
                    canvas.drawLine(left, top, left, bottom, paint)
                }

                if (index >= columnCount) {
                    canvas.drawLine(left, top, right, top, paint)
                }
            }
        }

        super.dispatchDraw(c)
    }

    companion object {
        const val ROW_COLUMN_COUNT = 3
        const val DEFAULT_DIVIDER_WIDTH = 16.0f
    }

}