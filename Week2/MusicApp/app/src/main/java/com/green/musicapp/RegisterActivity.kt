package com.green.musicapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Insert
import androidx.room.OnConflictStrategy

class RegisterActivity : AppCompatActivity() {
    private val TAG:String = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // DB - init
        var db:AppDatabase? = null
        var userList = mutableListOf<User>()
        db = AppDatabase.getInstance(this)

        // DB - 이전에 저장한 내용 모두 불러와서 추가
        val savedUser = db!!.userDao().getAll()
        if (savedUser.isNotEmpty()) {
            userList.addAll(savedUser)
        }

        val etRegisterId:EditText = findViewById(R.id.etRegisterId)
        val etRegisterPw:EditText = findViewById(R.id.etRegisterPw)
        val btnCheckId:Button = findViewById(R.id.btnCheckId)
        val btnRegister:Button = findViewById(R.id.btnAddRegister)
        var strId:String
        var strPw:String
        var checkedId:Boolean = false

        // 넘겨받은 데이터가 있는 경우, 바로 회원가입 할 수 있도록 EditText에 텍스트 넣어주기
        val getId:String? = intent.getStringExtra("id")
        val getPw:String? = intent.getStringExtra("pw")
        if (getId != null) {
            etRegisterId.setText(getId)
            etRegisterPw.setText(getPw)

            strId = etRegisterId.text.toString()
            strPw = etRegisterPw.text.toString()
        }


        fun colorMode(flag:Boolean) {
            if (flag) {
                btnCheckId.setBackgroundResource(R.drawable.btn_login_register_inavailable)
                btnCheckId.setTextColor(getColor(R.color.light_gray))
                btnRegister.setBackgroundResource(R.drawable.btn_login_register_available)
                btnRegister.setTextColor(getColor(R.color.melon_signature))
            }
            else {
                btnCheckId.setBackgroundResource(R.drawable.btn_login_register_available)
                btnCheckId.setTextColor(getColor(R.color.melon_signature))
                btnRegister.setBackgroundResource(R.drawable.btn_login_register_inavailable)
                btnRegister.setTextColor(getColor(R.color.light_gray))
            }
        }
        fun OpenDialog(view:View) {
            val builder:AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("사용가능한 아이디 입니다.\n사용하시겠습니까?")

            var listener = DialogInterface.OnClickListener { dialogInterface, i ->
                when(i) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        checkedId = true
                        colorMode(checkedId)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        checkedId = false
                        colorMode(checkedId)
                    }
                }
            }
            builder.setPositiveButton("사용", listener)
            builder.setNegativeButton("취소", listener)

            builder.show()
        }

        btnCheckId.setOnClickListener {
            strId = etRegisterId.text.toString()
            val user:User? = db!!.userDao().getMatchUser(strId!!)

            if (strId!!.equals("")) {
                checkedId = false
                colorMode(checkedId)
                Toast.makeText(applicationContext, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()

            }
            // 생성할 id 중복 체크
            else if (user == null) {
                OpenDialog(btnCheckId)
            }
            else {
                OpenDialog(btnCheckId)
                Toast.makeText(applicationContext, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            strId = etRegisterId.text.toString()
            strPw = etRegisterPw.text.toString()

            // DB에 id, pw 등록
            if (checkedId) {
                db!!.userDao().insert(User(strId!!, strPw!!))
                Toast.makeText(applicationContext, "아이디를 생성했습니다", Toast.LENGTH_SHORT).show()
                
                // LoginActivity의 EditText에 값 넣어주기
                intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("id", strId)
                intent.putExtra("pw", strPw)
                startActivity(intent)
                Toast.makeText(applicationContext, "RegisterActivity 종료", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}