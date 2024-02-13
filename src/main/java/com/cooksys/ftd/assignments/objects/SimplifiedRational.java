package com.cooksys.ftd.assignments.objects;

// import com.cooksys.ftd.assignments.objects.util.MissingImplementationException;

public class SimplifiedRational implements IRational {

    private int numerator;
    private int denominator;
    /**
     * Determines the greatest common denominator for the given values
     *
     * @param a the first value to consider
     * @param b the second value to consider
     * @return the greatest common denominator, or shared factor, of `a` and `b`
     * @throws IllegalArgumentException if a <= 0 or b < 0
     */
    public static int gcd(int a, int b) throws IllegalArgumentException {
        if ( a <= 0 || b < 0 ) {
            throw new IllegalArgumentException();
        }
        if ( a < b ) {
            int temp = a;
            a = b;
            b = temp;
        }
        if (a % b == 0) {
            return b;
        }
        else {
            return gcd(b, a % b);
        }

    }

    /**
     * Simplifies the numerator and denominator of a rational value.
     * <p>
     * For example:
     * `simplify(10, 100) = [1, 10]`
     * or:
     * `simplify(0, 10) = [0, 1]`
     *
     * @param numerator   the numerator of the rational value to simplify
     * @param denominator the denominator of the rational value to simplify
     * @return a two element array representation of the simplified numerator and denominator
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public static int[] simplify(int numerator, int denominator) throws IllegalArgumentException {
        if ( denominator == 0 ) {
            throw new IllegalArgumentException();
        }
        int[] simplified = new int[2];
        if (numerator == 0) {
            simplified[0] = 0;
            simplified[1] = 1;
        }
        int g = gcd(Math.abs(numerator),Math.abs(denominator));
        simplified[0] = numerator / g;
        simplified[1] = denominator / g;
        return simplified;
        
    }

    /**
     * Constructor for rational values of the type:
     * <p>
     * `numerator / denominator`
     * <p>
     * Simplification of numerator/denominator pair should occur in this method.
     * If the numerator is zero, no further simplification can be performed
     *
     * @param numerator   the numerator of the rational value
     * @param denominator the denominator of the rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public SimplifiedRational(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
            throw new IllegalArgumentException();
        }
        if (numerator == 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
        else {
            int g = gcd(Math.abs(numerator),Math.abs(denominator));
            numerator /= g;
            denominator /= g;
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    /**
     * @return the numerator of this rational number
     */
    @Override
    public int getNumerator() {
        return numerator;
    }

    /**
     * @return the denominator of this rational number
     */
    @Override
    public int getDenominator() {
        return denominator;
    }

    /**
     * Specializable constructor to take advantage of shared code between Rational and SimplifiedRational
     * <p>
     * Essentially, this method allows us to implement most of IRational methods directly in the interface while
     * preserving the underlying type
     *
     * @param numerator   the numerator of the rational value to construct
     * @param denominator the denominator of the rational value to construct
     * @return the constructed rational value (specifically, a SimplifiedRational value)
     * @throws IllegalArgumentException if the given denominator is 0
     */
    @Override
    public SimplifiedRational construct(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
            throw new IllegalArgumentException();
        }
        SimplifiedRational s = new SimplifiedRational(numerator, denominator);
        return s;
        }

    /**
     * @param obj the object to check this against for equality
     * @return true if the given obj is a rational value and its
     * numerator and denominator are equal to this rational value's numerator and denominator,
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimplifiedRational) {
            SimplifiedRational o = (SimplifiedRational) obj;
            if (o.getNumerator() == this.getNumerator() && o.getDenominator() == this.getDenominator()) {
                return true;
            }
        }
        return false;
    }

    /**
     * If this is positive, the string should be of the form `numerator/denominator`
     * <p>
     * If this is negative, the string should be of the form `-numerator/denominator`
     *
     * @return a string representation of this rational value
     */
    @Override
    public String toString() {
        String s = "";
        int n = this.getNumerator();
        int d = this.getDenominator();
        s = (n < 0 != d < 0 ? "-" : "") + Math.abs(n) + "/" + Math.abs(d);
        return s;
    }
}
