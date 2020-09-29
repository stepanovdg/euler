package utils;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.pow;

/**
 * Created by Dmitriy Stepanov on 10/17/17.
 */
public class Utils {

    public static List findMultiplesLessThen(List<Integer> ret, int belowWhat, int ofWhat) {
        if (ret == null) {
            ret = new ArrayList<>();
        }
        int cnt = 1;
        Integer mltpl;
        while ((mltpl = cnt++ * ofWhat) < belowWhat) {
            ret.add(mltpl);
            //System.out.println(mltpl);
        }
        return ret;
    }

    public static Set findMultiplesLessThen(List<Integer> ret, int belowWhat, int... ofWhats) {
        if (ret == null) {
            ret = new ArrayList<>();
        }
        for (int ofWhat : ofWhats) {
            findMultiplesLessThen(ret, belowWhat, ofWhat);
        }
        return new HashSet(ret);
    }

    public static Deque createFibbonachi(int lessThan) {
        Deque set = new ArrayDeque();
        int i;
        while ((i = fibbonachi(set)) < lessThan) {

        }
        return set;
    }

    public static int sumOftheCollection(Collection e) {
        return e.stream().mapToInt(o -> (int) o).sum();
    }

    private static int fibbonachi(Deque col) {
        int last, t2, t1;
        switch (col.size()) {
            case 0: {
                last = 1;
                break;
            }
            case 1: {
                last = 2;
                break;
            }
            default: {
                t2 = (int) col.pollLast();
                t1 = (int) col.pollLast();
                last = t2 + t1;
                col.addLast(t1);
                col.addLast(t2);
            }
        }
        col.addLast(last);
        return last;
    }

    private static Map<BigInteger, BigInteger> primeFactors(BigInteger b, BigInteger i, Map set) {
        if (set == null) {
            set = new TreeMap<BigInteger, BigInteger>();
        }
        while (b.compareTo(i) > 0) {
            if (b.remainder(i).equals(BigInteger.valueOf(0))) {
                //System.out.println(i);
                BigInteger buf = b.divide(i);
                BigInteger count = BigInteger.ONE;
                while ((buf.remainder(i)).equals(BigInteger.ZERO)) {
                    buf = buf.divide(i);
                    count = count.add(BigInteger.valueOf(1));
                }
                set.put(i, count);
                return primeFactors(buf, i, set);
            }
            i = i.add(BigInteger.valueOf(1));
        }
        set.put(b, BigInteger.valueOf(1));
        return set;
    }

    public static Map<BigInteger, BigInteger> primeFactors(BigInteger bigInteger) {
        return primeFactors(bigInteger, BigInteger.valueOf(2), null);
    }

    public static List biggestPalindrom2(Integer numbderDigits) {
        List l = new ArrayList();
        Double maxN = pow(10, numbderDigits) - 1;
        maxN = maxN * maxN;
        Double minN = pow(10, numbderDigits - 1);
        minN = minN * minN;
        for (Double i = maxN; i >= minN; i--) {
            if (StringUtils.isPalindrom(String.valueOf(i.intValue()))) {
                Map<BigInteger, BigInteger> m = primeFactors(BigInteger.valueOf(i.intValue()));
                long count = m.keySet().stream().filter(o -> o.toString().length() == 3).count();
                if (count == 2 && m.size() == 2) {
                    l.add(i);
                    return l;
                } else if (count > 2) {
                    continue;
                } else {
                    List l2 = new ArrayList();
                    BigInteger buf = new BigInteger("1");
                    /*for (BigInteger bi : m.keySet()) {
                        BigInteger mltp = m.get(bi);
                        BigInteger bbi;
                        for (bbi = BigInteger.ZERO; mltp.compareTo(bbi) > 0; bbi.add(BigInteger.ONE)) {
                            buf = buf.multiply(bi);
                            //
                        }
                    }*/
                    /*m.forEach((k, v) -> l2.add(k.multiply(v)));*/

                }

            }
        }
        return l;
    }

    public static List biggestPalindrom(Integer numbderDigits) {
        List l = new ArrayList();
        Double maxN = pow(10, numbderDigits) - 1;
        //maxN = maxN * maxN;
        Double minN = pow(10, numbderDigits - 1);
        //minN = minN * minN;
        for (Double i = maxN; i >= minN; i--) {
            for (Double b = maxN; b >= minN; b--) {
                Double num = i * b;
                if (StringUtils.isPalindrom(((Integer) num.intValue()).toString())) {
                    l.add(num);
                }

            }
        }
        return l;
    }

    public static BigInteger smallestPositiveDividedBy(Integer first, Integer last) {
        List l = new ArrayList();
        Map<BigInteger, BigInteger> shouldBeDividedBy = new HashMap();
        for (Integer i = first; i <= last; i++) {
            Map<BigInteger, BigInteger> m = primeFactors(BigInteger.valueOf(i));
            for (BigInteger b : m.keySet()) {
                BigInteger valueNew = (BigInteger) m.get(b);
                BigInteger existed = shouldBeDividedBy.put(b, valueNew);
                if (existed != null) {
                    shouldBeDividedBy.put(b, valueNew.max(existed));
                }
            }

        }
        BigInteger num = BigInteger.ONE;
        for (BigInteger b : shouldBeDividedBy.keySet()) {
            num = num.multiply(b.pow(shouldBeDividedBy.get(b).intValue()));
        }
        return num;
    }

    public static Integer sumSquareDifference(Integer num) {
        Integer sum = 0;
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= num; j++) {
                if (i == j) {
                    continue;
                } else {
                    sum = sum + i * j;
                }
            }
        }
        return sum;
    }

    public static BigInteger primeNubmer(Integer num) {
        //first approach (another use already known primes)
        long count = 2;
        BigInteger start = BigInteger.valueOf(3);
        while (count < num) {
            start = start.add(BigInteger.valueOf(2));
            if (primeFactors(start).size() == 1) {
                count++;
            }
        }
        return start;
    }

    private static Long productOfDigits(String str) {
        Long pr = 1l;
        for (int i = 0; i < str.length(); i++) {
            pr = pr * Integer.valueOf(((Character) str.charAt(i)).toString());
        }
        return pr;
    }

    public static Long sumOfadjacent(int adj, String str) {
        Long max = 0l;
        for (int i = adj; i < str.length(); i++) {
            String digits = str.substring(i - adj, i);
            max = Math.max(max, productOfDigits(digits));
        }
        return max;
    }

    public static Integer pythagoreanTriplet() {
        Integer a, b, c;
        for (int i = 1; i <= 1000; i++) {
            try {
                Integer pr = (1000 * i - 500000) / (i - 1000);
                a = pr;
            } catch (Exception e) {
                continue;
            }
            if (a < 1) {
                continue;
            } else {
                b = i;
                c = 1000 - a - b;
                if (c * c == a * a + b * b) {
                    return a * b * c;
                } else {
                    continue;
                }
            }
        }
        return 0;
    }

    public static List primesUnder(long i) {
        List<Long> primes = new ArrayList();
        primes.add(2l);
        primes.add(3l);
        boolean prime = true;
        for (Long b = 5l; b <= i; b = b + 2) {
            prime = true;
            for (Long pr : primes) {
                if (b % pr == 0) {
                    prime = false;
                    break;
                }
            }
            if (prime) {
                // System.out.println(b);
                primes.add(b);
            }
        }
        return primes;
    }

    private static void crossOutNotPrimes(Boolean[] bytearr, Integer prime) {
        for (Integer i = 2; i * prime < bytearr.length; i++) {
            bytearr[i * prime] = false;
        }
    }

    public static Set<Integer> primesUnderFast(int n) {
        Boolean[] bytearr = new Boolean[n + 1];
        Arrays.fill(bytearr, true);
        Set<Integer> primes = new HashSet<>();
        Long sum = 2l;
        primes.add(2);
        boolean notFound = true;

        int i;
        for (i = 2; i*i < bytearr.length; i++) {
            if (bytearr[i]) {
                //System.out.println("i2=" + i);
                if (!primes.contains(i)){
                    sum =sum+i;
                }
                primes.add(i);
                crossOutNotPrimes(bytearr, i);
            }
            if (sum>= n && notFound){
                System.out.println(sum-i);
                notFound = false;
            }
        }
        for (int i2 = i; i2 < bytearr.length; i2++) {
            if (bytearr[i2]) {
                //System.out.println("i2=" + i2);
                //System.out.println("i2=" + i);
                if (!primes.contains(i2)){
                    sum =sum+i2;
                }
                primes.add(i2);
            }
            if (sum>= n && notFound){
                System.out.println(sum-i2);
                notFound = false;
            }
        }
        System.out.println(sum);
        return primes;
    }
}
