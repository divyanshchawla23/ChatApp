package eu.divyansh.mychat.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.divyansh.mychat.data.Result.*
import androidx.lifecycle.viewModelScope
import eu.divyansh.mychat.Injection
import kotlinx.coroutines.launch



class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository
    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String) {
        viewModelScope.launch {
            roomRepository.createRoom(name)
            loadRooms()
        }
    }


    fun loadRooms() {
        viewModelScope.launch {
            when(val result= roomRepository.getRooms()){
                is Success -> _rooms.value = result.data
                is Error->{

                }
            }
        }
    }

}
