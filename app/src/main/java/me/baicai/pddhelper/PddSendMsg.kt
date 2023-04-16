package me.baicai.pddhelper

import android.os.Handler
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.Executor



sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


class GetHaowuVideoRepository(
    private val executor: Executor,
    private val resultHandler: Handler
) {
    fun makeGetHaowuVideoRequest(
    jsonBody:String,
    callback:(Result<String>)->Unit
){
    executor.execute{
        try{
            val response= makeSynchronousGetHaowuVideoRequest(jsonBody)
            resultHandler.post{
                callback(response)
            }
        }catch (e:Exception){
            val errorResult=Result.Error(e)
            resultHandler.post{
                callback(errorResult)
            }
        }
    }
}

    private fun makeSynchronousGetHaowuVideoRequest(jsonBody: String):Result<String>{
        val apiUrl = "http://192.168.0.66:8090/ping"
        val client=OkHttpClient()
        val request= Request.Builder()
            .url(apiUrl)
            .post(jsonBody.toRequestBody())
            .build()
        client.newCall(request).execute().use {
            response->
                if (!response.isSuccessful)    return Result.Error(Exception("Cannot open HttpURLConnection"))
                return   Result.Success<String>(response.body!!.string())
        }
    }
}
class HaowuVideoResponse{}

class PddSendViewModel(
    private val getHaowuVideoRepository: GetHaowuVideoRepository
) {
    fun makeGetHaowuVideoRequest(username: String, token: String) {
        val jsonBody = "{ username: \"$username\", token: \"$token\"}"
        getHaowuVideoRepository.makeGetHaowuVideoRequest(jsonBody) { result ->
            when (result) {
                is Result.Success<String> ->  result.data

                else -> result.toString()
            }
        }

    }
}
