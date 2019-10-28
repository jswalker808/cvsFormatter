package service;

import request.FormatResult;

public class FormatService {

    public FormatResult format(String csvString) {

        StringBuilder sb = new StringBuilder(csvString);

        boolean firstBracket = false;

        try {

            for (int i = 0; i < sb.length(); i++) {

                char c = sb.charAt(i);

                // handles string values in csv string
                if (c == '\"') {

                    if (!firstBracket) {
                        sb.replace(i, i + 1, "[");
                        firstBracket = true;
                    } else {
                        sb.replace(i, i + 1, "]");
                        firstBracket = false;
                    }
                }

                // handle numeric values
                else if (Character.isDigit(c)) {

                    char prev = sb.charAt(i - 1);
                    char next = sb.charAt(i + 1);

                    if (prev == ',' && !firstBracket) {
                        sb.insert(i, "[");
                    }
                    if (next == ',' && !firstBracket) {
                        sb.insert(i + 1, "]");
                    }
                }
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return new FormatResult("Error");
        }

        return new FormatResult(sb.toString());
    }

}
