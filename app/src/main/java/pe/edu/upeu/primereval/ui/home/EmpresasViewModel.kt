package pe.edu.upeu.primereval.ui.home

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pe.edu.upeu.primereval.domain.usecases.empresa.DeleteEmpresaUseCase
import pe.edu.upeu.primereval.domain.usecases.empresa.GetEmpresasUseCase
import pe.edu.upeu.primereval.domain.usecases.empresa.SaveEmpresaImageUseCase
import pe.edu.upeu.primereval.domain.usecases.empresa.SaveEmpresaUseCase
import pe.edu.upeu.primereval.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pe.edu.upeu.primereval.domain.model.Empresa
import javax.inject.Inject


@HiltViewModel
class EmpresasViewModel @Inject constructor(
    private val getAllBichosUseCase: GetEmpresasUseCase,
    private val saveBichosUseCase: SaveEmpresaUseCase,
    private val saveBichoImageUseCase: SaveEmpresaImageUseCase,
    private val deleteBichoUseCase: DeleteEmpresaUseCase
): ViewModel(){


    private val _getBichosState: MutableLiveData<DataState<List<Empresa>>> = MutableLiveData()
    val getBichosState: LiveData<DataState<List<Empresa>>>
        get() = _getBichosState

    private val _saveBichoState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val saveBichoState: LiveData<DataState<Boolean>>
        get() = _saveBichoState

    private val _deleteBichoState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val deleteBichoState: LiveData<DataState<Boolean>>
        get() = _deleteBichoState

    fun getAllBichos(){
        viewModelScope.launch {
            getAllBichosUseCase()
                .onEach { dataState ->
                    _getBichosState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun saveBicho(bicho: Empresa){
        viewModelScope.launch {
            saveBichosUseCase(bicho)
                .onEach { dataState ->
                    _saveBichoState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun deleteBicho(id: String){
        viewModelScope.launch {
            deleteBichoUseCase(id)
                .onEach { dataState ->
                    _deleteBichoState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun saveBichoImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment){
        saveBichoImageUseCase(activity, imageFileURI, imageType, fragment)
    }
}