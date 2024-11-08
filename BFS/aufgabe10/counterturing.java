public class counterturing {
    static int bandIndex;

    status zustand;
    char[] band;
    String input;

    final status acceptableEnd;
    public static int iteration;

    public counterturing(String input){
        this.input = input;
        this.zustand = status.q_0;
        acceptableEnd = status.q_end;

        band = new char[input.length() + 4];
    }

    public void initializeMachine(){
        band[0] = 'B';
        band[1] = 'B';
        band[band.length - 1] = 'B';
        band[band.length - 2] = 'B';
        for(int i = 0; i < input.length(); i++){
            band[i+2] = input.charAt(i);
        }
    }

    public void printBand() {
        // if only the counter outputs should be printed, add this code:
//        if(zustand != status.q_0){
//            return;
//        }
        for (int i = 1; i < bandIndex; i++) {
            if(band[i] == 'B'){
                continue;
            }
            System.out.print(band[i]);
        }
        System.out.print("(" + this.zustand + ")");
        for (int i = bandIndex; i < band.length; i++) {
            if(band[i] == 'B'){
                continue;
            }
            System.out.print(band[i]);
        }
        System.out.println();
        System.out.println();
    }

    public void setZustand(status zustand){
        this.zustand = zustand;
    }

    public void run(){
        bandIndex = 2;

        while(zustand != acceptableEnd){
            printBand();
            iteration++;

            switch (zustand){
                case q_0:
                    if(band[bandIndex] == '0'){
                        band[bandIndex] = 'B';
                        bandIndex++;
                    }
                    else if(band[bandIndex] == '1'){
                        setZustand(status.q_1);
                        bandIndex++;
                    }
                    else{
                        setZustand(status.q_end);
                    }
                    break;

                case q_1:
                    if(band[bandIndex] == '0'){
                        bandIndex++;
                    }
                    else if(band[bandIndex] == '1'){
                        bandIndex++;
                    }
                    else{
                        setZustand(status.q_2);
                        bandIndex--;
                    }
                    break;

                case q_2:
                    if(band[bandIndex] == '0'){
                        setZustand(status.q_4);
                        band[bandIndex] = '1';
                        bandIndex--;
                    }
                    else if(band[bandIndex] == '1'){
                        setZustand(status.q_3);
                        band[bandIndex] = '0';
                        bandIndex--;
                    }
                    else{return;}
                    break;

                case q_3:
                    if(band[bandIndex] == '0'){
                        bandIndex--;
                    }
                    else if(band[bandIndex] == '1'){
                        bandIndex--;
                    }
                    else{
                        setZustand(status.q_0);
                        bandIndex++;
                    }
                    break;

                case q_4:
                    if(band[bandIndex] == '0'){
                        band[bandIndex] = '1';
                        bandIndex--;
                    }
                    else if(band[bandIndex] == '1'){
                        setZustand(status.q_3);
                        band[bandIndex] = '0';
                        bandIndex--;
                    }
                    else{
                        return;
                    }
                    break;

            }
        }
    }

    public static void main(String[] argv){
        counterturing t = new counterturing("1111");
        t.initializeMachine();
        t.run();
        System.out.println(iteration);

    }
}
