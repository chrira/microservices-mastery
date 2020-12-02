package ch.puzzle.rest.exception;


import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper extends TraceableExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        logTrace(exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Json.createObjectBuilder()
                        .add("message", exception.getMessage())
                        .build())
                .build();
    }
}
