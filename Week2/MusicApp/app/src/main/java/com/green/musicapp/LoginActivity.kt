package com.green.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.reflect.typeOf

class LoginActivity : AppCompatActivity() {
    private val TAG:String = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin:Button = findViewById(R.id.btnLogin)
        val btnGoRegister:Button = findViewById(R.id.btnGoRegister)
        val etLoginId:EditText = findViewById(R.id.etLoginId)
        val etLoginPw:EditText = findViewById(R.id.etLoginPw)

        // DB - init
        var db:AppDatabase? = null
        var userList = mutableListOf<User>()
        db = AppDatabase.getInstance(this)

        // DB - 이전에 저장한 내용 모두 불러와서 추가
        val savedUser = db!!.userDao().getAll()
        if (savedUser.isNotEmpty()) {
            userList.addAll(savedUser)
        }

        // 회원가입 이후 넘겨받은 데이터가 있는 경우, 바로 로그인 할 수 있도록 EditText에 텍스트 넣어주기
        val getId:String? = intent.getStringExtra("id")
        val getPw:String? = intent.getStringExtra("pw")
        if (getId != null) {
            etLoginId.setText(getId)
            etLoginPw.setText(getPw)
        }

        btnLogin.setOnClickListener {
            val strId:String = etLoginId.text.toString()
            val strPw:String = etLoginPw.text.toString()

            if (!strId.equals("") && !strPw.equals("")) {
                val user:User? = db!!.userDao().getMatchUser(strId!!)
                if (user != null) {
                    // 비밀번호 맞은 경우
                    if (user.pw == strPw) {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    //비밀번호 틀린 경우
                    else {
                        Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(applicationContext, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(applicationContext, "아이디, 비밀번호를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            val putId:String = etLoginId.text.toString()
            val putPw:String = etLoginPw.text.toString()
            intent.putExtra("id", putId)
            intent.putExtra("pw", putPw)
            startActivity(intent)
        }

    }
}
