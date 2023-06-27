package RSG.RandomSentencesGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;

@Controller
public class RandomSentencesGeneratorController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/generator")
    public ModelAndView generate() throws IOException {
        final String unknown = "?";
        ModelAndView modelAndView = new ModelAndView("generator");
        modelAndView.addObject("firstAdjective", readRandomWord("src\\main\\resources\\static\\TextFiles\\adjectives.txt").orElse(unknown));
        modelAndView.addObject("firstNoun", readRandomWord("src\\main\\resources\\static\\TextFiles\\nouns.txt").orElse(unknown));
        modelAndView.addObject("secondAdjective", readRandomWord("src\\main\\resources\\static\\TextFiles\\adjectives.txt").orElse(unknown).toLowerCase());
        modelAndView.addObject("secondNoun", readRandomWord("src\\main\\resources\\static\\TextFiles\\nouns.txt").orElse(unknown));
        modelAndView.addObject("verb", readRandomWord("src\\main\\resources\\static\\TextFiles\\verbs.txt").orElse(unknown));
        modelAndView.addObject("place", readRandomWord("src\\main\\resources\\static\\TextFiles\\places.txt").orElse(unknown));
        return modelAndView;
    }

    private Optional<String> readRandomWord(String filePath) {
        File file = new File(filePath);
        try (RandomAccessFile fileReader = new RandomAccessFile(file, "r")) {
            long randomLocation = (long) (Math.random() * fileReader.length());
            fileReader.seek(randomLocation);
            fileReader.readLine();
            String randomLine = fileReader.readLine();
            return Optional.of(randomLine);
        } catch (NullPointerException e) {
            return readRandomWord(filePath);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
