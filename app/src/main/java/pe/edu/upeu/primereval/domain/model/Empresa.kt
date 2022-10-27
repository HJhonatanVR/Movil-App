package pe.edu.upeu.primereval.domain.model

import android.os.Parcelable
import pe.edu.upeu.primereval.utils.Constants.DEFAULT_EMPRESA_IMAGE
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Empresa(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description:String = "",
    val image: String = DEFAULT_EMPRESA_IMAGE
): Parcelable
