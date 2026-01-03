package com.example.coffeehub.utils

import android.app.Activity
import com.example.coffeehub.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInHelper(private val activity: Activity) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    val client = GoogleSignIn.getClient(activity, gso)

    fun firebaseLogin(
        idToken: String,
        onSuccess: (email: String, name: String?, googleId: String) -> Unit,
        onError: (error: String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val user = result.user
                onSuccess(
                    user?.email ?: "",
                    user?.displayName,
                    user?.uid ?: ""
                )
            }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Firebase authentication failed")
            }
    }
}
