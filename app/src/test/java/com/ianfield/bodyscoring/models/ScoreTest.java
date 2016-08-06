package com.ianfield.bodyscoring.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Ian on 20/11/2015.
 */
@RunWith(JUnit4.class)
public class ScoreTest {

    @Test
    public void testToString() {
        Score score = new Score();
        score.setCount(1);
        score.setScore(4.5);
        assertThat(score.toString()).isEqualTo("Score: 4.5, Count: 1");
    }
}
