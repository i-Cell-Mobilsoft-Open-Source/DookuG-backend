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
package hu.icellmobilsoft.dookug.common.util.date;

import java.time.LocalTime;
import java.time.OffsetDateTime;

/**
 * Date util.
 *
 * @author imre.scheffer
 */
public class DateUtil extends hu.icellmobilsoft.coffee.tool.utils.date.DateUtil {

    /**
     * Start of day: 00:00 at the same offset
     *
     * @param odt
     *            offset date time
     * @return start of day
     */
    public static OffsetDateTime startOfDay(OffsetDateTime odt) {
        return odt.toLocalDate().atStartOfDay().atOffset(odt.getOffset());
    }

    /**
     * End of day (inclusive upper bound): same day at 23:59:59.999999999 at the same offset
     *
     * @param odt
     *            offset date time
     * @return end of day
     */
    public static OffsetDateTime endOfDayInclusive(OffsetDateTime odt) {
        return odt.toLocalDate().atTime(LocalTime.MAX).atOffset(odt.getOffset());
    }

    /**
     * End of day (exclusive upper bound): start of next day at the same offset
     *
     * @param odt
     *            offset date time
     * @return end of day exclusive
     */
    public static OffsetDateTime endOfDayExclusive(OffsetDateTime odt) {
        return startOfDay(odt).plusDays(1);
    }

}
