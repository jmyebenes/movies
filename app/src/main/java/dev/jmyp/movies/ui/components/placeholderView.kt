package dev.jmyp.movies.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun placeholderView(
    backgroundColor: Color,
    icon: ImageVector,
    iconColor: Color
): Painter {

    val iconPainter = rememberVectorPainter(icon)

    return remember(backgroundColor, icon, iconColor) {
        object : Painter() {
            override val intrinsicSize: Size = Size.Unspecified

            override fun DrawScope.onDraw() {
                drawRect(backgroundColor)

                val iconSize = size.minDimension * 0.5f
                val left = (size.width - iconSize) / 2f
                val top = (size.height - iconSize) / 2f

                translate(left, top) {
                    with(iconPainter) {
                        draw(
                            size = Size(iconSize, iconSize),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconColor)
                        )
                    }
                }
            }
        }
    }
}