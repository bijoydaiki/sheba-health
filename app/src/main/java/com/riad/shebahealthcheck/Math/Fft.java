package com.riad.shebahealthcheck.Math;

public class Fft {

    public static double FFT(Double[] in, int size, double samplingFrequency) {
        double temp = 0;
        double POMP = 0;
        double frequency;
        double[] output = new double[2 * size];

        for (int i = 0; i < output.length; i++)
            output[i] = 0;

        for (int x = 0; x < size; x++) {
            output[x] = in[x];
        }

        DoubleFft1d fft = new DoubleFft1d(size);
        fft.realForward(output);

        for (int x = 0; x < 2 * size; x++) {
            output[x] = Math.abs(output[x]);
        }

        for (int p = 35; p < size; p++) {
            if (temp < output[p]) {
                temp = output[p];
                POMP = p;

            }
        }

        if (POMP < 35) {
            POMP = 0;
        }

        frequency = POMP * samplingFrequency / (2 * size);
        return frequency;
    }
}
