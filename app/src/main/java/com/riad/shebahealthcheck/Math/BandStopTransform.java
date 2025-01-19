
package com.riad.shebahealthcheck.Math;
import org.apache.commons.math3.complex.Complex;
public class BandStopTransform {
    private double wc;
    private double wc2;
    private final double a;
    private final double b;
    private final double a2;
    private final double b2;
    public BandStopTransform(double fc, double fw, com.riad.shebahealthcheck.Math.LayoutBase digital, com.riad.shebahealthcheck.Math.LayoutBase analog) {
        digital.reset();

        double ww = 2 * Math.PI * fw;

        wc2 = 2 * Math.PI * fc - (ww / 2);
        wc = wc2 + ww;

        if (wc2 < 1e-8)
            wc2 = 1e-8;
        if (wc > Math.PI - 1e-8)
            wc = Math.PI - 1e-8;

        a = Math.cos((wc + wc2) * .5) /
                Math.cos((wc - wc2) * .5);
        b = Math.tan((wc - wc2) * .5);
        a2 = a * a;
        b2 = b * b;

        int numPoles = analog.getNumPoles();
        int pairs = numPoles / 2;
        for (int i = 0; i < pairs; i++) {
            com.riad.shebahealthcheck.Math.PoleZeroPair pair = analog.getPair(i);
            com.riad.shebahealthcheck.Math.ComplexPair p = transform(pair.poles.first);
            com.riad.shebahealthcheck.Math.ComplexPair z = transform(pair.zeros.first);
            digital.addPoleZeroConjugatePairs(p.first, z.first);
            digital.addPoleZeroConjugatePairs(p.second, z.second);
        }

        if ((numPoles & 1) == 1) {
            com.riad.shebahealthcheck.Math.ComplexPair poles = transform(analog.getPair(pairs).poles.first);
            com.riad.shebahealthcheck.Math.ComplexPair zeros = transform(analog.getPair(pairs).zeros.first);

            digital.add(poles, zeros);
        }

        if (fc < 0.25)
            digital.setNormal(Math.PI, analog.getNormalGain());
        else
            digital.setNormal(0, analog.getNormalGain());
    }

    private com.riad.shebahealthcheck.Math.ComplexPair transform(Complex c) {
        if (c.isInfinite())
            c = new Complex(-1);
        else
            c = ((new Complex(1)).add(c)).divide((new Complex(1)).subtract(c)); // bilinear

        Complex u = new Complex(0);
        u = com.riad.shebahealthcheck.Math.MathSupplement.addmul(u, 4 * (b2 + a2 - 1), c);
        u = u.add(8 * (b2 - a2 + 1));
        u = u.multiply(c);
        u = u.add(4 * (a2 + b2 - 1));
        u = u.sqrt();

        Complex v = u.multiply(-.5);
        v = v.add(a);
        v = com.riad.shebahealthcheck.Math.MathSupplement.addmul(v, -a, c);

        u = u.multiply(.5);
        u = u.add(a);
        u = com.riad.shebahealthcheck.Math.MathSupplement.addmul(u, -a, c);

        Complex d = new Complex(b + 1);
        d = com.riad.shebahealthcheck.Math.MathSupplement.addmul(d, b - 1, c);

        return new com.riad.shebahealthcheck.Math.ComplexPair(u.divide(d), v.divide(d));
    }

}
