package com.light.loftcoin.util;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PercentFormatterTest {

    private PercentFormatter formatter;

    @Before
    public void setUp() throws Exception {
        formatter = new PercentFormatter();
    }

    @Test
    public void string_contains_exact_two_fractional_digits() {
        assertThat(formatter.format(1d)).isEqualTo("1.00%");
    }
}