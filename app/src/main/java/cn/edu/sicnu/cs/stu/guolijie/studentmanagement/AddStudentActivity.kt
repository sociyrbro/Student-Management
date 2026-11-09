package cn.edu.sicnu.cs.stu.guolijie.studentmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.data.StudentRepository
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.model.Student
import com.google.android.material.button.MaterialButton

/**
 * 第二个 Activity：填写并提交一名新学生。
 * 点「确定」把 Student 放进 Intent 的 extras 里，以 RESULT_OK 返回给第一个 Activity；
 * 点「取消」直接以 RESULT_CANCELED 返回，不传任何数据。
 */
class AddStudentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STUDENT = "extra_student"
    }

    private lateinit var etStudentId: EditText
    private lateinit var etName: EditText
    private lateinit var etClassName: EditText
    private lateinit var rgGender: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        applyContentInsets(findViewById(R.id.root))

        etStudentId = findViewById(R.id.etStudentId)
        etName = findViewById(R.id.etName)
        etClassName = findViewById(R.id.etClassName)
        rgGender = findViewById(R.id.rgGender)

        findViewById<MaterialButton>(R.id.btnConfirm).setOnClickListener { confirm() }
        findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener { cancel() }
    }

    private fun confirm() {
        val studentId = etStudentId.text.toString().trim()
        val name = etName.text.toString().trim()
        val className = etClassName.text.toString().trim()

        if (studentId.isEmpty() || name.isEmpty() || className.isEmpty()) {
            Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show()
            return
        }
        if (StudentRepository.isStudentIdExists(studentId)) {
            Toast.makeText(this, R.string.error_duplicate_id, Toast.LENGTH_SHORT).show()
            return
        }

        val gender = if (rgGender.checkedRadioButtonId == R.id.rbFemale) {
            getString(R.string.gender_female)
        } else {
            getString(R.string.gender_male)
        }

        val student = Student(
            studentId = studentId,
            name = name,
            gender = gender,
            className = className
        )

        // 把新学生放进返回值，第一个 Activity 会从 result.data 里取出它
        setResult(RESULT_OK, Intent().putExtra(EXTRA_STUDENT, student))
        finish()
    }

    private fun cancel() {
        setResult(RESULT_CANCELED)
        finish()
    }
}
