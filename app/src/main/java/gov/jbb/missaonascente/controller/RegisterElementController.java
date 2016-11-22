package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Element;
import gov.jbb.missaonascente.model.Explorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterElementController {
    private String currentPhotoPath = EMPTY_STRING;
    private LoginController loginController;
    private Element element;
    private ElementDAO elementDAO;
    private ExplorerDAO explorerDAO;
    private String email;
    private String date;

    private static final String EMPTY_STRING = "";

    private final String TAG = "RegisterElement";

    public RegisterElementController(LoginController loginController){
        this.loginController = loginController;
    }

    public void associateElementByQrCode(String code, Context context) throws SQLException,IllegalArgumentException{
        int currentBookPeriod;
        int currentBook;
        int newScore;
        int qrCodeNumber;

        qrCodeNumber = Integer.parseInt(code);

        elementDAO = new ElementDAO(context);
        explorerDAO = new ExplorerDAO(context);
        element = elementDAO.findElementByQrCode(qrCodeNumber);

        String catchCurrentDate = getCurrentDate();
        currentBook = element.getIdBook();

        Explorer explorer = loginController.getExplorer();
        email = explorer.getEmail();
        date = catchCurrentDate;

        BooksController booksController = new BooksController();
        booksController.currentPeriod();
        currentBookPeriod = booksController.getCurrentPeriod();

        if(currentBook == currentBookPeriod ) {
            try {
                elementDAO.insertElementExplorer(email, catchCurrentDate, qrCodeNumber, EMPTY_STRING);
                newScore = element.getElementScore();

                loginController.loadFile(context);

                Log.i("Old ","Score: "+loginController.getExplorer().getScore());

                loginController.getExplorer().updateScore(newScore);
                explorerDAO.updateExplorer(loginController.getExplorer());

                ExplorerController explorerController = new ExplorerController();

                MainController mainController = new MainController();
                if(mainController.checkIfUserHasInternet(context)) {
                    explorerController.insertExplorerElement(context,
                            loginController.getExplorer().getEmail(),
                            element.getIdElement(),
                            element.getUserImage(),
                            date);

                    explorerController.updateExplorerScore(context,
                            loginController.getExplorer().getScore(),
                            loginController.getExplorer().getEmail());
                }
            }catch (SQLException sqlException){
                currentPhotoPath = findImagePathByAssociation();
                throw sqlException;
            }
        }else{
            throw new IllegalArgumentException("Periodo Inválido");
        }
        Log.i("New ","Score: "+loginController.getExplorer().getScore());
    }

    public File createImageFile(File storageDirectory) throws IOException {
        File image;

        if(currentPhotoPath.equals(EMPTY_STRING)){
            String imageFileName = "USER_ELEMENT_ID_" + Integer.toString(element.getIdElement()) + "_";
            image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

            Log.d(TAG, "[" + imageFileName+ "]" + "[" + Integer.toString(element.getIdElement()) + "]");
        }else{
            image = new File(currentPhotoPath);
        }

        currentPhotoPath = image.getAbsolutePath();

        Log.d(TAG, "[" + currentPhotoPath + "]");

        return image;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void updateElementImage() {
        int result = elementDAO.updateElementExplorer(element.getIdElement(), email, date, currentPhotoPath);
    }


    private String getCurrentDate(){
        DateFormat formatBR = DateFormat.getDateInstance(DateFormat.LONG, new Locale("pt", "BR"));
        Date today = Calendar.getInstance().getTime();

        return formatBR.format(today);
    }

    public String findImagePathByAssociation(){
        Element element = null;

        try {
            element = elementDAO.findElementFromRelationTable(this.element.getIdElement(), email);
        }catch(IllegalArgumentException ex){
            ex.printStackTrace();
        }

        return (element == null) ? "" : element.getUserImage();
    }

    private String saveToInternalStorage(Bitmap bitmapImage, Context context){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDirectory", Context.MODE_PRIVATE);
        // Create imageDir

        currentPhotoPath = "image" + element.getIdElement() + ".jpg";
        File path = new File(directory, currentPhotoPath);
        elementDAO.updateElementExplorer(element.getIdElement(), email, date, currentPhotoPath);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 25, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String imagePath, Context context) {
        try {
            ContextWrapper contextWrapper = new ContextWrapper(context);
            File directory = contextWrapper.getDir("imageDirectory", Context.MODE_PRIVATE);
            File file = new File(directory, imagePath);
            Bitmap image = BitmapFactory.decodeStream(new FileInputStream(file));
            return image;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}