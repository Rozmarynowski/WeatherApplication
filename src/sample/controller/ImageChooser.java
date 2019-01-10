package sample.controller;

import javafx.scene.image.Image;

/**
 *
 * Class to chooses image of weather
 *
 */


public class ImageChooser {

    private String weatherImage;
    private Image image;

    /**
     *
     * Sets value of string weatherImage
     * @param weatherImage
     */

    public void setImageChoose(String weatherImage){
        this.weatherImage = weatherImage;
    }


    /**
     *
     * Chooses image to the current weather
     * @return object of Image class
     */

    public Image getImageChoose(){


        switch (weatherImage) {

            case "clear sky":

                    image = new Image(getClass().getResource("images/ClearSky.png").toExternalForm());



                break;

            case "few clouds":

                image = new Image(getClass().getResource("images/PartlyCloudly.png").toExternalForm());


                break;

            case "scattered clouds":
            case "broken clouds":

                image = new Image(getClass().getResource("images/OverCast.png").toExternalForm());


                break;

            case "shower rain":

                image = new Image(getClass().getResource("images/ShowerRain.png").toExternalForm());


                break;

            case "light rain":
            case "moderate rain":

                image = new Image(getClass().getResource("images/Rain.png").toExternalForm());


                break;


            case "thunderstorm":

                image = new Image(getClass().getResource("images/Thunder.png").toExternalForm());


                break;

            case "mist":

                image = new Image(getClass().getResource("images/Mist.png").toExternalForm());


                break;

            case "snow":

                image = new Image(getClass().getResource("images/Snow.png").toExternalForm());


                break;

            default:

               image = new Image(getClass().getResource("images/Overcast.png").toExternalForm());

        }
      return image;
    }
}
