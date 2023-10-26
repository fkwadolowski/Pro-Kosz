package com.example.first_mgr

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint = Paint()
    private var path: Path = Path()
    private var backgroundBitmap: Bitmap = BitmapFactory.decodeResource(
        resources,
        R.drawable.basketball_court_full
    )
    private var isHalfCourt = false
    init {
        paint.color = Color.GREEN
        paint.isAntiAlias = true
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var centerX = 0f
        var centerY = 0f
        if (!isHalfCourt) {
            centerX = (width - backgroundBitmap.width) / 2f
            centerY = (height - backgroundBitmap.height) / 2f
        }
        if (isHalfCourt) {
            val matrix = Matrix()
            matrix.setScale(1.2f, 1.2f)
            matrix.postTranslate(centerX, centerY)
            canvas.drawBitmap(backgroundBitmap, matrix, null)
        } else {
            canvas.drawBitmap(backgroundBitmap, centerX, centerY, null)
        }
        canvas.drawPath(path, paint)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
            } }
        invalidate()
        return true
    }
    fun clear() {
        path.reset()
        invalidate()
    }
    fun changeBackground() {
        if (isHalfCourt) {
            backgroundBitmap = BitmapFactory.decodeResource(
                resources,
                R.drawable.basketball_court_full
            )
        } else {
            backgroundBitmap = BitmapFactory.decodeResource(
                resources,
                R.drawable.basketball_court_half
            ) }
        isHalfCourt = !isHalfCourt
        invalidate()
    } }


