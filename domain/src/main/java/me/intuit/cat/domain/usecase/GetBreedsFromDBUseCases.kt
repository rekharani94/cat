package me.intuit.cat.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.repository.BreedsRepository
import javax.inject.Inject

data class GetBreedsFromDBUseCases
    @Inject constructor(private val catBreedsRepository: BreedsRepository
) {

    suspend operator fun invoke(): Flow<PagingData<BreedImage>> {
        return catBreedsRepository.getBreedsDirectlyFromDB()
    }
}
