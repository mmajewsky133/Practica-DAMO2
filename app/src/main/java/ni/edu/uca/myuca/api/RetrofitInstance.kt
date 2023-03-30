package ni.edu.uca.myuca.api

import com.google.gson.GsonBuilder
import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val gson = GsonBuilder().setLenient().create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: CoordinadorAPI by lazy {
        retrofit.create(CoordinadorAPI::class.java)
    }
}