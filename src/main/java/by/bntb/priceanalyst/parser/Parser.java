package by.bntb.priceanalyst.parser;

import by.bntb.priceanalyst.model.Page;

import java.util.Set;

public interface Parser {
    Set<Page> parsePrices();
}
