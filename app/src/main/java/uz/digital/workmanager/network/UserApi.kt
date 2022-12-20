package uz.digital.workmanager.network

import retrofit2.Response
import retrofit2.http.GET
import uz.digital.workmanager.model.UserDTO

interface UserApi {
    @GET("users")
    suspend fun getUsers(): Response<UserDTO>
}