package ru.alexmaryin.spacextimes_rx.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success

class CapsulesViewModel(private val repository: SpacexDataRepository): ViewModel() {

    private val capsules = MutableLiveData<Result>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchCapsules()
    }

    private fun fetchCapsules() {
        capsules.postValue(Loading)
        compositeDisposable.add(
            repository.getCapsules()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { capsulesList ->
                    capsules.postValue(Success(capsulesList))
                }, { throwable ->
                    capsules.postValue(Error("Error while fetching data from server:\n ${throwable.localizedMessage}"))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getCapsules(): LiveData<Result> = capsules
}