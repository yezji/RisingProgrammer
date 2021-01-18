package com.green.musicapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    private val TAG:String = "RegisterActivity"
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        val etRegisterEmail:EditText = findViewById(R.id.etRegisterEmail)
        val etRegisterPw:EditText = findViewById(R.id.etRegisterPw)
        val btnCheckEmail:Button = findViewById(R.id.btnCheckEmail)
        val btnRegister:Button = findViewById(R.id.btnAddRegister)
        var strEmail:String
        var strPw:String
        var checkedEmail:Boolean = false

        // email 형식 패턴
        val emailPattern:String = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$"

        // LoginActivity에서 넘겨받은 데이터가 있는 경우, 바로 회원가입 하도록 EditText에 텍스트 넣기
        val getEmail:String? = intent.getStringExtra("email")
        val getPw:String? = intent.getStringExtra("pw")
        if (getEmail != null) {
            etRegisterEmail.setText(getEmail)
            etRegisterPw.setText(getPw)

            strEmail = etRegisterEmail.text.toString()
            strPw = etRegisterPw.text.toString()
        }

        fun colorMode(isVerifiedEmail: Boolean) {
            val resInactiveBg: Int = R.drawable.btn_login_register_inavailable
            val resInactiveColor: Int = R.color.light_gray
            val resActiveBg: Int = R.drawable.btn_login_register_available
            val resActiveColor: Int = R.color.melon_signature

            if (isVerifiedEmail) {
                // 중복체크 버튼 비활성화
                btnCheckEmail.setBackgroundResource(resInactiveBg)
                btnCheckEmail.setTextColor(getColor(resInactiveColor))
                // 회원가입 버튼 활성화
                btnRegister.setBackgroundResource(resActiveBg)
                btnRegister.setTextColor(getColor(resActiveColor))
            }
            else {
                // 중복체크 버튼 활성화
                btnCheckEmail.setBackgroundResource(resActiveBg)
                btnCheckEmail.setTextColor(getColor(resActiveColor))
                // 회원가입 버튼 비활성화
                btnRegister.setBackgroundResource(resInactiveBg)
                btnRegister.setTextColor(getColor(resInactiveColor))
            }
        }
        fun OpenDialog(view: View) {
            val builder:AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("사용가능한 이메일 입니다.\n사용하시겠습니까?")

            var listener = DialogInterface.OnClickListener { dialogInterface, i ->
                when(i) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        checkedEmail = true
                        colorMode(checkedEmail)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        checkedEmail = false
                        colorMode(checkedEmail)
                    }
                }
            }
            builder.setPositiveButton("사용", listener)
            builder.setNegativeButton("취소", listener)

            builder.show()
        }

        btnCheckEmail.setOnClickListener {
            strEmail = etRegisterEmail.text.toString()
            hideKeyboard(btnCheckEmail)

            if (!Pattern.matches(emailPattern, strEmail)) {
                Toast.makeText(applicationContext, "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (strEmail!!.equals("")) {
                checkedEmail = false
                colorMode(checkedEmail)
                Toast.makeText(applicationContext, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()

            }


            firebaseAuth!!.createUserWithEmailAndPassword(strEmail, "000000")
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            firebaseAuth!!.currentUser!!.delete()
                            OpenDialog(btnCheckEmail)
                        }
                        else {
                            Toast.makeText(applicationContext, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

        }

        btnRegister.setOnClickListener {
            strEmail = etRegisterEmail.text.toString()
            strPw = etRegisterPw.text.toString()

            // DB에 id, pw 등록
            if (checkedEmail) {
                createUser(strEmail, strPw)

                // LoginActivity의 EditText에 값 넣어주기
                intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("email", strEmail)
                intent.putExtra("pw", strPw)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun createUser(email: String, password: String) {
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // 계정 생성에 성공하면 UI와 user의 정보를 업데이트
                    Toast.makeText(this, "계정을 성공적으로 생성했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "계정을 생성하지 못했습니다.", Toast.LENGTH_SHORT).show()
                }

            }
    }

    fun hideKeyboard(view: View) {
        var imm:InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}