package cn.edu.sicnu.cs.stu.guolijie.studentmanagement.data

import cn.edu.sicnu.cs.stu.guolijie.studentmanagement.model.Student

/**
 * 学生数据仓库（单例，内存存储）。
 * 第一个 Activity 从这里取列表，第二个 Activity 通过返回值把新学生交给它。
 * 实验只要求演示两个 Activity 的数据传递，因此没有做数据库持久化。
 */
object StudentRepository {

    private val students = mutableListOf(
        Student("2024110213", "郭理杰", "男", "计算机科学与技术 2401"),
        Student("2024110214", "李小明", "男", "计算机科学与技术 2401"),
        Student("2024110215", "王小红", "女", "软件工程 2402")
    )

    fun getStudents(): List<Student> = students

    /** 追加一名学生，返回它在列表中的下标。 */
    fun addStudent(student: Student): Int {
        students.add(student)
        return students.size - 1
    }

    /** 学号是否已经存在。 */
    fun isStudentIdExists(studentId: String): Boolean =
        students.any { it.studentId == studentId }
}
