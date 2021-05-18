public class Player {
    private String name;
    private long money;
    private String identity;
    public int slaveAssemblerCount;
    public int AssemblerCount;

    public int getSalaveAssemblerCount() {
        return slaveAssemblerCount;
    }

    public void setSalaveAssemblerCount(int salaveAssemblerCount) {
        slaveAssemblerCount = salaveAssemblerCount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", identity='" + identity + '\'' +
                '}';
    }
}
