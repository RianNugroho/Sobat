package id.sobat.sobat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import id.sobat.sobat.Model.DataLocal.Companion.TAG_NICKNAME
import id.sobat.sobat.Model.User

class SetNickActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_nick)
        mAuth = FirebaseAuth.getInstance()

        val etNickname = findViewById<EditText>(R.id.et_nickname)
        val btnNickname = findViewById<Button>(R.id.btn_nickname)

        btnNickname.setOnClickListener {
            val nickname = etNickname.text.toString()
            if (nickname != "" && nickname.isNotEmpty()) {
                val user = User()
                user.email = mAuth.currentUser?.email
                user.nama = mAuth.currentUser?.displayName
                user.nickname = nickname
                db.collection("users").document("${mAuth.currentUser?.uid}").set(user)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(applicationContext, getString(R.string.error_database), Toast.LENGTH_SHORT).show()
                                Log.w(TAG_NICKNAME, "Error getting documents.", it.exception)
                            }
                        }
            }
        }
    }

    private fun checkNickname() {
        db.collection("users").document("${mAuth.currentUser?.uid}").get()
                .addOnCompleteListener {
                    if (it.result.exists() && it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(TAG_NICKNAME, "Error getting documents.", it.exception)
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
