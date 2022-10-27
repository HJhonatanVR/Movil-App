package pe.edu.upeu.primereval.domain.repository

import pe.edu.upeu.primereval.domain.model.User
import pe.edu.upeu.primereval.utils.DataState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(email: String, password: String): Flow<DataState<Boolean>>

    suspend fun signUp(user: User, password: String): Flow<DataState<User>>

    suspend fun logOut(): Flow<DataState<Boolean>>

    suspend fun getUserData(): Flow<DataState<Boolean>>

    suspend fun saveUser(user: User): Flow<DataState<Boolean>>

}