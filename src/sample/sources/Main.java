package sample.sources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.controllers.MainFxmlController;
import sample.utils.Lang;
import sample.utils.LocaleManager;
import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Main extends Application implements Observer {

    private static final String FXML_MAIN = "../fxml/main_fxml.fxml";
    public static final String BUNDLES_FOLDER = "sample.bundles.Locale";

    private Stage primaryStage;
    private MainFxmlController controller;
    private FXMLLoader fxmlLoader;
    private VBox currentRoot;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        createGUI(LocaleManager.RU_LOCALE);
    }

    private void createGUI(Locale locale) {
        currentRoot = loadFXML(locale);
        primaryStage.setMinWidth(433);
        primaryStage.setMinHeight(387);
        Scene scene = new Scene(currentRoot);//, 470, 400);
        primaryStage.getIcons().add(new Image("sample/img/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        Lang lang = (Lang) arg;
        VBox newNode = loadFXML(lang.getLocale());
        currentRoot.getChildren().setAll(newNode.getChildren());
    }

    private VBox loadFXML(Locale locale)
    {
        fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource(FXML_MAIN));
        fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));

        VBox node = null;

        try
        {
            node = (VBox)fxmlLoader.load();

            controller = fxmlLoader.getController();
            controller.addObserver(this);
            primaryStage.setTitle(fxmlLoader.getResources().getString("key.listOfEmployees"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return node;
    }
}
