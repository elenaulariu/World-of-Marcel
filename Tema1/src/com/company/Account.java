package com.company;

import java.util.Collection;
import java.util.List;

public class Account {
    public Information information;
    public List<Character> characters;
    public int numberOfGames;

    public Account(Information information, List<Character> characters, int numberOfGames) {
        this.information = information;
        this.characters = characters;
        this.numberOfGames = numberOfGames;
    }

    public static class Information {
        private final Credentials credentials;
        private final Collection<String> favouriteGames;
        private final String name, country;

        // Creaza un obiect de tip Information folosind pattern-ul Builder
        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favouriteGames = builder.favouriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        // Returneaza elementele clasei
        public Credentials getCredentials() {
            return credentials;
        }

        public Collection<String> getFavouriteGames() {
            return favouriteGames;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        // Clasa builder, pentru crearea unui obiect de tip information sunt necesare doar credentials si name
        public static class InformationBuilder {
            private final Credentials credentials;
            private final String name;
            private Collection<String> favouriteGames;
            private String country;

            public InformationBuilder(Credentials credentials, String name) {
                this.credentials = credentials;
                this.name = name;
            }

            public InformationBuilder favouriteGames(Collection<String> favouriteGames) {
                this.favouriteGames = favouriteGames;
                return this;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() throws InformationIncompleteException {
                if (this.credentials == null || this.name == null) {
                    throw new InformationIncompleteException("You have not introduced the needed data!");
                } else {
                    Information information = new Information(this);
                    return information;
                }
            }
        }
    }
}