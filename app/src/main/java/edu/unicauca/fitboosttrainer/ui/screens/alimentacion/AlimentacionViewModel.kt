package edu.unicauca.fitboosttrainer.ui.screens.alimentacion

import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Category(val title: String)

class AlimentacionViewModel : ViewModel() {

    private val _categories = MutableStateFlow(
        listOf(
            Category("Snack"),
            Category("Desayuno"),
            Category("Almuerzo"),
            Category("Cena")
        )
    )
    val categories: StateFlow<List<Category>> = _categories

    private val _selectedNavItem = MutableStateFlow(BottomNavItem.ALIMENTACION)
    val selectedNavItem: StateFlow<BottomNavItem> = _selectedNavItem

    fun onNavItemSelected(item: BottomNavItem) {
        _selectedNavItem.value = item
    }
}
