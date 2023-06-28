package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.rep.BookRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class BookRest {

    @Inject
    BookRepository rep;


    //books GET
    @GET
    public List<Book> findAll(){
        return rep.findAll().list();
    }

    //books/{id} GET
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") Long id){
        var book = rep.findByIdOptional(id);
        if(book.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(book.get()).build();
    }

    //books POST
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createBook(Book p){
        rep.persist(p);
        return Response.status(Response.Status.CREATED.getStatusCode(), "book created").build();
    }

    //books/{id} PUT
    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id")Long id, Book bookObj){
        Book book = rep.findById(id);
        book.setAuthor(bookObj.getAuthor());
        book.setIsbn(bookObj.getIsbn());
        book.setPrice(bookObj.getPrice());
        book.setTitle(bookObj.getTitle());
        rep.persistAndFlush(book);
        return Response.ok().build();
    }

    //books/{id} DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteBook(@PathParam("id")Long id){
        rep.deleteById(id);
    }







}
