package app.services;

public class CarportSvg {

    private int width;
    private int length;
    private Svg carportSvg;

    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(0,0,"0 0 855 690", "75%"); //insteriser en ny svg tegning
        carportSvg.addRectangle(0,0,600,780,"stroke-width:1px; stroke: #000000; fill: white");
        addBeams();
        addRafters();
        addPost();
    }

    private void addBeams(){
        //Remme
        carportSvg.addRectangle(0,35,4.5,780,"stroke-width:1px; stroke: #000000; fill: white");
        carportSvg.addRectangle(0,565,4.5,780,"stroke-width:1px; stroke: #000000; fill: white");
    }

    private void addRafters(){
        for(double i = 0; i < 780; i += 55.714){
            carportSvg.addRectangle(i,0,600,4.5,"stroke: black; fill: white");
        }
    }

    private void addPost(){
        for(double i =110; i < 780; i+= 310){
            carportSvg.addRectangle(i,35,9.7,10,"stroke: black; fill: white");
            carportSvg.addRectangle(i,565,9.7,10,"stroke: black; fill: white");
        }
    }


    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
