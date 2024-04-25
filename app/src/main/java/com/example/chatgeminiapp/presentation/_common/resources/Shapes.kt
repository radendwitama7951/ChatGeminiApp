package com.example.chatgeminiapp.presentation._common.resources

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class MirrorHalfEclipseButtonShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()
            moveTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(size.width * .3f, size.height)
            arcTo(
                rect = Rect(
                    offset = Offset(0f, 0f),
                    size = Size(size.width * .3f, size.height)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            close()
        }
        return Outline.Generic(path)
    }

}

private class HalfEclipseButtonShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()
            lineTo(size.width * .7f, 0f)
            arcTo(
                rect = Rect(
                    offset = Offset(size.width * .7f, 0f),
                    size = Size(size.width * .3f, size.height)
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }

}
