import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerL {
    public static String HWID;
    public static void main(String[] args) throws IOException {
        final int serverPort =32102; // 服务端端口号

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("登录服务端已启动，等待连接...");

            while (true) {
                Socket socket = serverSocket.accept();

                // 处理客户端请求
                new Thread(new Handler(socket)).start();
            }
        }
    }

    private static class Handler implements Runnable {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                // 先读取数据长度
                int usernameLength = dis.readInt();
                int passwordLength = dis.readInt();
                int HWIDLength = dis.readInt();


                // 再根据长度读取数据内容
                byte[] usernameBytes = new byte[usernameLength];
                byte[] passwordBytes = new byte[passwordLength];
                byte[] HWIDBytes = new byte[HWIDLength];
                dis.readFully(usernameBytes);
                dis.readFully(passwordBytes);
                dis.readFully(HWIDBytes);
                String username = new String(usernameBytes, StandardCharsets.UTF_8);
                String password = new String(passwordBytes, StandardCharsets.UTF_8);
                HWID=new String(HWIDBytes,StandardCharsets.UTF_8);

                // 在这里进行用户名和密码的验证
                boolean isLoginSuccess = verify(username, password);
                try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                    if (isLoginSuccess) {

                        dos.writeInt(1);
                        dos.writeUTF("登录成功");
                        Date currentDate = new Date();

                        System.out.println("收到来自客户端的请求，登录成功 账号" + username + " " + currentDate + " 地区" + CityNameExample.getCityName(IPAddressExample.ipAddress) + "  " + HWID + "-=-");

                    }else {
                        dos.writeInt(0);
                        dos.writeUTF("用户名或密码错误 或激活码已经过期");
                        System.out.println("收到来自客户端的请求，密码错误 或HWID错误");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean verify(String username, String password) {
            boolean isLoginSuccess = false;
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
            String dateString2 = dateFormat.format(currentDate);
            try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("-");
                    if (username.equals(parts[0]) && password.equals(parts[1]) && dateString2.equals(parts[2])&&HWID.equals(parts[3])) {
                        isLoginSuccess = true;
                        break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isLoginSuccess;
        }
        private boolean verify2() {
            boolean isLoginSuccess2 = false;
            try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
                String line3;
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
                String dateString2 = dateFormat.format(currentDate);
                while ((line3 = br.readLine()) != null) {
                    String[] parts = line3.split("-");
                    if (dateString2.equals(parts[2])) {
                        isLoginSuccess2 = true;
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return isLoginSuccess2;
        }
        private boolean verify3() {
            boolean isLoginSuccess3 = false;
            try (BufferedReader br6 = new BufferedReader(new FileReader("user.txt"))) {
                String line2;
                while ((line2 = br6.readLine()) != null) {
                    String[] parts = line2.split("-");
                    if (HWID.equals(parts[3])) {
                        isLoginSuccess3 = true;
                        break;
                    }else {isLoginSuccess3=false;}
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return isLoginSuccess3;
        }



    }
    }
