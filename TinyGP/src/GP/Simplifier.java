package GP;

/**
 * Take unwieldy formulas from TinyGP and simplify their syntax.
 * <p>
 * examples:
 * input: ((X1  * ( 3.2 + 3.4)) * 2) output: ((X1  * 6.6) * 2)
 * input: (X1  * ((( 3.2 + 3.4) / ( 3.3 + 3.3)) * 2)) output: (X1  * 2)
 * <p>
 * example usage:<p>
 * Simplifier s = new Simplifier();
 * String newProgram = s.simplify(oldProgram);
 *
 * @author Brint Montgomery
 * @version 1.0 1/11/15
 */
public class Simplifier {

    private String orgProg;
    private String newProg;
    private int subFormLocIndex;
    private double lfVal;
    private String ctFunction;
    private double rtVal;
    private double result;

    private int initialSubFormulaCount;
    private int programSizeReduction;


    public Simplifier() {
    } // constructor

    /**
     * Main simplification algorithm is performed here.
     *
     * @param program - an original individual program string which is output from
     *                TinyGP
     * @return a new simplified individual program string derived from the
     * original program.
     */
    public String simplify(String program) {

        orgProg = program;

        newProg = orgProg.substring(1, orgProg.length() - 1); // strip-off outlying final parentheses.

        this.initialSubFormulaCount = countSimplifiableSubFormulasInNewProgram();

        while (countSimplifiableSubFormulasInNewProgram() > 0) {  // while there are any sub formulas left...
            locateAvailableSubFormula();
            pullAllSubformulaTokens();
            calcResultOfSubformula();
            replaceSubFormulaWithItsResult();
        }

        newProg = "(" + newProg + ")"; // add in outlying final parentheses again.

        return newProg;
    }

    /**
     * Shows information about how the class simplified the original program to
     * the new program.
     */
    public String toString() {

        String newline = System.getProperty("line.separator");

        this.programSizeReduction = orgProg.length() - newProg.length();

        int percentReduced = (int) (((float) programSizeReduction / (float) orgProg.length()) * 100f);
        String str = "";

        str += "org. program size:" + this.orgProg.length() + newline;
        str += "new program size :" + this.newProg.length() + newline;
        str += "size reduction   :" + programSizeReduction + " (" + percentReduced + ")%" + newline;
        str += "initial subformula count and simplifications: " + initialSubFormulaCount + newline;
        str += "simplified program: " + newline;
        str += this.newProg + newline;
        str += "original program: " + newline;
        str += this.orgProg;

        return str;
    }

    /**
     * Move through the whole program string, and count any remaining subformulas that
     * can be simplified (details). Subformulas that can be simplified contain no X#
     * variables.
     *
     * @return An integer showing the number of subformulas that can be
     * simplified which still in the new program.
     */
    public int countSimplifiableSubFormulasInNewProgram() {

        int iFirstParen;
        int iSecondParen;
        int count = 0;

        for (int i = 0; i < this.newProg.length(); i++) { // forloop through program string,
            iFirstParen = locNextParen(newProg, i);        // find index of next parenthesis starting from i
            if (iFirstParen == -1) break;                     // if no next par's left,  break to return count.
            iSecondParen = locNextParen(newProg, iFirstParen + 1);    // find index of next parenthesis starting from i+1
            if (iSecondParen == -1) break;                    // if no next par's left,  break to return count.
            if (isSimplifiableSubFormula(iFirstParen, iSecondParen)) {
                count++;      // count it
                i = iSecondParen; // prepare to move past 2nd par to efficiently keep looking
            } else {
                i = iSecondParen - 1; // prepare to start at 2nd par to efficiently keep looking
            }
        } // next loop

        return count;
    }

    /**
     * Looks for the first available subformula which can be simplified and returns its index.
     *
     * @return integer showing index location of subformula found, or a -1 if no
     * subformula which can be simplified is found.
     */
    public int locateAvailableSubFormula() {

        int iFirstParen;
        int iSecondParen;

        for (int i = 0; i < this.newProg.length(); i++) { // for loop moves through program string
            iFirstParen = locNextParen(newProg, i);        // find index of next parenthesis starting from i
            if (iFirstParen == -1) break;                     // if no next par's left,  break to return -1
            iSecondParen = locNextParen(newProg, iFirstParen + 1);    // find index of next parenthesis starting from i+1
            if (iSecondParen == -1) break;                    // if no next par's left,  break to return count.
            if (isSimplifiableSubFormula(iFirstParen, iSecondParen)) { // can this subformula be simplified?
                subFormLocIndex = iFirstParen;              // yes, so note where that subformula starts
                return subFormLocIndex;                         // since subformula found, return location of first parenthesis
            }
            i = iSecondParen - 1;                                 // no, so reset loop to where next parenthesis was found for more checking.
        } // next loop

        subFormLocIndex = -1; // to indicate nothing found.
        return subFormLocIndex;
    }

    /**
     * Establishes if a subformula can be simplifed (details). If opening and
     * closing parentheses pair noted, then a subformula of some type has been
     * found; and, if only constants noted within, then it can be simplified. But
     * if any X# variables, then not one that can be simplified.
     *
     * @param iFirstParen  location of first parenthesis
     * @param iSecondParen location of second parenthesis
     * @return true if subformula can be simplified, false it it cannot.
     */
    public boolean isSimplifiableSubFormula(int iFirstParen, int iSecondParen) {

        boolean isOpenParFound = (newProg.charAt(iFirstParen) == '(');
        boolean isCloseParFound = (newProg.charAt(iSecondParen) == ')');
        boolean isFreeOfVars = true;

        for (int i = iFirstParen; i < iSecondParen; i++) { // search for X's
            if (newProg.charAt(i) == 'X') { // if you see any
                isFreeOfVars = false;         // then this subformula can't be simplifed.
                break;
            }
        }

        return (isOpenParFound && isCloseParFound && isFreeOfVars);
    }

    /**
     * Given a string and a starting point in it, find the next instance of
     * either a "(" or a ")", otherwise return -1 if not parentheses found.
     *
     * @param str - a program string
     * @param i   - an index on where to start searching within the string.
     * @return an integer showing the location of either an opening or closing
     * parenthesis, or a -1 if no parentheses of either kind found.
     */
    public int locNextParen(String str, int i) {

        String ss = "";
        for (int j = i; j < str.length(); j++) {      // move thru the string from wherever
            ss = str.substring(j, j + 1);                      // get a single character
            if (ss.equals(")")) return j;             // return if it's a par
            if (ss.equals("(")) return j;             // of either type
        }

        return -1; // no par's found.
    }

    /**
     * Knowing the index for a subformula, load the left value, function, and
     * right value from the identified subformula.
     */
    public void pullAllSubformulaTokens() {

        String str = newProg.substring(subFormLocIndex, locNextParen(newProg, subFormLocIndex + 1) + 1); // get subformula
        String[] tokens = str.split("[ ]+");               // split up into three parts around the spaces

        tokens[0] = tokens[0].substring(1);   // drop "("
        tokens[2] = tokens[2].substring(0, tokens[2].length() - 1);  // drop ")"

        lfVal = Double.parseDouble(tokens[0]);
        ctFunction = tokens[1];
        rtVal = Double.parseDouble(tokens[2]);
    }

    /**
     * Knowing the three parts of the subformula, calculate the overall value of that
     * subformula from those parts.
     */
    public void calcResultOfSubformula() {

        switch (ctFunction) {
            case "+":
                result = lfVal + rtVal;
                break;
            case "-":
                result = lfVal - rtVal;
                break;
            case "*":
                result = lfVal * rtVal;
                break;
            case "/":
                result = lfVal / rtVal;
                break;
            default:
                throw new IllegalArgumentException("Invalid function character found:" + ctFunction);
        }
    }

    /**
     * Take the subformula at hand and replace it with it's numerical result
     * (details). For example, (1 / 2) would be replaced by the value of .5
     */
    public void replaceSubFormulaWithItsResult() {

        String str = newProg.substring(subFormLocIndex, locNextParen(newProg, subFormLocIndex + 1) + 1); // get subformula
        newProg = newProg.replace(str, String.valueOf(result)); // replace subformula with its result.
    }


    // getter/setter methods

    public String getOrgProg() {
        return orgProg;
    }

    public void setOrgProg(String orgProg) {
        this.orgProg = orgProg;
    }

    public String getNewProg() {
        return newProg;
    }

    public void setNewProg(String newProg) {
        this.newProg = newProg;
    }

    public int getSubFormLocIndex() {
        return subFormLocIndex;
    }

    public void setSubFormLocIndex(int subFormLocIndex) {
        this.subFormLocIndex = subFormLocIndex;
    }

    public double getLfVal() {
        return lfVal;
    }

    public void setLfVal(double lfVal) {
        this.lfVal = lfVal;
    }

    public String getCtFunction() {
        return ctFunction;
    }

    public void setCtFunction(String ctFunction) {
        this.ctFunction = ctFunction;
    }

    public double getRtVal() {
        return rtVal;
    }

    public void setRtVal(double rtVal) {
        this.rtVal = rtVal;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getInitialSubFormulaCount() {
        return initialSubFormulaCount;
    }

    public void setInitialSubFormulaCount(int initialSubFormulaCount) {
        this.initialSubFormulaCount = initialSubFormulaCount;
    }


    public int getProgramSizeReduction() {
        return programSizeReduction;
    }

    public void setProgramSizeReduction(int programSizeReduction) {
        this.programSizeReduction = programSizeReduction;
    }

} // end class


