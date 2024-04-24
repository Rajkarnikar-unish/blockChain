import com.google.gson.GsonBuilder;

import java.time.Instant;
import java.util.*;

public class Main {

    public static ArrayList<Block> blockChain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static void main(String[] args) {
        System.out.println(Instant.now());
        blockChain.add(new Block("Hi! this is first block", "0"));
        System.out.println(blockChain.get(0).hash);
        System.out.println("Trying to mine block 1...");
        blockChain.get(0).mineBlock(difficulty);

        blockChain.add(new Block("Second block here", blockChain.get(blockChain.size()-1).hash));
        System.out.println("Trying to mine block 2...");
        blockChain.get(1).mineBlock(difficulty);

        blockChain.add(new Block("Third block here", blockChain.get(blockChain.size()-1).hash));
        System.out.println("Trying to mine block 3...");
        blockChain.get(2).mineBlock(difficulty);
        System.out.println(Instant.now());

        System.out.println("\nBlockchain is valid:" + isChainValid());

        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockChainJson);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i=1; i<blockChain.size();i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes are not equal");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes are not equal");
                return false;
            }
            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}