package pe.edu.upeu.primereval.domain.usecases.logout

import pe.edu.upeu.primereval.domain.repository.LoginRepository
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(): Flow<DataState<Boolean>> =
        loginRepository.logOut()
}