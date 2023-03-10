package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.data.remote.model.JobOffer
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse
import com.github.lotaviods.linkfatec.data.retrofit.RetrofitClient
import com.github.lotaviods.linkfatec.data.service.JobOfferService
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class JobOfferWebClient : BaseWebClient(), KoinComponent {
    private val service: JobOfferService by lazy {
        get<RetrofitClient>().getService()
    }

    suspend fun getAllAvailableJobOffers(courseId: Int): ApplicationResponse<List<JobOffer>> {
        return execute { service.getAllAvailableJobs(courseId) }
    }

    suspend fun likeJob(
        jobId: Int,
        studentId: Int,
        like: Boolean
    ): ApplicationResponse<ResponseBody> {
        return execute { service.likeJob(jobId, studentId, like) }
    }

    suspend fun subscribeJob(jobId: Int, studentId: Int): ApplicationResponse<ResponseBody> {
        return execute { service.subscribeJob(jobId, studentId) }
    }
}