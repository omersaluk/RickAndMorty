package com.example.rickandmortyapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {

        public CharacterResponse(List<Character> results) {
            this.results = results;
        }

        private List<Character> results;

        public List<Character> getResults() {
            return results;
        }

        public void setResults(List<Character> results) {
            this.results = results;
        }

}
