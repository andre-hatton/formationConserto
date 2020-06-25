package pro.conserto.formationconserto.tools

import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.View

/**
 * Change la visibilité d'un élément
 * @param isVisible true pour affiché et false pour caché
 */
fun View.visible(isVisible: Boolean = true) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

/**
 * Change the data in MutableList with new list
 * @param dataList The new list
 */
inline fun <reified T> MutableList<T>.setList(dataList: List<T>) {
    clear()
    addAll(dataList)
}

/**
 * Handle Bitmap rotation
 * @param angle of rotation (in degree)
 * @return updated Bitmap
 */
fun Bitmap.rotate(angle: Float): Bitmap {
    if (angle == 0f) {
        return this
    }
    val matrix = Matrix()
    matrix.postRotate(angle)
    val rotatedImg = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    recycle()
    return rotatedImg
}