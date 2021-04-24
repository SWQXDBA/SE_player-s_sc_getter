import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class io {
    private static String path = "C:\\Users\\SWQXDBA\\Desktop\\Sandbox.sbc";

    //  C:\Users\SWQXDBA\Desktop\Sandbox.sbc
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // path = scanner.nextLine();
        File file = new File(path);
        if (!file.isFile()) {
            System.out.println("路径错误");
            return;
        }
        Map<String, Player> playersWithIdentity = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        while (-1 != bufferedReader.read()) {
            //识别身份和玩家名 进行对应
            String str = bufferedReader.readLine();
            if (-1 != str.indexOf("<IdentityId>")) {
                Player player = new Player();
                String IdentityId = str.split(">")[1].split("</")[0];

                str = bufferedReader.readLine();
                if (-1 != str.indexOf("<DisplayName>")) {

                    String DisplayName = str.split(">")[1].split("</")[0];
                    player.setIdentity(IdentityId);
                    player.setName(DisplayName);
                    playersWithIdentity.put(IdentityId, player);
                }
            }
        }
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (-1 != bufferedReader.read()) {
            String str = bufferedReader.readLine();
            if (-1 != str.indexOf("<OwnerIdentifier>")) {
                String OwnerIdentifier = str.split(">")[1].split("</")[0];
                Player player = playersWithIdentity.get(OwnerIdentifier);
                if (player != null) {
                    //尝试读取balance字段
                    str = bufferedReader.readLine();
                    if (-1 != str.indexOf("<Balance>")) {
                        String balance = str.split(">")[1].split("</")[0];
                        player.setMoney(Long.parseLong(balance));
                        playersWithIdentity.put(player.getIdentity(), player);
                    } else {
                        System.out.println("找不到<Balance>");
                    }
                } else {
                    System.out.println("找不到Owner!::OwnerIdentifier " + OwnerIdentifier);
                }
            }
        }
        //读取完毕 开始输出
        System.out.println("读取完毕!");
        System.out.println("共查找到" + playersWithIdentity.size() + "用户");
        Map<String, Long> userMoney = new HashMap<>();
        for (Map.Entry<String, Player> entry : playersWithIdentity.entrySet()) {
            Player p = entry.getValue();
            userMoney.put(p.getName(), p.getMoney());
        }
        while (scanner.hasNext()) {
            System.out.println(userMoney.get(scanner.nextLine()));
        }
    }
}
