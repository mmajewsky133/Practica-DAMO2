package ni.edu.uca.myuca.repository

import ni.edu.uca.myuca.api.RetrofitInstance
import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.data.model.ListaCoordinador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field

class Repository {

    suspend fun getCoords(): Response<ListaCoordinador> {
        return RetrofitInstance.api.getCoords()
    }

    suspend fun getCoord(id: Int): Response<Coordinador> {
        return RetrofitInstance.api.getCoord(id)
    }

    suspend fun addCoord(
        nombres: String,
        apellidos: String,
        fechaNac: String,
        titulo: String,
        email: String,
        facultad: String
    ): Response<Coordinador> {
        return RetrofitInstance.api.addCoord(
            nombres, apellidos, fechaNac, titulo, email, facultad
        )
    }

    suspend fun editCoord(
        id: Int,
        nombres: String,
        apellidos: String,
        fechaNac: String,
        titulo: String,
        email: String,
        facultad: String
    ): Response<Coordinador> {
        return RetrofitInstance.api.editCoord(
            id, nombres, apellidos, fechaNac, titulo, email, facultad
        )
    }

    suspend fun deleteCoord(id: Int): Response<Coordinador> {
        return RetrofitInstance.api.deleteCoord(id)
    }
}