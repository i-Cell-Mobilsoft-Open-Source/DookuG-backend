/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.dookug.engine.handlebars.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * DookugHelper methods test
 *
 * @author istvan.peli
 * @since 0.5.0
 */
class DookugHelpersTest {

    @Test
    void testDateTimeHelper() throws IOException {
        Options options = createOptionsWithParams(new Object[] { "yyyy-MM-dd HH:mm:ss" });
        Object testObj = DookugHelpers.formatDateTime.apply("2024-08-13T05:40:55Z", options);
        Assertions.assertEquals("2024-08-13 05:40:55", testObj.toString());

        testObj = DookugHelpers.formatDateTime.apply("2024-01-09T15:30:04Z", options);
        Assertions.assertEquals("2024-01-09 15:30:04", testObj.toString());

        options = createOptionsWithParams(new Object[] { "MM.dd-HH:mm" });
        testObj = DookugHelpers.formatDateTime.apply("2024-01-09T15:30:04Z", options);
        Assertions.assertEquals("01.09-15:30", testObj.toString());

        options = createOptionsWithParams(new Object[] { "yyyy-MM-dd HH:mm:ss", "CET" });
        testObj = DookugHelpers.formatDateTime.apply("2024-02-12T05:40:55Z", options);
        Assertions.assertEquals("2024-02-12 06:40:55", testObj.toString());
    }

    @Test
    void testTimeHelper() throws IOException {
        Options options = createOptionsWithParams(new Object[] { "HH:mm:ss" });
        Object testObj = DookugHelpers.formatTime.apply("15:30:55Z", options);
        Assertions.assertEquals("15:30:55", testObj.toString());

        options = createOptionsWithParams(new Object[] { "HH:mm:ss", "CET" });
        testObj = DookugHelpers.formatTime.apply("15:30:55Z", options);
        Assertions.assertEquals("16:30:55", testObj.toString());

        String amPmSymbol = DateTimeFormatter.ofPattern("a", Locale.getDefault()).format(OffsetDateTime.parse("2023-08-13T15:30:55Z"));
        options = createOptionsWithParams(new Object[] { "h:mm a" });
        testObj = DookugHelpers.formatTime.apply("15:30:55Z", options);
        Assertions.assertEquals("3:30 " + amPmSymbol, testObj.toString());
    }

    @Test
    void testDateHelper() throws IOException {
        Options options = createOptionsWithParams(new Object[] { "yyyy.MM.dd" });
        Object testObj = DookugHelpers.formatDate.apply("2023-08-13", options);
        Assertions.assertEquals("2023.08.13", testObj.toString());

        options = createOptionsWithParams(new Object[] { "MM.dd.yyyy" });
        testObj = DookugHelpers.formatDate.apply("2023-08-13", options);
        Assertions.assertEquals("08.13.2023", testObj.toString());

        options = createOptionsWithParams(new Object[] { "yyyy/MM/dd" });
        testObj = DookugHelpers.formatDate.apply("2024-01-09", options);
        Assertions.assertEquals("2024/01/09", testObj.toString());
    }

    private Options createOptionsWithParams(Object[] params) {
        return new Options(null, null, null, null, null, null, params, null, List.of());
    }
}
