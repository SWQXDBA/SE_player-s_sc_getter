import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class getMoney {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = "C:\\Users\\SWQXDBA\\Desktop\\Sandbox.sbc";
        try {
            PlayerAndMoneyGetter p = new PlayerAndMoneyGetter(path);
            int no10000 = 0;

            for (Map.Entry<String, Long> moneys : p.getUserMoney().entrySet()) {
                if (moneys.getValue() == 10000)
                    no10000++;
                System.out.println(moneys.getKey() + "::" + moneys.getValue());
            }

            System.out.println("默认的有" + no10000 + "个");
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                Map<String, Long> moneys = p.getUserMoney();
                for (Map.Entry<String, Long> m : moneys.entrySet()) {
                    if (m.getKey().contains(str))
                        System.out.println(m.getKey() + "::" + m.getValue());
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
