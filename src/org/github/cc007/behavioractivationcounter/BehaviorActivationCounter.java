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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double percentage = 0.9;
        int iterations = 1000;
        List<Integer> hist = Arrays.asList(0, 0, 0, 0);
        for (int i = 0; i < iterations; i++) {
            int state = 0;
            hist.set(0, hist.get(0) + 1);
            hist.set(1, hist.get(1) + 2);
            hist.set(2, hist.get(2) + 3);
            hist.set(3, hist.get(3) + 4);
            while (true) {
                if (state == 0) {
                    state = 1;
                    hist.set(1, hist.get(1) + 1);
                    hist.set(2, hist.get(2) + 1);
                    hist.set(3, hist.get(3) + 1);
                } else {
                    Random r = new Random(System.nanoTime());
                    if (r.nextDouble() < percentage) {
                        state--;
                    } else {
                        state++;
                    }
                    if (state == 4) {
                        break;
                    }
                    for (int j = state; j < 4; j++) {
                        hist.set(j, hist.get(j) + 1);
                    }
                }
            }
        }
        double sum = 0.0;
        for (Integer histNr : hist) {
            System.out.print(((double) histNr) / iterations + " ");
            sum += histNr;
        }
        System.out.println("= " + sum / iterations);
        hist = Arrays.asList(0, 0, 0, 0);
        for (int i = 0; i < iterations; i++) {
            int state = 0;
            hist.set(0, hist.get(0) + 1);
            while (true) {
                if (state == 0) {
                    state = 1;
                    hist.set(0, hist.get(0) + 1);
                    hist.set(1, hist.get(1) + 1);
                } else {
                    Random r = new Random(System.nanoTime());
                    if (r.nextDouble() < percentage) {
                        state--;
                    } else {
                        state++;
                    }
                    if (state == 4) {
                        break;
                    }
                    for (int j = state; j >= 0; j--) {
                        hist.set(j, hist.get(j) + 1);
                    }
                }
            }
        }
        sum = 0.0;
        for (Integer histNr : hist) {
            System.out.print(((double) histNr) / iterations + " ");
            sum += histNr;
        }
        System.out.println("= " + sum / iterations);
    }

}
