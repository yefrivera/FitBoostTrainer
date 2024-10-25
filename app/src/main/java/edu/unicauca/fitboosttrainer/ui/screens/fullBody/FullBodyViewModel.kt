package edu.unicauca.fitboosttrainer.ui.screens.fullBody

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem

class FullBodyViewModel : ViewModel() {

    private val _selectedNavItem = mutableStateOf(BottomNavItem.RUTINAS)
    val selectedNavItem: State<BottomNavItem> = _selectedNavItem

    fun selectNavItem(item: BottomNavItem) {
        _selectedNavItem.value = item
    }
}