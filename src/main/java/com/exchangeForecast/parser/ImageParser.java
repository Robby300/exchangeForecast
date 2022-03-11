package com.exchangeForecast.parser;

public class ImageParser {
    /*public File ParseImg(String href) {
        File file;

        try {
            document = Jsoup.connect(href).get();
            Elements img = document.getElementsByClass("game_header_image_full");

            try(InputStream in = new URL(img.attr("src")).openStream()){
                Files.copy(in, Paths.get("C\\images\\"));
                return new File("C\\images\\");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(IOException e){
            System.out.println("Image not found...");
            e.printStackTrace();
        }

        return null;
    }*/
}
