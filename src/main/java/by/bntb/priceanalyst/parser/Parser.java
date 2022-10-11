package by.bntb.priceanalyst.parser;

import by.bntb.priceanalyst.model.Page;

import java.io.IOException;
import java.util.Set;

public interface Parser {
    Set<Page> parsePrices() throws IOException;
}
