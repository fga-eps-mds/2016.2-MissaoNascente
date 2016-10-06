package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;
import java.util.List;

public class BooksController {
    private Element element;
    private Book[] book;
    private Explorer explorer;

    public BooksController(Context context, Explorer explorer){

        /* Instantiating three books in the books vector */
        setElement(new Element());
        book = new Book[]{new Book(), new Book(), new Book()};

        /* Specifing book names (must change to strings.xml) */
        getBook(0).setNameBook("Fall");
        getBook(1).setNameBook("Winter");
        getBook(2).setNameBook("Summer");

        /* Specifing book user */
        getBook(0).setExplorer(explorer);
        getBook(1).setExplorer(explorer);
        getBook(2).setExplorer(explorer);

        /* Inserting books in the book database */
        BookDAO bookDAO = new BookDAO(context);
        bookDAO.createTable(bookDAO.getWritableDatabase());
        bookDAO.insertBook(getBook(0));
        bookDAO.insertBook(getBook(1));
        bookDAO.insertBook(getBook(2));

    }

    public BooksController() {

    }

    public BooksController(SharedPreferences settings, Context context, Explorer explorer) {
        if (settings.getBoolean("mainScreenFirstTime", true)) {
            new BooksController(context, explorer);
            settings.edit().putBoolean("mainScreenFirstTime", false).apply();
        }
    }

    public void getAllBooksData(Context context){

        /* Initialize three books*/
        book = new Book[]{new Book(), new Book(), new Book()};
        findExplorer(context);
    }

    public void getElementsFromDatabase(Context context) {

        /* Initialize one element */
        setElement(new Element());

        /* Initialize database */
        ElementDAO elementDAO = new ElementDAO(context);
        findExplorer(context);

        /* Get all element from one book and sets them in List<Element> */
        getElement().setIdBook(book[0].getIdBook());
        getBook(0).setElements(elementDAO.findElementsBook(getElement().getIdBook()));
        getElement().setIdBook(book[1].getIdBook());
        getBook(1).setElements(elementDAO.findElementsBook(getElement().getIdBook()));
        getElement().setIdBook(book[2].getIdBook());
        getBook(2).setElements(elementDAO.findElementsBook(getElement().getIdBook()));
    }

    public String[] getElementsName(Context context, int idBook){
        getAllBooksData(context);
        getElementsFromDatabase(context);
        String[] names = new String[getBook(idBook).getElements().size()];
        for(int i = 0; i < getBook(idBook).getElements().size() ; i++){
            names[i] = new String();
            names[i] = getBook(idBook).getElements().get(i).getNameElement();
        }
        return names;
    }

    public int[] getElementsId(Context context, int idBook){
        getAllBooksData(context);
        getElementsFromDatabase(context);
        int[] idElements = new int[getBook(idBook).getElements().size()];
        for(int i = 0 ; i < getBook(idBook).getElements().size() ; i++){
            idElements[i] = getBook(idBook).getElements().get(i).getIdElement();
        }
        return idElements;
    }

    public int[] getElementsImage(Context context, int idBook){
        getAllBooksData(context);
        getElementsFromDatabase(context);
        int[] elementsImage = new int[getBook(idBook).getElements().size()];
        for(int i = 0 ; i < getBook(idBook).getElements().size() ; i++){
            elementsImage[i] = context.getResources().getIdentifier(getBook(idBook).getElements().get(i).getDefaultImage(),"drawable",context.getPackageName());
        }
        return elementsImage;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Book getBook(int id) {
        return book[id];
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public void findExplorer(Context context){
        BookDAO bookDAO = new BookDAO(context);
        LoginController loginController = new LoginController(context);
        loginController.loadFile(context);
        List<Book> book;
        book=bookDAO.findBookExplorer(loginController.getExplorer().getEmail());
        this.book[0]=book.get(0);
        this.book[1]=book.get(1);
        this.book[2]=book.get(2);
    }
}