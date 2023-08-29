package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Repositories.AuthorRepository;
import com.example.librarymanagementsystem.Repositories.BookRepository;
import com.example.librarymanagementsystem.RequestDto.UpdateNameAndPenNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;


    public String addAuthor(Author author)throws Exception{

        //Validation Checks
        if(author.getAuthorId()!=null){
            throw new Exception("Author Id should not be sent as a parameter");
        }

        authorRepository.save(author);
        return "Author has been successfully to the db";
    }

    public String updateNameAndPenName(UpdateNameAndPenNameRequest request)throws Exception{


        Optional<Author> authorOptional = authorRepository.findById(request.getAuthorId());

        if(!authorOptional.isPresent()){
            throw new Exception("AuthorId is Invalid");
        }

        Author author = authorOptional.get();

        author.setName(request.getNewName());
        author.setPenName(request.getNewPenName());

        authorRepository.save(author);

        return "Author Name and PenName has been updated";
    }

    public Author getAuthorById(Integer authorId){

        Author author = authorRepository.findById(authorId).get();
        return author;

    }
    public List<String> getListOfBookTitlesByAuthor(int authorId)
    {
        List<Book> listOfBooks=bookRepository.findAll();
        List<String> listOfTitles=new ArrayList<>();
        for(Book book:listOfBooks)
        {
            int id=book.getAuthor().getAuthorId();
            if(id==authorId)
            {
                listOfTitles.add(book.getTitle());
            }
        }
        return listOfTitles;

    }



}
