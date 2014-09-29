package fr.xebia.photobooth.api;

import fr.xebia.photobooth.domain.Order;
import fr.xebia.photobooth.domain.Validation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


@Path("/photos")
@Produces(MediaType.TEXT_PLAIN)
public class PhotoResource {

    @GET
    @Path("/hello")
    public String getHelloWorld() {
        return "Hello world";
    }

    @POST
    @Path("/create")
    public void newPhoto(Order order) {

    }


    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateCommand(Order order) {
        return Response.ok().entity(Validation.INSTANCE.isValid(order)).build();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveToFile(Order order) throws IOException {
        if(!Validation.INSTANCE.isValid(order)){
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
                    .entity("Not a valid order")
                    .build());
        }

        File urlFile = File.createTempFile("image", ".png");
        byte[] data = Base64.getDecoder().decode(order.stringFile);

        try (OutputStream stream = new FileOutputStream(urlFile);) {
            stream.write(data);
        }

        java.nio.file.Path source = urlFile.toPath();
        Files.move(source, Paths.get("src/main/webapp").resolve(source.getFileName()));

        return urlFile.getName();
    }

    @POST
    @Path("/saveWithURL")
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveToFileWithURL(Order order) throws IOException {
        if(!Validation.INSTANCE.isValid(order)){
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
                    .entity("Not a valid order")
                    .build());
        }

        File urlFile = File.createTempFile("image", ".png");
        urlFile.delete();

        try (InputStream in = new URL(order.stringFile).openStream()) {
            Files.copy(in, urlFile.toPath());
        }
        java.nio.file.Path source = urlFile.toPath();
        Files.move(source, Paths.get("src/main/webapp").resolve(source.getFileName()));

        //return Response.ok().entity(urlFile.getName()).build();
        return urlFile.getName();
    }
}