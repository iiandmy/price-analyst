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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("html_parser")
@RequiredArgsConstructor
public class HtmlParser implements Parser {
    private final ParserProperties properties;

    private final Pattern htmlLinkPattern = Pattern.compile("https://mc.ru/prices/\\w+.htm");

    @Override
    public Set<Page> parsePrices() throws IOException {
        Set<Page> parsedPages = new HashSet<>();

        Document htmlDoc = Jsoup.connect(properties.getParentLink()).get();

        getPagesAsHtml(htmlDoc);

        return parsedPages;
    }

    private Set<Document> getPagesAsHtml(Document htmlDoc) {
        Element pageBlock = cutUnnecessaryElements(
                htmlDoc.getElementsByClass(properties.getPageBlockClass()).first()
        );
        var nodes = pageBlock
                .childNodes()
                .stream()
                .filter((node) -> node.childNodes().size() != 0)
                .collect(Collectors.toList());

        return getHtmlPages(getLinksFromNodes(nodes));
    }

    private List<String> getLinksFromNodes(List<Node> nodes) {
        List<String> links = new ArrayList<>();

        nodes.forEach((node) -> links.add(getHtmlLink(node.toString())));

        return links.stream()
                .filter((s) -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private Set<Document> getHtmlPages(List<String> links) {
        Set<Document> pages = new HashSet<>();

        // FIXME
        links.forEach((link) -> {
            try {
                pages.add(Jsoup.connect(link).get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return pages;
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
