package fr.amsl.pokespot.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Add an authorization key to all request.
 */
@Singleton
class AuthorizationInterceptor @Inject constructor(@Named("Authorization") private val authorization: String) : Interceptor {
  override fun intercept(chain: Interceptor.Chain?): Response {
    val request: Request = chain!!.request()

    val newRequest = request.newBuilder()
        .addHeader(ApiConstant.HEADER_AUTHORIZATION, authorization)
        .build()

    return chain.proceed(newRequest ?: request)
  }
}