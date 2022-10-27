package pe.edu.upeu.primereval.di

import pe.edu.upeu.primereval.data.remote.EmpresasRepositoryImpl
import pe.edu.upeu.primereval.domain.repository.EmpresasRepository
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmpresasModule {

    @Provides
    @Singleton
    fun provideBichosRepository(
        @FirebaseModule.BichosCollection bichosCollection: CollectionReference
    ): EmpresasRepository {
        return EmpresasRepositoryImpl(
            bichosCollection
        )
    }

}