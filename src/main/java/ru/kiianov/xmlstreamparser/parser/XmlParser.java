package ru.kiianov.xmlstreamparser.parser;

import ru.kiianov.xmlstreamparser.dao.entity.CompanyEntity;
import ru.kiianov.xmlstreamparser.dao.impl.AbstractCrudDaoImpl;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    private static final int BATCH_SIZE = 1000;

    public void parse(AbstractCrudDaoImpl<CompanyEntity> companyDao, String xmlFile) {
        try (StaxEventProcessor processor = new StaxEventProcessor(Files.newInputStream(Paths.get(xmlFile)))) {
            List<String> parameters = new ArrayList<>();
            List<CompanyEntity> entities = new ArrayList<>(BATCH_SIZE);
            XMLEventReader reader = processor.getReader();

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    String elementName = startElement.getName().getLocalPart();
                    nextEvent = reader.nextEvent();
                    if (List.of("id",
                            "name",
                            "domain",
                            "year_founded",
                            "industry",
                            "size_range",
                            "locality",
                            "country",
                            "linkedIn_URL",
                            "current_employee_estimate",
                            "total_employee_estimate")
                            .contains(elementName)) {
                        parameters.add(nextEvent.asCharacters().getData().trim());
                    }
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if ("company".equals(endElement.getName().getLocalPart())) {
                        entities.add(createCompanyEntity(parameters));
                        parameters.clear();
                    }
                }

                if (entities.size() == BATCH_SIZE) {
                    companyDao.save(entities);
                    entities.clear();
                }
            }

            companyDao.save(entities);

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CompanyEntity createCompanyEntity(List<String> parameters) {
        return CompanyEntity.builder()
                .withId(Long.parseLong(parameters.get(0)))
                .withName(parameters.get(1))
                .withDomain(parameters.get(2))
                .withYearFounded(parameters.get(3))
                .withIndustry(parameters.get(4))
                .withSizeRange(parameters.get(5))
                .withLocality(parameters.get(6))
                .withCountry(parameters.get(7))
                .withLinkedinUrl(parameters.get(8))
                .withCurrentEmployeeEstimate(Long.parseLong(parameters.get(9)))
                .withTotalEmployeeEstimate(Long.parseLong(parameters.get(10)))
                .build();
    }
}
