package pe.edu.upeu.primereval.domain.usecases.empresa

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import pe.edu.upeu.primereval.domain.repository.EmpresasRepository
import javax.inject.Inject

class SaveEmpresaImageUseCase @Inject constructor(
    private val bichosRepository: EmpresasRepository
) {

    operator fun invoke(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment) =
        bichosRepository.saveBichoImage(activity, imageFileURI, imageType, fragment)
}