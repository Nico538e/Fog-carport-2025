package app.services;

public class Svg {

    private static final String SVG_Template = "    <svg version=\"1.1\"\n" +
            "         x=\"%d\"\n" +
            "         y=\"%d\"\n" +
            "         viewBox=\"%s\"\n" +
            "         width=\"%s\"\n" +
            "         preserveAspectRatio=\"xMinYMin\">"; //bevare sin form, den skævrider sig ikke

    private static final String SVG_ARROW_DEFS="<defs>\n" +
            "        <marker\n" +
            "                id=\"beginArrow\"\n" +
            "                markerWidth=\"12\"\n" +
            "                markerHeight=\"12\"\n" +
            "                refX=\"0\"\n" +
            "                refY=\"6\"\n" +
            "                orient=\"auto\">\n" +
            "            <path d=\"M0,6 L12,0 L12,12 L0,6\" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "        <marker\n" +
            "                id=\"endArrow\"\n" +
            "                markerWidth=\"12\"\n" +
            "                markerHeight=\"12\"\n" +
            "                refX=\"12\"\n" +
            "                refY=\"6\"\n" +
            "                orient=\"auto\">\n" +
            "            <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "    </defs>";

    private static final String SVG_LINE_TEMPLATE = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\" />";
    private static final String SVG_TEXT_TEMPLATE = "    <text style=\"%s\" transform=\"translate(25,300) rotate(-90)\">";
    private static final String SVG_RECT_TEMPLATE = "<rect x=\"%f\" y=\"%f\" height=\"%f\" width=\"%f\" style=\"%s\"/>";
    //%d= tal, %s=text, %f=floating-point, %.2f=floating-points med 2 dicimaler
    private StringBuilder svg = new StringBuilder();



    public Svg(int x, int y, String viewBox, String width){

        svg.append(String.format(SVG_Template, x, y, viewBox, width));
        svg.append(SVG_ARROW_DEFS);
    }

    public void addRectangle(double x, double y, double height, double width, String style){
        svg.append(String.format(SVG_RECT_TEMPLATE, x, y, height, width, style ));
    }

    public void addLine(int x1, int y1, int x2, int y2, String style){
        svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));
    }

    public void addArrow(int x1, int y1, int x2, int y2, String style){

        svg.append(String.format(SVG_LINE_TEMPLATE,x1, y1, x2, y2, style));
// kald addLine med en style indenholder  pilehovredr style="stroke: black;
//                 stroke-width: 1.2;
//                 marker-start: url(#beginArrow);
//                 marker-end: url(#endArrow);"
    }

    public void addText(int x, int y, int rotation, String text){


    }

    public void addSvg(Svg innerSvg){
        svg.append(innerSvg.toString());
    }

    @Override
    public String toString() {
        return svg.append("</svg").toString();
    }
}
