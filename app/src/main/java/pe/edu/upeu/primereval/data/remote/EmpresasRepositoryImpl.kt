package pe.edu.upeu.primereval.data.remote

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import pe.edu.upeu.primereval.di.FirebaseModule
import pe.edu.upeu.primereval.domain.model.Empresa
import pe.edu.upeu.primereval.domain.repository.EmpresasRepository
import pe.edu.upeu.primereval.ui.home.AddEditEmpresaFragment
import pe.edu.upeu.primereval.utils.DataState
import pe.edu.upeu.primereval.utils.StorageUtils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmpresasRepositoryImpl @Inject constructor(
    @FirebaseModule.BichosCollection private val bichosCollection: CollectionReference
): EmpresasRepository{
    override suspend fun saveBicho(bicho: Empresa): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var uploadSuccessful: Boolean = false
            bichosCollection.document(bicho.id).set(bicho, SetOptions.merge())
                .addOnSuccessListener {
                    uploadSuccessful = true
                }.addOnFailureListener {
                    uploadSuccessful = false
                }.await()
            emit(DataState.Success(uploadSuccessful))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun getAllBichos(): Flow<DataState<List<Empresa>>> = flow {
        emit(DataState.Loading)
        try {
            val bichos = bichosCollection
                .get()
                .await()
                .toObjects(Empresa::class.java)
            emit(DataState.Success(bichos))
            emit(DataState.Finished)
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    override fun saveBichoImage(
        activity: Activity,
        imageFileURI: Uri?,
        imageType: String,
        fragment: Fragment
    ) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType+System.currentTimeMillis()+"."
                    + StorageUtils.getFileExtension(
                activity,
                imageFileURI
            )
        )
        // Adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )
                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        when(fragment) {
                            is AddEditEmpresaFragment -> fragment.uploadImageSuccess(uri.toString())
                        }
                    }
            }
            .addOnFailureListener { exception ->
                // Hide the progress dialog if there is any error. And print the error in log.
                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    override suspend fun deleteBicho(id: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {

            var bichoSuccessfully: Boolean = false

            bichosCollection.document(id)
                .delete()
                .addOnSuccessListener {
                    bichoSuccessfully = true
                }
                .addOnFailureListener {
                    bichoSuccessfully = false
                }.await()

            emit(DataState.Success(bichoSuccessfully))
            emit(DataState.Finished)

        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}