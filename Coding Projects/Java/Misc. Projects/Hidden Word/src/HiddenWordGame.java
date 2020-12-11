class HiddenWordGame {

    String answer;

    HiddenWordGame() {
        answer = pick();
    }

    private String pick() {
        return WordList.wordList[(int)Math.round(Math.random()*WordList.wordList.length)];
    }

    String guess(String guess) {
        String out = "";

        for (int i = 0; i < guess.length(); i++) {
            out += guess.charAt(i) == answer.charAt(i) ? guess.charAt(i) : answer.indexOf(guess.charAt(i)) == -1 ? '*' : '+';
        }

        return out;
    }
}