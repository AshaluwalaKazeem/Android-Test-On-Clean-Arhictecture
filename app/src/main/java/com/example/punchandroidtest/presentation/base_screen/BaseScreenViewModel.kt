package com.example.punchandroidtest.presentation.base_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseScreenViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel()
{

}