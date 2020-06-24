package pro.conserto.formationconserto.tools

import android.view.View

/**
 * Change la visibilité d'un élément
 * @param isVisible true pour affiché et false pour caché
 */
fun View.visible(isVisible: Boolean = true) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}