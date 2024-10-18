package org.codeid.exceptions;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

public class ExceptionMapper {
    @Provider
    public static class ErrorMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            JsonObjectBuilder entityBuilder = Json.createObjectBuilder()
                    .add("exceptionType", exception.getClass().getName())
                    .add("code", code);

            if (exception.getMessage() != null) {
                entityBuilder.add("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(entityBuilder.build())
                    .build();
        }
    }
}
