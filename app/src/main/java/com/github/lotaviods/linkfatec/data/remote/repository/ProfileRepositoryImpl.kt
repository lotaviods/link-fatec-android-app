package com.github.lotaviods.linkfatec.data.remote.repository

import com.github.lotaviods.linkfatec.data.remote.client.ProfileWebClient
import com.github.lotaviods.linkfatec.data.repository.interfaces.ProfileRepository
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.resource.AppResource

class ProfileRepositoryImpl(private val webClient: ProfileWebClient) : ProfileRepository {
    override suspend fun sendProfileResume(studentId: Int, pdfBytes: ByteArray?): AppResource<Any> {
        try {
            val response = webClient.sendProfileResume(studentId, pdfBytes)

            if (response.isSuccessful) {
                return AppResource(null, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        } catch (e: Exception) {
            e.printStackTrace()

            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        }
    }

    override suspend fun sendProfilePicture(studentId: Int, bytes: ByteArray?): AppResource<Any> {
        try {
            val response = webClient.sendProfilePicture(studentId, bytes)

            if (response.isSuccessful) {
                return AppResource(null, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        } catch (e: Exception) {
            e.printStackTrace()

            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        }
    }
}