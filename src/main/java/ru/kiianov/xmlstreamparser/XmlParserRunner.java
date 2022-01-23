package ru.kiianov.xmlstreamparser;

import ru.kiianov.xmlstreamparser.dao.DBConnector;
import ru.kiianov.xmlstreamparser.dao.entity.CompanyEntity;
import ru.kiianov.xmlstreamparser.dao.impl.AbstractCrudDaoImpl;
import ru.kiianov.xmlstreamparser.dao.impl.CompanyDaoImpl;
import ru.kiianov.xmlstreamparser.parser.XmlParser;

public class XmlParserRunner {
    public static void main(String[] args) {
        DBConnector connector = new DBConnector("database");
        AbstractCrudDaoImpl<CompanyEntity> companyDao = new CompanyDaoImpl(connector);
        XmlParser parser = new XmlParser();
        Long startTime = System.currentTimeMillis();
        parser.parse(companyDao,args[0]);
        Long endTime = System.currentTimeMillis();
        System.out.println("Total time(s): " + (endTime-startTime)/1000);
    }
}
