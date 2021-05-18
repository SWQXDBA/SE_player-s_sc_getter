import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class Assemlers {
    private static String path = "C:\\Users\\SWQXDBA\\Desktop\\gridtest";
    private static PlayerAndMoneyGetter playerAndMoneyGetter;

    static {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入工作路径");
        path = scanner.nextLine();
    }

    static {
        try {
            playerAndMoneyGetter = new PlayerAndMoneyGetter(path + "\\Sandbox.sbc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        File output = new File("assms.txt");
        output.createNewFile();
        FileWriter f = new FileWriter(output);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));

        File file = new File(path + "\\SANDBOX_0_0_0_.sbs");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        System.out.println("装配机检测开始运行");
        while (bufferedReader.read() != -1) {
            String str = bufferedReader.readLine();
            if (str.contains("MyObjectBuilder_Assembler")) {
                String nextstr = bufferedReader.readLine();
                while (!nextstr.contains("<Owner>")) {
                    nextstr = bufferedReader.readLine();
                }

                String identity = nextstr.split("</")[0].split(">")[1];
                Player player = playerAndMoneyGetter.getByIdentity(identity);

                if (player == null) {
                    System.out.println("找不到网格所有者player::" + identity);
                    break;
                }
                while (!nextstr.contains("<SlaveEnabled>")) {
                    nextstr = bufferedReader.readLine();
                }
                if (nextstr.contains("true")) {
                    player.SalaveAssemblerCount++;
                    playerAndMoneyGetter.getPlayersWithIdentity().put(identity, player);
                }
                player.AssemerCount++;
            }

        }
        System.out.println("装配机检测结束");

        Map<String, Player> playerMap = playerAndMoneyGetter.getPlayerByNameMap();
        for (Map.Entry<String, Player> entry : playerMap.entrySet()) {
            Player p = entry.getValue();
            if (p.AssemerCount != 0) {
                f.write(p.getName() + "装配机数量" + p.AssemerCount + "\n");
                f.flush();
            }

        }


        for (Map.Entry<String, Player> entry : playerMap.entrySet()) {
            Player p = entry.getValue();
            if (p.getSalaveAssemblerCount() != 0) {
                f.write(p.getName() + "::" + p.getSalaveAssemblerCount() + "\n");
                f.flush();
            }
        }


    }
}
