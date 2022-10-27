package pe.edu.upeu.primereval.domain.usecases.login

import pe.edu.upeu.primereval.domain.repository.LoginRepository
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(email: String, password: String): Flow<DataState<Boolean>> =
        loginRepository.login(email, password)
}