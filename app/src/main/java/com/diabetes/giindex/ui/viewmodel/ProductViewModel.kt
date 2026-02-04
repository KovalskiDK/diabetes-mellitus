package com.diabetes.giindex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.local.entity.Product
import com.diabetes.giindex.data.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()
    
    private val _selectedGiRange = MutableStateFlow<GiRange>(GiRange.ALL)
    val selectedGiRange: StateFlow<GiRange> = _selectedGiRange.asStateFlow()
    
    val products: StateFlow<List<Product>> = combine(
        _searchQuery,
        _selectedCategory,
        _selectedGiRange
    ) { query, category, giRange ->
        Triple(query, category, giRange)
    }.flatMapLatest { (query, category, giRange) ->
        when {
            query.isNotBlank() -> repository.searchProducts(query)
            category != null -> repository.getProductsByCategory(category)
            giRange != GiRange.ALL -> repository.getProductsByGiRange(giRange.min, giRange.max)
            else -> repository.getAllProducts()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    val favoriteProducts: StateFlow<List<Product>> = repository.getFavoriteProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val categories: StateFlow<List<String>> = repository.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun selectCategory(category: String?) {
        _selectedCategory.value = category
        _searchQuery.value = ""
    }
    
    fun selectGiRange(range: GiRange) {
        _selectedGiRange.value = range
        _searchQuery.value = ""
        _selectedCategory.value = null
    }
    
    fun toggleFavorite(productId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(productId, !isFavorite)
        }
    }
    
    fun clearFilters() {
        _searchQuery.value = ""
        _selectedCategory.value = null
        _selectedGiRange.value = GiRange.ALL
    }
}

enum class GiRange(val min: Int, val max: Int, val label: String) {
    ALL(0, 200, "Все"),
    LOW(0, 55, "Низкий (1-55)"),
    MEDIUM(56, 69, "Средний (56-69)"),
    HIGH(70, 200, "Высокий (70+)")
}

class ProductViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
