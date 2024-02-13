package com.cooksys.ftd.assignments.objects;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class SimplifiedRationalGenerator extends Generator<SimplifiedRational> {

    public static int euclid(int n, int m) {
        while (m != 0) {
            int t = m;
            m = n % m;
            n = t;
        }
        return n;
    }

    public static int[] collapse(int t, int b) {
        int e = t != 0 ? euclid(Math.abs(t), Math.abs(b)) : 1;
        return new int[] { t / e, b / e};
    }

    public SimplifiedRationalGenerator() {
        super(SimplifiedRational.class);
    }

    public SimplifiedRational generate(SourceOfRandomness random, GenerationStatus status) {
        int n = random.nextInt();
        int d;
        do {
            d = random.nextInt();
        } while (d == 0);
        return new SimplifiedRational(n, d);
    }
}
