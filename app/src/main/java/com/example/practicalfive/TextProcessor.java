package com.example.practicalfive;

/**
 * Regular (non-Activity) helper class that validates and normalises the
 * municipality name entered by the user. It contains no Android UI code and
 * has no associated layout, so the same rules can be reused safely from
 * multiple Activities.
 */
public class TextProcessor {

    /**
     * Validates and normalises a municipality name.
     *
     * @param municipality the raw text entered by the user.
     * @return the correctly capitalised official name ("Beijing", "Shanghai",
     *         "Tianjin" or "Chongqing") when the input is valid, or
     *         {@code null} when the input is null, blank or not one of the
     *         four supported municipalities.
     */
    public String processMunicipality(String municipality) {
        if (municipality == null) {
            return null;
        }

        String trimmed = municipality.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        switch (trimmed.toLowerCase()) {
            case "beijing":
                return "Beijing";
            case "shanghai":
                return "Shanghai";
            case "tianjin":
                return "Tianjin";
            case "chongqing":
                return "Chongqing";
            default:
                return null;
        }
    }
}
