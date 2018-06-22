package com.ianfield.bodyscoring.models

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.google.common.truth.Truth.assertThat

/**
 * Created by Ian on 20/11/2015.
 */
@RunWith(JUnit4::class)
class ScoreTest {

    @Test
    fun testToString() {
        val score = Score()
        score.count = 1
        score.score = 4.5
        assertThat(score.toString()).isEqualTo("Score: 4.5, Count: 1")
    }
}
