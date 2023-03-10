package com.github.lotaviods.linkfatec.data.repository

import com.github.lotaviods.linkfatec.data.remote.model.JobOffer
import com.github.lotaviods.linkfatec.resource.AppResource

interface JobOfferRepository {

    suspend fun getAllAvailableJobOffers(courseId: Int): AppResource<List<JobOffer>>
    suspend fun likeJob(jobId: Int, studentId: Int, like: Boolean): AppResource<Any>

    suspend fun subscribeJob(jobId: Int, studentId: Int): AppResource<Any>
}