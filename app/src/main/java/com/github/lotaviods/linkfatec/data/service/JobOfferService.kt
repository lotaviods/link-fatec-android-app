package com.github.lotaviods.linkfatec.data.service

import com.github.lotaviods.linkfatec.data.remote.model.JobOffer
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JobOfferService {
    @GET("job-offers/available/course/{course_id}")
    suspend fun getAllAvailableJobs(@Path("course_id") courseId: Int): Response<List<JobOffer>>

    @POST("job-offer/{id}/like")
    @FormUrlEncoded
    suspend fun likeJob(
        @Path("id") jobId: Int,
        @Field("student_id") studentId: Int,
        @Field("like") shouldLike: Boolean
    ): Response<ResponseBody>

    @POST("student/job-offer/subscribe")
    @FormUrlEncoded
    suspend fun subscribeJob(
        @Field("job_id") jobId: Int,
        @Field("student_id") studentId: Int
    ): Response<ResponseBody>

}