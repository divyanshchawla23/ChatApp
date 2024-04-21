package eu.divyansh.mychat.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import eu.divyansh.mychat.Injection
import kotlinx.coroutines.launch
import eu.divyansh.mychat.data.Message
import eu.divyansh.mychat.data.MessageRepository
import eu.divyansh.mychat.data.Result.*
import eu.divyansh.mychat.data.User
import eu.divyansh.mychat.data.UserRepo

class MessageViewModel : ViewModel() {

    private val messageRepository: MessageRepository
    private val userRepository: UserRepo

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepo(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
        loadCurrentUser()
    }


    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }

    fun sendMessage(text: String) {
        if (_currentUser.value != null) {
            val message = Message(
                SenderfirstName = _currentUser.value!!.firstName,
                SenderID = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is Success -> Unit
                    is Error -> {

                    }
                }
            }
        }
    }


    fun loadMessages() {
        viewModelScope.launch {
            if (_roomId != null) {
                messageRepository.getChatMessages(_roomId.value.toString())
                    .collect { _messages.value = it }
            }
        }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Success -> _currentUser.value = result.data!!
                is Error -> {

                }

            }
        }
    }
}
