package test;

public class test_hashcode {
    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<100;i++){
            String s = "1000000003" + System.currentTimeMillis();
            Thread.sleep(1);
            System.out.println(s.hashCode());
        }
    }
}
