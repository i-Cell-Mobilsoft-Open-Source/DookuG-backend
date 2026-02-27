package hu.icellmobilsoft.dookug.api.rest.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.TemplatePath;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupResponse;

/**
 * REST endpoint for stored template group
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@Tag(name = IOpenapiConstants.Tag.DEV_TOOLS, description = IOpenapiConstants.Description.DEV_TOOLS)
@Path(TemplatePath.TEST_DOCUMENT_STORED_TEMPLATE_GROUP)
public interface IDocumentStoredTemplateGroupTest {

    @POST
    @Operation(summary = "Creates a new template group.",
            description = "Insert new template group to the TEMPLATE, TEMPLATE_PART, TEMPLATE_TEMPLATE_PART, TEMPLATE_PART_CONTENT tables.")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    CreateTemplateGroupResponse postStoredTemplateGroup(MultipartFormDataInput input) throws BaseException;
}
