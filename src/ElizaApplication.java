import java.util.*;

public class ElizaApplication {
    static Scanner kb = new Scanner(System.in);
    static HashMap<String, String> wordList = new HashMap<>();
    static ArrayList<String> hedges= new ArrayList<>();
    static ArrayList<String> qualifiers = new ArrayList<>();
    static ArrayList<String> vowelEnding = new ArrayList<>();
    static boolean pigMode = false;
    static boolean capMode = false;
    static boolean redMode = false;
    static ArrayList<String> log = new ArrayList<>();

    public static void main(String[] args) {
        init();
        run();

    }

    public static void init() {
        wordList.put("i", "you");
        wordList.put("me", "you");
        wordList.put("my", "your");
        wordList.put("am", "are");

        hedges.add("Please tell me more");
        hedges.add("Many of my patients tell me the same thing");
        hedges.add("It is getting late, maybe we had better quit");

        qualifiers.add("Why do you say that");
        qualifiers.add("You seem to think that");
        qualifiers.add("So, you are concerned that");

        vowelEnding.add("way");
        vowelEnding.add("tay");
        vowelEnding.add("yay");
    }

    public static void run() {
        String input;
        boolean quitRequested = false;

        System.out.println("Good day. What is your problem? ");
        do {
            System.out.print("Enter your response here or Q to quit: ");
            input = nextLine();
            log.add("  <you> "+input);
            if (quitRequested = input.equalsIgnoreCase("Q")) {
                log.add("*** Quitting");
                break;
            }

            if (input.equalsIgnoreCase("pig")) {
                pigMode = true;
                System.out.println("Notice: "+pig("PIG LATIN MODE"));
                log.add("*** Pig mode enabled");
                continue;
            }

            if (input.equalsIgnoreCase("cap")) {
                capMode = true;
                System.out.println("Notice: CAP MODE");
                log.add("*** Cap mode enabled");
                continue;
            }

            if (input.equalsIgnoreCase("red")) {
                redMode = true;
                System.out.println("Notice: "+"\u001B[91m"+"RED MODE!"+"\u001B[0m");
                log.add("*** Red mode enabled");
                continue;
            }

            if (input.equalsIgnoreCase("play game")) {
                redMode = true;
                System.out.println("Notice: Play game!");
                log.add("*** play game");
                GameApplication.main(null);
                System.out.println("Notice: Game end!");
                continue;
            }


            String response = ((new Random()).nextInt(2) == 0)?
                    RespondWithHedge(input):
                    RespondWithQualifier(input);

            if (capMode) response = response.toUpperCase();

            if (redMode) response = "\u001B[91m"+response+"\u001B[0m";

            log.add("<Eliza> "+response);
            System.out.println(response);
        }
        while(true);

        System.out.println(">>> END");
        System.out.println("=== Chat log ===");
        for (String line : log) {
            System.out.println(line);
        }

    }

    public static String RespondWithHedge(String input) {
        String response =  hedges.get((new Random()).nextInt(hedges.size()));
        return (pigMode?pig(response):response);
    }

    public static String RespondWithQualifier(String input) {
        String response =   qualifiers.get((new Random()).nextInt(qualifiers.size()))
                +" "+replace(input);
        return (pigMode?pig(response):response) ;
    }

    public static String replace(String input) {
        return arrayToString(replace(tokenization(input)));
    }

    public static ArrayList<String> replace(ArrayList<String> input) {
        ArrayList<String> newString = new ArrayList<>();
        for (String token : input) {
            if (wordList.containsKey(token.toLowerCase())) {
                String replacement = wordList.get(token.toLowerCase());
                if (Character.isUpperCase(token.charAt(0))) {
                    replacement = replacement.substring(0,1).toUpperCase()+((replacement.length()>1)?replacement.substring(1):"");
                }
                newString.add(replacement);

            }
            else {
                newString.add(token);
            }
        }
        return newString;
    }


    public static String pig(String input) {
        return arrayToString(pig(tokenization(input)));
    }

    public static ArrayList<String> pig(ArrayList<String> input) {
        ArrayList<String> newString = new ArrayList<>();
        for (String token : input) {
            String replacement = pigReplaceWord(token);
            newString.add(replacement);
        }
        return newString;
    }

    private static String pigReplaceWord(String word) {

        if (word.length() <= 0) {
            return "";
        }


        String newWord = swapAtFirstVowel(word);

        if (newWord.equals(word))
            return vowel(word);
        else
            return newWord + "ay";

    }

    private static String vowel(String word) {

        return word+vowelEnding.get((new Random()).nextInt(vowelEnding.size()));

    }

    private static String nextLine() {
        return kb.nextLine();
    }

    private static ArrayList<String> tokenization(String input) {
        return new ArrayList<String>(Arrays.asList(input.split(" ")));
    }

    private static String swapAtFirstVowel(String input) {
        if (input.length() <= 0) return "";
        ArrayList<String> vowelList = new ArrayList<>(Arrays.asList("A","a","E","e","I","i","O","o","U","u"));
        int index = -1;

        for (String v : vowelList) {
            index = input.indexOf(v);
            if (index > 0) {
                return input.substring(index) + input.substring(0,index);
            }
            else if (index == 0) {
                break;
            }
        }

        return input;
    }

    private static String arrayToString(ArrayList<String> input) {
        return arrayToString(input, " ");
    }
    private static String arrayToString(ArrayList<String> input, String  separator) {
        StringBuilder newString = new StringBuilder();
        while(!input.isEmpty()) {
            newString.append(input.remove(0));
            if (!input.isEmpty()) {
                newString.append(separator);
            }
        }
        return newString.toString();
    }
}
