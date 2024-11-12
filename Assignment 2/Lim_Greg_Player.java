

class Lim_Greg_Player extends Player {//extends Player
    // ====== Key observations from testing ======
    // All explanation, theory and proves are in the report
    // 1. Friendly is good
    // 2. Friendly defect: CD -> C is better than CD -> D
    // 3. Last round defecting is good
    // 4. Last rounds defecting can be good
    //      - the more players, the later to start defecting
    // 5. For last rounds defecting, fix start defecting is better than random start deffecting

    // pay out matix
    // ME |   C   ||   D   |
    //    | C | D || C | D |
    //  C | 6 | 3 || 8 | 5 |
    //  D | 3 | 0 || 5 | 2 |


    // ========== Stratergy ==========
    // IF round 0       :   C (friendly strategy) 
    // IF round is 109  :   D 
    // IF round > 107   :   D (109 is the last round, fix defect strategy, late defect strategy) 
    // IF player 1 defect more than 40% and player 2 defect more than 40%   :   D (protect against random and always defect) 
    // IF both defect   :   D (friendly defect strategy, do not defect if only 1 defect) 
    // ELSE             :   C (friendly strategy) 

    // ========== PARAMETERS TO TUNE ==========
    // when to start defacting
    // testing suggesting defecting only the last 2 rounds to be the most effective
    // defecting early make the score less consistent and is harmful
    final int DTime = 108-1; // 109 -> defact on last round

    // ========== DEPRICATED PARAMETERS ==========
    // end game start
    // tested with have a different atratergy in the last 20 rounds
    final int endGame = 95-1;

    // Number of chances to give before becomeing FTFT -> TFT
    // tested have a friendlyness metter
    int friendlyness = 5; //0->TFT

    int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
        // Friendly
        if (n==0) return 0;

        // Last round defect: defect with no punnishment
        if (n >= 109) return 1;

        // Defecting Time: defect with minimal punnishment
        if (n > DTime) return 1;

        // To detect if both palyers are defecting ofter. could suggest a random
        // not really impt
        if(n>5){
            int oppNumDefections1=0;
            int oppNumDefections2=0;
            for (int index = 0; index < n; ++index) {
                oppNumDefections1 += oppHistory1[index];
                oppNumDefections2 += oppHistory2[index];
            }
            if(oppNumDefections1/n > 0.4 && oppNumDefections2/n > 0.4) return 1;
        }

        // Assume if both defect, it connot be recovered
        // Friendly defect, only D when both D
        if (oppHistory1[n-1] == 1 && oppHistory2[n-1] == 1) return 1;

        // Friendly default
        return 0;
    }	
}