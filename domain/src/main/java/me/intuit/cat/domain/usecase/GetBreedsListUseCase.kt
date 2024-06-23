package me.intuit.cat.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.repository.BreedsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBreedsListUseCase @Inject constructor(private val catBreedsRepository: BreedsRepository) {

     operator fun invoke(limit:Int, page:Int): Flow<PagingData<BreedImage>> {
        return catBreedsRepository.getBreedsImageDirectlyFromDB()
    }

}