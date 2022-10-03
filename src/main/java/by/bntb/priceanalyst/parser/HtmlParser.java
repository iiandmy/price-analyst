package by.bntb.priceanalyst.parser;

import by.bntb.priceanalyst.model.Page;
import by.bntb.priceanalyst.properties.ParserProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component("html_parser")
@RequiredArgsConstructor
public class HtmlParser implements Parser {
    private ParserProperties properties;

    @Override
    public Set<Page> parsePrices() {
        Set<Page> parsedPages = new HashSet<>();



        return parsedPages;
    }

}
