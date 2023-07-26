package com.company.myapplication.helper.interceptors

import android.content.Context
import com.company.myapplication.data.remote.Authenticated
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AuthInterceptor @Inject constructor(@ApplicationContext val appContext: Context) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Authenticated::class }

        return if (shouldAttachAuthHeader) {
            //val tokenData = getTokenResponse(appContext)
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer Add your token here")
                    //.addHeader("Authorization", "Bearer ${tokenData?.jwt}")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            )
        } else {
            chain.proceed(chain.request())
        }

    }
}