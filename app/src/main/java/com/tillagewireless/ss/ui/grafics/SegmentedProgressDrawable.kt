package com.tillagewireless.ss.ui.grafics

import android.graphics.*
import android.graphics.drawable.Drawable


class SegmentedProgressDrawable(
    private val parts: Int,
    private val fillColor: Int,
    private val emptyColor: Int,
    private val separatorColor: Int
) :
    Drawable() {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cutOffWidth = 0
    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {

        // Calculate values
        val bounds: Rect = bounds
        val actualWidth: Int = bounds.width()
        val actualHeight: Int = bounds.height()


        //width with dividers + segment width
        val fullBlockWidth = (actualWidth / parts)
        //ToDo: to change the width of segment change this line
        val segmentWidth = (fullBlockWidth * 0.5f).toInt()
        //        int dividerWidth =fullBlockWidth-segmentWidth;
        cutOffWidth = (level * actualWidth / 10000)

        //Draw separator as background
        val fullBox = RectF(0F, 0F, actualWidth.toFloat(), actualHeight.toFloat())
        paint.color = separatorColor
        canvas.drawRect(fullBox, paint)

        //start drawing lines as segmented bars
        var startX = 0
        for (i in 0 until parts) {
            val endX = startX + segmentWidth

            //in ideal condition this would be the rectangle
            val part = RectF(startX.toFloat(), 0F, endX.toFloat(), actualHeight.toFloat())

            //if the segment is below level the paint color should be fill color
            when {
                startX + segmentWidth <= cutOffWidth -> {
                    paint.color = fillColor
                    canvas.drawRect(part, paint)
                }
                startX < cutOffWidth -> {
                    val part1 = RectF(startX.toFloat(), 0F, cutOffWidth.toFloat(),
                        actualHeight.toFloat()
                    )
                    paint.color = fillColor
                    canvas.drawRect(part1, paint)
                    val part2 = RectF(
                        cutOffWidth.toFloat(), 0F,
                        (startX + segmentWidth).toFloat(), actualHeight.toFloat()
                    )
                    paint.color = emptyColor
                    canvas.drawRect(part2, paint)
                }
                else -> {
                    paint.color = emptyColor
                    canvas.drawRect(part, paint)
                }
            }

            //update the startX to start the new segment with the gap of divider and segment width
            startX += fullBlockWidth
        }
    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

}