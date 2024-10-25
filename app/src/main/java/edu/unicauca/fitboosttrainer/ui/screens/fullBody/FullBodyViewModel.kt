package edu.unicauca.fitboosttrainer.ui.screens.fullBody

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem

class FullBodyViewModel : ViewModel() {
    // Estado para el elemento de navegación seleccionado
    private val _selectedNavItem = mutableStateOf(BottomNavItem.RUTINAS)
    val selectedNavItem: State<BottomNavItem> = _selectedNavItem

    // Función para cambiar el elemento seleccionado
    fun selectNavItem(item: BottomNavItem) {
        _selectedNavItem.value = item
    }
}