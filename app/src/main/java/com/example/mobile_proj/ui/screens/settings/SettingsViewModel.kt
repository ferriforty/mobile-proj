package com.example.mobile_proj.ui.screens.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_proj.MainActivity
import com.example.mobile_proj.activities.Authentication
import com.example.mobile_proj.data.models.Theme
import com.example.mobile_proj.data.repositories.ThemeRepository
import com.example.mobile_proj.database.Connection
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ThemeState(val theme: Theme)

class ThemeViewModel(
    private val repository: ThemeRepository
) : ViewModel() {
    val state = repository.theme.map { ThemeState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ThemeState(Theme.System)
    )
    fun changeTheme(theme: Theme) = viewModelScope.launch {
        repository.setTheme(theme)
    }
}

fun logOut(db: Connection, context: Context) {
    db.deleteSharedPreference()
    context.startActivity(Intent(context, Authentication::class.java))
    (context as Activity).finish()
}