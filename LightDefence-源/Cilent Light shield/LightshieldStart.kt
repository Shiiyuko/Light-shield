package net.SereinTeam


import LoginGUI
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import java.nio.charset.StandardCharsets
import javax.swing.JOptionPane
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException
var CDK=""
var Login=false
var su=false
var comboBox2Value=""
var Password=""
var User=""
val HWID=HWIDUtils.getHWID()
var LoginAble=false
class GDStart {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            while (su==false) {
                LoginGUI()
while (Login==false){Thread.sleep(100)}
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: UnsupportedLookAndFeelException) {
                    e.printStackTrace()
                }

                if (comboBox2Value != null) {
                    if (comboBox2Value.equals("注册")) {

                    CDK=JOptionPane.showInputDialog("请输入激活码")
                        if (User.length < 6 || Password.length < 6) {
                            JOptionPane.showMessageDialog(
                                null,
                                "用户名/密码 不能小于6位",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                            )
                        } else {



                            val serverHost = "mc2.snly.cc" // 服务端主机名
                            val serverPort = 32101 // 服务端端口号
                            val username: String = User // 用户名
                            val password: String = Password // 密码
                            try {
                                Socket(serverHost, serverPort).use { socket ->
                                    // 发送数据给服务端
                                    val dos = DataOutputStream(socket.getOutputStream())
                                    val usernameBytes =
                                        username.toByteArray(StandardCharsets.UTF_8)
                                    val passwordBytes =
                                        password.toByteArray(StandardCharsets.UTF_8)
                                    val HWIDBytes =
                                        HWID.toByteArray(StandardCharsets.UTF_8)
                                    val CDKBytes =
                                        CDK.toByteArray(StandardCharsets.UTF_8)
                                    dos.writeInt(usernameBytes.size)
                                    dos.writeInt(passwordBytes.size)
                                    dos.writeInt(HWIDBytes.size)
                                    dos.writeInt(CDKBytes.size)
                                    dos.write(usernameBytes)
                                    dos.write(passwordBytes)
                                    dos.write(HWIDBytes)
                                    dos.write(CDKBytes)
                                    dos.flush()

                                    // 读取服务端返回的结果
                                    val dis = DataInputStream(socket.getInputStream())
                                    val resultLength = dis.readInt()
                                    val resultBytes = ByteArray(resultLength)
                                    dis.readFully(resultBytes)
                                    val result =
                                        String(resultBytes, StandardCharsets.UTF_8)
                                    JOptionPane.showMessageDialog(null, "服务端返回结果：$result")
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                                JOptionPane.showMessageDialog(null, "连接服务端失败。")
                            }
                        }

                    } else if (comboBox2Value.equals("登录")) {

                        if (User.length < 1 || Password.length < 1) {
                            JOptionPane.showMessageDialog(
                                null,
                                "用户名/密码 不能为空",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                            )

                        } else {
                            val serverHost = "mc2.snly.cc" // 服务端主机名
                            val serverPort = 32102 // 服务端端口号
                            val username: String = User // 用户名
                            val password: String = Password // 密码
                            val HWID: String = HWIDUtils.getHWID() // 密码
                            var success = false // 验证结果
                            try {
                                Socket(serverHost, serverPort).use { socket ->
                                    DataOutputStream(socket.getOutputStream()).use { dos ->
                                        DataInputStream(socket.getInputStream()).use { dis ->

                                            // 先发送数据长度
                                            val usernameBytes =
                                                username.toByteArray(StandardCharsets.UTF_8)
                                            val passwordBytes =
                                                password.toByteArray(StandardCharsets.UTF_8)
                                            val HWIDBytes =
                                                HWID.toByteArray(StandardCharsets.UTF_8)
                                            dos.writeInt(usernameBytes.size)
                                            dos.writeInt(passwordBytes.size)
                                            dos.writeInt(HWIDBytes.size)

                                            // 再发送数据内容
                                            dos.write(usernameBytes)
                                            dos.write(passwordBytes)
                                            dos.write(HWIDBytes)
                                            dos.flush()

                                            // 接收服务端返回的验证结果
                                            val isSuccess = dis.readInt()
                                            if (isSuccess == 1) {
                                                su=true
                                                LoginAble=true
                                                success = true
                                            }
                                            val message = dis.readUTF()
                                            JOptionPane.showMessageDialog(
                                                null,
                                                message,
                                                if (success) "SUCCESS" else "ERROR",
                                                if (success) JOptionPane.INFORMATION_MESSAGE else JOptionPane.ERROR_MESSAGE
                                            )
                                            if (isSuccess == 2) {
                                                val KEY = JOptionPane.showInputDialog(null, "请输入新的激活码")
                                                if (KEY == HWIDUtils.hwidkey()) {     su=true
                                                    JOptionPane.showMessageDialog(
                                                        null,
                                                        "激活成功(30天)",
                                                        "SUCCESS",
                                                        JOptionPane.INFORMATION_MESSAGE
                                                    )
                                                    val serverHost2 = "mc2.snly.cc" // 服务端主机名
                                                    val serverPort2 = 31602 // 服务端端口号
                                                    val username2: String = User // 用户名
                                                    val password2: String = Password // 密码
                                                    try {
                                                        Socket(serverHost2, serverPort2).use { socket2 ->
                                                            // 发送数据给服务端
                                                            val dos2 =
                                                                DataOutputStream(socket2.getOutputStream())
                                                            val usernameBytes2 =
                                                                username2.toByteArray(StandardCharsets.UTF_8)
                                                            val passwordBytes2 =
                                                                password2.toByteArray(StandardCharsets.UTF_8)
                                                            dos2.writeInt(usernameBytes2.size)
                                                            dos2.writeInt(passwordBytes2.size)
                                                            dos2.write(usernameBytes)
                                                            dos2.write(passwordBytes)
                                                            dos2.flush()

                                                            // 读取服务端返回的结果
                                                            val dis2 =
                                                                DataInputStream(socket.getInputStream())
                                                            val resultLength = dis2.readInt()
                                                            val resultBytes = ByteArray(resultLength)
                                                            dis2.readFully(resultBytes)
                                                            val result =
                                                                String(resultBytes, StandardCharsets.UTF_8)
                                                        }
                                                    } catch (e: IOException) {
                                                        e.printStackTrace()
                                                        JOptionPane.showMessageDialog(null, "服务端请求超时")
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(
                                                        null,
                                                        "激活失败 错误的激活码",
                                                        "ERROR",
                                                        JOptionPane.ERROR_MESSAGE
                                                    )

                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                                JOptionPane.showMessageDialog(
                                    null,
                                    "连接服务器失败",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE
                                )
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "不合规的操作!", "ERROR", JOptionPane.ERROR_MESSAGE)

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请选择操作!", "WARNING", JOptionPane.WARNING_MESSAGE)

                }


                print("\nDONE")

            }
        }
    }
}