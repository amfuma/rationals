package com.cooksys.ftd.assignments.objects;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.When;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static com.cooksys.ftd.assignments.objects.SimplifiedRationalGenerator.collapse;
import static com.cooksys.ftd.assignments.objects.SimplifiedRationalGenerator.euclid;
import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class SimplifiedRationalProperties {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Property
    public void gcdFailA(@InRange(max = "-1") int a) {
        thrown.expect(IllegalArgumentException.class);
        SimplifiedRational.gcd(a, 0);
    }

    @Property
    public void gcdFailB(@InRange(max = "-1") int b) {
        thrown.expect(IllegalArgumentException.class);
        SimplifiedRational.gcd(0, b);
    }

    @Property
    public void gcdSuccess(@InRange(min = "1") int a, @InRange(min = "0") int b) {
        assertEquals(euclid(a, b), SimplifiedRational.gcd(a, b));
    }

    @Property
    public void simplifyFail(int n) {
        thrown.expect(IllegalArgumentException.class);
        SimplifiedRational.simplify(n, 0);
    }

    @Property
    public void simplifySuccess(int n, @When(satisfies = "#_ != 0") int d) {
        assertArrayEquals(collapse(n, d), SimplifiedRational.simplify(n, d));
    }

    @Property
    public void constructorFail(int n) {
        thrown.expect(IllegalArgumentException.class);
        new SimplifiedRational(n, 0);
    }

    @Property
    public void constructorSuccess(int n, @When(satisfies = "#_ != 0") int d) {
        int[] expected = collapse(n, d);

        SimplifiedRational r = new SimplifiedRational(n, d);
        assertEquals(expected[0], r.getNumerator());
        assertEquals(expected[1], r.getDenominator());
    }

    @Property
    public void constructFail(@GenSim SimplifiedRational r, int n) {
        thrown.expect(IllegalArgumentException.class);
        r.construct(n, 0);
    }

    @Property
    public void constructSuccess(@GenSim SimplifiedRational _r, int n, @When(satisfies = "#_ != 0") int d) {
        int[] expected = collapse(n, d);
        SimplifiedRational r = _r.construct(n, d);
        assertTrue(_r != r);
        assertEquals(expected[0], r.getNumerator());
        assertEquals(expected[1], r.getDenominator());
    }

    @Property
    public void equals(@GenSim SimplifiedRational r1) {
        assertNotEquals(r1, 1);
        assertNotEquals(r1, "");
        assertNotEquals(r1, null);

        IRational r2 = r1.construct(r1.getNumerator(), r1.getDenominator()).mul(r1.construct(2, 2));
        assertEquals(r1, r2);

        IRational r3 = r2.construct(r2.getNumerator() * 3, r2.getDenominator() * 5).mul(r2.construct(3, 5));
        assertNotEquals(r1, r3);
        assertNotEquals(r2, r3);
    }

    @Property
    public void toString(@GenSim SimplifiedRational r) {
        int n = r.getNumerator();
        int d = r.getDenominator();

        assertEquals((n < 0 != d < 0 ? "-" : "") + Math.abs(n) + "/" + Math.abs(d), r.toString());
    }

    @Property
    public void negate(@GenSim SimplifiedRational sr) {
        IRational result = sr.negate();
        assertTrue(sr != result);
        assertEquals(new SimplifiedRational(-sr.getNumerator(), sr.getDenominator()), result);
    }

    @Property
    public void invertFail(@When(satisfies = "#_ != 0") int d) {
        thrown.expect(IllegalStateException.class);
        new SimplifiedRational(0, d).invert();
    }

    @Property
    public void invert(@GenSim SimplifiedRational sr) {
        IRational result = sr.invert();
        assertTrue(sr != result);
        assertEquals(new SimplifiedRational(sr.getDenominator(), sr.getNumerator()), result);
    }

    @Property
    public void addFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.add(null);
    }

    @Property
    public void add(@GenSim SimplifiedRational r1, @GenSim SimplifiedRational r2) {
        IRational result = r1.add(r2);
        assertTrue(r1 != result && r2 != result);
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new SimplifiedRational((n1 * d2) + (n2 * d1), d1 * d2), result);
    }

    @Property
    public void subFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.sub(null);
    }

    @Property
    public void sub(@GenSim SimplifiedRational r1, @GenSim SimplifiedRational r2) {
        IRational result = r1.sub(r2);
        assertTrue(r1 != result && r2 != result);
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new SimplifiedRational((n1 * d2) - (n2 * d1), d1 * d2), result);
    }

    @Property
    public void mulFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.mul(null);
    }

    @Property
    public void mul(@GenSim SimplifiedRational r1, @GenSim SimplifiedRational r2) {
        IRational result = r1.mul(r2);
        assertTrue(r1 != result && r2 != result);
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        int n2 = r2.getNumerator();
        int d2 = r2.getDenominator();
        assertEquals(new SimplifiedRational(n1 * n2, d1 * d2), result);
    }

    @Property
    public void divFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.div(null);
    }

    @Property
    public void divZeroFail(@GenSim SimplifiedRational sr) {
        thrown.expect(IllegalArgumentException.class);
        sr.div(new SimplifiedRational(0, 1));
    }

    @Property
    public void div(@GenSim SimplifiedRational r1, @When(satisfies = "#_ != 0") int n2, @When(satisfies = "#_ != 0") int d2) {
        SimplifiedRational r2 = new SimplifiedRational(n2, d2);
        IRational result = r1.div(r2);
        assertTrue(r1 != result && r2 != result);
        int n1 = r1.getNumerator();
        int d1 = r1.getDenominator();
        assertEquals(new SimplifiedRational(n1 * r2.getDenominator(), d1 * r2.getNumerator()), result);
    }
}
