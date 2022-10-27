package pe.edu.upeu.primereval.domain.usecases.signup

import pe.edu.upeu.primereval.domain.model.User
import pe.edu.upeu.primereval.domain.repository.LoginRepository
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(user: User): Flow<DataState<Boolean>> =
        loginRepository.saveUser(user)
}