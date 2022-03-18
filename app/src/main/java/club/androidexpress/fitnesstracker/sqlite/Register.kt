package club.androidexpress.fitnesstracker.sqlite

data class Register(
    val id: Int,
    val type: String,
    val response: Double,
    val createdDate: String
)
