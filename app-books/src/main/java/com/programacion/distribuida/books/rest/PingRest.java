package com.programacion.distribuida.authors.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

public class PingRest {

    @GET
    @Path("/ping")
    public String ping(){
        return "pong";

    }
}
//como las politicas del cayambe uwu

