
public class turingmachine {
    static int bandIndex;

    zustand status;
    char[] band;
    String input;

    final zustand acceptableEnd;

    public turingmachine(String input){
        this.input = input;
        status = zustand.q0;
        acceptableEnd = zustand.q9;
        // length of the band should be at least 2 bigger than the input
        // because Blanks at the end and beginning
        band = new char[input.length() + 2];
    }

    public void initializeMachine(){
        band[0] = 'B';
        band[band.length -1] = 'B';
        for(int i = 0; i < input.length(); i++){
            band[i+1] = input.charAt(i);
        }
    }

    public void printBand(){
        for(int i = 0; i < bandIndex; i++){
            System.out.print(band[i]);
        }
        System.out.print("[" + status + "]");
        for(int i = bandIndex; i < band.length; i++){
            System.out.print(band[i]);
        }
        System.out.println();
    }

    public zustand getStatus(){return this.status;}

    public void setStatus(zustand status) {
        this.status = status;
    }

    public boolean run(){
        bandIndex = 1;

        while(status != acceptableEnd) {
            printBand();
            switch (status) {
                case q0:
                    if (band[bandIndex] == '0') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q2);
                        bandIndex++;
                    } else if (band[bandIndex] == '1') {
                        setStatus(zustand.q1);
                        band[bandIndex] = 'X';
                        bandIndex++;
                    } else {
                        return false;
                    } break;

                case q1:
                    if (band[bandIndex] == '0' || band[bandIndex] == '1') {
                        bandIndex++;
                    } else if (band[bandIndex] == '#') {
                        bandIndex++;
                        setStatus(zustand.q3);
                    } else {
                        return false;
                    }break;

                case q2:
                    if (band[bandIndex] == '0' || band[bandIndex] == '1') {
                        bandIndex++;
                    } else if (band[bandIndex] == '#') {
                        bandIndex++;
                        setStatus(zustand.q4);
                    } else {
                        return false;
                    }break;

                case q3:
                    if (band[bandIndex] == '1') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q5);
                        bandIndex--;
                    } else if (band[bandIndex] == 'X') {
                        bandIndex++;
                    }
                    else{return false;}
                    break;

                case q4:
                    if (band[bandIndex] == '0') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q5);
                        bandIndex--;
                    } else if (band[bandIndex] == 'X') {
                        bandIndex++;
                    }
                    else{
                        return false;
                    }break;

                case q5:
                    if(band[bandIndex] == 'X'){
                        bandIndex--;
                    }
                    else if(band[bandIndex] == '#'){
                        bandIndex--;
                        setStatus(zustand.q6);
                    }
                    else{
                        return false;
                    }break;

                case q6:
                    if (band[bandIndex] == '0' || band[bandIndex] == '1') {
                        bandIndex--;
                    } else if (band[bandIndex] == 'X') {
                        setStatus(zustand.q7);
                        bandIndex++;
                    } else {
                        return false;
                    }break;

                case q7:
                    if (band[bandIndex] == '0') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q2);
                        bandIndex++;
                    } else if (band[bandIndex] == '1') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q1);
                        bandIndex++;
                    }
                    else if(band[bandIndex] == '#'){
                        bandIndex++;
                        setStatus(zustand.q8);
                    }
                    else{
                        return false;
                    }break;

                case q8:
                    if(band[bandIndex] == 'B'){
                        band[bandIndex] = 'B';
                        setStatus(zustand.q9);
                    }
                    else if(band[bandIndex] == 'X'){
                        bandIndex++;
                    }
                    else{
                        return false;
                    }break;
            }
        }

        return true;
    }

    public static void main(String[] argv){
        turingmachine t = new turingmachine("01#01");
        t.initializeMachine();
        System.out.println(t.run());
    }
}
