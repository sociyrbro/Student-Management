package cn.edu.sicnu.cs.stu.guolijie.studentmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.R
import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.model.Student

/**
 * 学生列表适配器：把 Student 显示到 item_student 布局上。
 * 这里只做展示，不持有数据副本，数据变化由 Activity 通过 notifyItemInserted 通知刷新。
 */
class StudentAdapter(private val students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvInfo: TextView = itemView.findViewById(R.id.tvInfo)

        fun bind(student: Student) {
            tvName.text = student.name
            tvInfo.text = itemView.context.getString(
                R.string.student_info,
                student.studentId,
                student.gender,
                student.className
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size
}
