package com.green.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private val TAG:String = "LoginActivity"
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        val btnLogin:Button = findViewById(R.id.btnLogin)
        val btnGoRegister:Button = findViewById(R.id.btnGoRegister)
        val etLoginEmail:EditText = findViewById(R.id.etLoginEmail)
        val etLoginPw:EditText = findViewById(R.id.etLoginPw)

        // email 형식 패턴
        val emailPattern:String = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$"

        // 회원가입 이후 넘겨받은 데이터가 있는 경우, 바로 로그인 할 수 있도록 EditText에 텍스트 넣어주기
        val getEmail:String? = intent.getStringExtra("email")
        val getPw:String? = intent.getStringExtra("pw")
        if (getEmail != null) {
            etLoginEmail.setText(getEmail)
            etLoginPw.setText(getPw)
        }

        btnLogin.setOnClickListener {
            val strEmail:String = etLoginEmail.text.toString()
            val strPw:String = etLoginPw.text.toString()

            hideKeyboard(btnLogin)

            if (!Pattern.matches(emailPattern, strEmail)) {
                Toast.makeText(applicationContext, "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (!strEmail.equals("") && !strPw.equals("")) {
                loginUser(strEmail, strPw)
            }
            else {
                Toast.makeText(applicationContext, "이메일, 비밀번호를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            val putEmail:String = etLoginEmail.text.toString()
            val putPw:String = etLoginPw.text.toString()
            intent.putExtra("email", putEmail)
            intent.putExtra("pw", putPw)
            startActivity(intent)
        }

    }

    private fun loginUser(email:String, password:String) {
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseUser = firebaseAuth!!.currentUser
                        Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("pw", password)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
    }


    fun hideKeyboard(view: View) {
        var imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
