
public class turingmachine {
    static int bandIndex;

    zustand status;
    char[] band;
    String input;

    final zustand acceptableEnd;

    public turingmachine(String input){
        this.input = input;
        status = zustand.q_0;
        acceptableEnd = zustand.q_9;
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
        for(int i = 1; i < bandIndex; i++){
            if(band[i] == '#'){
                System.out.print("\\" + band[i]);
                continue;
            }
            System.out.print(band[i]);
        }
        System.out.print("$" + status + "$");
        for(int i = bandIndex; i < band.length-1; i++){
            if(band[i] == '#'){
                System.out.print("\\" + band[i]);
                continue;
            }
            System.out.print(band[i]);
        }
        System.out.print(" $\\vdash$ ");
    }

    public void setStatus(zustand status) {
        this.status = status;
    }

    public boolean run(){
        bandIndex = 1;

        while(status != acceptableEnd) {
            printBand();
            switch (status) {
                case q_0:
                    if (band[bandIndex] == '0') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q_2);
                        bandIndex++;
                    } else if (band[bandIndex] == '1') {
                        setStatus(zustand.q_1);
                        band[bandIndex] = 'X';
                        bandIndex++;
                    } else {
                        return false;
                    } break;

                case q_1:
                    if (band[bandIndex] == '0' || band[bandIndex] == '1') {
                        bandIndex++;
                    } else if (band[bandIndex] == '#') {
                        bandIndex++;
                        setStatus(zustand.q_3);
                    } else {
                        return false;
                    }break;

                case q_2:
                    if (band[bandIndex] == '0' || band[bandIndex] == '1') {
                        bandIndex++;
                    } else if (band[bandIndex] == '#') {
                        bandIndex++;
                        setStatus(zustand.q_4);
                    } else {
                        return false;
                    }break;

                case q_3:
                    if (band[bandIndex] == '1') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q_5);
                        bandIndex--;
                    } else if (band[bandIndex] == 'X') {
                        bandIndex++;
                    }
                    else{return false;}
                    break;

                case q_4:
                    if (band[bandIndex] == '0') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q_5);
                        bandIndex--;
                    } else if (band[bandIndex] == 'X') {
                        bandIndex++;
                    }
                    else{
                        return false;
                    }break;

                case q_5:
                    if(band[bandIndex] == 'X'){
                        bandIndex--;
                    }
                    else if(band[bandIndex] == '#'){
                        bandIndex--;
                        setStatus(zustand.q_6);
                    }
                    else{
                        return false;
                    }break;

                case q_6:
                    if (band[bandIndex] == '0' || band[bandIndex] == '1') {
                        bandIndex--;
                    } else if (band[bandIndex] == 'X') {
                        setStatus(zustand.q_7);
                        bandIndex++;
                    } else {
                        return false;
                    }break;

                case q_7:
                    if (band[bandIndex] == '0') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q_2);
                        bandIndex++;
                    } else if (band[bandIndex] == '1') {
                        band[bandIndex] = 'X';
                        setStatus(zustand.q_1);
                        bandIndex++;
                    }
                    else if(band[bandIndex] == '#'){
                        bandIndex++;
                        setStatus(zustand.q_8);
                    }
                    else{
                        return false;
                    }break;

                case q_8:
                    if(band[bandIndex] == 'B'){
                        band[bandIndex] = 'B';
                        setStatus(zustand.q_9);
                    }
                    else if(band[bandIndex] == 'X'){
                        bandIndex++;
                    }
                    else{
                        return false;
                    }break;
            }
        }

        printBand();
        return true;
    }

    public static void main(String[] argv){
        turingmachine t = new turingmachine("01#01");
        t.initializeMachine();
        System.out.println(t.run());
    }
}
