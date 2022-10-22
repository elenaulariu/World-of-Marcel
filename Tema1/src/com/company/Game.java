package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {

    // Metode pentru a adauga elemente noi
    public static void addAccount(Account account) {
        GameSingleton instance = GameSingleton.getInstance();
        instance.addAccount(account);
    }

    public static void addToDictionary(CellEnum typeOfCell, String story) {
        GameSingleton instance = GameSingleton.getInstance();
        instance.addToDictionary(typeOfCell, story);
    }

    // Metoda run pentru situatia in care utilizatorul decide sa ruleze jocul in terminal
    public static void run(String fileName) {
        // Parsam fisierul accounts.json, cream elemente de tip Accounts si le adaugam in lista de conturi
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);
            JSONArray givenAccounts = (JSONArray) ((JSONObject) obj).get("accounts");
            for (Object player : givenAccounts) {
                JSONObject cred = (JSONObject) ((JSONObject) player).get("credentials");
                String email = (String) cred.get("email");
                String password = (String) cred.get("password");
                String name = (String) ((JSONObject) player).get("name");
                String country = (String) ((JSONObject) player).get("country");
                List<String> favoriteGames = new ArrayList<>();
                JSONArray games = (JSONArray) ((JSONObject) player).get("favorite_games");
                for (Object game : games) {
                    favoriteGames.add((String) game);
                }
                Collections.sort(favoriteGames);
                int mapsCompleted = Integer.parseInt((String) ((JSONObject) player).get("maps_completed"));
                JSONArray charact = (JSONArray) ((JSONObject) player).get("characters");
                List<Character> characters = new ArrayList<>();
                for (Object character : charact) {
                    String characterName = (String) ((JSONObject) character).get("name");
                    String profession = (String) ((JSONObject) character).get("profession");
                    int level = Integer.parseInt((String) ((JSONObject) character).get("level"));
                    int experience = ((Long) ((JSONObject) character).get("experience")).intValue();
                    CharacterFactory.CharacterType characterType = CharacterFactory.CharacterType.valueOf(profession);
                    characters.add(CharacterFactory.createCharacter(characterType, characterName, experience, level));
                }
                Credentials credentials = new Credentials(email, password);
                Account.Information.InformationBuilder informationBuilder = new Account.Information.InformationBuilder(credentials, name);
                informationBuilder.favouriteGames(favoriteGames);
                informationBuilder.country(country);
                Account.Information information = informationBuilder.build();
                Account account = new Account(information, characters, mapsCompleted);
                addAccount(account);
            }
            // Cerem utilizatorului sa introduca datele de autentificare si il cautam in lista de utilizatori
            System.out.println("Introduce your account information:");
            System.out.println("email:");
            Scanner input = new Scanner(System.in);
            String email = input.nextLine();
            System.out.println("password:");
            String password = input.nextLine();
            int accountIndex = -1;
            for (Object object : GameSingleton.getInstance().getAccounts()) {
                if (((Account) object).information.getCredentials().getEmail().equals(email) &&
                        ((Account) object).information.getCredentials().getPassword().equals(password)) {
                    accountIndex = GameSingleton.getInstance().getAccounts().indexOf(object);
                }
            }
            if (accountIndex == -1) {
                System.out.println("Wrong credentials!");
            } else {
                // Preluam informatiile utilizatorului si ii cerem sa aleaga un personaj din cele afisate
                Account account = GameSingleton.getInstance().getAccounts().get(accountIndex);
                System.out.println("Choose a character:");
                System.out.println(account.characters);
                String characterName = input.nextLine();
                int characterIndex = -1;
                for (Object object : account.characters) {
                    if (((Character) object).name.equals(characterName)) {
                        characterIndex = account.characters.indexOf(object);
                    }
                }
                if (characterIndex == -1) {
                    System.out.println("Choose another character!");
                } else {
                    // Preluam personajul si generam o harta random, pe care acesta trebuie sa o parcurga
                    Character character = account.characters.get(characterIndex);
                    Grid map = Grid.mapGenerator(5, 5);
                    map.setCharacter(character);
                    map.setCurrentCell();
                    System.out.println(map.printMap());
                    // Incepe jocul
                    Game game = new Game();
                    // Se parseaza fisierul care contine povestile
                    game.storyTime("stories.json");
                    // I se afiseaza jucatorului optiunile
                    game.options(map);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InformationIncompleteException e) {
            e.printStackTrace();
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }
    }

    // Metoda run hardcodata pentru exemplificarea implementarii in terminal
    public static void runHardcoded(String fileName) {
        // Parsam fisierul accounts.json, cream elemente de tip Accounts si le adaugam in lista de conturi
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);
            JSONArray givenAccounts = (JSONArray) ((JSONObject) obj).get("accounts");
            for (Object player : givenAccounts) {
                JSONObject cred = (JSONObject) ((JSONObject) player).get("credentials");
                String email = (String) cred.get("email");
                String password = (String) cred.get("password");
                String name = (String) ((JSONObject) player).get("name");
                String country = (String) ((JSONObject) player).get("country");
                List<String> favoriteGames = new ArrayList<>();
                JSONArray games = (JSONArray) ((JSONObject) player).get("favorite_games");
                for (Object game : games) {
                    favoriteGames.add((String) game);
                }
                Collections.sort(favoriteGames);
                int mapsCompleted = Integer.parseInt((String) ((JSONObject) player).get("maps_completed"));
                JSONArray charact = (JSONArray) ((JSONObject) player).get("characters");
                List<Character> characters = new ArrayList<>();
                for (Object character : charact) {
                    String characterName = (String) ((JSONObject) character).get("name");
                    String profession = (String) ((JSONObject) character).get("profession");
                    int level = Integer.parseInt((String) ((JSONObject) character).get("level"));
                    int experience = ((Long) ((JSONObject) character).get("experience")).intValue();
                    CharacterFactory.CharacterType characterType = CharacterFactory.CharacterType.valueOf(profession);
                    characters.add(CharacterFactory.createCharacter(characterType, characterName, experience, level));
                }
                Credentials credentials = new Credentials(email, password);
                Account.Information.InformationBuilder informationBuilder = new Account.Information.InformationBuilder(credentials, name);
                informationBuilder.favouriteGames(favoriteGames);
                informationBuilder.country(country);
                Account.Information information = informationBuilder.build();
                Account account = new Account(information, characters, mapsCompleted);
                addAccount(account);
            }
            System.out.println("Introduce your account information:");
            // Generam random un cont pe care il vom folosi
            Random random = new Random();
            GameSingleton instance = GameSingleton.getInstance();
            Account usedAccount = instance.getAccounts().get(random.nextInt(instance.getAccounts().size()));
            System.out.println("email:");
            System.out.println(usedAccount.information.getCredentials().getEmail());
            System.out.println("password:");
            System.out.println(usedAccount.information.getCredentials().getPassword());
            System.out.println("Choose a character:");
            // Generam random un personaj
            Character usedCharacter = usedAccount.characters.get(random.nextInt(usedAccount.characters.size()));
            System.out.println(usedCharacter);
            Scanner scanner = new Scanner(System.in);
            // Daca utilizatorul apasa tasta P urmata de enter se va incepe jocul
            char c = scanner.next().charAt(0);
            if (c == 'P' || c == 'p') {
                // Se genereaza harta oferita in enunt
                Grid map = Grid.mapGeneratorHardcoded(5, 5);
                map.setCharacter(usedCharacter);
                map.setCurrentCell();
                System.out.println(map.printMap());
                Game game = new Game();
                // Se parseaza fisierul care contine povestile si se adauga in dictionar
                game.storyTime("stories.json");
                // Se creaza un arrayList care contine toate miscare care ar trebui realizate de personaj
                // pentru a ajunge la final
                ArrayList<String> directions = new ArrayList<>();
                directions.add("east");
                directions.add("east");
                directions.add("east");
                directions.add("east");
                directions.add("south");
                directions.add("south");
                directions.add("south");
                directions.add("south");
                game.optionsHardcoded(map, directions);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InformationIncompleteException e) {
            e.printStackTrace();
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }
    }

    // Metoda run in cazul in care utilizatorul alege interfata grafica
    // (Permite doar autentificarea utilizatorului si alegerea personajului)
    public static void runGraphicInterface(String fileName) {
        // Parsam fisierul accounts.json, cream elemente de tip Accounts si le adaugam in lista de conturi
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);
            JSONArray givenAccounts = (JSONArray) ((JSONObject) obj).get("accounts");
            for (Object player : givenAccounts) {
                JSONObject cred = (JSONObject) ((JSONObject) player).get("credentials");
                String email = (String) cred.get("email");
                String password = (String) cred.get("password");
                String name = (String) ((JSONObject) player).get("name");
                String country = (String) ((JSONObject) player).get("country");
                List<String> favoriteGames = new ArrayList<>();
                JSONArray games = (JSONArray) ((JSONObject) player).get("favorite_games");
                for (Object game : games) {
                    favoriteGames.add((String) game);
                }
                Collections.sort(favoriteGames);
                int mapsCompleted = Integer.parseInt((String) ((JSONObject) player).get("maps_completed"));
                JSONArray charact = (JSONArray) ((JSONObject) player).get("characters");
                List<Character> characters = new ArrayList<>();
                for (Object character : charact) {
                    String characterName = (String) ((JSONObject) character).get("name");
                    String profession = (String) ((JSONObject) character).get("profession");
                    int level = Integer.parseInt((String) ((JSONObject) character).get("level"));
                    int experience = ((Long) ((JSONObject) character).get("experience")).intValue();
                    CharacterFactory.CharacterType characterType = CharacterFactory.CharacterType.valueOf(profession);
                    characters.add(CharacterFactory.createCharacter(characterType, characterName, experience, level));
                }
                Credentials credentials = new Credentials(email, password);
                Account.Information.InformationBuilder informationBuilder = new Account.Information.InformationBuilder(credentials, name);
                informationBuilder.favouriteGames(favoriteGames);
                informationBuilder.country(country);
                Account.Information information = informationBuilder.build();
                Account account = new Account(information, characters, mapsCompleted);
                addAccount(account);
            }
            // Deschidem pagina de login
            GameSingleton instance = GameSingleton.getInstance();
            new LoginPage(instance.getAccounts());
        } catch (InformationIncompleteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Ofera utilizatorului optiuni in functie de tipul celului pe care se afla
    public void options(Grid map) throws InvalidCommandException {
        // Afiseaza o poveste daca celula nu a mai fost vizitata
        if (map.get(map.currentCell.x).get(map.currentCell.y).visited == false) {
            System.out.println(cellStory(map.currentCell));
        }
        // Daca celula este goala se deplaseaza in directia data de utilizator
        if (map.currentCell.cellEnum == CellEnum.EMPTY) {
            System.out.println("Choose a direction:");
            nextCell(map);
        } else {
            // Daca celula este cea de final, se afiseaza "You win" si se inchide jocul
            if (map.currentCell.cellEnum == CellEnum.FINISH) {
                System.out.println("You win");
                return;
            } else {
                // Daca celula este de tip shop, i se ofera utilizatorului posibilitatea sa cumpere potiuni
                if (map.currentCell.cellEnum == CellEnum.SHOP) {
                    shop(map);
                } else {
                    // Daca celula este de tip enemy, si nu a mai fost vizitata,
                    // personajul se va lupta cu inamicul,
                    // altfel va putea alege urmatoarea celula
                    if (map.currentCell.cellEnum == CellEnum.ENEMY) {
                        if (map.get(map.currentCell.x).get(map.currentCell.y).visited == false) fight(map);
                        else {
                            System.out.println("Choose a direction:");
                            nextCell(map);
                        }
                    }
                }
            }
        }
    }

    // Ofera utilizatorului optiuni in functie de tipul celului pe care se afla, pentru varianta hardcodata
    public void optionsHardcoded(Grid map, ArrayList input) throws InvalidCommandException {
        Scanner scanner = new Scanner(System.in);
        char c = scanner.next().charAt(0);
        // Daca se apasa tasta P se vor afisa optiunile in functie de tipul celulei
        // la fel ca si in cazul normal
        if (c == 'P' || c == 'p') {
            if (map.get(map.currentCell.x).get(map.currentCell.y).visited == false) {
                System.out.println(cellStory(map.currentCell));
            }
            if (map.currentCell.cellEnum == CellEnum.EMPTY) {
                System.out.println("Choose a direction:");
                nextCellHardcoded(map, input);
            } else {
                if (map.currentCell.cellEnum == CellEnum.FINISH) {
                    System.out.println("You win");
                    return;
                } else {
                    if (map.currentCell.cellEnum == CellEnum.SHOP) {
                        shopHardcoded(map, input);
                    } else if (map.currentCell.cellEnum == CellEnum.ENEMY) {
                        if (map.get(map.currentCell.x).get(map.currentCell.y).visited == false)
                            fightHardcoded(map, input);
                        else {
                            System.out.println("Choose a direction:");
                            nextCellHardcoded(map, input);
                        }
                    }
                }
            }
        }
    }

    // Se deplaseaza in directia aleasa de utilizator, atat timp cat deplasarea este posibila
    // si afiseaza harta revizuita, urmand sa afiseze optiunile pentru noua celula
    public void nextCell(Grid map) throws InvalidCommandException {
        Scanner input = new Scanner(System.in);
        String direction = input.nextLine();
        if (direction.equals("north")) {
            if (map.goNorth() == true) {
                System.out.println(map.printMap());
            }
        } else {
            if (direction.equals("south")) {
                if (map.goSouth() == true) {
                    System.out.println(map.printMap());
                }
            } else {
                if (direction.equals("east")) {
                    if (map.goEast() == true) {
                        System.out.println(map.printMap());
                    }
                } else {
                    if (direction.equals("west")) {
                        if (map.goWest() == true) {
                            System.out.println(map.printMap());
                        }
                    } else throw new InvalidCommandException("Wrong command");
                }
            }
        }
        options(map);
    }

    // Se deplaseaza in directia aflata la inceputul arrayListului, atat timp cat deplasarea este posibila
    // si afiseaza harta revizuita, urmand sa afiseze optiunile pentru noua celula
    public void nextCellHardcoded(Grid map, ArrayList input) throws InvalidCommandException {
        String direction = (String) input.get(0);
        input.remove(0);
        if (direction.equals("north")) {
            System.out.println("north");
            if (map.goNorth() == true) {
                System.out.println(map.printMap());
            }
        } else {
            if (direction.equals("south")) {
                System.out.println("south");
                if (map.goSouth() == true) {
                    System.out.println(map.printMap());
                }
            } else {
                if (direction.equals("east")) {
                    System.out.println("east");
                    if (map.goEast() == true) {
                        System.out.println(map.printMap());
                    }
                } else {
                    if (direction.equals("west")) {
                        System.out.println("west");
                        if (map.goWest() == true) {
                            System.out.println(map.printMap());
                        }
                    } else throw new InvalidCommandException("Wrong command");
                }
            }
        }
        optionsHardcoded(map, input);
    }

    // Afiseaza numarul de banuti si greutatea ramasa a inventarului, dar si potiunile existente in shop
    // si ii da posibilitatea utilizatorului sa aleaga daca, cate si care potiuni vrea sa cumpere
    public void shop(Grid map) throws InvalidCommandException {
        Scanner input = new Scanner(System.in);
        System.out.println("Coins" + Grid.character.inventory.numberOfCoins);
        System.out.println("Remaining weight" + Grid.character.inventory.remainingWeight());
        System.out.println(map.currentCell.content);
        System.out.println("Buy potion");
        String response = input.nextLine();
        if (response.equals("yes")) {
            System.out.println("Number of potions you want to buy:");
            int numberOfPotions = input.nextInt();
            for (int i = 0; i < numberOfPotions; i++) {
                System.out.println(map.currentCell.content);
                // Alegem indexul din shop al potiunii, o adaugam in inventar si o stergem din shop
                System.out.println("Choose potion index:");
                int potionPos = input.nextInt();
                Grid.character.buyPotion(((Shop) map.currentCell.content).shop.get(potionPos - 1));
                ((Shop) map.currentCell.content).deletePotion(potionPos - 1);
            }
            System.out.println("Choose a direction:");
            nextCell(map);
        } else if (response.equals("no")) {
            System.out.println("Choose a direction:");
            nextCell(map);
        } else throw new InvalidCommandException("Wrong response");
    }

    // Cumpara un mana potion si un health potion, asa cum ni se cere
    public void shopHardcoded(Grid map, ArrayList input) throws InvalidCommandException {
        System.out.println("Coins" + Grid.character.inventory.numberOfCoins);
        System.out.println("Remaining weight" + Grid.character.inventory.remainingWeight());
        System.out.println(map.currentCell.content);
        System.out.println("Buy potion");
        String response = "yes";
        if (response.equals("yes")) {
            System.out.println("Number of potions you want to buy:");
            int numberOfPotions = 2;
            System.out.println(numberOfPotions);
            for (int i = 0; i < numberOfPotions; i++) {
                System.out.println(map.currentCell.content);
                System.out.println("Choose potion index:");
                int potionPos = 1;
                System.out.println(potionPos);
                System.out.println(((Shop) map.currentCell.content).shop.get(potionPos - 1).potionUsage());
                Grid.character.buyPotion(((Shop) map.currentCell.content).shop.get(potionPos - 1));
                ((Shop) map.currentCell.content).deletePotion(potionPos - 1);
            }
            System.out.println("Choose a direction:");
            nextCellHardcoded(map, input);
        } else if (response.equals("no")) {
            System.out.println("Choose a direction:");
            nextCell(map);
        } else throw new InvalidCommandException("Wrong response");
    }

    // Personajul si inamicul se ataca pana cand unul dintre ei ramane fara viata
    public void fight(Grid map) throws InvalidCommandException {
        Enemy enemy = (Enemy) map.currentCell.content;
        // Utilizatorul alege daca vrea sa atace, sa foloseasca o abilitate sau o potiune
        System.out.println("Choose an option:\n1.Attack\n2.Use ability\n3.Use potion");
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        // Afiseaza valorile currentLife si currentMana
        System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
        // Daca alege sa atace, inamicul primeste damage, calculat in functie de personaj
        if (option == 1) {
            enemy.receiveDamage(Grid.character.getDamage());
        } else {
            // Daca alege sa foloseasca abilitati
            // dar nu exista, primeste posibilitatea sa aleaga din nou
            // altfel alege o abilitate, daca are suficienta mana o foloseste, altfel alege alta optiune
            if (option == 2) {
                if (!Grid.character.abilities.isEmpty()) {
                    System.out.println("Choose ability:");
                    System.out.println(Grid.character.abilities);
                    int ability = input.nextInt();
                    if (Grid.character.abilityUse(Grid.character.abilities.get(ability), enemy) == true) {
                        Grid.character.abilities.remove(ability);
                        System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
                    } else {
                        System.out.println("Not enough mana! Choose something else");
                        fight(map);
                    }
                } else {
                    System.out.println("No abilities");
                    fight(map);
                }
            } else {
                // Daca alege sa foloseasca o potiune, se verifica daca exista potiuni in inventar,
                // Daca da alege o potiune, altfel alege alta optiune
                if (option == 3) {
                    if (Grid.character.inventory.remainingWeight() != Grid.character.inventory.maxWeight) {
                        System.out.println("Choose potion:");
                        for (Object obj : Grid.character.inventory.potions)
                            System.out.println(((Potion) obj).potionUsage());
                        int potion = input.nextInt();
                        Potion use = Grid.character.inventory.potions.get(potion);
                        if (use.potionUsage().equals("Mana Potion")) {
                            Grid.character.regenerateMana(use.regenerationValue());
                        } else Grid.character.regenerateLife(use.regenerationValue());
                    } else {
                        System.out.println("No potions");
                        fight(map);
                    }
                    System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
                } else throw new InvalidCommandException("Wrong command");
            }
        }
        // Daca inamicul nu mai are viata, ii creste experienta personajului, si se verifica daca ii poate
        // creste nivelul, iar apoi se deplaseaza la urmatoarea celula
        if (enemy.currentLife <= 0) {
            System.out.println("Enemy killed");
            Grid.character.experience = Grid.character.experience + 15;
            Grid.character.raiseLevel();
            System.out.println("Choose a direction:");
            nextCell(map);
        } else {
            // Altfel ataca inamicul, atac simplu sau abilitatea (ales in mod random)
            System.out.println("Enemy attacks");
            enemy.attack(Grid.character);
            // Daca personajul nu mai are viata, jocul s-a terminat
            if (Grid.character.currentLife <= 0) {
                System.out.println("GAME OVER!");
                return;
            } else {
                // Altfel lupta continua
                System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
                fight(map);
            }
        }
    }

    // Lupta intre personaj si inamic, personajul foloseste cat timp are posibilitatea
    // toate abilitatiile, urmate de cele doua potiuni, iar apoi de atacuri simple
    public void fightHardcoded(Grid map, ArrayList input) throws InvalidCommandException {
        Enemy enemy = (Enemy) map.currentCell.content;
        System.out.println("Choose an option:\n1.Attack\n2.Use ability\n3.Use potion");
        System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
        while (!Grid.character.abilities.isEmpty() && enemy.currentLife > 0 && Grid.character.currentLife > 0) {
            System.out.println("2");
            System.out.println("Choose ability:");
            System.out.println(Grid.character.abilities);
            if (Grid.character.abilityUse(Grid.character.abilities.get(0), enemy) == true) {
                System.out.println(Grid.character.abilities.get(0).toString());
                Grid.character.abilities.remove(0);
                System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
            } else {
                System.out.println("Not enough mana! Choose something else");
                System.out.println("1");
                enemy.receiveDamage(Grid.character.getDamage());
            }
            System.out.println("Enemy attacks");
            System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
            enemy.attack(Grid.character);
        }
        if (Grid.character.abilities.isEmpty() && enemy.currentLife > 0 && Grid.character.currentLife > 0) {
            System.out.println("3");
            System.out.println("Choose potion:");
            for (Object obj : Grid.character.inventory.potions)
                System.out.println(((Potion) obj).potionUsage());
            System.out.println("0");
            Grid.character.regenerateMana(Grid.character.inventory.potions.get(0).regenerationValue());
            enemy.attack(Grid.character);
            System.out.println("Enemy attacks");
            System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
            if (Grid.character.currentLife <= 0) {
                System.out.println("GAME OVER!");
                return;
            } else {
                System.out.println("3");
                System.out.println("Choose potion:");
                for (Object obj : Grid.character.inventory.potions)
                    System.out.println(((Potion) obj).potionUsage());
                System.out.println("0");
                Grid.character.regenerateLife(Grid.character.inventory.potions.get(0).regenerationValue());
                enemy.attack(Grid.character);
                System.out.println("Enemy attacks");
                System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
                if (Grid.character.currentLife <= 0) {
                    System.out.println("GAME OVER!");
                    return;
                }
            }
        }
        while (enemy.currentLife > 0 && Grid.character.currentLife > 0) {
            System.out.println("1");
            enemy.receiveDamage(Grid.character.getDamage());
            enemy.attack(Grid.character);
            System.out.println("Enemy attacks");
            System.out.println("Life:" + Grid.character.currentLife + "\nMana:" + Grid.character.currentMana);
        }
        if (Grid.character.currentLife <= 0) {
            System.out.println("GAME OVER!");
            return;
        } else if (enemy.currentLife <= 0) {
            System.out.println("Enemy killed");
            Grid.character.experience = Grid.character.experience + 15;
            Grid.character.raiseLevel();
            System.out.println("Choose a direction:");
            Scanner scanner = new Scanner(System.in);
            if (scanner.next().charAt(0) == 'P') nextCellHardcoded(map, input);
        }
    }

    // Parseaza fisierul cu povesti, si adauga in dictionar povestea in functie de tipul celulei
    public void storyTime(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);
            JSONArray stories = (JSONArray) ((JSONObject) obj).get("stories");
            for (Object story : stories) {
                String cellType = (String) ((JSONObject) story).get("type");
                CellEnum type = CellEnum.valueOf(cellType);
                String text = (String) ((JSONObject) story).get("value");
                addToDictionary(type, text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Alege random o poveste din dictionar in functie de tipul celulei curente
    public String cellStory(Cell cell) {
        Random random = new Random();
        List<String> stories = GameSingleton.getInstance().getDictionary().get(cell.cellEnum);
        return stories.get(random.nextInt(stories.size()));
    }
}