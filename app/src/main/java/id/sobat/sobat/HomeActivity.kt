package id.sobat.sobat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import id.sobat.sobat.Model.DataLocal

class HomeActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun checkNickname() {
        db.collection("users").document("${mAuth.currentUser?.uid}").get()
                .addOnCompleteListener {
                    if (!it.result.exists() && it.isSuccessful) {
                        val intent = Intent(this, SetNickActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(DataLocal.TAG_NICKNAME, "Error getting documents.", it.exception)
                    }
                }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            checkNickname()
        }
    }
}
