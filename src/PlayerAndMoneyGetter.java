import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PlayerAndMoneyGetter {
    private String path = "C:\\Users\\SWQXDBA\\Desktop";

    private Map<String, Player> playersWithIdentity = new HashMap<>();
    private Map<String, Long> userMoney = new HashMap<>();

    public PlayerAndMoneyGetter(String path) throws IOException {
        this.path = path;
//        Scanner scanner = new Scanner(System.in);
        // path = scanner.nextLine();
        File file = new File(path);
        if (!file.isFile()) {
            System.out.println("路径错误");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
//第一次遍历填写IdentityId和displayname（玩家名称）
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            //识别身份和玩家名 进行对应

            if (str.contains("<IdentityId>")) {
                Player player = new Player();
                String IdentityId = str.split(">")[1].split("</")[0];

                str = bufferedReader.readLine();
                if (str.contains("<DisplayName>")) {

                    String DisplayName = str.split(">")[1].split("</")[0];
                    player.setIdentity(IdentityId);
                    player.setName(DisplayName);
                    playersWithIdentity.put(IdentityId, player);
                }
            }
        }
        //第二遍根据IdentityId放入money
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while ((str = bufferedReader.readLine()) != null) {


            if (str.contains("<OwnerIdentifier>")) {
                String OwnerIdentifier = str.split(">")[1].split("</")[0];
                Player player = playersWithIdentity.get(OwnerIdentifier);
                //有一些非玩家的身份 比如spacepirate等不存在钱 所以需要进行判断
                if (player != null) {
                    //尝试读取balance字段
                    str = bufferedReader.readLine();
                    if (str.contains("<Balance>")) {
                        String balance = str.split(">")[1].split("</")[0];
                        player.setMoney(Long.parseLong(balance));
                        playersWithIdentity.put(player.getIdentity(), player);
                    } else {
                    //    System.out.println("找不到<Balance>");
                    }
                } else {
                    //     System.out.println("找不到Owner!::OwnerIdentifier " + OwnerIdentifier);
                }
            }
        }
        //读取完毕 开始输出
        System.out.println("读取完毕!");
        System.out.println("共查找到" + playersWithIdentity.size() + "用户");
        System.out.println("输入玩家名称获取余额");

        for (Map.Entry<String, Player> entry : playersWithIdentity.entrySet()) {
            Player p = entry.getValue();
            if (userMoney.containsKey(p.getName())) {
                System.out.println("重名了！！！" + p.getName());
            }
            userMoney.put(p.getName(), p.getMoney());
        }
        bufferedReader.close();
//        int no10000 = 0;
//        for(Map.Entry<String,Long> moneys:userMoney.entrySet()){
//            if(moneys.getValue()==10000)
//                no10000++;
//            System.out.println(moneys.getKey()+"::"+moneys.getValue());
//        }
//        System.out.println("默认的有"+no10000+"个");
//        while (scanner.hasNext()) {
//            System.out.println(userMoney.get(scanner.nextLine()));
//        }
//        scanner.close();
    }

    public Map<String, Player> getPlayerByNameMap() {
        Map<String, Player> m = new HashMap<>();
        for (Map.Entry<String, Player> entry : playersWithIdentity.entrySet()) {
            Player p = entry.getValue();
            m.put(p.getName(), p);
        }
        return m;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Player> getPlayersWithIdentity() {
        return playersWithIdentity;
    }

    public Map<String, Long> getUserMoney() {
        return userMoney;
    }

    //  C:\Users\SWQXDBA\Desktop\Sandbox.sbc
    public Player getByIdentity(String i) {
        return playersWithIdentity.get(i);
    }

}
