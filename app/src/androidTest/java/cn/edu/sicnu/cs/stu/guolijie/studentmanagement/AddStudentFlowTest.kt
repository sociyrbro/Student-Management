package cn.edu.sicnu.cs.stu.guolijie.studentmanagement

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 端到端 UI 测试：主界面 → 点击「添加学生」→ 填写表单 → 确定 →
 * 返回主界面并能看到刚新增的学生，用来验证两个 Activity 之间的数据回传是否生效。
 */
@RunWith(AndroidJUnit4::class)
class AddStudentFlowTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val device = UiDevice.getInstance(instrumentation)
    private val packageName = instrumentation.targetContext.packageName

    @Test
    fun addStudent_returnsToMainList() {
        // 每次运行用一个不同的学号，避免重复运行时被「学号已存在」拦下
        val studentId = (System.currentTimeMillis() % 100000000L).toString()
        val studentName = "张小明"

        ActivityScenario.launch(MainActivity::class.java).use {
            device.findObject(By.res(packageName, "btnAdd")).click()

            val idBox = device.wait(Until.findObject(By.res(packageName, "etStudentId")), 5000)
            idBox.text = studentId
            device.findObject(By.res(packageName, "etName")).text = studentName
            device.findObject(By.res(packageName, "etClassName")).text = "软件工程 2402"

            device.findObject(By.res(packageName, "btnConfirm")).click()

            assertTrue(
                "确定后应返回主界面并显示新增的学生",
                device.wait(Until.hasObject(By.text(studentName)), 5000)
            )
        }
    }
}
