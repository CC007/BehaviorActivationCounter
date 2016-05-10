/* 
 * The MIT License
 *
 * Copyright 2016 Rik Schaaf aka CC007 (http://coolcat007.nl/).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.github.cc007.behavioractivationcounter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Rik Schaaf aka CC007
 */
public class BehaviorActivationCounter {

    private static final double chanceChange = 0.01;
    private static final double chanceFailure = 0.2;
    private static final int iterations = 100000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Integer> altSequentialHist = altSequential();
        double sumAltSequential = printAndSumHistogram(altSequentialHist);
        List<Integer> managedHist = managed();
        double sumManaged = printAndSumHistogram(managedHist);
        List<Integer> topDownHist = topDown();
        double sumTopDown = printAndSumHistogram(topDownHist);
        List<Integer> bottomUpHist = bottomUp();
        double sumBottomUp = printAndSumHistogram(bottomUpHist);
        System.out.println("Ratio: " + sumTopDown / (sumBottomUp + sumTopDown));

    }

    public static double printAndSumHistogram(List<Integer> hist) {
        double sum = 0.0;
        for (Integer histNr : hist) {
            System.out.print(((double) histNr) / iterations + " ");
            sum += histNr;
        }
        System.out.println("= " + sum / iterations);
        return sum;
    }

    public static List<Integer> altSequential() {
        List<Integer> hist = Arrays.asList(0, 0, 0, 0);
        for (int i = 0; i < iterations; i++) {
            int state = 0;
            hist.set(0, hist.get(0) + 1);
            while (true) {
                Random r = new Random(System.nanoTime());
                if (r.nextDouble() < chanceChange) {
                    if (state == 0) {
                        state = 1;
                    } else {
                        if (r.nextDouble() < chanceFailure) {
                            state = 0;
                        } else {
                            state++;
                        }
                        if (state == 4) {
                            break;
                        }
                    }
                }
                hist.set(state, hist.get(state) + 1);
            }
        }
        return hist;
    }
    public static List<Integer> managed() {
        List<Integer> hist = Arrays.asList(0, 0, 0, 0, 0);
        for (int i = 0; i < iterations; i++) {
            int state = 0;
            while (true) {
                Random r = new Random(System.nanoTime());
                hist.set(4, hist.get(4) + 1);
                hist.set(state, hist.get(state) + 1);
                if (r.nextDouble() < chanceChange) {
                    if (state == 0) {
                        state = 1;
                    } else {
                        if (r.nextDouble() < chanceFailure) {
                            state--;
                        } else {
                            state++;
                        }
                        if (state == 4) {
                            break;
                        }
                    }
                }
            }
        }
        return hist;
    }

    public static List<Integer> topDown() {
        List<Integer> hist = Arrays.asList(0, 0, 0, 0);
        for (int i = 0; i < iterations; i++) {
            int state = 0;
            hist.set(0, hist.get(0) + 1);
            hist.set(1, hist.get(1) + 2);
            hist.set(2, hist.get(2) + 3);
            hist.set(3, hist.get(3) + 4);
            while (true) {
                Random r = new Random(System.nanoTime());
                if (r.nextDouble() < chanceChange) {
                    if (state == 0) {
                        state = 1;
                    } else {
                        if (r.nextDouble() < chanceFailure) {
                            state--;
                        } else {
                            state++;
                        }
                        if (state == 4) {
                            break;
                        }
                    }
                }
                for (int j = state; j < 4; j++) {
                    hist.set(j, hist.get(j) + 1);
                }
            }
        }
        return hist;
    }

    public static List<Integer> bottomUp() {
        List<Integer> hist = Arrays.asList(0, 0, 0, 0);
        for (int i = 0; i < iterations; i++) {
            int state = 0;
            hist.set(0, hist.get(0) + 1);
            while (true) {
                Random r = new Random(System.nanoTime());
                if (r.nextDouble() < chanceChange) {
                    if (state == 0) {
                        state = 1;
                    } else {
                        if (r.nextDouble() < chanceFailure) {
                            state--;
                        } else {
                            state++;
                        }
                        if (state == 4) {
                            break;
                        }
                    }
                }
                for (int j = state; j >= 0; j--) {
                    hist.set(j, hist.get(j) + 1);
                }
            }
        }
        return hist;
    }

}
