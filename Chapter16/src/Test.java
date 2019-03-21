public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        test.setAction2((e) -> System.out.println("Hey"));
    }

    public void setAction2(T2 t) {
        t.m(4.5);
    }
}

interface T2 {
    public void m(Double d);
}