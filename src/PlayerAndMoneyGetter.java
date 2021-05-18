import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PlayerAndMoneyGetter {
    private String path;

    private Map<String, Player> playersWithIdentity = new HashMap<>();
    private Map<String, Long> userMoney = new HashMap<>();

    public PlayerAndMoneyGetter(String path) throws IOException {
        this.path = path;
//        Scanner scanner = new Scanner(System.in);
        // path = scanner.nextLine();
        File file = new File(path);
        if (!file.isFile()) {
            System.out.println("·������");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
//��һ�α�����дIdentityId��displayname��������ƣ�
        while (-1 != bufferedReader.read()) {
            //ʶ����ݺ������ ���ж�Ӧ
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
        //�ڶ������IdentityId����money
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (-1 != bufferedReader.read()) {
            String str = bufferedReader.readLine();

            if (-1 != str.indexOf("<OwnerIdentifier>")) {
                String OwnerIdentifier = str.split(">")[1].split("</")[0];
                Player player = playersWithIdentity.get(OwnerIdentifier);
                //��һЩ����ҵ���� ����spacepirate�Ȳ�����Ǯ ������Ҫ�����ж�
                if (player != null) {
                    //���Զ�ȡbalance�ֶ�
                    str = bufferedReader.readLine();
                    if (-1 != str.indexOf("<Balance>")) {
                        String balance = str.split(">")[1].split("</")[0];
                        player.setMoney(Long.parseLong(balance));
                        playersWithIdentity.put(player.getIdentity(), player);
                    } else {
                        System.out.println("�Ҳ���<Balance>");
                    }
                } else {
                    System.out.println("�Ҳ���Owner!::OwnerIdentifier " + OwnerIdentifier);
                }
            }
        }
        //��ȡ��� ��ʼ���
        System.out.println("��ȡ���!");
        System.out.println("�����ҵ�" + playersWithIdentity.size() + "�û�");
        System.out.println("����������ƻ�ȡ���");

        for (Map.Entry<String, Player> entry : playersWithIdentity.entrySet()) {
            Player p = entry.getValue();
            userMoney.put(p.getName(), p.getMoney());
        }
        bufferedReader.close();
//        int no10000 = 0;
//        for(Map.Entry<String,Long> moneys:userMoney.entrySet()){
//            if(moneys.getValue()==10000)
//                no10000++;
//            System.out.println(moneys.getKey()+"::"+moneys.getValue());
//        }
//        System.out.println("Ĭ�ϵ���"+no10000+"��");
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
