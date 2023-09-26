package com.testvagrant.ekamexamples.api.clients.grpc;

import com.endpoints.examples.bookstore.BookAuthorRequest;
import com.endpoints.examples.bookstore.BookResponse;
import com.endpoints.examples.bookstore.BookServiceGrpc.BookServiceBlockingStub;
import com.endpoints.examples.bookstore.GetBookRequest;
import com.testvagrant.ekam.api.retrofit.GrpcClient;
import com.testvagrant.ekamexamples.api.exception.AuthorNotFoundException;
import com.testvagrant.ekamexamples.api.exception.BookNotFoundException;
import io.grpc.ManagedChannel;

import java.net.URISyntaxException;

import static com.endpoints.examples.bookstore.BookServiceGrpc.newBlockingStub;

public class BookClient extends GrpcClient {
    ManagedChannel channel;

    BookServiceBlockingStub bookServiceStub;

    public BookClient(String host) {
        try {
            channel = build(host);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        bookServiceStub = newBlockingStub(channel);
    }

    public BookResponse getBookByISBN(Integer ISBN) throws BookNotFoundException {
        try {
            GetBookRequest getBookRequest = GetBookRequest.newBuilder().setIsbn(ISBN).build();
            return bookServiceStub.getBook(getBookRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new BookNotFoundException("Book not found");
        }
    }

    public BookResponse getBookByAuthor(String authorName) throws AuthorNotFoundException {
        try {
            BookAuthorRequest getBookRequest = BookAuthorRequest.newBuilder().setAuthor(authorName).build();
            return bookServiceStub.getBooksViaAuthor(getBookRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AuthorNotFoundException("Author details not found ");
        }
    }

}
