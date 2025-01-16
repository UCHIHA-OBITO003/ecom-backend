package com.snow.ecomproject.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class SearchParserService {


    private static final Pattern PRICE_PATTERN = Pattern.compile(
            "(?i)(?<direction>(under|below|less\\s+than|above|more\\s+than|over))\\s+(?<number>\\d+(\\.\\d+)?)"
    );

    public ParsedSearch parseSearchQuery(String originalQuery) {
        if (originalQuery == null || originalQuery.trim().isEmpty()) {
            return new ParsedSearch("", null, null);
        }

        String lower = originalQuery.toLowerCase();

        Double maxPrice = null;
        Double minPrice = null;

        Matcher matcher = PRICE_PATTERN.matcher(lower);
        if (matcher.find()) {
            String direction = matcher.group("direction");
            String numberString = matcher.group("number");
            double numericValue = Double.parseDouble(numberString);


            if (direction.contains("under") || direction.contains("below") || direction.contains("less")) {
                maxPrice = numericValue;
            } else if (direction.contains("above") || direction.contains("over") || direction.contains("more")) {
                minPrice = numericValue;
            }
        }


        String cleanedQuery = lower.replaceAll(PRICE_PATTERN.pattern(), "").trim();


        cleanedQuery = cleanedQuery.replaceAll("(?i)thousand", "000");
        cleanedQuery = cleanedQuery.replaceAll("(?i)rupees|dollars|bucks", "");
        cleanedQuery = cleanedQuery.replaceAll("\\s{2,}", " ").trim();

        return new ParsedSearch(cleanedQuery, minPrice, maxPrice);
    }


    public static class ParsedSearch {
        private final String namePart;
        private final Double minPrice;
        private final Double maxPrice;


        public ParsedSearch(String namePart, Double minPrice, Double maxPrice) {
            this.namePart = namePart;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
        }

        public String getNamePart() {
            return namePart;
        }

        public Double getMinPrice() {
            return minPrice;
        }

        public Double getMaxPrice() {
            return maxPrice;
        }
    }
}
