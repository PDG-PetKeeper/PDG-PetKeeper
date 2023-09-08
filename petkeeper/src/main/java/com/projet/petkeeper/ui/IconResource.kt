package com.projet.petkeeper.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
/**
 * Class for representing icons as Painters, allowing the use of both
 * Drawable resources and ImageVectors as icons in Compose.
 *
 * @param resID The resource ID of the Drawable. Null if using an ImageVector.
 * @param imageVector The ImageVector to be used as the icon. Null if using a Drawable resource.
 */
class IconResource private constructor(
    @DrawableRes private val resID: Int?,
    private val imageVector: ImageVector?
) {

    /**
     * Converts the IconResource into a Composable Painter resource.
     *
     * @return A Composable Painter resource representing the icon.
     */
    @Composable
    fun asPainterResource(): Painter {
        resID?.let {
            return painterResource(id = resID)
        }
        return rememberVectorPainter(image = imageVector!!)
    }

    companion object {
        /**
         * Creates an IconResource from a Drawable resource.
         *
         * @param resID The resource ID of the Drawable.
         * @return An IconResource instance representing the Drawable resource.
         */
        fun fromDrawableResource(@DrawableRes resID: Int): IconResource {
            return IconResource(resID, null)
        }

        /**
         * Creates an IconResource from an ImageVector.
         *
         * @param imageVector The ImageVector to be used as the icon.
         * @return An IconResource instance representing the ImageVector.
         */
        fun fromImageVector(imageVector: ImageVector?): IconResource {
            return IconResource(null, imageVector)
        }
    }
}