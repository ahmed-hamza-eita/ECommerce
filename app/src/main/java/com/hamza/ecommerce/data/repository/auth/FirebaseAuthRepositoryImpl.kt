package com.hamza.ecommerce.data.repository.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.user.UserDetailsModel
import com.hamza.ecommerce.utils.CrashlyticsUtils
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.security.auth.login.LoginException


class FirebaseAuthRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FirebaseAuthRepository {


    private fun performLogin(
        provider: AuthProvider,
        loginAction: suspend () -> AuthResult
    ): Flow<Resource<UserDetailsModel>> =
        flow {
            try {
                //Loading
                emit(Resource.Loading())

                //Auth
              //  val authResult = withContext(IO) { loginAction() }
                val authResult = loginAction()
                val userId = authResult.user?.uid
                if (userId == null) {
                    val msg = "Sign in UserID not found"
                    logAuthIssueToCrashlytics(msg, provider.name)
                    emit(Resource.Error(Exception(msg)))
                    return@flow
                }

                // Get user details from Firestore
                val userDoc = firestore.collection("users").document(userId).get().await()
                if (!userDoc.exists()) {
                    // First time login, store user data in Firestore
                    val userDetails = UserDetailsModel(
                        id = userId,
                        email = authResult.user?.email ?: "",
                        name = authResult.user?.displayName ?: "",
                        createdAt = System.currentTimeMillis(),
                        disabled = false,
                        reviews = emptyList()
                    )
                    firestore.collection("users").document(userId).set(userDetails).await()
                    emit(Resource.Success(userDetails))
                } else {
                    // Map user details to UserDetailsModel
                    val userDetails = userDoc.toObject(UserDetailsModel::class.java)
                    userDetails?.let {
                        emit(Resource.Success(userDetails))
                    } ?: run {
                        val msg = "Error mapping user details to UserDetailsModel, user id = $userId"
                        logAuthIssueToCrashlytics(msg, provider.name)
                        emit(Resource.Error(Exception(msg)))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ) =
        performLogin(AuthProvider.EMAIL) {
            auth.signInWithEmailAndPassword(email, password).await()
        }


    override suspend fun loginWithGoogle(idToken: String) =
        performLogin(AuthProvider.GOOGLE) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
        }


    override suspend fun loginWithFacebook(idToken: String) =
        performLogin(AuthProvider.FACEBOOK) {
            val credential = FacebookAuthProvider.getCredential(idToken)
            auth.signInWithCredential(credential).await()
        }


    override fun signOut() {
        auth.signOut()
    }


    private fun logAuthIssueToCrashlytics(msg: String, provider: String) {
        CrashlyticsUtils.sendCustomLogToCrashlytics<LoginException>(
            msg, CrashlyticsUtils.LOGIN_KEY to msg,
            CrashlyticsUtils.LOGIN_PROVIDER to provider
        )
    }

    companion object {
        private const val TAG = "FirebaseAuthRepositoryI"
    }

}

enum class AuthProvider {
    EMAIL, GOOGLE, FACEBOOK
}