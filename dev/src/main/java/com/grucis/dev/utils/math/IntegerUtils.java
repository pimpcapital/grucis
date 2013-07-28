package com.grucis.dev.utils.math;

public class IntegerUtils {
  public static int findNextPowerOfTwo(int k) {
    k--;
    for(int i = 1; i < Integer.highestOneBit(k); i <<= 1)
      k = k | k >> i;
    return k + 1;
  }
}
