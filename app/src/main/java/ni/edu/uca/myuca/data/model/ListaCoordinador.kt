package ni.edu.uca.myuca.data.model

import com.google.gson.annotations.SerializedName

data class ListaCoordinador(
    @SerializedName("coordinadores")
    val coordinadores: List<Coordinador>
    )