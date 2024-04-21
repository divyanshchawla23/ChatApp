package eu.divyansh.mychat.data

data class Message(
    val SenderfirstName: String = "",
    val SenderID: String = "",
    val text: String = "",
    val timesStamp: Long  = System.currentTimeMillis(),
    val isSentByCureentUser : Boolean = false

)
