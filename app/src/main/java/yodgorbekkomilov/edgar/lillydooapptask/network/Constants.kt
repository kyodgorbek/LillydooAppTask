package yodgorbekkomilov.edgar.lillydooapptask.network

const val BASE_URL = "https://opnwork.coffeecompile.com/"

const val TOKEN = "api/token/"
const val LOGIN = "api/user/me/"

data class ModelLogin(
    val refresh: String,
    val access: String
)