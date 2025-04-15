package com.tlog.viewmodel.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CartViewModelFactory(
    private val userId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST") // 강제 형변환 경고 무시
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java))
            return CartViewModel(userId) as T
        throw IllegalArgumentException("Unknown ViewModel class") // 다른 뷰모델 생성하려고 하면 에러 ㄷ
    }

}