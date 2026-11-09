package cn.edu.sicnu.cs.stu.guolijie.studentmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.adapter.StudentAdapter
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.data.StudentRepository
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.model.Student
import com.google.android.material.button.MaterialButton

/**
 * 第一个 Activity：用列表显示所有学生。
 * 点击「添加学生」启动第二个 Activity，并在它返回时接收新的学生数据、刷新列表。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerStudents: RecyclerView
    private lateinit var tvCount: TextView
    private lateinit var adapter: StudentAdapter

    /** 注册「启动 Activity 并等待结果」，替代已废弃的 startActivityForResult。 */
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val student = result.data?.let { data ->
                IntentCompat.getParcelableExtra(
                    data,
                    AddStudentActivity.EXTRA_STUDENT,
                    Student::class.java
                )
            }
            if (student != null) {
                insertStudent(student)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyContentInsets(findViewById(R.id.root))

        supportActionBar?.title = getString(R.string.main_title)

        recyclerStudents = findViewById(R.id.recyclerStudents)
        tvCount = findViewById(R.id.tvCount)

        adapter = StudentAdapter(StudentRepository.getStudents())
        recyclerStudents.layoutManager = LinearLayoutManager(this)
        recyclerStudents.adapter = adapter

        findViewById<MaterialButton>(R.id.btnAdd).setOnClickListener {
            addStudentLauncher.launch(Intent(this, AddStudentActivity::class.java))
        }

        updateStudentCount()
    }

    /** 把第二个 Activity 带回的学生加入仓库，并通知列表插入一行。 */
    private fun insertStudent(student: Student) {
        val index = StudentRepository.addStudent(student)
        adapter.notifyItemInserted(index)
        recyclerStudents.scrollToPosition(index)
        updateStudentCount()
    }

    private fun updateStudentCount() {
        tvCount.text = getString(R.string.student_count, StudentRepository.getStudents().size)
    }
}
