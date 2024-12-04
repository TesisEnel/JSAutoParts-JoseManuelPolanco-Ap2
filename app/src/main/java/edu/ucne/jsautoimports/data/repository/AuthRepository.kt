package edu.ucne.jsautoimports.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import edu.ucne.jsautoimports.data.remote.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun loginUser(email: String, password: String): Flow<Resources<AuthResult>> = flow {
        try {
            emit(Resources.Loading())
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error(e.message ?: "Error desconocido"))
        }
    }

    fun registerUser(email: String, password: String): Flow<Resources<AuthResult>> = flow {
        try {
            emit(Resources.Loading())
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error(e.message ?: "Error desconocido"))
        }
    }

    fun getUser(): String? {
        return auth.currentUser?.email
    }

    fun logout(){
        auth.signOut()
    }
}