package pe.edu.upeu.primereval.domain.usecases.empresa

import pe.edu.upeu.primereval.domain.repository.EmpresasRepository
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteEmpresaUseCase @Inject constructor(
    private val bichosRepository: EmpresasRepository
) {

    suspend operator fun invoke(id: String): Flow<DataState<Boolean>> =
        bichosRepository.deleteBicho(id)
}