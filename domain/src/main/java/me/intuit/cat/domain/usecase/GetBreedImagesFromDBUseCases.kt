package me.intuit.cat.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.repository.BreedsRepository
import javax.inject.Inject

data class GetBreedImagesFromDBUseCases
    @Inject constructor(private val catBreedsRepository: BreedsRepository
) {

    suspend operator fun invoke(): Flow<List<Breed>> {
        return catBreedsRepository.getBreedsImagesFromDB()
    }
}
