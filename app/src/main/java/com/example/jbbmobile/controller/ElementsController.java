package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

public class ElementsController {
    private Element element;

    public ElementsController(){

    }

    public Element findElementByID(int idElement, String email, Context context) {
        setElement(new Element());
        getElement().setIdElement(idElement);

        ElementDAO elementDAO = new ElementDAO(context);
        setElement(elementDAO.findElementFromRelationTable(idElement, email));

        return getElement();
    }

    public Element findElementByQrCode(int qrCode, Context context) throws Exception{
        ElementDAO elementDAO = new ElementDAO(context);
        Element element = elementDAO.findElementByQrCode(qrCode);
        return element;
    }

    public ElementsController(Context context){
        createElement(context);
    }

    private Element getElement() {
        return element;
    }

    private void setElement(Element element) {
        this.element = element;
    }

    public Element associateElementbyQrCode(String code, Context context) throws SQLException,IllegalArgumentException{
        ElementDAO elementDAO = new ElementDAO(context);
        int currentBookPeriod, currentBook;

        int qrCodeNumber = Integer.parseInt(code);
        Element element;

        element = elementDAO.findElementByQrCode(qrCodeNumber);
        element.setDate();
        String catchCurrentDate = element.getCatchDate();
        currentBook =element.getIdBook();

        LoginController loginController = new LoginController();
        loginController.loadFile(context);
        String emailExplorer = loginController.getExplorer().getEmail();

        currentBookPeriod = BooksController.currentPeriod;

        if(currentBook == currentBookPeriod ) {
            elementDAO.insertElementExplorer(emailExplorer, catchCurrentDate, qrCodeNumber);
        }else{
            throw new IllegalArgumentException("Periodo Invalido");
        }
        return element;
    }



    protected void createElement(Context context) {
        ElementDAO elementDao = new ElementDAO(context);

        try {
            // Pau Santo
            String description = "Espécie da Flora, pau-santo Kielmeyera speciosa A.St. Hil. " +
                    "Espécie comum no Cerrado a casca de se tronco tem aspecto poroso, sendo extremamente macio, " +
                    "apresenta folhas coriáceas que caem no período da seca, florescem entre março e maio, " +
                    "suas flores apresentam tons brancos e rosadas e são polinizadas por abelhas. " +
                    "Os frutos secos aparecem de julho a setembro, não são comestíveis. " +
                    "Os frutos começam a abrir no final de agosto e as sementes aladas podem ser vistas, sua forma alada facilita a dispersão pelo vento. " +
                    "Sua reprodução é sincronizada com o período chuvoso, ou seja, " +
                    "os frutos maduros no final da seca lançam suas sementes que, com a chegada da chuva, tem mais chances de germinação.";
            setElement(new Element(1, 1, 100, "ponto_2", "Pau-Santo", "Pau-Santo", 1, description));
            elementDao.insertElement(getElement());
            elementDao.insertElementExplorer(1, "a@a.com", "2016-12-31");

            // Jacarandá
            description = "Espécie da flora, jacarandá-do-cerrado- Dalbergia miscolobium Benth. " +
                    "A espécie apresenta um caule castanho acinzentado com fissuras sinuosas por vezes, " +
                    "abrigam larvas de insetos que atraem aves como pica-pau. " +
                    "Perdem as folhas no período da seca, suas folhas compostas por 9 a 20 folíolos coriáceos, " +
                    "apresentando manchas circulares de cor preta decorrentes de infecção por fungos. " +
                    "Suas flores são pequenas, formando grandes inflorescências de cor violácea, " +
                    "podem ser observadas de novembro a maio e atraem vários tipos de insetos para fazer sua polinização, inclusive, abelhas grandes e pequenas. " +
                    "Seus frutos que aparecem de maio a julho, têm uma alta viabilidade, ou seja, alta taxa de germinação, " +
                    "podendo ficar por meses em dormência, e germinarem quando as primeiras chuvas caem. Possui frutos secos dispersos pelo vento.";
            setElement(new Element(2, 2, 200, "ponto_3", "Jacarandá do Cerrado", "Jacaranda", 1, description));
            elementDao.insertElement(getElement());
            elementDao.insertElementExplorer(2, "a@a.com", "2016-12-31");

            // Paineira do Cerrado
            description = "Eriotheca pubescens (Mart.&amp; Zucc.) Schott &amp; Endl. paineira-do-cerrado." +
                    "A espécie possui ampla distribuição no Centro-Oeste, Nordeste e Sudeste." +
                    "Não é endêmica do Brasil, geralmente flore em julho e agosto apresenta fruto em novembro." +
                    "As sementes se localizam dentro da paina o que facilita sua dispersão pelo vento." +
                    "Essa espécie apresenta folhas alternas, compostas palmadas. Árvore melífera belas flores claras, polinizada por abelhas." +
                    "é utilizada por aves para fazerem seus ninhos e também serve como recheio de travesseiros.";
            setElement(new Element(3, 3, 300, "ponto_5", "Paineira do Cerrado", "Paineira", 2, description));
            elementDao.insertElement(getElement());
            elementDao.insertElementExplorer(3, "a@a.com", "2016-12-31");

            // Cupinzeiro terrestre
            description = "Os cupins são insetos sociais (vivem em grandes colônias) da ordem Isoptera," +
                    "formam ninhos que podem atingir forma de torre que são feitos de partículas de solo e excremento" +
                    "de cupins grudados com as secreções salivares dos operários.  formam um dos grupos dominantes na fauna de solo" +
                    "de ecossistemas tropicais, exercendo um papel importante nos processos de ciclagem de nutrientes e formação de solo." +
                    "Ao abrir túneis e construir seus ninhos, os cupins arejam e melhoram a estrutura do solo, além de movimentar verticalmente grande quantidade de partículas." +
                    "Os termiteiros servem de abrigo a uma fauna diversa, incluindo artrópodes, vertebrados e outros grupos." +
                    "Os ninhos velhos e abandonados servem de substrato para o desenvolvimento de várias de plantas." +
                    "Devido a esse poder de modificar a estrutura do habitat, os cupins podem ser incluídos entre os “engenheiros do ecossistema”.";
            setElement(new Element(4, 4, 400, "ponto_6", "Cupinzeiro terrestre", "Cupim", 2, description));
            elementDao.insertElement(getElement());
            elementDao.insertElementExplorer(4, "a@a.com", "2016-12-31");

            // Nascente do córrego Cabeça de Veado
            description = "Na Estação Ecológica do Jardim Botânico encontram-se várias nascentes que alimentam o córrego cabeça de Veado." +
                    "Nascentes são aparecimento, na superfície do terreno de um lençol subterrâneo que dão origem a cursos d’água." +
                    "Pesquisas realizadas no córrego cabeça de veado mostram que suas águas são referência em termos de qualidade e de biodiversidade," +
                    "típicas de riachos que se mantém em seu estado primitivo." +
                    "Ao se observar essas nascentes deve-se expandir o olhar para" +
                    "o ecossistema que integra os compartimentos terrestres e aquáticos, conhecidos como ecossistemas ripários.";
            setElement(new Element(5, 5, 500, "ponto_23", "Nascente do córrego Cabeça de Veado", "Nascente", 3, description));
            elementDao.insertElement(getElement());
            elementDao.insertElementExplorer(5, "a@a.com", "2016-12-31");

            // Gavinha
            description = "Algumas espécies de plantas apresentam folhas modificadas que realizam outras funções além da fotossíntese," +
                    "por exemplo, as gavinhas que são folhas modificadas lembrando molas." +
                    "Essas estruturas têm como principal função fixar a planta," +
                    "enrolando-se sobre suportes. Muitas por serem verdes, contendo clorofila," +
                    "além de fixar auxiliam no processo de fotossíntese.";
            setElement(new Element(6, 6, 600, "ponto_34", "Gavinha", "Gavinha", 3, description));
            elementDao.insertElement(getElement());
            elementDao.insertElementExplorer(6, "a@a.com", "2016-12-31");

            elementDao.close();
        } catch (SQLiteConstraintException e){
            e.getMessage();
        }
    }
}
