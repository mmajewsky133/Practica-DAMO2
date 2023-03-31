package ni.edu.uca.myuca.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ni.edu.uca.myuca.data.model.Coordinador
import org.json.JSONException
import org.json.JSONObject

class CoordsViewModel() : ViewModel() {

    //Profe, aqui cambia la ip a su ip local y al puerto de apache que este usando
    val url = "http://192.168.1.7:1527/Practica-DAMO2-API/CoordGetSet.php"

    fun conseguirDatos(context: Context, listSubmitter: (ArrayList<Coordinador>) -> Unit) {

        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                listSubmitter(parseJsonObj(response))
            }
        ) { error ->
            println("error al obtener datos online")
        }
        queue.add(stringRequest)
    }

    private fun parseJsonObj(jsonRespose: String): ArrayList<Coordinador> {
        var coordinadores =  ArrayList<Coordinador>()

        try {
            val coordinadorObj = JSONObject(jsonRespose).getJSONArray("coordinadores")

            for (i in 0 until  coordinadorObj.length()) {
                val jsonObj =  coordinadorObj.getJSONObject(i)

                val id = jsonObj.getInt("id")
                val nombres = jsonObj.getString("nombres")
                val apellidos = jsonObj.getString("apellidos")
                val fechaNac = jsonObj.getString("fechaNac")
                val titulo = jsonObj.getString("titulo")
                val email = jsonObj.getString("email")
                val facultad = jsonObj.getString("facultad")

                var coordinador = Coordinador(
                    id, nombres, apellidos,
                    fechaNac, titulo, email, facultad
                )

                if (!coordinador.titulo.contains("msc", true)){
                    coordinadores.add(coordinador)
                }

            }
            return coordinadores
        } catch (e: JSONException) {
            println("error al procesar json: ${e.message}")
        }

        return coordinadores
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(CoordsViewModel::class) {
                CoordsViewModel()
            }
            build()
        }
    }
}