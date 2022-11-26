package com.codewitthhades.quarkus.reactiverestapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/api/users")
public class UsersResource {

    private final List<User> users;

    public UsersResource() {
        this.users = new CopyOnWriteArrayList<>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(users).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("id") String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user -> Response.ok(user).build())
                .orElseGet(() -> Response.noContent().build());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserRequest userRequest) {
        User user = new User(userRequest.getName(), userRequest.getSurname());
        users.add(user);
        return Response.ok(user).build();
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@QueryParam("id") String id, UserRequest userRequest) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setSurname(userRequest.getSurname());
                    return user;
                })
                .map(user -> Response.ok(user).build())
                .orElseGet(() -> Response.noContent().build());
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("id") String id) {
        User userToDelete = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (userToDelete != null && users.remove(userToDelete)) {
            return Response.ok().build();
        } else {
            return Response.noContent().build();
        }
    }

}