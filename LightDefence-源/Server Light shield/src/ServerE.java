import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeDate.getDay;

public class ServerE {
    public static void main(String[] args) throws IOException {
        final int serverPort =32101 ; // 服务端端口号

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("服务端已启动，等待连接...");

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
            String result = null;
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                // 先读取数据长度
                int usernameLength = dis.readInt();
                int passwordLength = dis.readInt();
                int HWIDLength = dis.readInt();
                int CDKLength = dis.readInt();

                // 再根据长度读取数据内容
                byte[] usernameBytes = new byte[usernameLength];
                byte[] passwordBytes = new byte[passwordLength];
                byte[] HWIDBytes = new byte[HWIDLength];
                byte[] CDKBytes = new byte[CDKLength];
                dis.readFully(usernameBytes);
                dis.readFully(passwordBytes);
                dis.readFully(HWIDBytes);
                dis.readFully(CDKBytes);
                String username = new String(usernameBytes, StandardCharsets.UTF_8);
                String password = new String(passwordBytes, StandardCharsets.UTF_8);
                String HWID = new String(HWIDBytes, StandardCharsets.UTF_8);
                String CDK= new String(CDKBytes,StandardCharsets.UTF_8);
                System.out.println("收到来自客户端的请求，用户名&密码&HWID：" + username + " " + password+" " +HWID);

                String inputStr = CDK;


                try {
                    File inputFile = new File("cdk.txt");
                    File outputFile = new File("cdk_tmp.txt");
                    BufferedReader br2 = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputFile));
                    String line;
                    boolean flag = false;
                    while ((line = br2.readLine()) != null) {
                        if (inputStr.equals(line.trim())) {
                            flag = true;
                        } else {
                            bw2.write(line);
                            bw2.newLine();
                        }
                    }
                    br2.close();
                    bw2.close();
                    if (flag) {
                        inputFile.delete();
                        outputFile.renameTo(inputFile);
                        if (username != null) {
                            // 获取系统当前日期
                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
                            String dateString = dateFormat.format(currentDate);


                            try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
                                bw.write(username + "-" + password + "-" + dateString+"-"+HWID); // 将日期加到用户信息后面
                                bw.newLine();
                                bw.flush();
                                System.out.println("用户 " + username + " 注册成功");
                                result = "注册成功";
                            }

                        } else {
                            System.out.println("用户 " + username + " 已经存在");
                            result = "ERROR";
                        }
                    } else {
                        outputFile.delete();
                        result = "激活码错误！";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



                // 将结果返回给客户端
                byte[] resultBytes = result.getBytes(StandardCharsets.UTF_8);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(resultBytes.length);
                dos.write(resultBytes);
                dos.flush();

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

        private boolean isUserExists(String username) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("-");
                    if (username.equals(parts[0])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
