package me.intuit.cat.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.repository.BreedsRepository
import javax.inject.Inject

data class BreedDetailUseCases
    @Inject constructor(private val catBreedsRepository: BreedsRepository
) {

       operator fun invoke(imageId: String,
                                ): Flow<List<Breed>> {
        return catBreedsRepository.getBreeds(imageId = imageId )
    }
}
