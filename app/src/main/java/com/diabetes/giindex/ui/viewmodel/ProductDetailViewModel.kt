package com.diabetes.giindex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.local.entity.Product
import com.diabetes.giindex.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val productId: Long
) : ViewModel() {
    
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadProduct()
    }
    
    private fun loadProduct() {
        viewModelScope.launch {
            _isLoading.value = true
            _product.value = repository.getProductById(productId)
            _isLoading.value = false
        }
    }
    
    fun toggleFavorite() {
        viewModelScope.launch {
            _product.value?.let { product ->
                repository.toggleFavorite(product.id, product.isFavorite)
                _product.value = product.copy(isFavorite = !product.isFavorite)
            }
        }
    }
    
    fun updateTranslation(newNameRu: String) {
        viewModelScope.launch {
            _product.value?.let { product ->
                repository.updateTranslation(
                    productId = product.id,
                    nameRu = newNameRu,
                    source = "MANUAL",
                    verified = true
                )
                _product.value = product.copy(
                    nameRu = newNameRu,
                    translationVerified = true
                )
            }
        }
    }
    
    fun getGiCategory(): String {
        return when (_product.value?.gi ?: 0) {
            in 0..55 -> "Низкий"
            in 56..69 -> "Средний"
            else -> "Высокий"
        }
    }
    
    fun getGiColor(): Long {
        return when (_product.value?.gi ?: 0) {
            in 0..55 -> 0xFF4CAF50
            in 56..69 -> 0xFFFF9800
            else -> 0xFFF44336
        }
    }
}

class ProductDetailViewModelFactory(
    private val repository: ProductRepository,
    private val productId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(repository, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
