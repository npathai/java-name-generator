package com.github.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

class NameGeneratorTest {

    private NameGenerator generator;

    @Nested
    class RandomnessTest {

        @BeforeEach
        public void initialize() {
            generator = new NameGenerator();
        }

        @Test
        public void validateNameFormat() {
            String name = generator.generateRandomName();
            assertThat(name).doesNotStartWith("_");
            assertThat(name).contains("_");
            assertThat(name).doesNotEndWith("_");
            assertThat(name).doesNotContainPattern(Pattern.compile("[0-9]"));
        }

        @Test
        public void validateRetryNameFormat() {
            String name = generator.generateRandomName(true);
            assertThat(name).doesNotStartWith("_");
            assertThat(name).contains("_");
            assertThat(name).doesNotEndWith("_");
            assertThat(name.charAt(name.length() - 1)).isBetween('0', '9');
        }
    }

    @Nested
    class SpecialTests {

        @Test
        public void steveWozniakIsNotBoring() {
            Random mockRandom = Mockito.mock(Random.class);
            generator = new NameGenerator(mockRandom);
            when(mockRandom.nextInt(anyInt()))
                    .thenReturn(10)
                    .thenReturn(231)
                    .thenCallRealMethod();

            String name = generator.generateRandomName();
            assertThat(name).isNotEqualTo("boring_wozniak");
        }
    }
}