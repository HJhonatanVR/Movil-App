package pe.edu.upeu.primereval.domain.repository

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import pe.edu.upeu.primereval.domain.model.Empresa
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow

interface EmpresasRepository {


    suspend fun saveBicho(bicho: Empresa): Flow<DataState<Boolean>>

    suspend fun getAllBichos(): Flow<DataState<List<Empresa>>>

    fun saveBichoImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment)

    suspend fun deleteBicho(id: String): Flow<DataState<Boolean>>
}