package cz.czechitas.java2webapps.ukol2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {

    //generovani nahodnych cisel
    private final Random random = new Random();

    //nacteni souboru txt
    private static List<String> readAllLines(String resource)throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource)) {
            assert inputStream != null;
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

                //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
                return reader
                        .lines()
                        .collect(Collectors.toList());
            }
        }
    }

    @GetMapping("/")
    public ModelAndView vygenerujCitat() throws IOException {
        //generovani nahodneho cisla od 1-8
        int nahodneCislo = random.nextInt(0, 8);

        //potrebuju volat n polozku - pro citaty
        String nahodnyCitat = readAllLines("citaty.txt").get(nahodneCislo);

        //obrazky
        List<String> seznamObrazku=List.of("RFHFV7lVQBY","KDYGrlXsHtQ","VfXZg_-8f6A","npxXWgQ33ZQ", "LwvifzvZeao", "u7WazOBoALQ", "uHdiSKbQIME", "mFPJXEQfb9M");
        String nahodnyObrazek = seznamObrazku.get(nahodneCislo);
        //volani sablony

        ModelAndView result = new ModelAndView("citatDne");

        //generovani citatu a pozadi
        result.addObject("citat", nahodnyCitat);
        result.addObject("pozadi", nahodnyObrazek);
        return result;
    }
}
