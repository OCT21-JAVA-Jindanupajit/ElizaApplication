import java.util.*;

public class ElizaApplication {
    static Scanner kb = new Scanner(System.in);
    static HashMap<String, String> wordList = new HashMap<>();
    static ArrayList<String> hedges= new ArrayList<>();
    static ArrayList<String> qualifiers = new ArrayList<>();

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
    }

    public static void run() {
        String input;
        boolean quitRequested = false;

        System.out.println("Good day. What is your problem? ");
        do {
            System.out.print("Enter your response here or Q to quit: ");
            input = nextLine();
            if (quitRequested = input.equalsIgnoreCase("Q"))
                break;

            System.out.println(
                    ((new Random()).nextInt(2) == 0)?
                        RespondWithHedge(input):
                        RespondWithQualifier(input)
                    );
        }
        while(true);

        System.out.println(">>> END");
    }

    public static String RespondWithHedge(String input) {
        return hedges.get((new Random()).nextInt(hedges.size()));
    }

    public static String RespondWithQualifier(String input) {
        return qualifiers.get((new Random()).nextInt(qualifiers.size()))
                +" "+replace(input);
    }

    public static String replace(String input) {

        ArrayList<String> replaced =  replace(tokenization(input));
        StringBuilder newString = new StringBuilder();
        while(!replaced.isEmpty()) {
            newString.append(replaced.remove(0));
            if (!replaced.isEmpty()) {
                newString.append(" ");
            }
        }
        return newString.toString();
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

    private static String nextLine() {
        return kb.nextLine();
    }

    private static ArrayList<String> tokenization(String input) {
        return new ArrayList<String>(Arrays.asList(input.split(" ")));
    }
}
