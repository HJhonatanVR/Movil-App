package pe.edu.upeu.primereval.domain.usecases.empresa

import pe.edu.upeu.primereval.domain.repository.EmpresasRepository
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.primereval.domain.model.Empresa
import javax.inject.Inject

class SaveEmpresaUseCase @Inject constructor(
    private val bichosRepository: EmpresasRepository
) {

    suspend operator fun invoke(bicho: Empresa): Flow<DataState<Boolean>> =
        bichosRepository.saveBicho(bicho)
}