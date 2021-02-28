package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@HiltViewModel
class CrewDetailViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    var crewId = ""

    private var _crewDetails = MutableLiveData<Crew>()
    val crewDetails: LiveData<Crew>
        get() = _crewDetails

    private var _crew = MutableLiveData<Result>(Loading)
    val crew: LiveData<Result>
        get() {
            viewModelScope.launch {
                _crew.postValue(Loading)
                if (networkHelper.isNetworkConnected()) {
                    repository.getCrewById(crewId).let { response ->
                        if (response.isSuccessful) {
                            _crew.postValue(Success(response.body()))
                            _crewDetails.postValue(response.body()!!)
                        } else _crew.postValue(Error(response.errorBody().toString()))
                    }
                } else _crew.postValue(Error("No internet connection!"))
            }
            return _crew
        }
}