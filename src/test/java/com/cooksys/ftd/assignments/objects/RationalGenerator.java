package com.cooksys.ftd.assignments.objects;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class RationalGenerator extends Generator<Rational> {

    public RationalGenerator() {
        super(Rational.class);
    }

    public Rational generate(SourceOfRandomness random, GenerationStatus status) {
        int n = random.nextInt();
        ;
        int d;
        do {
            d = random.nextInt();
        } while (d == 0);
        return new Rational(n, d);
    }
}
