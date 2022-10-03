package by.bntb.priceanalyst;

import by.bntb.priceanalyst.model.Page;
import by.bntb.priceanalyst.model.Table;
import by.bntb.priceanalyst.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;

@SpringBootTest
public class ParserTests {

    @Autowired
    private Parser parser;

    private static Page mockPage;
    private static Table mockTable;

    @BeforeAll
    static void setup() {
        mockPage = new Page();
        mockPage.setTables(new HashSet<>());
        mockPage.setName("");
        mockPage.setLastUpdate(new Date());
    }

    @Test
    void parseHtml() {

    }
}
