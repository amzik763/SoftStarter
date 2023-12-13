package com.tillagewireless.ss.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tillagewireless.ss.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout(refreshToken: String)
        = withContext(Dispatchers.IO) { repository.logout(refreshToken) }
}