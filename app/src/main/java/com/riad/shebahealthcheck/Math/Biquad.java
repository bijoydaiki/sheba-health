
package com.riad.shebahealthcheck.Math;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;

public class Biquad {

//    double m_a0;
//    double m_a1;
//    double m_a2;
//    double m_b1;
//    double m_b2;
//    double m_b0;
    double m_a0;
    double m_a1;
    double m_a2;
    double m_b1;
    double m_b2;
    double m_b0;

    public double getA0() {
        return m_a0;
    }

    public double getA1() {
        return m_a1 * m_a0;
    }

    public double getA2() {
        return m_a2 * m_a0;
    }

    public double getB0() {
        return m_b0 * m_a0;
    }

    public double getB1() {
        return m_b1 * m_a0;
    }

    public double getB2() {
        return m_b2 * m_a0;
    }

    public Complex response(double normalizedFrequency) {
        double a0 = getA0();
        double a1 = getA1();
        double a2 = getA2();
        double b0 = getB0();
        double b1 = getB1();
        double b2 = getB2();

        double w = 2 * Math.PI * normalizedFrequency;
        Complex czn1 = ComplexUtils.polar2Complex(1., -w);
        Complex czn2 = ComplexUtils.polar2Complex(1., -2 * w);
        Complex ch = new Complex(1);
        Complex cbot = new Complex(1);

        Complex ct = new Complex(b0 / a0);
        Complex cb = new Complex(1);
        ct = com.riad.shebahealthcheck.Math.MathSupplement.addmul(ct, b1 / a0, czn1);
        ct = com.riad.shebahealthcheck.Math.MathSupplement.addmul(ct, b2 / a0, czn2);
        cb = com.riad.shebahealthcheck.Math.MathSupplement.addmul(cb, a1 / a0, czn1);
        cb = com.riad.shebahealthcheck.Math.MathSupplement.addmul(cb, a2 / a0, czn2);
        ch = ch.multiply(ct);
        cbot = cbot.multiply(cb);

        return ch.divide(cbot);
    }

    public void setCoefficients(double a0, double a1, double a2, double b0, double b1, double b2) {
        m_a0 = a0;
        m_a1 = a1 / a0;
        m_a2 = a2 / a0;
        m_b0 = b0 / a0;
        m_b1 = b1 / a0;
        m_b2 = b2 / a0;
    }

    public void setOnePole(Complex pole, Complex zero) {
        double a0 = 1;
        double a1 = -pole.getReal();
        double a2 = 0;
        double b0 = -zero.getReal();
        double b1 = 1;
        double b2 = 0;
        setCoefficients(a0, a1, a2, b0, b1, b2);
    }

    public void setTwoPole(Complex pole1, Complex zero1,
                           Complex pole2, Complex zero2) {
        double a0 = 1;
        double a1;
        double a2;

        if (pole1.getImaginary() != 0) {

            a1 = -2 * pole1.getReal();
            a2 = pole1.abs() * pole1.abs();
        } else {

            a1 = -(pole1.getReal() + pole2.getReal());
            a2 = pole1.getReal() * pole2.getReal();
        }

        double b0 = 1;
        double b1;
        double b2;

        if (zero1.getImaginary() != 0) {

            b1 = -2 * zero1.getReal();
            b2 = zero1.abs() * zero1.abs();
        } else {

            b1 = -(zero1.getReal() + zero2.getReal());
            b2 = zero1.getReal() * zero2.getReal();
        }

        setCoefficients(a0, a1, a2, b0, b1, b2);
    }

    public void setPoleZeroForm(com.riad.shebahealthcheck.Math.BiquadPoleState bps) {
        setPoleZeroPair(bps);
        applyScale(bps.gain);
    }

    public void setIdentity() {
        setCoefficients(1, 0, 0, 1, 0, 0);
    }

    public  void  applyScale(double scale){
        m_b0 *=scale;
        m_b1 *=scale;
        m_b2 *=scale;
    }


    public void setPoleZeroPair(com.riad.shebahealthcheck.Math.PoleZeroPair pair) {
        if (pair.isSinglePole()) {
            setOnePole(pair.poles.first, pair.zeros.first);
        } else {
            setTwoPole(pair.poles.first, pair.zeros.first,
                    pair.poles.second, pair.zeros.second);
        }
    }
}
