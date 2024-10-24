package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.R

class InitialViewModel : ViewModel() {
    private val _imageIndex = mutableStateOf(0)
    val imageIndex: State<Int> = _imageIndex

    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    fun previousImage() {
        _imageIndex.value = (_imageIndex.value - 1 + images.size) % images.size
    }

    fun nextImage() {
        _imageIndex.value = (_imageIndex.value + 1) % images.size
    }
}