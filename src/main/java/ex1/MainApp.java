package ex1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainApp {

    @FunctionalInterface
    interface Filtru<T>{
        public boolean test(T p);
    }
    public static void scriere(List<Angajat> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            File file=new File("src/main/resources/angajat.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static List<Angajat> citire() {
            try {
                File file = new File("src/main/resources/angajat.json");
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<Angajat> persoane = mapper
                        .readValue(file, new TypeReference<List<Angajat>>() {
                        });
                return persoane;

            }catch (FileNotFoundException e){
                    System.out.println(e);
                }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    static void afisare_filtrata1(List<Angajat> listAng, Filtru<Angajat> f) {
        for(Angajat a:listAng)
            if(f.test(a))
                System.out.println(a);
    }

        public static void main(String[] args) {
            //List<Angajat> pers=new ArrayList<Angajat>();
            List<Angajat> listaAngajat = citire();
            //System.out.println(listaMobilier);
            Scanner scan = new Scanner(System.in);
            int ui = 0;
            do {
                System.out.println("0=exit");
                System.out.println("1=afisare");
                System.out.println("2=cautare angajat");
                System.out.println("3=adaugare");
                System.out.println("4=angajati cu salariu>2500");
                System.out.println("5=angajati care au functie de conducere");
                System.out.println("6=angajati care nu au functie de conducere");
                System.out.println("7=numele angajatiilor cu litere mari");
                ui = scan.nextInt();
                switch (ui) {
                    case 0 -> {
                        System.out.println("iesire din program");
                    }
                    case 1 -> {
                        //for(Angajat m:listaAngajat) {
                        //   System.out.println(m);
                        //}
                        listaAngajat.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.println("numele angajatului?");
                        Scanner scan2 = new Scanner(System.in);
                        String uiName = scan2.nextLine();
                        for (int i = 0; i < listaAngajat.size(); i++) {
                            if (uiName.equals(listaAngajat.get(i).getNume())) {
                                System.out.println(listaAngajat.get(i));
                            }
                        }
                    }
                    case 3 -> {
                        Scanner sc_ad = new Scanner(System.in);
                        System.out.println("nume?");
                        String nume = sc_ad.nextLine();
                        System.out.println("post?");
                        String post = sc_ad.nextLine();
                        System.out.println("salariu?");
                        Float salariu = sc_ad.nextFloat();
                        LocalDate dataAng = LocalDate.now();
                        Angajat ang = new Angajat(nume, post, dataAng, salariu);
                        if (listaAngajat == null) {
                            listaAngajat = new ArrayList<>();
                            listaAngajat.add(ang);
                        } else {
                            listaAngajat.add(ang);
                        }
                    }
                    case 4->{
                        afisare_filtrata1(listaAngajat, a->a.getSalariu()>2500);
                    }
                    case 5->{
                        List<Angajat> listaAngajatConducere=listaAngajat
                                .stream()
                                .filter((a)->(a.getPost()).toLowerCase().contains("sef") || (a.getPost()).toLowerCase().contains("director"))
                                .filter((a)->(a.getData_ang()).getMonthValue()==4 && (a.getData_ang().getYear()==2022))
                                .collect(Collectors.toList());
                        listaAngajatConducere.forEach(System.out::println);
                    }
                    case 6->{
                        List<Angajat> listaAngajatFaraConducere=listaAngajat
                                .stream()
                                .filter((a)->!(a.getPost()).toLowerCase().contains("sef") && !(a.getPost()).toLowerCase().contains("director"))
                                .sorted((b,a)->a.compareTo(b))
                                .collect(Collectors.toList());
                        listaAngajatFaraConducere.forEach(System.out::println);
                    }
                    case 7->{
                        listaAngajat
                                .stream()
                                .map((a)->a.getNume().toUpperCase())
                                .forEach(System.out::println);
                    }
                }
            }
            while (ui != 0);
            scriere(listaAngajat);
        }
    }
