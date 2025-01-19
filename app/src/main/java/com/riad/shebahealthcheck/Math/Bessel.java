package com.riad.shebahealthcheck.Math;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;

public class Bessel extends com.riad.shebahealthcheck.Math.Cascade {
    private double fact(int n) {
        if (n == 0)
            return 1;

        double y = n;
        for (double m = n - 1; m > 0; m--)
            y = y * m;

        return y;
    }
    class AnalogLowPass extends com.riad.shebahealthcheck.Math.LayoutBase {
        int degree;
        double[] m_a;
        Complex[] m_root;

        private double reversebessel(int k, int n) {
            double result = fact(2 * n - k)
                    / ((fact(n - k) * fact(k)) * Math.pow(2., n - k));
            return result;
        }

        public AnalogLowPass(int _degree) {
            super(_degree);
            degree = _degree;
            m_a = new double[degree + 1]; // input coefficients (degree+1 elements)
            m_root = new Complex[degree]; // array of roots (degree elements)
            setNormal(0, 1);
        }

        public void design() {
            reset();

            for (int i = 0; i < degree + 1; ++i) {
                m_a[i] = reversebessel(i, degree);
            }

            LaguerreSolver laguerreSolver = new LaguerreSolver();

            m_root = laguerreSolver.solveAllComplex(m_a, 0.0);

            Complex inf = Complex.INF;
            int pairs = degree / 2;
            for (int i = 0; i < pairs; ++i) {
                Complex c = m_root[i];
                addPoleZeroConjugatePairs(c, inf);
            }

            if ((degree & 1) == 1)
                add(new Complex(m_root[pairs].getReal()), inf);
        }

    }

    private void setupLowPass(int order, double sampleRate,
                              double cutoffFrequency, int directFormType) {

        AnalogLowPass m_analogProto = new AnalogLowPass(order);

        m_analogProto.design();

        com.riad.shebahealthcheck.Math.LayoutBase m_digitalProto = new com.riad.shebahealthcheck.Math.LayoutBase(order);

        new com.riad.shebahealthcheck.Math.LowPassTransform(cutoffFrequency / sampleRate, m_digitalProto,
                m_analogProto);

        setLayout(m_digitalProto, directFormType);
    }


    public void lowPass(int order, double sampleRate, double cutoffFrequency) {
        setupLowPass(order, sampleRate, cutoffFrequency,
                com.riad.shebahealthcheck.Math.DirectFormAbstract.DIRECT_FORM_II);
    }


    public void lowPass(int order, double sampleRate, double cutoffFrequency,
                        int directFormType) {
        setupLowPass(order, sampleRate, cutoffFrequency, directFormType);
    }

    private void setupHighPass(int order, double sampleRate,
                               double cutoffFrequency, int directFormType) {

        AnalogLowPass m_analogProto = new AnalogLowPass(order);
        m_analogProto.design();

        com.riad.shebahealthcheck.Math.LayoutBase m_digitalProto = new com.riad.shebahealthcheck.Math.LayoutBase(order);

        new com.riad.shebahealthcheck.Math.HighPassTransform(cutoffFrequency / sampleRate, m_digitalProto,
                m_analogProto);

        setLayout(m_digitalProto, directFormType);
    }

    public void highPass(int order, double sampleRate, double cutoffFrequency,
                         int directFormType) {
        setupHighPass(order, sampleRate, cutoffFrequency, directFormType);
    }


    public void highPass(int order, double sampleRate, double cutoffFrequency) {
        setupHighPass(order, sampleRate, cutoffFrequency,
                com.riad.shebahealthcheck.Math.DirectFormAbstract.DIRECT_FORM_II);
    }

    private void setupBandStop(int order, double sampleRate,
                               double centerFrequency, double widthFrequency, int directFormType) {

        AnalogLowPass m_analogProto = new AnalogLowPass(order);
        m_analogProto.design();

        com.riad.shebahealthcheck.Math.LayoutBase m_digitalProto = new com.riad.shebahealthcheck.Math.LayoutBase(order * 2);

        new BandStopTransform(centerFrequency / sampleRate, widthFrequency
                / sampleRate, m_digitalProto, m_analogProto);

        setLayout(m_digitalProto, directFormType);
    }


    public void bandStop(int order, double sampleRate, double centerFrequency,
                         double widthFrequency) {
        setupBandStop(order, sampleRate, centerFrequency, widthFrequency,
                com.riad.shebahealthcheck.Math.DirectFormAbstract.DIRECT_FORM_II);
    }


    public void bandStop(int order, double sampleRate, double centerFrequency,
                         double widthFrequency, int directFormType) {
        setupBandStop(order, sampleRate, centerFrequency, widthFrequency,
                directFormType);
    }

    private void setupBandPass(int order, double sampleRate,
                               double centerFrequency, double widthFrequency, int directFormType) {

        AnalogLowPass m_analogProto = new AnalogLowPass(order);
        m_analogProto.design();

        com.riad.shebahealthcheck.Math.LayoutBase m_digitalProto = new com.riad.shebahealthcheck.Math.LayoutBase(order * 2);

        new BandPassTransform(centerFrequency / sampleRate, widthFrequency
                / sampleRate, m_digitalProto, m_analogProto);

        setLayout(m_digitalProto, directFormType);

    }


    public void bandPass(int order, double sampleRate, double centerFrequency,
                         double widthFrequency) {
        setupBandPass(order, sampleRate, centerFrequency, widthFrequency,
                com.riad.shebahealthcheck.Math.DirectFormAbstract.DIRECT_FORM_II);
    }

    public void bandPass(int order, double sampleRate, double centerFrequency,
                         double widthFrequency, int directFormType) {
        setupBandPass(order, sampleRate, centerFrequency, widthFrequency,
                directFormType);
    }

}
