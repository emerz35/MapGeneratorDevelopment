package generation.namegenerators;

import main.Utils;

/**
 *
 * @author Tuesday
 */
public class NameGenerator {
    
    private final String[] onset = {"w", "r", "t", "y", "p", "s", "d", "f", "g", "h", "j", "k", "l", "z", "c", "v", "b", "n", "m", "sh", "ch",
                            "pl", "bl", "cl", "kl", "gl", "pr", "br", "tr", "dr", "cr", "kr", "qu", "pu", "gu", "thr", "fl", "sl",
                            "fr", "shr", "wh", "sw", "sp", "st", "sk", "sm", "sn", "sph", "spl", "scl", "spr", "str", "scr", "squ", ""},
            nucleus = {"a", "e", "i", "o", "u", "y", "oo", "ei", "ie", "eu", "ee", "oi", "ai", "ou"},
            coda = { "r", "t", "p", "s", "sh", "d", "f", "g", "ng", "j", "k", "l", "x", "ch", "ve", "b", "n", "m", "lp", "lb", "lt",
                    "ld", "lch", "ldge", "lge", "lk", "rp", "rb", "rt", "rch", "rge", "rk", "lf", "lve", "lth", "ls",
                    "lse", "lsh", "rf", "rve", "rth", "rce", "rs", "rsh", "lm", "ln", "rm", "rn", "rl", "mp", "nt", "nd", "nch", "nge", "ndge",
                    "nk", "mph", "nth", "mth", "ns", "nce", "nze", "ft", "sp", "st", "sk", "fth", "pt", "ct", "pth", "pse", "ps", 
                    "dth", "lps", "lst", "rmth", "rpt", "rps", "rpse", "rtz", "rct", "mpt", "mpse", "nct", "xt",""};
    
    
    public String generateName(){
        int syllableNum = Utils.randInt(1, 3);
        String ret = "";
        for(int i = 0; i<syllableNum;i++){
            ret+=onset[Utils.randInt(0, onset.length)]+nucleus[Utils.randInt(0, nucleus.length)]+coda[Utils.randInt(0, coda.length)];
        }
        return ret.substring(0,1).toUpperCase()+ ret.substring(1);
    }
    
}
