public class test {
    static int add() {
        int ret = 5;
        try {
            ret += 10;
            return ret;
        } catch (RuntimeException e) {

        } catch (Exception e2) {

        } finally {
            ret += 10;

        }
        return ret;
    }

    static int add2() {
        int ret = 5;
        try {
            ret += 10;
            return ret;
        } catch (RuntimeException e) {

        } catch (Exception e2) {

        } finally {
            ret += 10;
            return ret;
        }

    }

    public static void main(String[] args) {
        System.out.println(add());
        System.out.println(add2());
    }
}
