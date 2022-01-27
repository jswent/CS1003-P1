public class test {

    public static void main(String[] args) throws Exception {
        Scenarios scenarios = new Scenarios();
        NgramGenerator ngrams = new NgramGenerator(3);
        System.out.println(ngrams.createNgrams("computr"));
        //scenarios.scenario4();
    
    }
}