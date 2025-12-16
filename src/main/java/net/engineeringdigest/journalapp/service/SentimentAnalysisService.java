package net.engineeringdigest.journalapp.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {
    /**
     * Maps a sentiment score (out of 10) to a descriptive String.
     * * @param outOfTen The sentiment score (expected range 0.0 to 10.0).
     * @return A descriptive sentiment string with a thank you footer.
     */
    public String fetch(Double outOfTen) {
        if (outOfTen == null) {
            return "No sentiment score provided.\n\n Thank you Sir/Madam";
        }

        String sentiment = "";
        if (outOfTen >= 9.0) {
            sentiment = "Outstanding! You are truly thriving.";
        } else if (outOfTen >= 7.0) {
            sentiment = "Excellent. You seem to be in a very positive space.";
        } else if (outOfTen >= 5.0) {
            sentiment = "Good to see you. Maintaining a positive outlook is key.";
        } else if (outOfTen >= 3.0) {
            sentiment = "It appears you are going through a neutral or challenging phase. We're here for you.";
        } else if (outOfTen >= 2.0) {
            sentiment = "Poor. It seems like you are having a difficult time.";
        } else if (outOfTen >= 0.0) {
            sentiment = "Critical. Please remember to seek support if you need it.";
        } else {
            sentiment = "Invalid sentiment score detected.";
        }

        return outOfTen + "\n"+  sentiment + "\n\n Thank you Sir/Madam";
    }
}