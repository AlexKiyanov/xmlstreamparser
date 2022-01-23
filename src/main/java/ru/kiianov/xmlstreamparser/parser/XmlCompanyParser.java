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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class XmlCompanyParser {
    private static final int BATCH_SIZE_THRESHOLD = 1000;

    public void parse(AbstractCrudDaoImpl<CompanyEntity> companyDao, String xmlFile) {
        try (StaxEventProcessor processor = new StaxEventProcessor(Files.newInputStream(Paths.get(xmlFile)))) {
            List<String> parameters = new ArrayList<>();
            Set<CompanyEntity> entities = new LinkedHashSet<>(BATCH_SIZE_THRESHOLD);
            final XMLEventReader reader = processor.getReader();

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();

                nextEvent = getNextXmlEvent(parameters, reader, nextEvent);

                endCompanyElementCheck(parameters, entities, nextEvent);

                if (entities.size() == BATCH_SIZE_THRESHOLD) {
                    companyDao.save(entities);
                    entities.clear();
                }
            }

            companyDao.save(entities);

        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void endCompanyElementCheck(List<String> parameters,
                                               Set<CompanyEntity> entities,
                                               XMLEvent nextEvent) {
        if (nextEvent.isEndElement()) {
            EndElement endElement = nextEvent.asEndElement();
            if ("company".equals(getElementName(endElement))) {
                entities.add(createCompanyEntity(parameters));
                parameters.clear();
            }
        }
    }

    private static XMLEvent getNextXmlEvent(List<String> parameters,
                                            XMLEventReader reader,
                                            XMLEvent nextEvent) throws XMLStreamException {
        if (nextEvent.isStartElement()) {
            StartElement startElement = nextEvent.asStartElement();
            String elementName = getElementName(startElement);
            nextEvent = reader.nextEvent();
            if (isContainsCompanyParameters(elementName)) {
                parameters.add(getEventData(nextEvent));
            }
        }
        return nextEvent;
    }

    private static String getElementName(StartElement startElement) {
        return startElement.getName().getLocalPart();
    }

    private static String getElementName(EndElement endElement) {
        return endElement.getName().getLocalPart();
    }

    private static String getEventData(XMLEvent nextEvent) {
        return nextEvent.asCharacters().getData().trim();
    }

    private static boolean isContainsCompanyParameters(String elementName) {
        return List.of("id",
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
                .contains(elementName);
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
