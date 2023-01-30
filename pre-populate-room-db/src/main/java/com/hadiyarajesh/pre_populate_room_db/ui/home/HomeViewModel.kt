package com.hadiyarajesh.pre_populate_room_db.ui.home

import androidx.lifecycle.ViewModel
import com.hadiyarajesh.pre_populate_room_db.database.entity.User
import com.hadiyarajesh.pre_populate_room_db.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    /**
     * If you're not using a [Flow] to read the database, this step is mandatory.
     */
//    init {
//        /**
//         * Initiate a fake DB read operation to (create and) initialise database on first app install.
//         * This will be executed every time when this viewmodel is instantiated, so it's not ideal for production
//         * Rather, use a datastore (or shared preference) to execute this operation only once in production.
//         */
//        viewModelScope.launch {
//            getUser()
//        }
//    }

    private suspend fun getUser(): User? {
        return homeRepository.getFirstUser()
    }

    fun getAllUsers(): Flow<List<User>> {
        return homeRepository.getAllUsers()
    }
}
