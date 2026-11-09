package cn.edu.sicnu.cs.stu.guolijie.studentmanagement.model

import android.os.Parcel
import android.os.Parcelable

/**
 * 学生实体类：学号、姓名、性别、班级。
 * 实现 Parcelable，方便通过 Intent 在第二个 Activity 结束后把新学生带回第一个 Activity。
 */
data class Student(
    val studentId: String,
    val name: String,
    val gender: String,
    val className: String
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(studentId)
        dest.writeString(name)
        dest.writeString(gender)
        dest.writeString(className)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Student> = object : Parcelable.Creator<Student> {
            override fun createFromParcel(source: Parcel): Student = Student(
                studentId = source.readString().orEmpty(),
                name = source.readString().orEmpty(),
                gender = source.readString().orEmpty(),
                className = source.readString().orEmpty()
            )

            override fun newArray(size: Int): Array<Student?> = arrayOfNulls(size)
        }
    }
}
