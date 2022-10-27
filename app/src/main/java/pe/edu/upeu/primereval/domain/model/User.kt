package pe.edu.upeu.primereval.domain.model

import pe.edu.upeu.primereval.utils.Constants.INFO_NOT_SET

data class User (
    val id: String = INFO_NOT_SET,
    val email: String = INFO_NOT_SET
)
