/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 i-Cell Mobilsoft Zrt.
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

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

/**
 * Common used Handlebars - Hepler collection
 * Boolean return values are non-empty strings if they are equal, null if they are not. It is due to the {@link CharSequence} return value of {@link Helper} interface.
 * Handlebars can evaluate boolean TRUE when a value exists (non-null, not empty).
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
public enum DookugHelpers implements Helper<Object> {

    /**
     * Compares the value of two arguments.
     * usage:
     * {{#if (equals yourField 'white')}} ... {{/if}}
     */
    equals {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            return Objects.equals(context, options.param(0)) ? "true" : null;
        }
    },

    /**
     * "&#38;&#38;" (and) operator between N values.
     * usage:
     * 1. {{#if (and falseValue trueValue notExistingValue)}} YES {{else}} NO {{/if}}
     * 2. {{#if (and (equals 'black' 'white') (equals 'white' 'white'))}} YES {{else}} NO {{/if}}
     * returns:
     * 1. NO
     * 2. NO
     */
    and {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context == null) {
                return null;
            }
            for (Object param : options.params) {
                if (param == null) {
                    return null;
                }
            }
            return "true";
        }
    },

    /**
     * "||" (or) operator between N values.
     * usage:
     * 1. {{#if (or falseValue trueValue notExistingValue)}} YES {{else}} NO {{/if}}
     * 2. {{#if (or (equals 'black' 'white') (equals 'white' 'white'))}} YES {{else}} NO {{/if}}
     * returns:
     * 1. YES
     * 2. YES
     */
    or {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context != null) {
                return "true";
            }
            for (Object param : options.params) {
                if (param != null) {
                    return "true";
                }
            }
            return null;
        }
    },

    /**
     * "!" (not) operator of value
     * usage:
     * 1. {{#if (not falseValue)}} YES {{else}} NO {{/if}}
     * 2. {{#if (not existingValue)}} YES {{else}} NO {{/if}}
     * 3. {{#if (not (equals 'black' 'white'))}} YES {{else}} NO {{/if}}
     * returns:
     * 1. YES
     * 2. NO
     * 3. YES
     */
    not {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if( context == null ) {
                return "true";
            }
            if (Boolean.parseBoolean(context.toString()) == false) {
                return "true";
            }
            return null;

        }
    },

    /**
     * "in" operator of N values.
     * Checks if value is equal to any member of the list
     * usage:
     * yourField = 'white'
     * {{#if (in yourField 'black' 'white' 'gray')}} YES {{else}} NO {{/if}}
     * returns:
     * YES
     */
    in {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context != null) {
                for (Object param : options.params) {
                    if (context.equals(param)) {
                        return "true";
                    }
                }
            }
            return null;
        }
    },

    /**
     * Checks if date is before another date
     * usage:
     * {{#if (before yourField '2023-08-13T05:40:55Z')}} ... {{/if}}
     */
    before {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context == null) {
                return null;
            }
            OffsetDateTime inputDate = OffsetDateTime.parse(context.toString());
            OffsetDateTime limit = OffsetDateTime.parse(options.param(0).toString());
            return inputDate.isBefore(limit) ? "true" : null;
        }
    },

    /**
     * Checks if date is between date1 and date2 parameters
     * usage:
     * {{#if (between yourField '2023-08-13T05:40:55Z' '2023-08-15T05:40:55Z')}} ... {{/if}}
     */
    between {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context == null) {
                return null;
            }
            OffsetDateTime inputDate = OffsetDateTime.parse(context.toString());
            OffsetDateTime lowerLimit = OffsetDateTime.parse(options.param(0));
            OffsetDateTime upperLimit = OffsetDateTime.parse(options.param(1));
            return upperLimit.isAfter(inputDate) && lowerLimit.isBefore(inputDate) ? "true" : null;
        }
    },

    /**
     * Checks if date is after another date
     * usage:
     * {{#if (after yourField '2023-08-13T05:40:55Z')}} ... {{/if}}
     */
    after {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context == null) {
                return null;
            }
            OffsetDateTime inputDate = OffsetDateTime.parse(context.toString());
            OffsetDateTime limit = OffsetDateTime.parse(options.param(0).toString());
            return inputDate.isAfter(limit) ? "true" : null;
        }
    },

    /**
     * Minute subtraction from date
     * usage:
     * {{dateMinusMinutes '2023-08-13T05:40:55Z' 60}}
     * returns:
     * '2023-08-13T04:40:55Z'
     */
    dateMinusMinutes {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context == null) {
                return null;
            }
            OffsetDateTime inputDate = OffsetDateTime.parse(context.toString());
            Object offset = options.param(0);
            long numberOffset = (offset instanceof Number) ? ((Number)offset).longValue() : Long.parseLong(offset.toString());
            return inputDate.minusMinutes(numberOffset).toString();
        }
    },

    /**
     * Minute addition to date
     * usage:
     * {{datePlusMinutes '2023-08-13T05:40:55Z' 60}}
     * returns:
     * '2023-08-13T06:40:55Z'
     */
    datePlusMinutes {
        @Override
        public CharSequence apply(final Object context, final Options options) throws IOException {
            if (context == null) {
                return null;
            }
            OffsetDateTime inputDate = OffsetDateTime.parse(context.toString());
            Object offset = options.param(0);
            long numberOffset = (offset instanceof Number) ? ((Number)offset).longValue() : Long.parseLong(offset.toString());
            return inputDate.plusMinutes(numberOffset).toString();
        }
    },

    /**
     * Declares a new variable in context and returns its value.
     * Variables are global wherever they are declared within the template!
     * usage:
     * {{declare 'myColor' 'white'}}
     * ... later in code ...
     * {{#if myColor}} {{myColor}} {{/if}}
     * returns:
     * 'white'
     */
    declare {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            options.data(context.toString(), options.params[0]);
            return options.params[0].toString();
        }
    },

    /**
     * Declares a new variable in context.
     * Variables are global wherever they are declared within the template!
     * usage:
     * {{declareVoid 'myColor' 'white'}}
     * ... later in code ...
     * {{#if myColor}} {{myColor}} {{/if}}
     */
    declareVoid {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            options.data(context.toString(), options.params[0]);
            return "";
        }
    },

    /**
     * DateTime conversion to custom format. Result could be converted to given time zone.
     * usage:
     * 1. {{formatDateTime '2023-08-13T05:40:55Z' 'yyyy-MM-dd HH:mm:ss'}
     * 2. {{formatDateTime '2023-08-13T05:40:55Z' 'yyyy-MM-dd HH:mm:ss' 'CET'}}
     * 3. {{formatDateTime '2024-01-09T15:30:04Z' 'MM.dd-HH:mm'}}
     * returns:
     * 1. '2023-08-13 05:40:55'
     * 2. '2023-08-13 05:40:55'
     * 3. '01.09-15:30'
     */
    formatDateTime {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            Object[] params = options.params;
            OffsetDateTime inputDateTime = OffsetDateTime.parse(context.toString());
            String pattern = params[0].toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            if (params.length > 1) {
                String zoneId = params[1].toString();
                ZonedDateTime zoned =  inputDateTime.toInstant().atZone(ZoneId.of(zoneId.toString()));
                inputDateTime = zoned.toOffsetDateTime();
            }
            return inputDateTime.format(formatter);
        }
    },

    /**
     * Time conversion to custom format usage:
     * 1. {{formatTime '15:30:55Z' 'HH:mm:ss'}}
     * 2. {{formatTime '15:30:55Z' 'h:mm a'}}
     * 3. {{formatTime '15:30:55Z' 'HH:mm:ss', 'CET'}}
     * returns:
     * 1. '15:30:55'
     * 2. '3:30 PM'
     * 3. '16:30:55'
     */
    formatTime {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            OffsetTime inputTime = OffsetTime.parse(context.toString());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(options.param(0));
            if (options.params.length > 1) {
                String zoneId = options.param(1).toString();
                OffsetDateTime offsetDateTime = inputTime.atDate(LocalDate.now());
                ZonedDateTime zonedDateTime = offsetDateTime.toInstant().atZone(ZoneId.of(zoneId.toString()));

                inputTime = zonedDateTime.toOffsetDateTime().toOffsetTime();
            }
            return inputTime.format(formatter);
        }
    },


    /**
     * Date conversion to custom format
     * usege:
     * 1. {{formatDate '2023-08-13' 'yyyy.MM.dd'}}
     * 2. {{formatDate '2023-08-13' 'MM.dd.yyyy'}}
     * returns:
     * 1. '2023.08.13'
     * 2. '08.13.2023'
     */
    formatDate {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            LocalDate localDate = LocalDate.parse(context.toString());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(options.param(0));

            return localDate.format(formatter);
        }
    },

    /**
     * Number conversion to custom format
     * usage:
     * 1. {{formatNumber '8.0' '#'}}
     * 2. {{formatNumber numberVariable '00'}}
     * 3. {{formatNumber numberVariable '00', 'HU'}}
     * returns:
     * 1. '8'
     * 2. '08'
     * 3. '08'
     */
    formatNumber {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            Object[] params = options.params;
            Number inputNumber = (context instanceof Number) ? ((Number)context).doubleValue() : Double.parseDouble(context.toString());
            DecimalFormat myFormatter = null;
            switch (params.length) {
                case 1:
                    myFormatter = new DecimalFormat(params[0].toString());
                    break;
                case 2:
                    myFormatter = new DecimalFormat(params[0].toString(), new DecimalFormatSymbols(Locale.forLanguageTag(params[1].toString())));
                    break;
                default:
                    myFormatter = new DecimalFormat();
            }
            return myFormatter.format(inputNumber);
        }
    },

    /**
     * Mathematical operations without number formatting
     * usage:
     * {{math '+' numericProperty 4}}
     * {{math '*' stringProperty 10}}
     * {{#if (equals (math '-' number1 number2) 5)}} ... {{/if}}
     * {{declareVoid 'valueSum' (math '+' valueSum result)}} (dynamic increase of value)
     * {{math '+' (math '*' num1 '1000') num2}}
     */
    math {
        @Override
        public CharSequence apply(final Object context, final Options options) {
            if (context == null) {
                return null;
            }
            String operation = context.toString();
            Double number1 = (options.param(0) instanceof Number) ? ((Number)options.param(0)).doubleValue() : Double.parseDouble(options.param(0));
            Double number2 = (options.param(1) instanceof Number) ? ((Number)options.param(1)).doubleValue() : Double.parseDouble(options.param(1));
            switch (operation) {
            case "+":
                return String.valueOf(number1 + number2);
            case "-":
                return String.valueOf(number1 - number2);
            case "*":
                return String.valueOf(number1 * number2);
            case "/":
                return String.valueOf(number1 / number2);
            case "%":
                return String.valueOf(number1 % number2);
            default:
                return "-1";
            }
        }
    }
}