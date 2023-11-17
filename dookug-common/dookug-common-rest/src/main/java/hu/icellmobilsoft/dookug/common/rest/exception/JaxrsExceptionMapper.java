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
package hu.icellmobilsoft.dookug.common.rest.exception;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceInvoker;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.TechnicalFault;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.exception.IExceptionMessageTranslator;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;

/**
 * JAX-RS API default exception mapper
 *
 * @author imre.scheffer 0.3.0
 */
@Provider
public class JaxrsExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private IExceptionMessageTranslator exceptionMessageTranslator;

    @Override
    public Response toResponse(WebApplicationException e) {
        log.error("JAX-RS error: ", e);
        log.writeLogToError();
        TechnicalFault dto = new TechnicalFault();
        // status code vagy exception-re figyeljunk? (e.getResponse().getStatus())
        if (e instanceof NotAllowedException) {
            // Nem létező végpont + HTTP metódus párnál adjuk vissza a natív RESTEASY hibát
            // HTTP response code: 405
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_METHOD_NOT_ALLOWED);
        } else if (e instanceof NotSupportedException) {
            // Nem támogatott content-type vagy accept header esetén adjuk vissza a natív RESTEASY hibát
            // HTTP response code: 415

            // Megvizsgáljuk, hogy csak a Content-Type header invalid, vagy az Accept is
            try {
                getWildcardContentTypeResourceInvoker();

                exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_UNSUPPORTED_MEDIA_TYPE);
            } catch (NotAcceptableException e2) {
                // Ha a Content-Type és az Accept header is invalid
                return Response.fromResponse(e.getResponse())//
                        .entity(getNotAcceptableMessage())//
                        .status(Response.Status.NOT_ACCEPTABLE) // override NOT_SUPPORTED status
                        .build();
            }
        } else if (e instanceof BadRequestException) {
            // Rosszul formázott kérésnél adjuk vissza az első rosszul formázott XML taget és a JAX által dobott hibát (mit várt volna)
            // HTTP response code: 400
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_BAD_REQUEST);
        } else if (e instanceof NotFoundException) {
            // Nem létező URL path
            // HTTP response code: 404
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_NOT_FOUND);
        } else if (e instanceof ForbiddenException) {
            // HTTP response code: 403
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_FORBIDDEN);
        } else if (e instanceof NotAcceptableException) {
            // HTTP response code: 406
            // Ebben az esetben nem ismert accept jött be, így a válászban csak sima nyelvesített fordítást lehet bele rakni
            // a szokásos dto itt nem megfelelő
            return Response.fromResponse(e.getResponse()).entity(getNotAcceptableMessage()).build();
        } else if (e instanceof NotAuthorizedException) {
            // HTTP response code: 401
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_UNAUTHORIZED);
        } else if (e instanceof InternalServerErrorException) {
            // HTTP response code: 500
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_INTERNAL_SERVER_ERROR);
        } else if (e instanceof ServiceUnavailableException) {
            // HTTP response code: 503
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_SERVICE_UNAVAILABLE);
        } else {
            // minden más általános hiba, melyet nem kezelünk speciálisan
            exceptionMessageTranslator.addCommonInfo(dto, e, CoffeeFaultType.OPERATION_FAILED);
        }
        return Response.fromResponse(e.getResponse()).entity(dto).build();
    }

    private String getNotAcceptableMessage() {
        return FaultType.REST_NOT_ACCEPTABLE + "\n" + exceptionMessageTranslator.getLocalizedMessage(FaultType.REST_NOT_ACCEPTABLE);
    }

    /**
     * Megkeresi a {@code ResourceInvoker}-t, wildcard content type-pal. Arra szolgál, hogy eldöntsük, hogy invalid content type-on kívűl van-e más
     * hiba a request-tel.
     *
     * @return a talált ResourceInvoker adatok
     */
    private ResourceInvoker getWildcardContentTypeResourceInvoker() {
        // resteasy 3.x:
        // Map<Class<?>, Object> contextDataMap = ResteasyProviderFactory.getContextDataMap();
        // HttpRequest originalRequest = (HttpRequest) contextDataMap.get(HttpRequest.class);
        // resteasy 4.x:
        HttpRequest originalRequest = ResteasyProviderFactory.getInstance().getContextData(HttpRequest.class);

        try {
            MockHttpRequest modifiedRequest = MockHttpRequest.deepCopy(originalRequest)//
                    .contentType((MediaType) null)//
                    .contentType(MediaType.WILDCARD_TYPE);
            // resteasy 3.x: Registry registry = (Registry) contextDataMap.get(Registry.class);
            Registry registry = ResteasyProviderFactory.getInstance().getContextData(Registry.class);
            return registry.getResourceInvoker(modifiedRequest);
        } catch (IOException e) {
            log.error("Error in error handler", e);
            return null;
        }
    }
}
