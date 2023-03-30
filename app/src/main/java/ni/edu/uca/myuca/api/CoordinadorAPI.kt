package ni.edu.uca.myuca.api

import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.data.model.ListaCoordinador
import retrofit2.Response
import retrofit2.http.*

interface CoordinadorAPI {

    @GET("CoordGetSet.php")
    suspend fun getCoords(): Response<ListaCoordinador>

    @GET("CoordGetSet.php")
    suspend fun getCoord(@Query("id") id: Int): Response<Coordinador>

    @FormUrlEncoded
    @POST("CoordGetSet.php")
    suspend fun addCoord(
        @Field("nombres") nombres: String,
        @Field("apellidos") apellidos: String,
        @Field("fechaNac") fechaNac: String,
        @Field("titulo") titulo: String,
        @Field("email") email: String,
        @Field("facultad") facultad: String
    ): Response<Coordinador>

    @FormUrlEncoded
    @POST("CoordGetSet.php")
    suspend fun editCoord(
        @Field("id") id: Int,
        @Field("nombres") nombres: String,
        @Field("apellidos") apellidos: String,
        @Field("fechaNac") fechaNac: String,
        @Field("titulo") titulo: String,
        @Field("email") email: String,
        @Field("facultad") facultad: String
    ): Response<Coordinador>

    @FormUrlEncoded
    @POST("CoordElim.php")
    suspend fun deleteCoord(@Field("id") id: Int): Response<Coordinador>
}