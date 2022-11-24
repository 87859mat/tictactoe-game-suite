package numerical;

import boardgame.Grid;

public class NumericalGrid extends Grid{ 
    public NumericalGrid() {
        super(3, 3);
    }

    @Override
    public String getStringGrid() {
        String gridString = "";

        for(int i = 1; i <= 3; i++) {
            gridString += this.getValue(1, i);
            gridString += "|" + this.getValue(2, i) + "|";
            gridString += this.getValue(3, i);
            if(i < 3) {
                gridString += "\n-+-+-\n";
            }
        }
        return gridString;
    }


}
