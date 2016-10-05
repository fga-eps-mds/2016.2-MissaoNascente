package com.example.jbbmobile.controller;

import android.content.Context;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementsController {
    private Element element;

    public ElementsController(){

    }

    public Element findElementByID(int idElement, Context context){
        setElement(new Element());
        getElement().setIdElement(idElement);

        ElementDAO elementDAO = new ElementDAO(context);
        setElement(elementDAO.findElement(getElement()));
        return getElement();
    }

    public ElementsController(Context context){
        createElement(context);
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void createElement(Context context){
        ElementDAO elementDao = new ElementDAO(context);
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());

        List <String> description = new ArrayList<String>();
        description.add("Espécie da Flora, pau-santo Kielmeyera speciosa A.St. Hil.");
        description.add("Espécie comum no Cerrado a casca de se tronco tem aspecto poroso, sendo extremamente macio,");
        description.add("apresenta folhas coriáceas que caem no período da seca, florescem entre março e maio,");
        description.add("suas flores apresentam tons brancos e rosadas e são polinizadas por abelhas.");
        description.add("Os frutos secos aparecem de julho a setembro, não são comestíveis.");
        description.add("Os frutos começam a abrir no final de agosto e as sementes aladas podem ser vistas, sua forma alada facilita a dispersão pelo vento.");
        description.add("Sua reprodução é sincronizada com o período chuvoso, ou seja,");
        description.add("os frutos maduros no final da seca lançam suas sementes que, com a chegada da chuva, tem mais chances de germinação.");
        setElement(new Element(0, 0, 100, "ponto_2", "Pau-Santo", "null", 1, 1, description));
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Espécie da flora, jacarandá-do-cerrado- Dalbergia miscolobium Benth");
        description.add("A espécie apresenta um caule castanho acinzentado com fissuras sinuosas por vezes,");
        description.add("abrigam larvas de insetos que atraem aves como pica-pau.");
        description.add("Perdem as folhas no período da seca, suas folhas compostas por 9 a 20 folíolos coriáceos,");
        description.add("apresentando manchas circulares de cor preta decorrentes de infecção por fungos.");
        description.add("Suas flores são pequenas, formando grandes inflorescências de cor violácea,");
        description.add("podem ser observadas de novembro a maio e atraem vários tipos de insetos para fazer sua polinização, inclusive, abelhas grandes e pequenas.");
        description.add("Seus frutos que aparecem de maio a julho, têm uma alta viabilidade, ou seja, alta taxa de germinação,");
        description.add("podendo ficar por meses em dormência, e germinarem quando as primeiras chuvas caem. Possui frutos secos dispersos pelo vento.");
        setElement(new Element(1, 1, 100, "ponto_3", "Jacarandá do Cerrado", "Jacaranda", 1, 2, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Eriotheca pubescens (Mart.&amp; Zucc.) Schott &amp; Endl. paineira-do-cerrado.");
        description.add("A espécie possui ampla distribuição no Centro-Oeste, Nordeste e Sudeste.");
        description.add("Não é endêmica do Brasil, geralmente flore em julho e agosto apresenta fruto em novembro.");
        description.add("As sementes se localizam dentro da paina o que facilita sua dispersão pelo vento.");
        description.add("Essa espécie apresenta folhas alternas, compostas palmadas. Árvore melífera belas flores claras, polinizada por abelhas.");
        description.add("A paina que é produzida no fruto, quando maduro e aberto,");
        description.add("é utilizada por aves para fazerem seus ninhos e também serve como recheio de travesseiros.");
        setElement(new Element(2, 2, 100, "ponto_5", "Paineira do Cerrado", "Paineira", 2, 3, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Os cupins são insetos sociais (vivem em grandes colônias) da ordem Isoptera,");
        description.add("formam ninhos que podem atingir forma de torre que são feitos de partículas de solo e excremento");
        description.add("de cupins grudados com as secreções salivares dos operários.  formam um dos grupos dominantes na fauna de solo");
        description.add("de ecossistemas tropicais, exercendo um papel importante nos processos de ciclagem de nutrientes e formação de solo.");
        description.add("Ao abrir túneis e construir seus ninhos, os cupins arejam e melhoram a estrutura do solo, além de movimentar verticalmente grande quantidade de partículas.");
        description.add("Os termiteiros servem de abrigo a uma fauna diversa, incluindo artrópodes, vertebrados e outros grupos.");
        description.add("Os ninhos velhos e abandonados servem de substrato para o desenvolvimento de várias de plantas.");
        description.add("Devido a esse poder de modificar a estrutura do habitat, os cupins podem ser incluídos entre os “engenheiros do ecossistema”.");
        setElement(new Element(3, 3, 100, "ponto_6", "Cupinzeiro terrestre", "Cupim", 2, 4, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Na Estação Ecológica do Jardim Botânico encontram-se várias nascentes que alimentam o córrego cabeça de Veado.");
        description.add("Nascentes são aparecimento, na superfície do terreno de um lençol subterrâneo que dão origem a cursos d’água.");
        description.add("Pesquisas realizadas no córrego cabeça de veado mostram que suas águas são referência em termos de qualidade e de biodiversidade,");
        description.add("típicas de riachos que se mantém em seu estado primitivo.");
        description.add("Ao se observar essas nascentes deve-se expandir o olhar para");
        description.add("o ecossistema que integra os compartimentos terrestres e aquáticos, conhecidos como ecossistemas ripários.");
        setElement(new Element(4, 4, 100, "ponto_23", "Nascente do córrego Cabeça de Veado", "Nascente", 2, 5, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Algumas espécies de plantas apresentam folhas modificadas que realizam outras funções além da fotossíntese,");
        description.add("por exemplo, as gavinhas que são folhas modificadas lembrando molas.");
        description.add("Essas estruturas têm como principal função fixar a planta,");
        description.add("enrolando-se sobre suportes. Muitas por serem verdes, contendo clorofila,");
        description.add("além de fixar auxiliam no processo de fotossíntese.");
        setElement(new Element(5, 5, 100, "ponto_34", "Gavinha", "Gavinha", 3, 6, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        setElement(new Element(6, 6, 100,  "ponto_2", "Pau-Santo", "null",1, 4, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertElement(getElement());

        setElement(new Element(7, 7, 100, "ponto_5", "Paineira do Cerrado", "Paineira", 3, 5, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertElement(getElement());
    }
}
