package ni.edu.uca.myuca.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.data.model.ListaCoordinador
import ni.edu.uca.myuca.repository.Repository
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat

class CoordsViewModel(private val repository: Repository) : ViewModel() {

    val listaCoords: MutableLiveData<Response<ListaCoordinador>> = MutableLiveData()
    val coord: MutableLiveData<Response<Coordinador>> = MutableLiveData()
    val addedCoord: MutableLiveData<Response<Coordinador>> = MutableLiveData()
    val editedCoord: MutableLiveData<Response<Coordinador>> = MutableLiveData()
    val deletedCoord: MutableLiveData<Response<Coordinador>> = MutableLiveData()

    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun conseguirCoordinadores() {
        viewModelScope.launch {
            val response = repository.getCoords()
            listaCoords.value = response
        }
    }

    fun conseguirCoordinador(id: Int) {
        viewModelScope.launch {
            val response = repository.getCoord(id)
            coord.value = response
        }
    }

    fun agregarCoordinador(coordinador: Coordinador) {
        viewModelScope.launch {
            val response = repository.addCoord(
                coordinador.nombres,
                coordinador.apellidos,
                coordinador.fechaNac,
                coordinador.titulo,
                coordinador.email,
                coordinador.facultad
            )
        }
    }

    fun editarCoordinador(coordinador: Coordinador) {
        viewModelScope.launch {
            val response = repository.editCoord(
                coordinador.id,
                coordinador.nombres,
                coordinador.apellidos,
                coordinador.fechaNac,
                coordinador.titulo,
                coordinador.email,
                coordinador.facultad
            )
            editedCoord.value = response
        }
    }

    fun eliminarCoordinador(id: Int) {
        viewModelScope.launch {
            val response = repository.deleteCoord(id)
            deletedCoord.value = response
        }
    }

    fun entradasValidas(
        nombres: String,
        apellidos: String,
        fechaNac: String,
        facultad: String,
        titulo: String,
        email: String
    ): Boolean {
        if ((nombres.isBlank() || apellidos.isBlank() || fechaNac.isBlank() ||
                    facultad.isBlank() || titulo.isBlank() || email.isBlank())) {
            try {
                dateFormat.parse(fechaNac.trim())
            } catch (e: ParseException){
                return false
            }
        }
        return true
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(CoordsViewModel::class) {
                CoordsViewModel(Repository())
            }
            build()
        }
    }
}