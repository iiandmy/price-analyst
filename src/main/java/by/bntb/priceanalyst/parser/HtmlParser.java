package by.bntb.priceanalyst.parser;

import by.bntb.priceanalyst.model.Page;
import by.bntb.priceanalyst.properties.ParserProperties;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("html_parser")
@RequiredArgsConstructor
public class HtmlParser implements Parser {
    private final ParserProperties properties;

    private final Pattern htmlLinkPattern = Pattern.compile("https://mc.ru/prices/\\w+.htm");
    private final Pattern dateTimePattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}");

    @Override
    public Set<Page> parsePrices() throws IOException {
        Set<Page> parsedPages = new HashSet<>();

        Document htmlDoc = Jsoup.connect(properties.getParentLink()).get();

        getRawPages(htmlDoc).forEach((rawPage) -> {
            parsedPages.add(parsePage(rawPage));
        });

        return parsedPages;
    }

    private List<Page> getRawPages(Document htmlDoc) {
        Element pageBlock = cutUnnecessaryElements(
                htmlDoc.getElementsByClass(properties.getPageBlockClass()).first()
        );
        var nodes = pageBlock
                .childNodes()
                .stream()
                .filter((node) -> node.childNodes().size() != 0)
                .collect(Collectors.toList());

        return parseLinksAndNames(nodes);
    }

    private List<Page> parseLinksAndNames(List<Node> nodes) {
        List<Page> pages = new ArrayList<>();

        nodes.forEach((node) -> {
            String link = getHtmlLink(node.toString());
            if (!link.isEmpty()) {
                Page page = new Page();
                page.setName(node.lastChild().toString());
                page.setLink(link);
                pages.add(page);
            }
        });

        return pages;
    }

    private Page parsePage(Page rawPage) {
        Document pageDocument;

        try {
            pageDocument = Jsoup.connect(rawPage.getLink()).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        rawPage.setLastUpdate(parseUpdateTime(pageDocument));

        return rawPage;
    }

    // FIXME
    private Date parseUpdateTime(Document pageDocument) {
        Date updateDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Element infoBlock = pageDocument.getElementById(properties.getInfoBlockId());
        String infoString = infoBlock.lastElementChild().text();
        Matcher matcher = dateTimePattern.matcher(infoString);

        if (!matcher.find()) {
            return new Date();
        }

        String dateString = infoString.substring(matcher.start(), matcher.end());
        try {
            updateDate = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return updateDate;
    }

    /*
        First and last elements in list doesn't contain html links,
        So there are no need in them
     */
    private Element cutUnnecessaryElements(Element page) {
        page.firstElementChild().remove();
        page.lastElementChild().remove();
        return page;
    }

    private String getHtmlLink(String string) {
        Matcher matcher = htmlLinkPattern.matcher(string);
        if (!matcher.find()) {
            return "";
        }

        return string.substring(matcher.start(), matcher.end());
    }

}
