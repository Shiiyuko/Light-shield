import net.SereinTeam.*
import java.awt.*
import javax.swing.*


class LoginGUI : JFrame("验证") {

    private val usernameLabel = JLabel("                           用户名")
    private val usernameField = JTextField(20)
    private val passwordLabel = JLabel("                            密码")
    private val passwordField = JPasswordField(20)
    private val loginButton = JButton("登录")
    private val registerButton = JButton("注册")
    private val activationCodeLabel = JLabel("激活码")
    private val activationCodeField = JTextField(20)
    private val hintLabel = JLabel("请输入用户名和密码进行登录或注册")
    init {
        Login = false
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(800, 450)
        val panel:JPanel=object :JPanel(){
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                val icon = ImageIcon("background.png")
                val image = icon.image
                g.drawImage(image, 0, 0, this.width, this.height, this)
            }
        }

        panel.layout = BorderLayout(20, 20)
        panel.border = BorderFactory.createEmptyBorder(50, 50, 50, 50)


        panel.layout = GridLayout(0, 2, 10, 10)
        panel.add(usernameLabel)
        panel.add(usernameField)
        panel.add(passwordLabel)
        panel.add(passwordField)
        panel.add(loginButton)
        panel.add(registerButton)
        panel.add(activationCodeLabel)
        panel.add(activationCodeField)






        activationCodeLabel.isVisible = false
        activationCodeField.isVisible = false

        val font = Font("微软雅黑", Font.PLAIN, 20)
        listOf(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, registerButton, activationCodeLabel, activationCodeField, hintLabel).forEach {
            it.font = font
            it.foreground = Color(0, 0, 0)
        }

        listOf(loginButton, registerButton).forEach {
            it.background = Color(238, 131, 0)
            it.foreground = Color.BLACK
            it.isFocusPainted = false
            it.isContentAreaFilled = true
            it.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        }

        panel.add(hintLabel, BorderLayout.SOUTH)
        add(panel)
        val icon = ImageIcon("LOGO.png").image
        iconImage = icon

        loginButton.addActionListener {
            Login = true
            User = usernameField.text
            Password = String(passwordField.password)
            // 进行登录验证
            comboBox2Value = "登录"
            dispose()
        }
        registerButton.addActionListener {
            Login = true
            User = usernameField.text
            Password = usernameField.text
            CDK = activationCodeField.text
            // 进行注册验证
            comboBox2Value = "注册"
            dispose()
        }
        setLocationRelativeTo(null) // 居中显示
        isVisible = true
    }
}